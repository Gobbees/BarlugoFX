package com.barlugofx.app;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.barlugofx.model.imageTools.Image;
import com.barlugofx.model.imageTools.ImageImpl;

/**
 *
 *
 */
public class IOManagerImpl {
    /**
     * This function open the input file and returns the Image to the caller.
     * @param file the input file
     * @return the opened image
     * @throws IOException if there's an error with the file operation
     */
    public Image loadImageFromFile(final File file) throws IOException {
        return ImageImpl.buildFromBufferedImage(ImageIO.read(file));
    }

}
