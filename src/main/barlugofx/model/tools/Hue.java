import barlugofx.model.imagetools.ColorUtils;
package barlugofx.model.tools;

import java.awt.Color;
import java.awt.Point;

import barlugofx.model.imagetools.ColorUtils;
import barlugofx.model.tools.common.AbstractImageTool;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParameterName;

/**
 * This class handles the change of an {@link Image} involving Hue. This class
 * accepts one parameter, HUE, which must be a float number between -1
 * and 1.
 *
 * Eventual other value will result in an {@link IllegalStateException}.
 */
public final class Hue extends AbstractImageTool implements ParallelizableImageTool {
    private static final int MAX = 1;
    private static final int MIN = -1;
    private static final float DEFAULT_VALUE = 0f;

    private float value;

    private Hue() {
        super();
        value = DEFAULT_VALUE;
    }

    /**
     * Creates a new Hue.
     *
     * @return the instantiated modifier.
     */
    public static Hue createHue() {
        return new Hue();
    }

    @Override
    public void executeTool(final int[][] pixels, final int[][] newPixels, final Point begin, final Point end) {
        for (int i = begin.y; i < end.y; i++) {
            for (int j = begin.x; j < end.x; j++) {
                float[] hsv = new float[3];
                hsv = Color.RGBtoHSB(ColorUtils.getRed(pixels[i][j]), ColorUtils.getGreen(pixels[i][j]),
                        ColorUtils.getGreen(pixels[i][j]), hsv);
                hsv[0] = value == 0 ? hsv[0] : hsv[0] + value; // truncate qui non e' necessario
                newPixels[i][j] = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
                newPixels[i][j] = ColorUtils.setAlpha(newPixels[i][j], ColorUtils.getAlpha(pixels[i][j]));
            }
        }
    }

    @Override
    public void inizializeTool() {
        value = super.getValueFromParameter(ParameterName.HUE, MIN, MAX, DEFAULT_VALUE);
    }

    @Override
    protected boolean isAccepted(final ParameterName name) {
        return name == ParameterName.HUE;
    }


    @Override
    public Tools getToolType() {
        return Tools.HUE;
    }
}
