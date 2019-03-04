package barlugofx.model.tools;

import barlugofx.model.imageTools.ColorManipulator;
import barlugofx.model.imageTools.ColorManipulatorImpl;
import barlugofx.model.imageTools.Image;
import barlugofx.model.imageTools.ImageImpl;
import barlugofx.model.tools.common.ImageFilterImpl;
import barlugofx.model.tools.common.ParametersName;

/**
 * A brightness class that allows to change an image contrast. It only accepts one parameter, Brightness, which must be between
 * -255 and 255. Eventual other value will result in an {@link IllegalStateException}.
 *
 *
 */
public final class Brightness extends ImageFilterImpl {
    private static final double MAXVALUE = 255;
    private static final int DEFAULT_VALUE = 0;
    private Brightness() {
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
        final ColorManipulator setter = ColorManipulatorImpl.createColorExtractor();
        final int[][] pixels = toApply.getImageRGBvalues();
        final int[][] newPixels = new int[pixels.length][pixels[0].length];
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                newPixels[i][j] = pixels[i][j];
                newPixels[i][j] = setter.updateBlue(newPixels[i][j], value);
                newPixels[i][j] = setter.updateGreen(newPixels[i][j], value);
                newPixels[i][j] = setter.updateRed(newPixels[i][j], value);
            }
        }
        return ImageImpl.buildFromPixels(newPixels);
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return ParametersName.BRIGHTNESS == name;
    }

}
