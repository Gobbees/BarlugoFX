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
        return (pixel & RESET_BLUE) + newBlueValue;
    }

    @Override
    public int setGreen(final int pixel, final int newGreenValue) {
        return (pixel & RESET_GREEN) + (newGreenValue << GREENSHIFT);
    }

    @Override
    public int setRed(final int pixel, final int newRedValue) {
        return (pixel & RESET_RED) + (newRedValue << REDSHIFT);
    }

    @Override
    public int setAlpha(final int pixel, final int newAlphaValue) {
        return (pixel & RESET_ALPHA) + (newAlphaValue << ALPHASHIFT);
    }

    @Override
    public int updateRed(final int pixel, final int valueToAdd) {
        final int newRed = getRed(pixel) + valueToAdd;
        if (newRed > MAX_CAP) {
            return setRed(pixel, MAX_CAP);
        }
        if (newRed < MIN_CAP) {
            return setRed(pixel, MIN_CAP);
        }
        return setRed(pixel, newRed);
    }

    @Override
    public int updateGreen(final int pixel, final int valueToAdd) {
        final int newGreen = getGreen(pixel) + valueToAdd;
        if (newGreen > MAX_CAP) {
            return setGreen(pixel, MAX_CAP);
        }
        if (newGreen < MIN_CAP) {
            return setGreen(pixel, MIN_CAP);
        }
        return setGreen(pixel, newGreen);
    }

    @Override
    public int updateBlue(final int pixel, final int valueToAdd) {
        final int newBlue = getBlue(pixel) + valueToAdd;
        if (newBlue > MAX_CAP) {
            return setBlue(pixel, MAX_CAP);
        }
        if (newBlue < MIN_CAP) {
            return setBlue(pixel, MIN_CAP);
        }
        return setBlue(pixel, newBlue);
    }

    @Override
    public int updateAlpha(final int pixel, final int valueToAdd) {
        final int newAlpha = getAlpha(pixel) + valueToAdd;
        if (newAlpha > MAX_CAP) {
            return setAlpha(pixel, MAX_CAP);
        }
        if (newAlpha < MIN_CAP) {
            return setAlpha(pixel, MIN_CAP);
        }
        return setAlpha(pixel, newAlpha);
    }

}
