package com.barlugofx.model.imageTools;


/**
 *
 * This inferface expose the function we need to work with rgb values.
 *
 */
public interface ColorManipulator {
    /**
     * Return the value from 0 to 255, of the red component for the pixel.
     * @param pixel the pixel from which we extract the red value
     * @return the red value from 0 to 255.
     * @see jav
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

    /**
     * Set the blue value of the pixel to the one of newBlueValue.
     * Node that if the new value exceeds the inferior limit or the superior one, it will be rounded to 0 or 255, respectevely.
     * @param pixel the target pixel to update;
     * @param newBlueValue the blue update;
     * @return the updated pixel.
     */
    int setBlue(int pixel, int newBlueValue);

    /**
     * Set  the green value of the pixel to the one of newGreenValue.
     * Node that if the new value exceeds the inferior limit or the superior one, it will be rounded to 0 or 255, respectevely.
     * @param pixel the target pixel to update;
     * @param newGreenValue the blue update;
     * @return the updated pixel.
     */
    int setGreen(int pixel, int newGreenValue);

    /**
     * Set  the red value of the pixel to the one of newRedValue.
     * Node that if the new value exceeds the inferior limit or the superior one, it will be rounded to 0 or 255, respectevely.
     * @param pixel the target pixel to update;
     * @param newRedValue the blue update;
     * @return the updated pixel.
     */
    int setRed(int pixel, int newRedValue);

    /**
     * Set  the alpha value of the pixel to the one of newAlphaValue.
     * Node that if the new value exceeds the inferior limit or the superior one, it will be rounded to 0 or 255, respectevely.
     * @param pixel the target pixel to update;
     * @param newAlphaValue the blue update;
     * @return the updated pixel.
     */
    int setAlpha(int pixel, int newAlphaValue);

    /**
     * Update the red value of the pixel by adding at the current value the one of valueToAdd.
     * Node that if the new value exceeds the inferior limit or the superior one, it will be rounded to 0 or 255, respectevely.
     * @param pixel the target pixel to update;
     * @param valueToAdd the value to add to the pixel;
     * @return the updated pixel.
     */
    int updateRed(int pixel, int valueToAdd);

    /**
     * Update the green value of the pixel by adding at the current value the one of valueToAdd.
     * Node that if the new value exceeds the inferior limit or the superior one, it will be rounded to 0 or 255, respectevely.
     * @param pixel the target pixel to update;
     * @param valueToAdd the value to add to the pixel;
     * @return the updated pixel.
     */
    int updateGreen(int pixel, int valueToAdd);

    /**
     * Update the blue value of the pixel by adding at the current value the one of valueToAdd.
     * Node that if the new value exceeds the inferior limit or the superior one, it will be rounded to 0 or 255, respectevely.
     * @param pixel the target pixel to update;
     * @param valueToAdd the value to add to the pixel;
     * @return the updated pixel.
     */
    int updateBlue(int pixel, int valueToAdd);

    /**
     * Update the alpha value of the pixel by adding at the current value the one of valueToAdd.
     * Node that if the new value exceeds the inferior limit or the superior one, it will be rounded to 0 or 255, respectevely.
     * @param pixel the target pixel to update;
     * @param valueToAdd the value to add to the pixel;
     * @return the updated pixel.
     */
    int updateAlpha(int pixel, int valueToAdd);

    /**
     * Create an int storing all RGB values. Alpha goes in the first 8 bit, Red in second, green in the third, blue in the last.
     * If the value of the pixels is not between 0 and 255, the behaviour in undefined.
     * @param red a value from 0 to 255.
     * @param blue a value from 0 to 255.
     * @param green a value from 0 to 255.
     * @param alpha a value from 0 to 255.
     * @return the int.
     */
    int pixelsToInt(int red, int blue, int green, int alpha);
}
