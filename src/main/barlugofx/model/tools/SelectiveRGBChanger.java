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
 *  This class allows to change selectively the 3 channels of red green blue. It accepts up to three parameters: RED, which is an int from
 *  -255 to 255, BLUE, wich is an int with the same restrictions, and GREEN, which again is an int from -255 to 255.
 *
 */
public final class SelectiveRGBChanger extends ImageToolImpl implements ParallelizableImageTool {
    private static final int MAX = 255;
    private static final int DEFAULT =  0;
    private static final Set<ParametersName> ACCEPTED = new HashSet<>(
            Arrays.asList(ParametersName.RED, ParametersName.GREEN, ParametersName.BLUE));

    private int red;
    private int blue;
    private int green;

    private SelectiveRGBChanger() {
        super();
    }
    /**
     * Creates a new SelectiveRGB changes.
     * @return a new SelectiveRGBChanges.
     */
    public static SelectiveRGBChanger createSelective() {
        return new SelectiveRGBChanger();
    }

    @Override
    public void executeFilter(final int[][] pixels, final int[][] newPixels, final Point begin, final Point end) {
        for (int i = begin.y; i < end.y; i++) {
            for (int j = begin.x; j < end.x; j++) {
                newPixels[i][j] = pixels[i][j];
                newPixels[i][j] = ColorManipulatorUtils.updateRed(newPixels[i][j], red);
                newPixels[i][j] = ColorManipulatorUtils.updateGreen(newPixels[i][j], green);
                newPixels[i][j] = ColorManipulatorUtils.updateBlue(newPixels[i][j], blue);
            }
        }
    }

    @Override
    public void inizializeFilter() {
        red = getValueFromParameter(ParametersName.RED, -MAX, MAX, DEFAULT);
        green = getValueFromParameter(ParametersName.GREEN, -MAX, MAX, DEFAULT);
        blue = getValueFromParameter(ParametersName.BLUE, -MAX, MAX, DEFAULT);
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return ACCEPTED.contains(name);
    }
}
