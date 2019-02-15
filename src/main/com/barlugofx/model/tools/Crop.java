package com.barlugofx.model.tools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.barlugofx.model.imageTools.Image;
import com.barlugofx.model.imageTools.ImageImpl;
import com.barlugofx.model.tools.common.ImageFilterImpl;
import com.barlugofx.model.tools.common.ParametersName;

/**
 * This allow the crop of an image. It needs 4 parameter X1, X2, Y1,Y2, representing the beginning coordinates (inclusive) and the end
 * coordinates (exclusive) of the crop.
 *
 * X1 must be less than X2 and >= 0. Same goes for Y1 and Y2.
 * X2 and Y2 must be less than the image width and height respectively.
 */
public final class Crop extends ImageFilterImpl {
    private static final Set<ParametersName> ACCEPTED = new HashSet<>(
            Arrays.asList(ParametersName.X1, ParametersName.X2, ParametersName.Y1, ParametersName.Y2));

    private Crop() {
    }

    /**
     * creates and instantiate a new Crop.
     * @return the new Crop.
     */
    public static Crop createCrop() {
        return new Crop();
    }

    @Override
    public Image applyFilter(final Image toApply) {
        final int x2 = super.getValueFromParameter(ParametersName.X2, 1, toApply.getWidth(), toApply.getWidth());
        final int x1 = super.getValueFromParameter(ParametersName.X1, 0, x2, 0);
        final int y2 = super.getValueFromParameter(ParametersName.Y2, 1, toApply.getHeight(), toApply.getHeight());
        final int y1 = super.getValueFromParameter(ParametersName.Y1, 0, y2, 0);
        final int[][] pixels = toApply.getImageRGBvalues();
        final int[][] newPixels = new int[y2 - y1][x2 - x1];
        for (int i = 0; i < newPixels.length; i++) {
            for (int j = 0; j < newPixels[0].length; j++) {
                newPixels[i][j] = pixels[i + y1][j + x1];
            }
        }
        return ImageImpl.buildFromPixels(newPixels);
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return ACCEPTED.contains(name);
    }

}