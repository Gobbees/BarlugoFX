package com.barlugofx.model.imageTools;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A fast implementation of ImageImpl.
 *
 *
 */
public final class ImageImpl implements Image {
    private static final int CORRECTOR = 0xFF;
    private static final int REDSHIFT = 16;
    private static final int GREENSHIFT = 8;
    private static final int ALPHASHIFT = 24;
    private static final int ALPHAVALUE = -16777216; // If the alpha channel is not present, this means that the alpha
    // is 255, in a 32 bit
    // integer, this means that the first 8 are at 1, which is exactly the number
    // above

    private final int width;
    private final int height;
    private final boolean hasAlphaChannel;
    private final int[][] pixels;
    private static final Set<Integer> ACCEPTED_BUFFERIMG_TYPE = new HashSet<>(
            Arrays.asList(BufferedImage.TYPE_3BYTE_BGR,
                    BufferedImage.TYPE_4BYTE_ABGR,
                    BufferedImage.TYPE_4BYTE_ABGR_PRE,
                    BufferedImage.TYPE_INT_BGR));

    private ImageImpl(final BufferedImage image) {
        width = image.getWidth();
        height = image.getHeight();
        hasAlphaChannel = image.getAlphaRaster() != null;
        pixels = constructPixels(image);
    }

    private ImageImpl(final int[][] pixels) {
        width = pixels[0].length;
        height = pixels.length;
        hasAlphaChannel = true;
        this.pixels = pixels;
    }

    /**
     * Static factory method for building an Image.
     *
     * @param image the bufferedImage to startWith.
     * @return a new builded image.
     */
    public static Image buildFromBufferedImage(final BufferedImage image) {
        if (!ACCEPTED_BUFFERIMG_TYPE.contains(image.getType())) {
            throw new IllegalArgumentException(
                    "The Buffered Image is not a BGR but an RGB. Please convert it to a BGR");
        }
        return new ImageImpl(image);
    }

    /**
     * Builds an image starting from its matrix of pixels. Note that any successive modification on the pixels will result in a modification
     * of the image.
     * @param pixels the matrix from which we build on
     * @return a new Image
     */
    public static Image buildFromPixels(final int[][] pixels) {
        return new ImageImpl(pixels);
    }

    @Override
    public int[][] getImageRGBvalues() {
        return pixels;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    private int[][] constructPixels(final BufferedImage image) {
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        final int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (pixels[pixel] & CORRECTOR) << ALPHASHIFT; // alpha
                argb += pixels[pixel + 1] & CORRECTOR; // blue
                argb += (pixels[pixel + 2] & CORRECTOR) << GREENSHIFT; // green
                argb += (pixels[pixel + 3] & CORRECTOR) << REDSHIFT; // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += ALPHAVALUE; // 255 alpha
                argb += pixels[pixel] & CORRECTOR; // blue
                argb += (pixels[pixel + 1] & CORRECTOR) << GREENSHIFT; // green
                argb += (pixels[pixel + 2] & CORRECTOR) << REDSHIFT; // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;
    }
}
