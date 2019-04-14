package barlugofx.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageUtils;
import barlugofx.model.parallelhandler.ParallelFilterExecutor;
import barlugofx.model.tools.BlackAndWhite;
import barlugofx.model.tools.Brightness;
import barlugofx.model.tools.Contrast;
import barlugofx.model.tools.Cropper;
import barlugofx.model.tools.Exposure;
import barlugofx.model.tools.Hue;
import barlugofx.model.tools.Rotator;
import barlugofx.model.tools.Saturation;
import barlugofx.model.tools.SelectiveRGBChanger;
import barlugofx.model.tools.Vibrance;
import barlugofx.model.tools.WhiteBalance;
import barlugofx.model.tools.common.ImageTool;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParameterImpl;
import barlugofx.model.tools.common.ParameterName;
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
    //Tools
    private final ParallelizableImageTool exposure;
    private final ParallelizableImageTool contrast;
    private final ParallelizableImageTool brightness;
    private final ImageTool wb;
    private final ParallelizableImageTool saturation;
    private final ParallelizableImageTool hue;
    private final ParallelizableImageTool srgb;
    private final ParallelizableImageTool bw;
    private final ImageTool cropper;
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
        setImage(file);
        exposure = Exposure.createExposure();
        contrast = Contrast.createContrast();
        brightness = Brightness.createBrightness();
        wb = WhiteBalance.createWhiteBalance();
        srgb = SelectiveRGBChanger.createSelective();
        bw = BlackAndWhite.createBlackAndWhite();
        saturation = Saturation.createSaturation();
        hue = Hue.createHue();
        cropper = Cropper.createCropper();
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
    public BufferedImage getBufferedImage() {
        return ImageUtils.convertImageToBufferedImageWithAlpha(image);
    }
    @Override
    public void setImage(final File file) throws IOException {  //TODO reset history and all components
        image = fileManager.loadImageFromFile(file);
    }
    @Override
    public String getInputFileName() {
        return fileManager.getFileName();
    }

    @Override
    public void setExposure(final int value) {
       exposure.addParameter(ParameterName.EXPOSURE, new ParameterImpl<Float>(value * HSB_MULTIPLIER));
       if (parallel) {
           image = executor.applyTool(exposure, image);
       } else {
           image = exposure.applyTool(image);
       }
       exposure.removeParameter(ParameterName.EXPOSURE);
    }

    @Override
    public void setContrast(final int value) {
        contrast.addParameter(ParameterName.CONTRAST, new ParameterImpl<Integer>(value));
        if (parallel) {
            image = executor.applyTool(contrast, image);
        } else {
            image = contrast.applyTool(image);
        }
        contrast.removeParameter(ParameterName.CONTRAST);
    }

    @Override
    public void setBrightness(final int value) {
        brightness.addParameter(ParameterName.BRIGHTNESS, new ParameterImpl<Integer>(value));
        if (parallel) {
            image = executor.applyTool(brightness, image);
        } else {
            image = brightness.applyTool(image);
        }
        brightness.removeParameter(ParameterName.BRIGHTNESS);
    }

    @Override
    public void setWhiteBalance(final int value) {
        wb.addParameter(ParameterName.WHITEBALANCE, new ParameterImpl<Float>(value * WB_MULTIPLIER));
        image = wb.applyTool(image);
        wb.removeParameter(ParameterName.WHITEBALANCE);
    }

    @Override
    public void setSaturation(final int value) {
        saturation.addParameter(ParameterName.SATURATION, new ParameterImpl<Float>(value * HSB_MULTIPLIER));
        if (parallel) {
            image = executor.applyTool(saturation, image);
        } else {
            image = saturation.applyTool(image);
        }
        saturation.removeParameter(ParameterName.SATURATION);
    }

    @Override
    public void setHue(final int value) {
        hue.addParameter(ParameterName.HUE, new ParameterImpl<Float>(value * HSB_MULTIPLIER));
        if (parallel) {
            image = executor.applyTool(hue, image);
        } else {
            image = hue.applyTool(image);
        }
        hue.removeParameter(ParameterName.HUE);
    }

    @Override
    public void setVibrance(final int value) {
        vibrance.addParameter(ParameterName.VIBRANCE_INCREMENT, new ParameterImpl<Float>(value * VIBRANCE_MULTIPLIER));
        if (parallel) {
            image = executor.applyTool(vibrance, image);
        } else {
            image = vibrance.applyTool(image);
        }
        vibrance.removeParameter(ParameterName.VIBRANCE_INCREMENT);
    }

    @Override
    public void setSelectiveColors(final int r, final int g, final int b) {
        //TODO make it possible to change only one of these.
        srgb.addParameter(ParameterName.RED, new ParameterImpl<Integer>(r));
        srgb.addParameter(ParameterName.GREEN, new ParameterImpl<Integer>(g));
        srgb.addParameter(ParameterName.BLUE, new ParameterImpl<Integer>(b));
        if (parallel) {
            image = executor.applyTool(srgb, image);
        } else {
            image = srgb.applyTool(image);
        }
        srgb.removeParameter(ParameterName.RED);
        srgb.removeParameter(ParameterName.GREEN);
        srgb.removeParameter(ParameterName.BLUE);
    }

    @Override
    public void setBlackAndWhite(final double r, final double g, final double b) {
        bw.addParameter(ParameterName.WRED, new ParameterImpl<Double>(r * BW_MULTIPLIER + BW_SHIFTER));
        bw.addParameter(ParameterName.WGREEN, new ParameterImpl<Double>(g * BW_MULTIPLIER + BW_SHIFTER));
        if (parallel) {
            image = executor.applyTool(bw, image);
        } else {
            image = bw.applyTool(image);
        }
        bw.removeParameter(ParameterName.WRED);
        bw.removeParameter(ParameterName.WGREEN);
        bw.removeParameter(ParameterName.WBLUE);
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
       rotator.addParameter(ParameterName.ANGLE, new ParameterImpl<Double>(angle));
       image = rotator.applyTool(image);
       rotator.removeParameter(ParameterName.ANGLE);
    }
    @Override
    public void crop(final int x1, final int y1, final int x2, final int y2) {
        cropper.addParameter(ParameterName.X1, new ParameterImpl<Integer>(x1));
        cropper.addParameter(ParameterName.X2, new ParameterImpl<Integer>(x2));
        cropper.addParameter(ParameterName.Y1, new ParameterImpl<Integer>(y1));
        cropper.addParameter(ParameterName.Y2, new ParameterImpl<Integer>(y2));
        image = cropper.applyTool(image);
        cropper.removeParameter(ParameterName.X1);
        cropper.removeParameter(ParameterName.X2);
        cropper.removeParameter(ParameterName.Y1);
        cropper.removeParameter(ParameterName.Y2);
    }
    @Override
    public void savePreset(final Properties filters, final File file) throws IOException, InterruptedException, ExecutionException {
        fileManager.writePreset(filters, file);
    }
    @Override
    public void applyPreset(final File file) {
        //return;
    }
    //TODO history
}
