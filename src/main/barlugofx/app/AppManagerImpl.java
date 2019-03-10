package barlugofx.app;

import java.io.File;
import java.io.IOException;

import barlugofx.model.imagetools.Image;
import barlugofx.model.tools.BlackAndWhite;
import barlugofx.model.tools.Brightness;
import barlugofx.model.tools.Contrast;
//import barlugofx.model.tools.Cropper;
import barlugofx.model.tools.HSBModifier;
//import barlugofx.model.tools.Rotator;
import barlugofx.model.tools.SelectiveRGBChanger;
import barlugofx.model.tools.Vibrance;
import barlugofx.model.tools.WhiteBalance;
import barlugofx.model.tools.common.ParameterImpl;
import barlugofx.model.tools.common.ParametersName;

/**
 * The main controller class.
 */
public class AppManagerImpl implements AppManager {
    //multipliers used to adapt the input from the view to the model
    private static final float HSB_MULTIPLIER = 0.01f;
    private static final float WB_MULTIPLIER = 0.015f;
    private static final float VIBRANCE_MULTIPLIER = 0.01f;
    private static final float BW_MULTIPLIER = 0.004f;
    private static final float BW_SHIFTER = 0.8f;

    private Image image;
    //tools
    private final HSBModifier hsb;
    private final Contrast contrast;
    private final Brightness brightness;
    private final WhiteBalance wb;
    private final SelectiveRGBChanger srgb;
    private final BlackAndWhite bw;
    //private final Cropper cropper;
    //private final Rotator rotator;
    private final Vibrance vibrance;
    //TODO
    /**
     * @param input
     */
    public AppManagerImpl(final File input) {
        final IOManagerImpl fileManager = new IOManagerImpl();
        try {
            image = fileManager.loadImageFromFile(input);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        hsb = HSBModifier.createHSB();
        contrast = Contrast.createContrast();
        brightness = Brightness.createBrightness();
        wb = WhiteBalance.createWhiteBalance();
        srgb = SelectiveRGBChanger.createSelective();
        bw = BlackAndWhite.createBlackAndWhite();
        //cropper = Cropper.createCropper();
        //rotator = Rotator.createRotator();
        vibrance = Vibrance.createVibrance();
    }

    /* (non-Javadoc)
     * @see com.barlugofx.app.AppManager#getImage()
     */
    @Override
    public Image getImage() {
        return image;
    }

    /* (non-Javadoc)
     * @see barlugofx.app.AppManager#setExposure(int)
     */
    @Override
    public void setExposure(final int value) {
        //TODO HISTORY
        hsb.addParameter(ParametersName.EXPOSURE, new ParameterImpl<Float>(value * HSB_MULTIPLIER));
        image = hsb.applyFilter(image);
        hsb.removeParameter(ParametersName.EXPOSURE);
    }

    /* (non-Javadoc)
     * @see barlugofx.app.AppManager#setContrast(int)
     */
    @Override
    public void setContrast(final int value) {
        contrast.addParameter(ParametersName.CONTRAST, new ParameterImpl<Integer>(value));
        image = contrast.applyFilter(image);
        contrast.removeParameter(ParametersName.CONTRAST);
    }

    /* (non-Javadoc)
     * @see barlugofx.app.AppManager#setBrightness(int)
     */
    @Override
    public void setBrightness(final int value) {
        brightness.addParameter(ParametersName.BRIGHTNESS, new ParameterImpl<Integer>(value));
        image = brightness.applyFilter(image);
        brightness.removeParameter(ParametersName.BRIGHTNESS);
    }

    /* (non-Javadoc)
     * @see barlugofx.app.AppManager#setWB(int)
     */
    @Override
    public void setWB(final int value) {
        System.out.println("WB: " + (value * WB_MULTIPLIER));
        wb.addParameter(ParametersName.WHITEBALANCE, new ParameterImpl<Float>(value * WB_MULTIPLIER));
        image = wb.applyFilter(image);
        wb.removeParameter(ParametersName.WHITEBALANCE);
    }

    /* (non-Javadoc)
     * @see barlugofx.app.AppManager#setSaturation(int)
     */
    @Override
    public void setSaturation(final int value) {
        System.out.println("Saturation: " + (value * HSB_MULTIPLIER));
        hsb.addParameter(ParametersName.SATURATION, new ParameterImpl<Float>(value * HSB_MULTIPLIER));
        image = hsb.applyFilter(image);
        hsb.removeParameter(ParametersName.SATURATION);
    }

    /* (non-Javadoc)
     * @see barlugofx.app.AppManager#setHue(int)
     */
    @Override
    public void setHue(final int value) {
        //TODO set hue slider colors after that the history will be finished
        hsb.addParameter(ParametersName.HUE, new ParameterImpl<Float>(value * HSB_MULTIPLIER));
        image = hsb.applyFilter(image);
        hsb.removeParameter(ParametersName.HUE);
    }

    /* (non-Javadoc)
     * @see barlugofx.app.AppManager#setVibrance(int)
     */
    @Override
    public void setVibrance(final int value) {
        vibrance.addParameter(ParametersName.VIBRANCE_INCREMENT, new ParameterImpl<Float>(value * VIBRANCE_MULTIPLIER));
        image = vibrance.applyFilter(image);
        vibrance.removeParameter(ParametersName.VIBRANCE_INCREMENT);
    }

    /* (non-Javadoc)
     * @see barlugofx.app.AppManager#setSC(int, int, int)
     */
    @Override
    public void setSC(final int r, final int g, final int b) {
        //TODO make it possible to change only one of these.
        srgb.addParameter(ParametersName.RED, new ParameterImpl<Integer>(r));
        srgb.addParameter(ParametersName.GREEN, new ParameterImpl<Integer>(g));
        srgb.addParameter(ParametersName.BLUE, new ParameterImpl<Integer>(b));
        image = srgb.applyFilter(image);
        srgb.removeParameter(ParametersName.RED);
        srgb.removeParameter(ParametersName.GREEN);
        srgb.removeParameter(ParametersName.BLUE);
    }

    /* (non-Javadoc)
     * @see barlugofx.app.AppManager#setSC(int, int, int)
     */
    @Override
    public void setBW(final double r, final double g, final double b) {
        bw.addParameter(ParametersName.WRED, new ParameterImpl<Double>(r * BW_MULTIPLIER + BW_SHIFTER));
        bw.addParameter(ParametersName.WGREEN, new ParameterImpl<Double>(g * BW_MULTIPLIER + BW_SHIFTER));
        image = bw.applyFilter(image);
        bw.removeParameter(ParametersName.WRED);
        bw.removeParameter(ParametersName.WGREEN);
        bw.removeParameter(ParametersName.WBLUE);
    }

    //TODO history
    //TODO
}
