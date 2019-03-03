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
     * @param red
     * @param green
     * @param blue
     */
    void setBW(final double red, final double green, final double blue);
    
    /**
     * @param value
     */
    void setBrightness(final int value);
}
