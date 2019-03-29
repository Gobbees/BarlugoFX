package barlugofx.model.imagetools;

/**
 *
 * The Image interface represents graphical images.
 *
 */
public interface Image {

    /**
     * This method allows to edit the underlying matrix of pixels in the image.
     * Note that the image is assumed (like it is commonly do) as a matrix containing a byte alpha (if present), a byte for blue,
     * a byte for green and a byte for red.
     * If the order is modified than any operation done on the matrix could retrieve unexpected result.
     * @return a matrix height * width containing all the pixels, where each int represents one pixel.
     */
    int[][] getImageRGBvalues();

    /**
     * The width of the image.
     * @return width the width of the image
     */
    int getWidth();

    /**
     * Return the height of the image.
     * @return height the height of the image.
     */
    int getHeight();

}
