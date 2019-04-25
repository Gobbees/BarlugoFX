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
 * This class allows to change selectively the 3 channels of red green blue in an {@link Image}. It
 * accepts up to three parameters: RED, GREEN and BLUE, which are an ints from -255 to 255.
 *
 */
public final class SelectiveRGBChanger extends AbstractImageTool implements ParallelizableImageTool {
    private static final int MAX = 255;
    private static final int DEFAULT_VALUE =  0;
    private static final Set<ParameterName> ACCEPTED = new HashSet<>(
            Arrays.asList(ParameterName.RED, ParameterName.GREEN, ParameterName.BLUE));

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
    public void executeTool(final int[][] pixels, final int[][] newPixels, final Point begin, final Point end) {
        for (int i = begin.y; i < end.y; i++) {
            for (int j = begin.x; j < end.x; j++) {
                newPixels[i][j] = pixels[i][j];
                newPixels[i][j] = ColorUtils.updateRed(newPixels[i][j], red);
                newPixels[i][j] = ColorUtils.updateGreen(newPixels[i][j], green);
                newPixels[i][j] = ColorUtils.updateBlue(newPixels[i][j], blue);
            }
        }
    }

    @Override
    public void inizializeTool() {
        red = getValueFromParameter(ParameterName.RED, -MAX, MAX, DEFAULT_VALUE);
        green = getValueFromParameter(ParameterName.GREEN, -MAX, MAX, DEFAULT_VALUE);
        blue = getValueFromParameter(ParameterName.BLUE, -MAX, MAX, DEFAULT_VALUE);
    }

    @Override
    protected boolean isAccepted(final ParameterName name) {
        return ACCEPTED.contains(name);
    }

    @Override
    public Tools getToolType() {
        return Tools.SELECTIVECOLOR;
    }

    /**
     * Return the default value for this tool.
     * @return the default value.
     */
    public static int getDefaultValue() {
        return DEFAULT_VALUE;
    }
}
