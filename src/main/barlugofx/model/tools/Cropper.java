package barlugofx.model.tools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.tools.common.ImageToolImpl;
import barlugofx.model.tools.common.ParametersName;

/**
 * This allow the crop of an image. It needs 4 parameter X1, X2, Y1,Y2, representing the beginning coordinates (inclusive) and the end
 * coordinates (exclusive) of the crop.
 *
 * X1 must be less than X2 and >= 0. Same goes for Y1 and Y2.
 * X2 and Y2 must be less than the image width and height respectively.
 */
public final class Cropper extends ImageToolImpl {

    private static final int MIN_VALUE_1 = 0;
    private static final int MAX_VALUE_2 = 1;
    private static final int DEFAULT_VALUE_1 = 0;

    private static final Set<ParametersName> ACCEPTED = new HashSet<>(
            Arrays.asList(ParametersName.X1, ParametersName.X2, ParametersName.Y1, ParametersName.Y2));

    private Cropper() {
        super();
    }

    /**
     * creates and instantiate a new Cropper.
     * @return the new Crop.
     */
    public static Cropper createCropper() {
        return new Cropper();
    }

    @Override
    public Image applyFilter(final Image toApply) {
        final int x2 = super.getValueFromParameter(ParametersName.X2, MAX_VALUE_2, toApply.getWidth(), toApply.getWidth());
        final int x1 = super.getValueFromParameter(ParametersName.X1, MIN_VALUE_1, x2, DEFAULT_VALUE_1);
        final int y2 = super.getValueFromParameter(ParametersName.Y2, MAX_VALUE_2, toApply.getHeight(), toApply.getHeight());
        final int y1 = super.getValueFromParameter(ParametersName.Y1, MIN_VALUE_1, y2, DEFAULT_VALUE_1);
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
