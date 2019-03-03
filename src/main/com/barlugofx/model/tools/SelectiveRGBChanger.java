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
 *  This class allows to change selectively the 3 channels of red green blue. It accepts up to three parameters: RED, which is an int from
 *  -255 to 255, BLUE, wich is an int with the same restrictions, and GREEN, which again is an int from -255 to 255.
 *
 */
public final class SelectiveRGBChanger extends ImageFilterImpl {
    private static final int MAX = 255;
    private static final int DEFAULT =  0;
    private static final ColorManipulator COL = ColorManipulatorImpl.createColorExtractor();
    private static final Set<ParametersName> ACCEPTED = new HashSet<>(
            Arrays.asList(ParametersName.RED, ParametersName.GREEN, ParametersName.BLUE));

    private SelectiveRGBChanger() {
    }
    /**
     * Creates a new SelectiveRGB changes.
     * @return a new SelectiveRGBChanges.
     */
    public static SelectiveRGBChanger createSelective() {
        return new SelectiveRGBChanger();
    }

    @Override
    public Image applyFilter(final Image toApply) {
        final int red = getValueFromParameter(ParametersName.RED, -MAX, MAX, DEFAULT);
        final int green = getValueFromParameter(ParametersName.GREEN, -MAX, MAX, DEFAULT);
        final int blue = getValueFromParameter(ParametersName.BLUE, -MAX, MAX, DEFAULT);

        final int[][] pixels = toApply.getImageRGBvalues();
        final int[][] newPixels = new int[pixels.length][pixels[0].length];
        for (int i = 0; i < newPixels.length; i++) {
            for (int j = 0; j < newPixels[0].length; j++) {
                newPixels[i][j] = pixels[i][j];
                newPixels[i][j] = COL.updateRed(newPixels[i][j], red);
                newPixels[i][j] = COL.updateGreen(newPixels[i][j], green);
                newPixels[i][j] = COL.updateBlue(newPixels[i][j], blue);
            }
        }
        return ImageImpl.buildFromPixels(newPixels);
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return ACCEPTED.contains(name);
    }
}
