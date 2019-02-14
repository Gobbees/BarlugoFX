package com.barlugofx.model.imageTools;

import java.awt.Color;
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
     * Converts a matrix of pixels to a bufferedImage of TYPE 4BYTE ABGR.
     * NOTE that if you then want to print this image using something like ImageIo.write, this function will work only with
     * file format supporting an alpha channel, like png.
     * @param input the image from which obtaining the bufferedImage.
     * @return the BufferedImage.
     */
    public static BufferedImage convertImageToBufferedImageWithAlpha(final Image input) {
        final int width = input.getWidth();
        final int height = input.getHeight();
        final int[][] pixels = input.getImageRGBvalues();
        final BufferedImage target = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        final byte[] targetPixel = ((DataBufferByte) target.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                final int element = pixels[i][j];
                targetPixel[i * width * 4 + j * 4] = (byte) (element >>> ALPHASHIFT);
                targetPixel[i * width * 4 + j * 4 + 1] =  (byte) element;
                targetPixel[i * width * 4 + j * 4 + 2] = (byte) (element >>> GREENSHIFT);
                targetPixel[i * width * 4 + j * 4 + 3] = (byte) (element >>> REDSHIFT);
            }
        }
        return target;
    }

    /**
     * Converts a matrix of pixels to a bufferedImage of TYPE 3BYTE BGR.
     * NOTE that if you then want to print this image using something like ImageIo.write, this function will work only with
     * file format not supporting an alpha channel, like jpg.
     * @param input the image from which obtaining the bufferedImage.
     * @return the BufferedImage.
     */
    public static BufferedImage convertImageToBufferedImageWithoutAlpha(final Image input) {
        final int width = input.getWidth();
        final int height = input.getHeight();
        final int[][] pixels = input.getImageRGBvalues();
        final BufferedImage target = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        final byte[] targetPixel = ((DataBufferByte) target.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                final int element = pixels[i][j];
                targetPixel[i * width * 3 + j * 3] =  (byte) element;
                targetPixel[i * width * 3 + j * 3 + 1] = (byte) (element >>> GREENSHIFT);
                targetPixel[i * width * 3 + j * 3 + 2] = (byte) (element >>> REDSHIFT);
            }
        }
        return target;
    }

    /**
     * This method convert a matrix of pixels written as RGB into an equivalent matrix of float value ins HSB, with H value being the
     * [i][j][0],  S value [i][j][1] and B value [i][j][2].This functions uses the static method RGBtoHSB of java.awt.Color .
     * @param pixels the matrix to convert
     * @return the converted float matrix.
     */
    public static float[][][] rgbToHsb(final int[][] pixels) {
        final float[][][] result = new float[pixels.length][pixels[0].length][3];
        final ColorManipulator getter = ColorManipulatorImpl.createColorExtractor();
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                Color.RGBtoHSB(getter.getRed(pixels[i][j]), getter.getGreen(pixels[i][j]), getter.getBlue(pixels[i][j]),
                        result[i][j]);
            }
        }
        return result;
    }

    /**
     * This method converts hsb values into a matrix of rgb pixels. The hsv values has to be passed as a 3-D float array, in which the
     * [i][j][0] value corresponds to Hue, the [i][j][1] to Saturation and the [i][j][2]  to Brightness. Alpha value (if present) is not
     * handled.
     * @param pixelsHSB the float 3-D array that we need to convert.
     * @return the converted pixels matrix
     */
    public static int[][] hsbToRgb(final float[][][] pixelsHSB){
        final int[][] pixels = new int[pixelsHSB.length][pixelsHSB[0].length];
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                pixels[i][j] = Color.HSBtoRGB(pixelsHSB[i][j][0], pixelsHSB[i][j][1], pixelsHSB[i][j][2]);
            }
        }
        return pixels;
    }
}
