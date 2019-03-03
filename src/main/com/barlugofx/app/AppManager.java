package com.barlugofx.app;

import com.barlugofx.model.imageTools.Image;

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
     * This function, taken the input, computes the new image applying bw tool on it.
     * @param red the wred value
     * @param green the wgreen value
     * @param blue the wblue value
     */
    void setBW(double red, double green, double blue);

    /**
     * This function, taken the input, computes the new image applying brightness tool on it.
     * @param value the brightness value
     */
    void setBrightness(int value);
}
