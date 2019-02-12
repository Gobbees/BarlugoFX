package com.barlugofx.model.imageTools;

/**
 * A faster implementation of the one used by java.awt.color since it does not need for each pixel a constructor.
 * The logic used is the same.
 *
 */
public final class ColorExtractorImpl implements ColorExtractor {
    private static final int CORRECTOR = 0x000000FF;
    private static final int REDSHIFT = 16;
    private static final int GREENSHIFT = 8;
    private static final int ALPHASHIFT = 24;
    private ColorExtractorImpl() {

    }

    /**
     * A static factory method for creating a ColorExtractor.
     * @return a ColorExtractor
     */
    public static ColorExtractor createColorExtractor() {
        return new ColorExtractorImpl();
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

}
