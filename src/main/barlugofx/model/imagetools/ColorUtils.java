package barlugofx.model.imagetools;

import java.awt.Color;

/**
 * A faster implementation of the one used by java.awt.color since it does not
 * need for each pixel a constructor. The logic used is the same.
 * @see Color
 *
 */
public final class ColorUtils {
    private static final int CORRECTOR = 0x000000FF;
    private static final int REDSHIFT = 16;
    private static final int GREENSHIFT = 8;
    private static final int ALPHASHIFT = 24;
    private static final int SHIFT = 8;
    private static final int RESET_BLUE = 0xFFFFFF00;
    private static final int RESET_GREEN = 0xFFFF00FF;
    private static final int RESET_RED = 0xFF00FFFF;
    private static final int RESET_ALPHA = 0x00FFFFFF;
    private static final int MAX_CAP = 255;
    private static final int MIN_CAP = 0;

    private ColorUtils() {

    }

    /**
     * Return the value from 0 to 255, of the red component for the pixel.
     *
     * @param pixel the pixel from which we want to extract the red value
     * @return the red value from 0 to 255.
     */
    public static int getRed(final int pixel) {
        return pixel >> REDSHIFT & CORRECTOR;
    }

    /**
     * Return the value from 0 to 255, of the blue component for the pixel.
     *
     * @param pixel the pixel from which we extract the blue value
     * @return the red value from 0 to 255.
     */
    public static int getBlue(final int pixel) {
        return pixel & CORRECTOR;
    }

    /**
     * Return the value from 0 to 255, of the green component for the pixel.
     *
     * @param pixel the pixel from which we want to extract the green value
     * @return the red value from 0 to 255.
     */
    public static int getGreen(final int pixel) {
        return pixel >> GREENSHIFT & CORRECTOR;
    }

    /**
     * Return the value from 0 to 255, of the alpha component for the pixel.
     *
     * @param pixel the pixel from which we want to extract the alpha value
     * @return the red value from 0 to 255.
     */
    public static int getAlpha(final int pixel) {
        return pixel >> ALPHASHIFT & CORRECTOR;
    }

    /**
     * Create an int storing all RGB values. Alpha goes in the first 8 bit, Red in
     * second, green in the third, blue in the last. If the value of the pixels is
     * not between 0 and 255, the behavior in undefined.
     *
     * @param red   a value from 0 to 255.
     * @param blue  a value from 0 to 255.
     * @param green a value from 0 to 255.
     * @param alpha a value from 0 to 255.
     * @return a 4 byte int containing all the values.
     */
    public static int pixelsToInt(final int red, final int blue, final int green, final int alpha) {
        int rgb = alpha;
        rgb = (rgb << SHIFT) + red;
        rgb = (rgb << SHIFT) + green;
        rgb = (rgb << SHIFT) + blue;
        return rgb;
    }

    /**
     * Set the blue value of the pixel to the one of newBlueValue. Node that if the
     * new value exceeds the inferior limit or the superior one, it will be rounded
     * to 0 or 255, respectively.
     *
     * @param pixel        the target pixel to update;
     * @param newBlueValue the blue update;
     * @return the updated pixel.
     */
    public static int setBlue(final int pixel, final int newBlueValue) {
        return (pixel & RESET_BLUE) + truncate(newBlueValue);
    }

    /**
     * Set the green value of the pixel to the one of newGreenValue. Node that if
     * the new value exceeds the inferior limit or the superior one, it will be
     * rounded to 0 or 255, respectively.
     *
     * @param pixel         the target pixel to update;
     * @param newGreenValue the blue update;
     * @return the updated pixel.
     */
    public static int setGreen(final int pixel, final int newGreenValue) {
        return (pixel & RESET_GREEN) + (truncate(newGreenValue) << GREENSHIFT);
    }

    /**
     * Set the red value of the pixel to the one of newRedValue. Node that if the
     * new value exceeds the inferior limit or the superior one, it will be rounded
     * to 0 or 255, respectively.
     *
     * @param pixel       the target pixel to update;
     * @param newRedValue the blue update;
     * @return the updated pixel.
     */
    public static int setRed(final int pixel, final int newRedValue) {
        return (pixel & RESET_RED) + (truncate(newRedValue) << REDSHIFT);
    }

    /**
     * Set the alpha value of the pixel to the one of newAlphaValue. Node that if
     * the new value exceeds the inferior limit or the superior one, it will be
     * rounded to 0 or 255, respectively.
     *
     * @param pixel         the target pixel to update;
     * @param newAlphaValue the blue update;
     * @return the updated pixel.
     */
    public static int setAlpha(final int pixel, final int newAlphaValue) {
        return (pixel & RESET_ALPHA) + (truncate(newAlphaValue) << ALPHASHIFT);
    }

    /**
     * Update the red value of the pixel by adding at the current value the one of
     * valueToAdd. Note that if the new value exceeds the inferior limit or the
     * superior one, it will be rounded to 0 or 255, respectively.
     *
     * @param pixel      the target pixel to update;
     * @param valueToAdd the value to add to the pixel;
     * @return the updated pixel.
     */
    public static int updateRed(final int pixel, final int valueToAdd) {
        return setRed(pixel, getRed(pixel) + valueToAdd);
    }

    /**
     * Update the green value of the pixel by adding at the current value the one of
     * valueToAdd. Note that if the new value exceeds the inferior limit or the
     * superior one, it will be rounded to 0 or 255, respectively.
     *
     * @param pixel      the target pixel to update;
     * @param valueToAdd the value to add to the pixel;
     * @return the updated pixel.
     */
    public static int updateGreen(final int pixel, final int valueToAdd) {
        return setGreen(pixel, getGreen(pixel) + valueToAdd);
    }

    /**
     * Update the blue value of the pixel by adding at the current value the one of
     * valueToAdd. Note that if the new value exceeds the inferior limit or the
     * superior one, it will be rounded to 0 or 255, respectively.
     *
     * @param pixel      the target pixel to update;
     * @param valueToAdd the value to add to the pixel;
     * @return the updated pixel.
     */
    public static int updateBlue(final int pixel, final int valueToAdd) {
        return setBlue(pixel, getBlue(pixel) + valueToAdd);
    }

    /**
     * Update the alpha value of the pixel by adding at the current value the one of
     * valueToAdd. Note that if the new value exceeds the inferior limit or the
     * superior one, it will be rounded to 0 or 255, respectively.
     *
     * @param pixel      the target pixel to update;
     * @param valueToAdd the value to add to the pixel;
     * @return the updated pixel.
     */
    public static int updateAlpha(final int pixel, final int valueToAdd) {
        return setAlpha(pixel, getAlpha(pixel) + valueToAdd);
    }

    /**
     * Truncate a color between 0 and 255.
     *
     * @param rgbValue the color to truncate
     * @return the truncated color.
     */
    public static int truncate(final int rgbValue) {
        if (rgbValue > MAX_CAP) {
            return MAX_CAP;
        }
        if (rgbValue < MIN_CAP) {
            return MIN_CAP;
        }
        return rgbValue;
    }
}
