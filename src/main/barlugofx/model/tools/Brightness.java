package barlugofx.model.tools;

import barlugofx.model.imagetools.ColorManipulator;
import barlugofx.model.imagetools.ColorManipulatorImpl;
import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.tools.common.ImageToolImpl;
import barlugofx.model.tools.common.ParametersName;

/**
 * A brightness class that allows to change an image contrast. It only accepts one parameter, Brightness, which must be between
 * -255 and 255. Eventual other value will result in an {@link IllegalStateException}.
 *
 *
 */
public final class Brightness extends ImageToolImpl {
    private static final double MAXVALUE = 255;
    private static final int DEFAULT_VALUE = 0;
    private static final ColorManipulator COL = ColorManipulatorImpl.createColorExtractor();
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
        final int value = super.getValueFromParameter(ParametersName.BRIGHTNESS, -MAXVALUE, MAXVALUE, DEFAULT_VALUE);
        final int[][] pixels = toApply.getImageRGBvalues();
        final int[][] newPixels = new int[pixels.length][pixels[0].length];
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                newPixels[i][j] = pixels[i][j];
                newPixels[i][j] = COL.updateBlue(newPixels[i][j], value);
                newPixels[i][j] = COL.updateGreen(newPixels[i][j], value);
                newPixels[i][j] = COL.updateRed(newPixels[i][j], value);
            }
        }
        return ImageImpl.buildFromPixels(newPixels);
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return ParametersName.BRIGHTNESS == name;
    }

}
