package com.barlugofx.app;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.barlugofx.model.imageTools.Image;
import com.barlugofx.model.tools.BlackAndWhite;
import com.barlugofx.model.tools.Brightness;
import com.barlugofx.model.tools.Contrast;
import com.barlugofx.model.tools.Cropper;
import com.barlugofx.model.tools.HSBModifier;
import com.barlugofx.model.tools.Rotator;
import com.barlugofx.model.tools.SelectiveRGBChanger;
import com.barlugofx.model.tools.Vibrance;
import com.barlugofx.model.tools.WhiteBalance;
import com.barlugofx.model.tools.common.Parameter;
import com.barlugofx.model.tools.common.ParameterImpl;
import com.barlugofx.model.tools.common.ParametersName;

/**
 * The main controller class.
 */
public class AppManagerImpl implements AppManager {
    private Image image;
    private IOManagerImpl fileManager;
    private final BlackAndWhite bw;
    private final Brightness brightness;
    private final Contrast contrast;
    private final Cropper cropper;
    private final HSBModifier hsbModifier;
    private final Rotator rotator;
    private final SelectiveRGBChanger selectiveRGB;
    private final Vibrance vibrance;
    private final WhiteBalance wb;
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
        bw = BlackAndWhite.createBlackAndWhite();
        brightness = Brightness.createBrightnees();
        contrast = Contrast.createContrast();
        cropper = Cropper.createCropper();
        hsbModifier = HSBModifier.createHSB();
        rotator = Rotator.createRotator();
        selectiveRGB = SelectiveRGBChanger.createSelective();
        vibrance = Vibrance.createVibrance();
        wb = WhiteBalance.createWhiteBalance();
    }

    /* (non-Javadoc)
     * @see com.barlugofx.app.AppManager#getImage()
     */
    @Override
    public Image getImage() {
        return image;
    }
    /* (non-Javadoc)
     * @see com.barlugofx.app.AppManager#apply()
     */
    @Override
    public void setBW(final double red, final double green, final double blue) {
        //TODO
//        bw.addParameter(ParametersName.WRED, new ParameterImpl<Double>(red));
//        bw.addParameter(ParametersName.WGREEN, new ParameterImpl<Double>(green));
//        bw.addParameter(ParametersName.WBLUE, new ParameterImpl<Double>(blue));
//        image = bw.applyFilter(image);
//        bw.removeParameter(ParametersName.WRED);
//        bw.removeParameter(ParametersName.WGREEN);
//        bw.removeParameter(ParametersName.WBLUE);
    }

    /* (non-Javadoc)
     * @see com.barlugofx.app.AppManager#setBrightness(double)
     */
    @Override
    public void setBrightness(final int value) {
        //TODO history
        System.out.println("Computed");
        brightness.removeParameter(ParametersName.BRIGHTNESS);
        brightness.addParameter(ParametersName.BRIGHTNESS, new ParameterImpl<Integer>(value));
        image = brightness.applyFilter(image);
    }
    //TODO finish filters
    //TODO history
    //TODO
}
