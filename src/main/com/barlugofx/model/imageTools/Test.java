package com.barlugofx.model.imageTools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * A Rapid TEST.
 *
 *
 */
public class Test {

    /**
     * TEST.
     * @param args a.
     * @throws IOException a.
     */
    public static void main(final String[] args) throws IOException {
        final File file  = new File("/home/matteo/Desktop/a.jpg");
        final BufferedImage image = ImageIO.read(file);
        final Image toWorkWith = ImageImpl.buildFromBufferedImage(image);
        System.out.println(toWorkWith.getHeight());
        System.out.println(toWorkWith.getWidth());

        final Color changeColor = ColorImpl.createColorExtractor();
        final int[][] removeRed = toWorkWith.getImageRGBvalues();
        for (int i = 0; i < removeRed.length; i++) {
            for (int j = 0; j < removeRed[0].length; j++) {

                removeRed[i][j] = changeColor.setGreen(removeRed[i][j], 0);
            }
        }
        final BufferedImage output = ImageImpl.convertPixelsToBufferedImage(removeRed);
        ImageIO.write(output, "jpg", new File("/home/matteo/Desktop/b.jpg"));

        System.out.println(Arrays.toString(ImageIO.getWriterFormatNames()));

    }
}
