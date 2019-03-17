package barlugofx.model.tools;

import java.awt.Point;

import barlugofx.model.imagetools.ColorManipulatorUtils;
import barlugofx.model.imagetools.ImageUtils;
import barlugofx.model.tools.common.ImageToolImpl;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParametersName;

/**
 * The vibrance class implements a filter similar to saturation with the only
 * difference that the increment in saturation is applied considering, for each
 * pixel, is brightest color in relation to the average. In this way, parts of
 * the image already saturated will not encounter great differences. It accepts
 * one parameter: VIBRANCE_INCREMENT which specify the value to add. Must be a
 * float between -1 and 1.
 */
public final class Vibrance extends ImageToolImpl implements ParallelizableImageTool {
    private static final float MIN_INCREMENT = -1;
    private static final float MAX_INCREMENT = 1;
    private static final float MAX_SATURATION = 1;
    private static final float DEFAULT_VALUE = 0;

    private float increment;

    private Vibrance() {
        super();
    }

    /**
     * Creates a new Vibrance element.
     *
     * @return the instantieted vibrance element.
     */
    public static Vibrance createVibrance() {
        return new Vibrance();
    }


    @Override
    public void inizializeFilter() {
        increment = super.getValueFromParameter(ParametersName.VIBRANCE_INCREMENT, MIN_INCREMENT,
                MAX_INCREMENT, DEFAULT_VALUE) / 100;
    }

    @Override
    public void executeFilter(final int[][] pixels, final int[][] newPixels, final Point begin, final Point end) {
        final float[][][] hsb = ImageUtils.rgbToHsb(begin, end, pixels);
        int iPixels;
        int jPixels;
        for (int i = 0; i < hsb.length; i++) {
            iPixels = i + begin.y;
            for (int j = 0; j < hsb[0].length; j++) {
                jPixels = j + begin.x;
                float brightness = ColorManipulatorUtils.getRed(pixels[iPixels][jPixels])
                        + ColorManipulatorUtils.getBlue(pixels[iPixels][jPixels]) + ColorManipulatorUtils.getGreen(pixels[iPixels][jPixels]);
                brightness = brightness / 3;
                final int maxColor = Integer.max(ColorManipulatorUtils.getRed(pixels[iPixels][jPixels]), Integer.max(
                        ColorManipulatorUtils.getBlue(pixels[iPixels][jPixels]), ColorManipulatorUtils.getGreen(pixels[iPixels][jPixels])));
                hsb[i][j][1] = truncateSum(hsb[i][j][1], (maxColor - brightness) * increment);
            }
        }
        ImageUtils.hsbToRgb(pixels, newPixels, begin, hsb);
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
