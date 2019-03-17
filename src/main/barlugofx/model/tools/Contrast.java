package barlugofx.model.tools;

import java.awt.Point;

import barlugofx.model.imagetools.ColorManipulatorUtils;
import barlugofx.model.tools.common.ImageToolImpl;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParametersName;

/**
 * A contrast class that allows to change an image contrast. It only accepts one parameter, Contrast, which must be between
 * -255 and 255. Eventual other value will result in an {@link IllegalStateException}.
 *
 *
 */
public final class Contrast extends ImageToolImpl implements ParallelizableImageTool {
    private static final double MAXVALUE = 255;
    private static final int TRANSLATION = 128;
    private static final int DEFAULT_VALUE = 0;

    private int value = DEFAULT_VALUE;
    private double contrastCorrectionFactor;
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
    public void executeFilter(final int[][] pixels, final int[][] newPixels, final Point begin, final Point end) {
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                newPixels[i][j] = pixels[i][j];
                newPixels[i][j] = ColorManipulatorUtils.setBlue(newPixels[i][j],
                        (int) (contrastCorrectionFactor * (ColorManipulatorUtils.getBlue(pixels[i][j]) - TRANSLATION) + TRANSLATION));
                newPixels[i][j] = ColorManipulatorUtils.setGreen(newPixels[i][j],
                        (int) (contrastCorrectionFactor * (ColorManipulatorUtils.getGreen(pixels[i][j]) - TRANSLATION) + TRANSLATION));
                newPixels[i][j] = ColorManipulatorUtils.setRed(newPixels[i][j],
                        (int) (contrastCorrectionFactor * (ColorManipulatorUtils.getRed(pixels[i][j]) - TRANSLATION) + TRANSLATION));
            }
        }
    }

    @Override
    public void inizializeFilter() {
        value = super.getValueFromParameter(ParametersName.CONTRAST, -MAXVALUE, MAXVALUE, DEFAULT_VALUE);
        contrastCorrectionFactor = (MAXVALUE + 4) * (value + MAXVALUE) / (MAXVALUE * (MAXVALUE + 4 - value));
    }


    @Override
    protected boolean isAccepted(final ParametersName name) {
        return name == ParametersName.CONTRAST;
    }
}
