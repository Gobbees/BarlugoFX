package barlugofx.app;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import barlugofx.model.imagetools.Image;
import barlugofx.utils.Format;

/**
 * This interface describes the application manager (the mvc controller).
 * It recieves input from the view, computes the problem using the model and produces a result.
 */
public interface AppManager {
    /**
     * This function returns the manager's image. 
     * @return the image
     */
    Image getImage();
    /**
     * Returns the input file name.
     * @return the file name
     */
    String getInputFileName();
    /**
     * Sets the exposure to value and computes the new image.
     * @param value the exposure input value
     */
    void setExposure(int value);
    /**
     * Sets the contrast to value and computes the new image.
     * @param value the contrast input value
     */
    void setContrast(int value);
    /**
     * Sets the brightness to value and computes the new image.
     * @param value the brightness input value
     */
    void setBrightness(int value);
    /**
     * Sets the white balance to value and computes the new image.
     * @param value the wb input value
     */
    void setWB(int value);
    /**
     * Sets the saturation to value and computes the new image.
     * @param value the saturation input value
     */
    void setSaturation(int value);
    /**
     * Sets the hue to value and computes the new image.
     * @param value the hue input value
     */
    void setHue(int value);
    /**
     * Sets the vibrance to value and computes the new image.
     * @param value the vibrance input value
     */
    void setVibrance(int value);
    /**
     * Sets the selective colour regulation to r,g,b and computes the new image.
     * @param r the red input value
     * @param g the green input value
     * @param b the blue input value
     */
    void setSC(int r, int g, int b);
    /**
     * Sets the black n white regulation to r,g,b and computes the new image.
     * @param r the red input value
     * @param g the green input value
     * @param b the blue input value
     */
    void setBW(double r, double g, double b);
    /**
     * Rotates the image by requested angle.
     * @param angle the requested angle
     */
    void rotate(double angle);
    /**
     * Crops the image based on coordinates.
     * @param x1 the topleft x coordinate
     * @param y1 the topleft y coordinate
     * @param x2 the bottomright x coordinate
     * @param y2 the bottomright y coordinate
     */
    void crop(int x1, int y1, int x2, int y2);
    /**
     * Exports the image in the requested file and format.
     * @param file the output file
     * @param format the output format
     * @throws IOException if the operation fails caused by an I/O error
     * @throws InterruptedException if the operation has been interrupted unexpectedly
     * @throws ExecutionException if the operation has been interrupted unexpectedly
     */
    void exportImage(File file, Format format) throws IOException, InterruptedException, ExecutionException;
    /**
     * Export the image in the JPEG format with the requested quality.
     * @param file the output file
     * @param quality the requested quality
     * @throws IOException if the operation fails caused by an I/O error
     * @throws InterruptedException if the operation has been interrupted unexpectedly
     * @throws ExecutionException if the operation has been interrupted unexpectedly
     */
    void exportImage(File file, float quality) throws IOException, InterruptedException, ExecutionException;
    /**
     * Save the selected filters in the requested file.
     * @param filters the filters to be saved
     * @param file the output file
     * @throws IOException if the operation fails caused by an I/O error
     * @throws InterruptedException if the operation has been interrupted unexpectedly
     * @throws ExecutionException if the operation has been interrupted unexpectedly
     */
    void savePreset(Properties filters, File file) throws IOException, InterruptedException, ExecutionException;
}
