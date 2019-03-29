package barlugofx.app;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import barlugofx.model.imagetools.Image;
import barlugofx.model.parallelhandler.ParallelFilterExecutor;
import barlugofx.model.tools.BlackAndWhite;
import barlugofx.model.tools.Brightness;
import barlugofx.model.tools.Contrast;
import barlugofx.model.tools.HSBModifier;
import barlugofx.model.tools.Rotator;
import barlugofx.model.tools.SelectiveRGBChanger;
import barlugofx.model.tools.Vibrance;
import barlugofx.model.tools.WhiteBalance;
import barlugofx.model.tools.common.ImageTool;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParameterImpl;
import barlugofx.model.tools.common.ParametersName;
import barlugofx.utils.Format;

/**
 * The main controller class.
 */
public final class AppManagerImpl implements AppManager {
    //multipliers used to adapt the input from the view to the model
    private static final float HSB_MULTIPLIER = 0.01f;
    private static final float WB_MULTIPLIER = 0.015f;
    private static final float VIBRANCE_MULTIPLIER = 0.01f;
    private static final float BW_MULTIPLIER = 0.004f;
    private static final float BW_SHIFTER = 0.8f;

    private Image image;
    //tools
    private final ParallelizableImageTool hsb;
    private final ParallelizableImageTool contrast;
    private final ParallelizableImageTool brightness;
    private final ImageTool wb;
    private final ParallelizableImageTool srgb;
    private final ParallelizableImageTool bw;
    //private final Cropper cropper;
    private final ImageTool rotator;
    private final ParallelizableImageTool vibrance;
    private final IOManager fileManager;
    private final ParallelFilterExecutor executor;
    private final boolean parallel;
    /**
     * The constructor of the class. It takes the input file chosen by the user and initiates all the elements
     * @param file the input file
     * @throws IOException if the file opening fails
     */
    public AppManagerImpl(final File file) throws IOException {
        fileManager = new IOManagerImpl();
        image = fileManager.loadImageFromFile(file);
        hsb = HSBModifier.createHSB();
        contrast = Contrast.createContrast();
        brightness = Brightness.createBrightness();
        wb = WhiteBalance.createWhiteBalance();
        srgb = SelectiveRGBChanger.createSelective();
        bw = BlackAndWhite.createBlackAndWhite();
        //cropper = Cropper.createCropper();
        rotator = Rotator.createRotator();
        vibrance = Vibrance.createVibrance();
        executor = ParallelFilterExecutor.executor();
        parallel = ParallelFilterExecutor.shouldYouParallelize(image);
    }
    @Override
    public Image getImage() {
        return image;
    }
    @Override
    public String getInputFileName() {
        return fileManager.getFileName();
    }

    @Override
    public void setExposure(final int value) {
       hsb.addParameter(ParametersName.EXPOSURE, new ParameterImpl<Float>(value * HSB_MULTIPLIER));
       if (parallel) {
           image = executor.applyTool(hsb, image);
       } else {
           image = hsb.applyFilter(image);
       }
       hsb.removeParameter(ParametersName.EXPOSURE);
    }

    @Override
    public void setContrast(final int value) {
        contrast.addParameter(ParametersName.CONTRAST, new ParameterImpl<Integer>(value));
        if (parallel) {
            image = executor.applyTool(contrast, image);
        } else {
            image = contrast.applyFilter(image);
        }
        contrast.removeParameter(ParametersName.CONTRAST);
    }

    @Override
    public void setBrightness(final int value) {
        brightness.addParameter(ParametersName.BRIGHTNESS, new ParameterImpl<Integer>(value));
        if (parallel) {
            image = executor.applyTool(brightness, image);
        } else {
            image = brightness.applyFilter(image);
        }
        brightness.removeParameter(ParametersName.BRIGHTNESS);
    }

    @Override
    public void setWB(final int value) {
        wb.addParameter(ParametersName.WHITEBALANCE, new ParameterImpl<Float>(value * WB_MULTIPLIER));
        image = wb.applyFilter(image);
        wb.removeParameter(ParametersName.WHITEBALANCE);
    }

    @Override
    public void setSaturation(final int value) {
        hsb.addParameter(ParametersName.SATURATION, new ParameterImpl<Float>(value * HSB_MULTIPLIER));
        if (parallel) {
            image = executor.applyTool(hsb, image);
        } else {
            image = hsb.applyFilter(image);
        }
        hsb.removeParameter(ParametersName.SATURATION);
    }

    @Override
    public void setHue(final int value) {
        hsb.addParameter(ParametersName.HUE, new ParameterImpl<Float>(value * HSB_MULTIPLIER));
        if (parallel) {
            image = executor.applyTool(hsb, image);
        } else {
            image = hsb.applyFilter(image);
        }
        hsb.removeParameter(ParametersName.HUE);
    }

    @Override
    public void setVibrance(final int value) {
        vibrance.addParameter(ParametersName.VIBRANCE_INCREMENT, new ParameterImpl<Float>(value * VIBRANCE_MULTIPLIER));
        if (parallel) {
            image = executor.applyTool(vibrance, image);
        } else {
            image = vibrance.applyFilter(image);
        }
        vibrance.removeParameter(ParametersName.VIBRANCE_INCREMENT);
    }

    @Override
    public void setSC(final int r, final int g, final int b) {
        //TODO make it possible to change only one of these.
        srgb.addParameter(ParametersName.RED, new ParameterImpl<Integer>(r));
        srgb.addParameter(ParametersName.GREEN, new ParameterImpl<Integer>(g));
        srgb.addParameter(ParametersName.BLUE, new ParameterImpl<Integer>(b));
        if (parallel) {
            image = executor.applyTool(srgb, image);
        } else {
            image = srgb.applyFilter(image);
        }
        srgb.removeParameter(ParametersName.RED);
        srgb.removeParameter(ParametersName.GREEN);
        srgb.removeParameter(ParametersName.BLUE);
    }

    @Override
    public void setBW(final double r, final double g, final double b) {
        bw.addParameter(ParametersName.WRED, new ParameterImpl<Double>(r * BW_MULTIPLIER + BW_SHIFTER));
        bw.addParameter(ParametersName.WGREEN, new ParameterImpl<Double>(g * BW_MULTIPLIER + BW_SHIFTER));
        if (parallel) {
            image = executor.applyTool(bw, image);
        } else {
            image = bw.applyFilter(image);
        }
        bw.removeParameter(ParametersName.WRED);
        bw.removeParameter(ParametersName.WGREEN);
        bw.removeParameter(ParametersName.WBLUE);
    }

    @Override
    public void exportImage(final File file, final Format format) throws IOException, InterruptedException, ExecutionException {
        fileManager.exportImage(image, file, format);
    }

    @Override
    public void exportImage(final File file, final float quality) throws IOException, InterruptedException, ExecutionException {
        fileManager.exportJPEGWithQuality(image, file, quality);
    }
    @Override
    public void rotate(final double angle) {
       rotator.addParameter(ParametersName.ANGLE, new ParameterImpl<Double>(angle));
       image = rotator.applyFilter(image);
       rotator.removeParameter(ParametersName.ANGLE);
    }
	@Override
	public void savePreset(final Properties filters, final File file) throws IOException, InterruptedException, ExecutionException {
		fileManager.writePreset(filters, file);
	}
    //TODO history
}
