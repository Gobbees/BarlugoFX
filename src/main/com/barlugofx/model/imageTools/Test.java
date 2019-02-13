package com.barlugofx.model.imageTools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.barlugofx.model.tools.Contrast;
import com.barlugofx.model.tools.common.ImageFilter;
import com.barlugofx.model.tools.common.ParameterImpl;
import com.barlugofx.model.tools.common.ParametersName;

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
        final File file  = new File("/home/matteo/Desktop/a.jpg");
        final BufferedImage image = ImageIO.read(file);
        final Image toWorkWith = ImageImpl.buildFromBufferedImage(image);
        final ImageFilter contrast = Contrast.createContrast();
        contrast.addParameter(ParametersName.CONTRAST, new ParameterImpl<>(-128));
        final BufferedImage output = ImageUtilities.convertImageToBufferedImage(contrast.applyFilter(toWorkWith));
        ImageIO.write(output, "jpg", new File("/home/matteo/Desktop/b.png"));
        time = System.nanoTime() - time;

        System.out.println("Execution time in milliseconds : " +
                time / 1000000);

    }
}
