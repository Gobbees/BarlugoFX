package com.barlugofx.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.barlugofx.model.imageTools.Image;
import com.barlugofx.model.imageTools.ImageImpl;
import com.barlugofx.model.imageTools.ImageUtilities;
import com.barlugofx.model.tools.Crop;
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
        final File file  = new File("/home/matteo/Desktop/0.jpeg");
        final BufferedImage image = ImageIO.read(file);
        final Image toWorkWith = ImageImpl.buildFromBufferedImage(image);
        final ImageFilter hsb = Crop.createCrop();
        hsb.addParameter(ParametersName.X1, new ParameterImpl<>(0));
        hsb.addParameter(ParametersName.X2, new ParameterImpl<>(300));
        hsb.addParameter(ParametersName.Y1, new ParameterImpl<>(0));
        hsb.addParameter(ParametersName.Y2, new ParameterImpl<>(300));
        final BufferedImage output = ImageUtilities.convertImageToBufferedImageWithAlpha(hsb.applyFilter(toWorkWith));
        ImageIO.write(output, "png", new File("/home/matteo/Desktop/3.png"));
        time = System.nanoTime() - time;

        System.out.println("Execution time in milliseconds : " +
                time / 1000000);

    }
}
