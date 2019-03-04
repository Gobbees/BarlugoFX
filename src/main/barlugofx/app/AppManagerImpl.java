package barlugofx.app;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import barlugofx.model.imageTools.Image;
import barlugofx.model.tools.BlackAndWhite;
import barlugofx.model.tools.Brightness;
import barlugofx.model.tools.Contrast;
import barlugofx.model.tools.Cropper;
import barlugofx.model.tools.HSBModifier;
import barlugofx.model.tools.Rotator;
import barlugofx.model.tools.SelectiveRGBChanger;
import barlugofx.model.tools.Vibrance;
import barlugofx.model.tools.WhiteBalance;
import barlugofx.model.tools.common.ImageFilter;
import barlugofx.model.tools.common.Parameter;
import barlugofx.model.tools.common.ParameterImpl;
import barlugofx.model.tools.common.ParametersName;

/**
 * The main controller class.
 */
public class AppManagerImpl implements AppManager {
    //multipliers used to adapt the input from the view to the model
    private static final float EXPOSURE_MULTIPLIER = 100.0f;
    
    private Image image;
    private IOManagerImpl fileManager;
    //tools
    private final HSBModifier hsb;
    private final Contrast contrast;
    private final Brightness brightness;
    private final WhiteBalance wb;
    private final SelectiveRGBChanger srgb;
    private final BlackAndWhite bw;
    private final Cropper cropper;
    private final Rotator rotator;
    //TODO
    /**
     * @param input
     */
    public AppManagerImpl(final File input) {
        fileManager = new IOManagerImpl();
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
        cropper = Cropper.createCropper();
        rotator = Rotator.createRotator();
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
        hsb.removeParameter(ParametersName.EXPOSURE);
        hsb.addParameter(ParametersName.EXPOSURE, new ParameterImpl<Float>(value / EXPOSURE_MULTIPLIER));
        image = hsb.applyFilter(image);
    }

    /* (non-Javadoc)
     * @see barlugofx.app.AppManager#setContrast(int)
     */
    @Override
    public void setContrast(final int value) {
        contrast.removeParameter(ParametersName.CONTRAST);
        contrast.addParameter(ParametersName.CONTRAST, new ParameterImpl<Integer>(value));
        image = contrast.applyFilter(image);
    }

    /* (non-Javadoc)
     * @see barlugofx.app.AppManager#setBrightness(int)
     */
    @Override
    public void setBrightness(final int value) {
        brightness.removeParameter(ParametersName.BRIGHTNESS);
        brightness.addParameter(ParametersName.BRIGHTNESS, new ParameterImpl<Integer>(value));
        image = brightness.applyFilter(image);
    }

    //TODO finish filters
    //TODO history
    //TODO
}
