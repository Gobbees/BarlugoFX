package barlugofx.model.tools;

import java.awt.Color;
import java.awt.Point;

import barlugofx.model.imagetools.ColorUtils;
import barlugofx.model.tools.common.ImageToolImpl;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParametersName;

/**
 * The vibrance class implements a filter similar to saturation with the only
 * difference that the increment in saturation is applied considering, for each
 * pixel, its brightest color in relation to the average. In this way, parts of
 * the image already saturated will not encounter great differences. It accepts
 * one parameter: VIBRANCE_INCREMENT which specify the value to add. Must be a
 * float between -1 and 1.
 *
 * Eventual other value will result in an {@link IllegalStateException}.
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
    public void inizializeTool() {
        increment = super.getValueFromParameter(ParametersName.VIBRANCE_INCREMENT, MIN_INCREMENT, MAX_INCREMENT,
                DEFAULT_VALUE) / 100;
    }

    @Override
    public void executeTool(final int[][] pixels, final int[][] newPixels, final Point begin, final Point end) {
        for (int i = begin.y; i < end.y; i++) {
            for (int j = begin.x; j < end.x; j++) {
                float[] hsb = new float[3];
                final int red = ColorUtils.getRed(pixels[i][j]);
                final int green = ColorUtils.getGreen(pixels[i][j]);
                final int blue = ColorUtils.getBlue(pixels[i][j]);
                final float brightness = (red + green + blue) / 3;
                final int maxColor = Integer.max(green, Integer.max(red, blue));
                hsb = Color.RGBtoHSB(red, green, blue, hsb);
                hsb[1] = truncateSum(hsb[1], (maxColor - brightness) * increment);
                newPixels[i][j] = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
                newPixels[i][j] = ColorUtils.setAlpha(newPixels[i][j], ColorUtils.getAlpha(pixels[i][j]));
            }
        }
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
