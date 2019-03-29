package barlugofx.model.imagetools;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * This class contains a series of utilities specific for Image class and that can be changed any time.
 *
 */
public final class ImageUtils {
    private static final int REDSHIFT = 16;
    private static final int GREENSHIFT = 8;
    private static final int ALPHASHIFT = 24;

    private ImageUtils() {

    }
    /**
     * Converts a matrix of pixels to a bufferedImage of TYPE 4BYTE ABGR.
     * NOTE that if you then want to print this image using something like ImageIo.write, this function will work only with
     * file format supporting an alpha channel, like .png .
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
     * This method convert a matrix of pixels written as RGB into an equivalent
     * matrix of float value ins HSB, with H value being the [i][j][0], S value
     * [i][j][1] and B value [i][j][2].This functions uses the static method
     * RGBtoHSB.
     *
     * @param pixels the matrix to convert
     * @param begin  the point from which start the conversion.
     * @param end    the point at which stop the conversion.
     * @return the converted float matrix.
     * @see Color
     * @deprecated it's always better if you have to then get back to RGB to use one float at a time.
     */
    @Deprecated
    public static float[][][] rgbToHsb(final Point begin, final Point end, final int[]... pixels) {
        final float[][][] result = new float[end.y - begin.y][end.x - begin.x][3];
        for (int i = begin.y; i < end.y; i++) {
            for (int j = begin.x; j < end.x; j++) {
                Color.RGBtoHSB(ColorUtils.getRed(pixels[i][j]), ColorUtils.getGreen(pixels[i][j]),
                        ColorUtils.getBlue(pixels[i][j]), result[i - begin.y][j - begin.x]);
            }
        }
        return result;
    }

    /**
     * This method converts HSB values into a matrix of RGB pixels. The HSV values
     * has to be passed as a 3-D float array, in which the [i][j][0] value
     * corresponds to Hue, the [i][j][1] to Saturation and the [i][j][2] to
     * Brightness. Alpha value is taken from old pixels.
     *
     * @param pixelsHSB the float 3-D array that we need to convert.
     * @param oldPixels the old values from which take the alpha.
     * @param begin     the point from which start the conversion.
     * @param pixels    the matrix in which save the new values.
     * @see Color
     * @deprecated it's always better if you have to then get back to RGB to use one float at a time.
     */
    @Deprecated
    public static void hsbToRgb(final int[][] oldPixels, final int[][] pixels, final Point begin, final float[][]... pixelsHSB) {
        int iPixels;
        int jPixels;
        for (int i = 0; i < pixelsHSB.length; i++) {
            iPixels = i + begin.y;
            for (int j = 0; j < pixelsHSB[0].length; j++) {
                jPixels = j + begin.x;
                pixels[iPixels][jPixels] = Color.HSBtoRGB(pixelsHSB[i][j][0], pixelsHSB[i][j][1], pixelsHSB[i][j][2]);
                pixels[iPixels][jPixels] = ColorUtils.setAlpha(pixels[iPixels][jPixels],
                        ColorUtils.getAlpha(oldPixels[iPixels][jPixels]));
            }
        }
    }
}
