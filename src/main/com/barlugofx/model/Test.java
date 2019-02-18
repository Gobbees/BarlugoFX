package com.barlugofx.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.barlugofx.model.imageTools.Image;
import com.barlugofx.model.imageTools.ImageImpl;
import com.barlugofx.model.imageTools.ImageUtilities;
import com.barlugofx.model.tools.HSBModifier;
import com.barlugofx.model.tools.Vibrance;
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
        final File file  = new File("/home/matteo/Desktop/Prova.JPG");
        final BufferedImage image = ImageIO.read(file);
        final Image toWorkWith = ImageImpl.buildFromBufferedImage(image);
        final ImageFilter vibrance = Vibrance.createVibrance();
        final ImageFilter hsbTrue = HSBModifier.createHSB();
        long time = System.nanoTime();
        hsbTrue.addParameter(ParametersName.SATURATION, new ParameterImpl<>(0.2f));
        vibrance.addParameter(ParametersName.VIBRANCE_INCREMENT, new ParameterImpl<>(0.2f));
        final BufferedImage output = ImageUtilities.convertImageToBufferedImageWithAlpha(hsbTrue.applyFilter(toWorkWith));

        final BufferedImage outputB = ImageUtilities.convertImageToBufferedImageWithAlpha(vibrance.applyFilter(toWorkWith));
        time = System.nanoTime() - time;
        ImageIO.write(output, "png", new File("/home/matteo/Desktop/3.png"));
        ImageIO.write(outputB, "png", new File("/home/matteo/Desktop/4.png"));
        System.out.println("Execution time in milliseconds : " +
                time / 1000000);

    }
}
