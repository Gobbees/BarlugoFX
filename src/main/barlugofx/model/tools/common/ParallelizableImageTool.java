package barlugofx.model.tools.common;

import java.awt.Point;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;

/**
 * This interface add a new method to {@link ImageTool} that allows the possibility of parallel computing if
 * necessary.
 *
 */
public interface ParallelizableImageTool extends ImageTool {
    /**
     * This method performs this tool into the given matrix pixels, assuming that each integer represents a pixel.
     * If pixels and newPixels sizes are different, than the result is undefined.
     * Note that the matrix should contain the rectangle formed by the two points, begin and end.
     * Note that before calling this method you should call {@link ParallelizableImageTool#inizializeTool()}
     * @param pixels the target pixels from which applying filter.
     * @param newPixels the pixels in which the result is saved.
     * @param begin the x coordinate and y coordinate to which start applying the filter
     * @param end the x coordinate and y coordinate to which end the filter.
     * @throws IndexOutOfBoundsException if either x, y of the two points are not into the matrix.
     */
    void executeTool(int[][] pixels, int[][] newPixels, Point begin, Point end);

    /**
     * This method performs all the thing necessary for the correct execution of
     * {@link ParallelizableImageTool#executeTool(int[][], int[][], Point, Point)}.
     * It MUST be called before the filter execution.
     */
    void inizializeTool();

    /* In this way I don't have to write again each time the basic applyFilter. */
    @Override
    default Image applyTool(final Image toApply) {
        final int[][] pixels = toApply.getImageRGBvalues();
        final int[][] newPixels = new int[pixels.length][pixels[0].length];
        inizializeTool();
        executeTool(pixels, newPixels, new Point(0, 0), new Point(toApply.getWidth(), toApply.getHeight()));
        return ImageImpl.buildFromPixels(newPixels);
    }
}
