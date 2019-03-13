package com.barlugofx.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.imagetools.ImageUtils;
import barlugofx.model.tools.Brightness;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParameterImpl;
import barlugofx.model.tools.common.ParametersName;
import barlugofx.model.tools.parallelHandler.ParallelFilterExecutor;

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
        final File file  = new File("/home/matteo/Desktop/Prova.jpg");
        final BufferedImage image = ImageIO.read(file);
        final Image toWorkWith = ImageImpl.buildFromBufferedImage(image);
        final ParallelizableImageTool brightness = Brightness.createBrightness();
        brightness.addParameter(ParametersName.BRIGHTNESS, new ParameterImpl<>(150));

        long time1 = System.nanoTime();
        final BufferedImage output = ImageUtils.convertImageToBufferedImageWithAlpha(brightness.applyFilter(toWorkWith));
        time1 = System.nanoTime() - time1;

        final ParallelFilterExecutor exec = ParallelFilterExecutor.executor();
        long time2 = System.nanoTime();
        final BufferedImage outpu2 = ImageUtils.convertImageToBufferedImageWithAlpha(exec.applyTool(brightness, toWorkWith));
        time2 = System.nanoTime() - time2;
        System.out.println("NO parallel : " +
                time1 / 1000000);
        System.out.println("YES parallel : " +
                time2 / 1000000);
        ImageIO.write(output, "png", new File("/home/matteo/Desktop/3.png"));


    }
}

