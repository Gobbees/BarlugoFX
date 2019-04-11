package barlugofx.model.tools;

import java.awt.Point;

import barlugofx.model.imagetools.ColorUtils;
import barlugofx.model.tools.common.AbstractImageTool;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParameterName;

/**
 * This class allows changes of an {@link Image} brightness. It only accepts
 * one parameter, Brightness, which must be between -255 and 255. Eventual other
 * value will result in an {@link IllegalStateException}.
 *
 *
 */
public final class Brightness extends AbstractImageTool implements ParallelizableImageTool {
    private static final double MAXVALUE = 255;
    private static final int DEFAULT_VALUE = 0;
    private int value = DEFAULT_VALUE;

    private Brightness() {
        super();
    }
    /**
     * Creates a new Brightness class.
     * @return a new Brightness element.
     */
    public static Brightness createBrightness() {
        return new Brightness();
    }

    @Override
    public void inizializeTool() {
        value  = super.getValueFromParameter(ParameterName.BRIGHTNESS, -MAXVALUE, MAXVALUE, DEFAULT_VALUE);
    }

    @Override
    public void executeTool(final int[][] pixels, final int[][] newPixels, final Point begin, final Point end) {
        for (int i = begin.y; i < end.y; i++) {
            for (int j = begin.x; j < end.x; j++) {
                newPixels[i][j] = pixels[i][j];
                newPixels[i][j] = ColorUtils.updateBlue(newPixels[i][j], value);
                newPixels[i][j] = ColorUtils.updateGreen(newPixels[i][j], value);
                newPixels[i][j] = ColorUtils.updateRed(newPixels[i][j], value);
            }
        }
    }

    @Override
    protected boolean isAccepted(final ParameterName name) {
        return ParameterName.BRIGHTNESS == name;
    }
}
