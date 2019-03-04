package barlugofx.app;

import barlugofx.model.imageTools.Image;

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
}
