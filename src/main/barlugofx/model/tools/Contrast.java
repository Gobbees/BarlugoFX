package barlugofx.model.tools;

import java.awt.Point;

import barlugofx.model.imagetools.ColorUtils;
import barlugofx.model.tools.common.AbstractImageTool;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParameterName;

/**
 * This class allows changes of an {@link Image} contrast. It only accepts one
 * parameter, Contrast, which must be between -255 and 255. Eventual other value
 * will result in an {@link IllegalStateException}.
 *
 *
 */
public final class Contrast extends AbstractImageTool implements ParallelizableImageTool {
    private static final double MAXVALUE = 255;
    private static final int TRANSLATION = 128;
    private static final int DEFAULT_VALUE = 0;

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
    public void executeTool(final int[][] pixels, final int[][] newPixels, final Point begin, final Point end) {
        for (int i = begin.y; i < end.y; i++) {
            for (int j = begin.x; j < end.x; j++) {
                newPixels[i][j] = pixels[i][j];
                newPixels[i][j] = ColorUtils.setBlue(newPixels[i][j],
                        (int) (contrastCorrectionFactor * (ColorUtils.getBlue(pixels[i][j]) - TRANSLATION) + TRANSLATION));
                newPixels[i][j] = ColorUtils.setGreen(newPixels[i][j],
                        (int) (contrastCorrectionFactor * (ColorUtils.getGreen(pixels[i][j]) - TRANSLATION) + TRANSLATION));
                newPixels[i][j] = ColorUtils.setRed(newPixels[i][j],
                        (int) (contrastCorrectionFactor * (ColorUtils.getRed(pixels[i][j]) - TRANSLATION) + TRANSLATION));
            }
        }
    }

    @Override
    public void inizializeTool() {
        final int value = super.getValueFromParameter(ParameterName.CONTRAST, -MAXVALUE, MAXVALUE, DEFAULT_VALUE);
        contrastCorrectionFactor = (MAXVALUE + 4) * (value + MAXVALUE) / (MAXVALUE * (MAXVALUE + 4 - value));
    }


    @Override
    protected boolean isAccepted(final ParameterName name) {
        return name == ParameterName.CONTRAST;
    }

    @Override
    public Tools getToolType() {
        return Tools.CONTRAST;
    }

    /**
     * Return the default value for this tool.
     * @return the default value.
     */
    public static int getDefaultValue() {
        return DEFAULT_VALUE;
    }
}
