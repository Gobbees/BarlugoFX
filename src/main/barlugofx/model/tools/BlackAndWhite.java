package barlugofx.model.tools;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import barlugofx.model.imagetools.ColorManipulatorUtils;
import barlugofx.model.tools.common.ImageToolImpl;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParametersName;

/**
 * This class models a black and white filter which accepts up to three
 * parameter, which are WRED, WBLUE, WGREEN. This parameters are double and
 * their standard value is 1. The value must be equal of greater than 0. For
 * general use, we higly suggest using numbers that are between 0.8-1.2. The
 * higher the value, the more the channel selected will count on the black and
 * white image.
 *
 */
public final class BlackAndWhite extends ImageToolImpl implements ParallelizableImageTool {
    private static final double MIN_VALUE = 0;
    private static final double DEFAULT_VALUE = 1.0;
    private static final double RED_MULTIPLIER = 0.299;
    private static final double GREEN_MULTIPLIER = 0.587;
    private static final double BLUE_MULTIPLIER = 0.114;
    private static final Set<ParametersName> ACCEPTED = new HashSet<>(
            Arrays.asList(ParametersName.WRED, ParametersName.WGREEN, ParametersName.WBLUE));

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
    public void executeFilter(final int[][] pixels, final int[][] newPixels, final Point begin, final Point end) {
        for (int i = 0; i < newPixels.length; i++) {
            for (int j = 0; j < newPixels[0].length; j++) {
                newPixels[i][j] = pixels[i][j];
                final int newValue = (int) (redFactor * ColorManipulatorUtils.getRed(pixels[i][j])
                        + greenFactor * ColorManipulatorUtils.getGreen(pixels[i][j])
                        + blueFactor * ColorManipulatorUtils.getBlue(pixels[i][j]));
                newPixels[i][j] = ColorManipulatorUtils.setRed(newPixels[i][j], newValue);
                newPixels[i][j] = ColorManipulatorUtils.setGreen(newPixels[i][j], newValue);
                newPixels[i][j] = ColorManipulatorUtils.setBlue(newPixels[i][j], newValue);
            }
        }
    }

    @Override
    public void inizializeFilter() {
        redFactor = super.getValueFromParameter(ParametersName.WRED, MIN_VALUE, Double.MAX_VALUE, DEFAULT_VALUE)
                * RED_MULTIPLIER;
        greenFactor = super.getValueFromParameter(ParametersName.WGREEN, MIN_VALUE, Double.MAX_VALUE, DEFAULT_VALUE)
                * GREEN_MULTIPLIER;
        blueFactor = super.getValueFromParameter(ParametersName.WBLUE, MIN_VALUE, Double.MAX_VALUE, DEFAULT_VALUE)
                * BLUE_MULTIPLIER;
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return ACCEPTED.contains(name);
    }

}
