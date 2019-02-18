package com.barlugofx.model.tools;

import com.barlugofx.model.imageTools.ColorManipulator;
import com.barlugofx.model.imageTools.ColorManipulatorImpl;
import com.barlugofx.model.imageTools.Image;
import com.barlugofx.model.imageTools.ImageImpl;
import com.barlugofx.model.imageTools.ImageUtilities;
import com.barlugofx.model.tools.common.ImageFilterImpl;
import com.barlugofx.model.tools.common.ParametersName;

/**
 * The vibrance class implements a filter similar to saturation with the only difference that the increment in saturation is applied
 * considering, for each pixel, is brightest color in relation to the average. In this way, parts of the image already saturated
 * will not encounter great differences.
 * It accepts one parameter: VIBRANCE_INCREMENT which specify the value to add. Must be a float between -1 and 1.
 */
public final class Vibrance extends ImageFilterImpl {
    private static final ColorManipulator COL = ColorManipulatorImpl.createColorExtractor();
    private static final float MIN_INCREMENT = -1;
    private static final float MAX_INCREMENT = 1;
    private static final float MAX_SATURATION = 1;
    private static final float DEFAULT_VALUE = 0;

    private Vibrance() {
    }

    /**
     * Creates a new Vibrance element.
     * @return the instantieted vibrance element.
     */
    public static Vibrance createVibrance() {
        return new Vibrance();
    }

    @Override
    public Image applyFilter(final Image toApply) {
        final float increment = super.getValueFromParameter(ParametersName.VIBRANCE_INCREMENT, MIN_INCREMENT,
                MAX_INCREMENT, DEFAULT_VALUE) / 100;

        final int[][] pixels = toApply.getImageRGBvalues();
        final float[][][] hsb = ImageUtilities.rgbToHsb(toApply.getImageRGBvalues());
        for (int i = 0; i < hsb.length; i++) {
            for (int j = 0; j < hsb[0].length; j++) {
                float brightness = COL.getRed(pixels[i][j]) + COL.getBlue(pixels[i][j]) + COL.getGreen(pixels[i][j]);
                brightness = brightness / 3;
                final int maxColor = Integer.max(COL.getRed(pixels[i][j]), Integer.max(COL.getBlue(pixels[i][j]), COL.getGreen(pixels[i][j])));
                hsb[i][j][1] = truncateSum(hsb[i][j][1], (maxColor - brightness) * increment);
            }
        }
        return ImageImpl.buildFromPixels(ImageUtilities.hsbToRgb(hsb));
    }

    private float truncateSum(final float saturation, final float toAdd) {
        final float result = saturation + toAdd;
        if (result > MAX_SATURATION) {
            return MAX_SATURATION;
        } else {
            if (result < 0) {
                return 0;
            } else {
                return result;
            }
        }
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return ParametersName.VIBRANCE_INCREMENT == name;
    }

}
