package barlugofx.app;

import java.io.File;
import java.io.IOException;

import barlugofx.model.imageTools.Image;
import barlugofx.utils.Format;

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
    /**
     * Returns the input file name.
     * @return the file name
     */
    String getFileName();
    /**
     * Saves the image in the output file with the output format.
     * @param image the image to be saved
     * @param file the output file
     * @param format the output format
     * @throws IOException if the operation fails
     */
    void exportImage(Image image, File file, Format format) throws IOException;
    /**
     * Saves the image in the output file with the JPEG format and the requested quality.
     * @param image the image to be saved
     * @param file the output file
     * @param quality the requested quality
     * @throws IOException if the operation fails
     */
    void exportJPEGWithQuality(Image image, File file, float quality) throws IOException;
}
