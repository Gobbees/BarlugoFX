package barlugofx.model.imageTools;

/**
 *
 * This class is a wrapper that expose only the necessary information about the image we need.
 *
 */
public interface Image {
    /**
     * A method returning the RGB value for each pixel of an image.
     * Note that the image is assumed (like it is commonly do) as a matrix containing a byte alpha (if present), a byte for blue,
     * a byte for green and a byte for red.
     * If the order is modified than any operation done on the matrix could retrieve unexpected result.
     * @return a matrix width*height containing all the pixels.
     */
    int[][] getImageRGBvalues();

    /**
     * Return the width of the image.
     * @return width.
     */
    int getWidth();

    /**
     * Return the height of the image.
     * @return height.
     */
    int getHeight();

}
