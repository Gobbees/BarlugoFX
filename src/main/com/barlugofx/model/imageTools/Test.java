package com.barlugofx.model.imageTools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        long time = System.nanoTime();
        final File file  = new File("/home/matteo/Desktop/a.png");
        final BufferedImage image = ImageIO.read(file);
        final Image toWorkWith = ImageImpl.buildFromBufferedImage(image);

        final Color changeColor = ColorImpl.createColorExtractor();
        final int[][] removeRed = toWorkWith.getImageRGBvalues();
        for (int i = 0; i < removeRed.length; i++) {
            for (int j = 0; j < removeRed[0].length; j++) {

                removeRed[i][j] = changeColor.setAlpha(removeRed[i][j], 0);
            }
        }
        final BufferedImage output = ImageImpl.convertPixelsToBufferedImage(removeRed);
        ImageIO.write(output, "png", new File("/home/matteo/Desktop/b.png"));
        time = System.nanoTime() - time;

        System.out.println("Execution time in milliseconds : " +
                time / 1000000);

    }
}
