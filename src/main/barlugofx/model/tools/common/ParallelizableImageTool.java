package barlugofx.model.tools.common;

import java.awt.Point;

/**
 * This interface add a new method to the tool that allows the possibility of parallel computing if
 * necessary.
 *
 */
public interface ParallelizableImageTool extends ImageTool {
    /**
     * This method perform this filter into the given matrix pixels, assuming that each integer represents a pixel.
     * If pixels and newPixels sizes are different, than the result is undefined.
     * Note that the matrix should contain the rectangle formed by the two points, begin and end.
     * @param pixels the target pixels from which applying filter.
     * @param newPixels the pixels in which the result is saved.
     * @param begin the x coordinate and y coordinate to which start applying the filter
     * @param end the x coordinate and y coordinate to which end the filter.
     * @throws IndexOutOfBoundsException if either x, y of the two points are not into the matrix.
     */
    void executeFilter(int[][] pixels, int[][] newPixels, Point begin, Point end);
}
