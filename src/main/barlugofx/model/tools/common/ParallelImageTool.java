package barlugofx.model.tools.common;

import java.awt.Point;

/**
 * This interface add a new method to the tool that allows the possibility of parallel computing if
 * necessary.
 *
 */
public interface ParallelImageTool extends ImageTool{
    /**
     * This method perform this filter into the given matrix pixels, assuming that each integer represents a pixel.
     * Note that the matrix should contain the rectangle formed by the two points, begin and end.
     * @param pixels the matrix on which perform the operation.
     * @param begin the x coordinate and y coordinate to which start applying the filter
     * @param end the x coordinate and y coordinate to which end the filter.
     * @throws IndexOutOfBoundsException if either x, y of the two points are not into the matrix.
     */
    void executeFilter(int[][] pixels, Point begin, Point end);
}
