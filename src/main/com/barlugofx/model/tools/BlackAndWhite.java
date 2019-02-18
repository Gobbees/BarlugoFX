package com.barlugofx.model.tools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.barlugofx.model.imageTools.ColorManipulator;
import com.barlugofx.model.imageTools.ColorManipulatorImpl;
import com.barlugofx.model.imageTools.Image;
import com.barlugofx.model.imageTools.ImageImpl;
import com.barlugofx.model.tools.common.ImageFilterImpl;
import com.barlugofx.model.tools.common.ParametersName;

/**
 *  This class models a black and white filter which accepts up to three parameter, which are WRED, WBLUE, WGREEN. This parameters
 *  are double and their standard value is 1. The value must be equal of greater than 0. For general use, we higly suggest using numbers
 *  that are between 0.8-1.2. The higher the value, the more the channel selected will count on  the black and white image.
 *
 */
public final class BlackAndWhite extends ImageFilterImpl {
    private static final ColorManipulator COL = ColorManipulatorImpl.createColorExtractor();
    private static final double MIN_VALUE = 0;
    private static final double DEFAULT_VALUE = 1.0;
    private static final double RED_MULTIPLIER = 0.299;
    private static final double GREEN_MULTIPLIER = 0.587;
    private static final double BLUE_MULTIPLIER = 0.114;
    private static final Set<ParametersName> ACCEPTED = new HashSet<>(
            Arrays.asList(ParametersName.WRED, ParametersName.WGREEN, ParametersName.WBLUE));

    private BlackAndWhite() {

    }
    /**
     * Creates a new BlackAndWhite instance.
     * @return the black and white instance.
     */
    public static BlackAndWhite createBlackAndWhite() {
        return new BlackAndWhite();
    }

    @Override
    public Image applyFilter(final Image toApply) {
        final double redFactor = super.getValueFromParameter(ParametersName.WRED, MIN_VALUE, Double.MAX_VALUE, DEFAULT_VALUE) * RED_MULTIPLIER;
        final double greenFactor = super.getValueFromParameter(ParametersName.WGREEN, MIN_VALUE, Double.MAX_VALUE, DEFAULT_VALUE) * GREEN_MULTIPLIER;
        final double blueFactor = super.getValueFromParameter(ParametersName.WBLUE, MIN_VALUE, Double.MAX_VALUE, DEFAULT_VALUE) * BLUE_MULTIPLIER;

        final int[][] pixels = toApply.getImageRGBvalues();
        final int[][] newPixels = new int[pixels.length][pixels[0].length];

        for (int i = 0; i < newPixels.length; i++) {
            for (int j = 0; j < newPixels[0].length; j++) {
                newPixels[i][j] = pixels[i][j];
                final int newValue = (int) (redFactor * COL.getRed(pixels[i][j])
                        + greenFactor * COL.getGreen(pixels[i][j]) + blueFactor * COL.getBlue(pixels[i][j]));
                newPixels[i][j] = COL.setRed(newPixels[i][j], newValue);
                newPixels[i][j] = COL.setGreen(newPixels[i][j], newValue);
                newPixels[i][j] = COL.setBlue(newPixels[i][j], newValue);
            }
        }
        return ImageImpl.buildFromPixels(newPixels);
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return ACCEPTED.contains(name);
    }
}
