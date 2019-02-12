package com.barlugofx.model.imageTools;


/**
 *
 * This inferface expose the function we need to work with rgb values.
 *
 */
public interface Color {
    /**
     * Return the value from 0 to 255, of the red component for the pixel.
     * @param pixel the pixel from which we extract the red value
     * @return the red value from 0 to 255.
     */
    int getRed(int pixel);
    /**
     * Return the value from 0 to 255, of the blue component for the pixel.
     * @param pixel the pixel from which we extract the blue value
     * @return the red value from 0 to 255.
     */
    int getBlue(int pixel);
    /**
     * Return the value from 0 to 255, of the green component for the pixel.
     * @param pixel the pixel from which we extract the green value
     * @return the red value from 0 to 255.
     */
    int getGreen(int pixel);
    /**
     * Return the value from 0 to 255, of the alpha component for the pixel.
     * @param pixel the pixel from which we extract the alpha value
     * @return the red value from 0 to 255.
     */
    int getAlpha(int pixel);
}
