package com.barlugofx.model.imageTools;

/**
 * A faster implementation of the one used by java.awt.color since it does not
 * need for each pixel a constructor. The logic used is the same.
 *
 */
public final class ColorImpl implements Color {
    private static final int CORRECTOR = 0x000000FF;
    private static final int REDSHIFT = 16;
    private static final int GREENSHIFT = 8;
    private static final int ALPHASHIFT = 24;
    private static final int SHIFT = 8;
    private static final int RESET_BLUE = 0xFFFFFF00;
    private static final int RESET_GREEN = 0xFFFF00FF;
    private static final int RESET_RED = 0xFF00FFFF;
    private static final int RESET_ALPHA = 0x00FFFFFF;
    private static final int MAX_CAP =  255;
    private static final int MIN_CAP = 0;

    private ColorImpl() {

    }

    /**
     * A static factory method for creating a ColorExtractor.
     *
     * @return a ColorExtractor
     */
    public static Color createColorExtractor() {
        return new ColorImpl();
    }

    @Override
    public int getRed(final int pixel) {
        return pixel >> REDSHIFT & CORRECTOR;
    }

    @Override
    public int getBlue(final int pixel) {
        return pixel & CORRECTOR;
    }

    @Override
    public int getGreen(final int pixel) {
        return pixel >> GREENSHIFT & CORRECTOR;
    }

    @Override
    public int getAlpha(final int pixel) {
        return pixel >> ALPHASHIFT & CORRECTOR;
    }

    @Override
    public int pixelsToInt(final int red, final int blue, final int green, final int alpha) {
        int rgb = alpha;
        rgb = (rgb << SHIFT) + red;
        rgb = (rgb << SHIFT) + green;
        rgb = (rgb << SHIFT) + blue;
        return rgb;
    }

    @Override
    public int setBlue(final int pixel, final int newBlueValue) {
        return (pixel & RESET_BLUE) + truncate(newBlueValue);
    }

    @Override
    public int setGreen(final int pixel, final int newGreenValue) {
        return (pixel & RESET_GREEN) + (truncate(newGreenValue) << GREENSHIFT);
    }

    @Override
    public int setRed(final int pixel, final int newRedValue) {
        return (pixel & RESET_RED) + (truncate(newRedValue) << REDSHIFT);
    }

    @Override
    public int setAlpha(final int pixel, final int newAlphaValue) {
        return (pixel & RESET_ALPHA) + (truncate(newAlphaValue) << ALPHASHIFT);
    }

    @Override
    public int updateRed(final int pixel, final int valueToAdd) {
        return setRed(pixel, getRed(pixel) + valueToAdd);
    }

    @Override
    public int updateGreen(final int pixel, final int valueToAdd) {
        return setGreen(pixel, getGreen(pixel) + valueToAdd);
    }

    @Override
    public int updateBlue(final int pixel, final int valueToAdd) {
        return setBlue(pixel, getBlue(pixel) + valueToAdd);
    }

    @Override
    public int updateAlpha(final int pixel, final int valueToAdd) {
        return setAlpha(pixel, getAlpha(pixel) + valueToAdd);
    }


    private int truncate(final int rgbValue) {
        if (rgbValue > MAX_CAP) {
            return MAX_CAP;
        }
        if (rgbValue < MIN_CAP) {
            return MIN_CAP;
        }
        return rgbValue;
    }
}
