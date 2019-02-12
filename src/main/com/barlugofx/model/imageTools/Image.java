package com.barlugofx.model.imageTools;

/**
 *
 * This class is a wrapper that expose only the necessary information about the image we need.
 *
 */
public interface Image {
    /**
     * A method returning the RGB value for each pixel of an image.
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
