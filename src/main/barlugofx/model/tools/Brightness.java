package barlugofx.model.tools;

import java.awt.Point;

import barlugofx.model.imagetools.ColorManipulator;
import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.tools.common.ImageToolImpl;
import barlugofx.model.tools.common.ParallelImageTool;
import barlugofx.model.tools.common.ParametersName;

/**
 * A brightness class that allows to change an image contrast. It only accepts one parameter, Brightness, which must be between
 * -255 and 255. Eventual other value will result in an {@link IllegalStateException}.
 *
 *
 */
public final class Brightness extends ImageToolImpl implements ParallelImageTool {
    private static final double MAXVALUE = 255;
    private static final int DEFAULT_VALUE = 0;
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
    public Image applyFilter(final Image toApply) {
        final int[][] pixels = toApply.getImageRGBvalues();
        final int[][] newPixels = new int[pixels.length][pixels[0].length];
        executeFilter(pixels, newPixels, new Point(0, 0), new Point(toApply.getWidth(), toApply.getHeight()));
        return ImageImpl.buildFromPixels(newPixels);
    }

    @Override
    public void executeFilter(final int[][] pixels, final int[][] newPixels, final Point begin, final Point end) {
        final int value = super.getValueFromParameter(ParametersName.BRIGHTNESS, -MAXVALUE, MAXVALUE, DEFAULT_VALUE);
        for (int i = begin.y; i < end.y; i++) {
            for (int j = begin.x; j < end.x; j++) {
                newPixels[i][j] = pixels[i][j];
                newPixels[i][j] = ColorManipulator.updateBlue(newPixels[i][j], value);
                newPixels[i][j] = ColorManipulator.updateGreen(newPixels[i][j], value);
                newPixels[i][j] = ColorManipulator.updateRed(newPixels[i][j], value);
            }
        }
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return ParametersName.BRIGHTNESS == name;
    }
}
