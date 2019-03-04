package barlugofx.app;

import java.io.File;
import java.io.IOException;

import barlugofx.model.imageTools.Image;

/**
 * This interface defines the components that manages all the I/O operations.
 *
 */
public interface IOManager {
    /**
     * This function open the input file and returns the Image to the caller.
     * @param file the input file
     * @return the opened image
     * @throws IOException if there's an error with the file operation
     */
    Image loadImageFromFile(File file) throws IOException;
}
