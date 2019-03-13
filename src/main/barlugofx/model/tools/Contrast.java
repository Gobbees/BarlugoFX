package barlugofx.model.tools;

import barlugofx.model.imagetools.ColorManipulator;
import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.tools.common.ImageToolImpl;
import barlugofx.model.tools.common.ParametersName;

/**
 * A contrast class that allows to change an image contrast. It only accepts one parameter, Contrast, which must be between
 * -255 and 255. Eventual other value will result in an {@link IllegalStateException}.
 *
 *
 */
public final class Contrast extends ImageToolImpl {
    private static final double MAXVALUE = 255;
    private static final int TRANSLATION = 128;
    private static final int DEFAULT_VALUE = 0;

    private Contrast() {
        super();
    }
    /**
     * Creates a new Contrast class.
     * @return a new Contrast element.
     */
    public static Contrast createContrast() {
        return new Contrast();
    }

    @Override
    public Image applyFilter(final Image toApply) {
        final int value = super.getValueFromParameter(ParametersName.CONTRAST, -MAXVALUE, MAXVALUE, DEFAULT_VALUE);
        final double contrastCorrectionFactor = (MAXVALUE + 4) * (value + MAXVALUE)
                / (MAXVALUE * (MAXVALUE + 4 - value));
        final int[][] pixels = toApply.getImageRGBvalues();
        final int[][] newPixels = new int[pixels.length][pixels[0].length];
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                newPixels[i][j] = pixels[i][j];
                newPixels[i][j] = ColorManipulator.setBlue(newPixels[i][j],
                        (int) (contrastCorrectionFactor * (ColorManipulator.getBlue(pixels[i][j]) - TRANSLATION) + TRANSLATION));
                newPixels[i][j] = ColorManipulator.setGreen(newPixels[i][j],
                        (int) (contrastCorrectionFactor * (ColorManipulator.getGreen(pixels[i][j]) - TRANSLATION) + TRANSLATION));
                newPixels[i][j] = ColorManipulator.setRed(newPixels[i][j],
                        (int) (contrastCorrectionFactor * (ColorManipulator.getRed(pixels[i][j]) - TRANSLATION) + TRANSLATION));
            }
        }
        return ImageImpl.buildFromPixels(newPixels);
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return name == ParametersName.CONTRAST;
    }

}
