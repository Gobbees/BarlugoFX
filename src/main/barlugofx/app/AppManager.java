package barlugofx.app;

import barlugofx.model.imagetools.Image;

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
}
