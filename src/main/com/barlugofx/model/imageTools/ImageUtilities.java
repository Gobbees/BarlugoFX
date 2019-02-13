package com.barlugofx.model.imageTools;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * This class contains a series of utilites specific for Image class and that can be changed anytime.
 *
 */
public final class ImageUtilities {
    private static final int REDSHIFT = 16;
    private static final int GREENSHIFT = 8;
    private static final int ALPHASHIFT = 24;

    private ImageUtilities() {

    }
    /**
     * Converts a matrix of pixels to a bufferedImage.
     * @param input the image from which obtaining the bufferedImage.
     * @return the BufferedImage.
     */
    public static BufferedImage convertImageToBufferedImage(final Image input) {
        final int width = input.getWidth();
        final int height = input.getHeight();
        final int[][] pixels = input.getImageRGBvalues();
        final BufferedImage target = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        final byte[] targetPixel = ((DataBufferByte) target.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                final int element = pixels[i][j];
                targetPixel[i * width * 4 + j * 4] = (byte) (element >> ALPHASHIFT);
                targetPixel[i * width * 4 + j * 4 + 1] = (byte) (element >> REDSHIFT);
                targetPixel[i * width * 4 + j * 4 + 2] = (byte) (element >> GREENSHIFT);
                targetPixel[i * width * 4 + j * 4 + 3] = (byte) element;
            }
        }
        return target;
    }
}
