package com.barlugofx.model.tools;

import java.util.Arrays;
import java.util.function.UnaryOperator;

import com.barlugofx.model.imageTools.ColorManipulator;
import com.barlugofx.model.imageTools.ColorManipulatorImpl;
import com.barlugofx.model.imageTools.Image;
import com.barlugofx.model.imageTools.ImageImpl;
import com.barlugofx.model.tools.common.ImageFilterImpl;
import com.barlugofx.model.tools.common.ParametersName;

/**
 *  The white balance class implements one of the simpliest auto-balancing algorithm used by gimp. It works discarding pixel colors at
 *  each end of the Red, Green and Blue histograms which are used by only 0.05% of the pixels in the image
 *  and stretches the remaining range as much as possible.
 *  The value in input must be a float greater than 0. For optimal result we suggest values around 0.3-0.7;
 *  @see <a href="https://docs.gimp.org/2.8/en/gimp-layer-white-balance.html">GIMP documentation</a>
 */
public final class WhiteBalance extends ImageFilterImpl {
    private static final double MINVALUE = 0;
    private static final double MAXRGB = 255.0;
    private static final float DEFAULT = 0f;
    private static final ColorManipulator COL = ColorManipulatorImpl.createColorExtractor();

    private WhiteBalance() {
    }
    /**
     * Creates a new WhiteBalance filter.
     * @return the instantieted whitebalance filter
     */
    public static WhiteBalance createWhiteBalance() {
        return new WhiteBalance();
    }

    @Override
    public Image applyFilter(final Image toApply) {
        final float value = super.getValueFromParameter(ParametersName.WHITEBALANCE, MINVALUE, Integer.MAX_VALUE, DEFAULT);
        final int[][] pixels = toApply.getImageRGBvalues();
        final int[][] newPixels = new int[pixels.length][pixels[0].length];
        final int[] pixelsAsArray = Arrays.stream(pixels).flatMapToInt(x -> Arrays.stream(x)).toArray();
        final int[] newRed = whiteBalanceRGB(rgbValues(x -> COL.getRed(x), pixelsAsArray), value);
        final int[] newGreen = whiteBalanceRGB(rgbValues(x -> COL.getGreen(x), pixelsAsArray), value);
        final int[] newBlue = whiteBalanceRGB(rgbValues(x -> COL.getBlue(x), pixelsAsArray), value);

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                newPixels[i][j] = pixels[i][j];
                newPixels[i][j] = COL.setRed(newPixels[i][j], newRed[i * pixels[0].length + j]);
                newPixels[i][j] = COL.setGreen(newPixels[i][j], newGreen[i * pixels[0].length + j]);
                newPixels[i][j] = COL.setBlue(newPixels[i][j], newBlue[i * pixels[0].length + j]);
            }
        }
        return ImageImpl.buildFromPixels(newPixels);
    }

    private int[] rgbValues(final UnaryOperator<Integer> obtainRgb, final int[] array) {
        final int[] onlyThatColor = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            onlyThatColor[i] = obtainRgb.apply(array[i]);
        }
        return onlyThatColor;
    }

    private int[] whiteBalanceRGB(final int[] array, final double percentile) {
        final int[] copy = new int[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);
        Arrays.sort(copy);

        final double lowPercentile = percentile(copy, percentile);
        final double highPercentile = percentile(copy, 100.0 - percentile);
        if (highPercentile == lowPercentile) {
            return array;
        }
        for (int i = 0; i < array.length; i++) {
            copy[i] = (int) ((array[i] - lowPercentile) * MAXRGB / (highPercentile - lowPercentile));
        }
        return copy;
    }

    /**
     * @see <a href="https://en.wikipedia.org/wiki/Percentile">Percentile</a>
     */
    private double percentile(final int[] array, final double percentile) {
        final double nPercent = (array.length + 1) * percentile / 100;
        if (nPercent <= 1) {
            return array[0];
        } else if (nPercent >= array.length) {
            return array[array.length - 1];
        } else {
            final int intNPercent = (int) nPercent;
            final double d = nPercent - intNPercent;
            return array[intNPercent - 1] + d * (array[intNPercent] - array[intNPercent - 1]);
        }
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return name == ParametersName.WHITEBALANCE;
    }

}
