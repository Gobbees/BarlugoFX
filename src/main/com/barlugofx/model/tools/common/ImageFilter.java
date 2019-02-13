package com.barlugofx.model.tools.common;

import com.barlugofx.model.imageTools.Image;

/**
 * This interface represents a possible filter that can be applied to an image.
 *
 */
public interface ImageFilter {

    /**
     * Applies the filter to toApply and returns the result in a new Image.
     * @param toApply the image to which apply the filter
     * @return the edited image.
     * @throws IllegalStateException if there are not the necessary valid parameters or the parameter is not of the proper type.
     */
    Image applyFilter(Image toApply);

    /**
     * Stores the parameter value and his name to be used by the filter.
     * @param name the name of the parameter.
     * @param value the value of the parameter.
     * @throws IllegalArgumentException if the name is not appropriate for the filter or the parameter is already present.
     * Please check on filter docs that your are passing the right argument.
     */
    void addParameter(ParametersName name, Parameter<?> value);

    /**
     * Remove the parameter value and his name from the filter.
     * @param name the name of the parameter to be removed.
     * @throws IllegalArgumentException if the name of the parameter is not present.
     */
    void removeParameter(ParametersName name);
}
