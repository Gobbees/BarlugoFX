package barlugofx.model.tools;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import barlugofx.model.imagetools.ColorUtils;
import barlugofx.model.tools.common.AbstractImageTool;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParameterName;
import barlugofx.utils.Tools;

/**
 * This class models a black and white filter which accepts up to three
 * parameter, WRED, WBLUE, WGREEN. This parameters are double and
 * their standard value is 1. The value must be equal or greater than 0. For
 * general use, it's suggested using numbers that are between 0.8-1.2. The
 * higher the value, the more the channel selected will count on the black and
 * white image.
 *
 * Eventual other value or parameters will result in an {@link IllegalStateException}.
 *
 */
public final class BlackAndWhite extends AbstractImageTool implements ParallelizableImageTool {
    private static final double MIN_VALUE = 0;
    private static final double DEFAULT_VALUE = 1.0;
    private static final double RED_MULTIPLIER = 0.299;
    private static final double GREEN_MULTIPLIER = 0.587;
    private static final double BLUE_MULTIPLIER = 0.114;
    private static final Set<ParameterName> ACCEPTED = new HashSet<>(
            Arrays.asList(ParameterName.WRED, ParameterName.WGREEN, ParameterName.WBLUE));

    private double blueFactor = DEFAULT_VALUE;
    private double greenFactor = DEFAULT_VALUE;
    private double redFactor = DEFAULT_VALUE;

    private BlackAndWhite() {
        super();
    }

    /**
     * Creates a new BlackAndWhite instance.
     *
     * @return the black and white instance.
     */
    public static BlackAndWhite createBlackAndWhite() {
        return new BlackAndWhite();
    }

    @Override
    public void executeTool(final int[][] pixels, final int[][] newPixels, final Point begin, final Point end) {
        for (int i = begin.y; i < end.y; i++) {
            for (int j = begin.x; j < end.x; j++) {
                newPixels[i][j] = pixels[i][j];
                final int newValue = (int) (redFactor * ColorUtils.getRed(pixels[i][j])
                        + greenFactor * ColorUtils.getGreen(pixels[i][j])
                        + blueFactor * ColorUtils.getBlue(pixels[i][j]));
                newPixels[i][j] = ColorUtils.setRed(newPixels[i][j], newValue);
                newPixels[i][j] = ColorUtils.setGreen(newPixels[i][j], newValue);
                newPixels[i][j] = ColorUtils.setBlue(newPixels[i][j], newValue);
            }
        }
    }

    @Override
    public void inizializeTool() {
        redFactor = super.getValueFromParameter(ParameterName.WRED, MIN_VALUE, Double.MAX_VALUE, DEFAULT_VALUE)
                * RED_MULTIPLIER;
        greenFactor = super.getValueFromParameter(ParameterName.WGREEN, MIN_VALUE, Double.MAX_VALUE, DEFAULT_VALUE)
                * GREEN_MULTIPLIER;
        blueFactor = super.getValueFromParameter(ParameterName.WBLUE, MIN_VALUE, Double.MAX_VALUE, DEFAULT_VALUE)
                * BLUE_MULTIPLIER;
    }

    @Override
    protected boolean isAccepted(final ParameterName name) {
        return ACCEPTED.contains(name);
    }


    @Override
    public Tools getToolType() {
        return Tools.BLACKANDWHITE;
    }

    /**
     * Return the default value for this tool.
     * @return the default value.
     */
    public static double getDefaultValue() {
        return DEFAULT_VALUE;
    }
}
