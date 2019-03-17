package barlugofx.model.tools;

import java.awt.Point;

import barlugofx.model.imagetools.ColorManipulatorUtils;
import barlugofx.model.tools.common.ImageToolImpl;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParametersName;

/**
 * A brightness class that allows to change an image contrast. It only accepts one parameter, Brightness, which must be between
 * -255 and 255. Eventual other value will result in an {@link IllegalStateException}.
 *
 *
 */
public final class Brightness extends ImageToolImpl implements ParallelizableImageTool {
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
    public void inizializeFilter() {
        value  = super.getValueFromParameter(ParametersName.BRIGHTNESS, -MAXVALUE, MAXVALUE, DEFAULT_VALUE);
    }

    @Override
    public void executeFilter(final int[][] pixels, final int[][] newPixels, final Point begin, final Point end) {
        for (int i = begin.y; i < end.y; i++) {
            for (int j = begin.x; j < end.x; j++) {
                newPixels[i][j] = pixels[i][j];
                newPixels[i][j] = ColorManipulatorUtils.updateBlue(newPixels[i][j], value);
                newPixels[i][j] = ColorManipulatorUtils.updateGreen(newPixels[i][j], value);
                newPixels[i][j] = ColorManipulatorUtils.updateRed(newPixels[i][j], value);
            }
        }
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return ParametersName.BRIGHTNESS == name;
    }
}
