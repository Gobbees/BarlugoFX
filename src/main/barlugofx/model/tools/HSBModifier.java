package barlugofx.model.tools;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import barlugofx.model.imagetools.ColorUtils;
import barlugofx.model.tools.common.ImageToolImpl;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParametersName;

/**
 * This class handles the change of an {@link Image} involving Hue, Saturation and
 * Exposure (Brightness in HSB). This class accepts up to three parameter: HUE,
 * which must be a positive float number between -1 and 1, Saturation, which
 * must be a float number between -1 and 1, and Exposure, a float number between
 * -1 and 1. By receiving more than one parameter, this class allows multiple
 * changes to HSV limiting conversions, resulting in more efficient changes.
 *
 * Eventual other value will result in an {@link IllegalStateException}.
 */
public final class HSBModifier extends ImageToolImpl implements ParallelizableImageTool {
    private static final int MAX = 1;
    private static final int MIN = -1;
    private static final float DEFAULT_VALUE = 0f;
    private static final Set<ParametersName> ACCEPTED = new HashSet<>(
            Arrays.asList(ParametersName.EXPOSURE, ParametersName.HUE, ParametersName.SATURATION));

    private float hue = DEFAULT_VALUE;
    private float saturation = DEFAULT_VALUE;
    private float exposure = DEFAULT_VALUE;

    private HSBModifier() {
        super();
    }
    /**
     * Creates a new HSBModifier.
     * @return the instantiated modifier.
     */
    public static HSBModifier createHSB() {
        return new HSBModifier();
    }

    @Override
    public void executeTool(final int[][] pixels, final int[][] newPixels, final Point begin, final Point end) {
        for (int i = begin.y; i < end.y; i++) {
            for (int j = begin.x; j < end.x; j++) {
                float[] hsv = new float[3];
                hsv = Color.RGBtoHSB(ColorUtils.getRed(pixels[i][j]), ColorUtils.getGreen(pixels[i][j]),
                        ColorUtils.getGreen(pixels[i][j]), hsv);
                hsv[0] = hue == 0 ? hsv[0] : hsv[0] + hue; //truncate qui non e' necessario
                hsv[1] = saturation == 0 ? hsv[1] : truncateSum(hsv[1], saturation);
                hsv[2] = exposure == 0 ? hsv[2] : truncateSum(hsv[2], exposure);
				newPixels[i][j] = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
				newPixels[i][j] = ColorUtils.setAlpha(newPixels[i][j], ColorUtils.getAlpha(pixels[i][j]));
            }
        }
    }

    @Override
    public void inizializeTool() {
        hue = super.getValueFromParameter(ParametersName.HUE, MIN, MAX, DEFAULT_VALUE);
        saturation = getValueFromParameter(ParametersName.SATURATION, MIN, MAX, DEFAULT_VALUE);
        exposure = getValueFromParameter(ParametersName.EXPOSURE, MIN, MAX, DEFAULT_VALUE);
    }

    private float truncateSum(final float hsv, final float hue) {
        final float result = hsv + hue;
        if (result > MAX) {
            return MAX;
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
        return ACCEPTED.contains(name);
    }


}
