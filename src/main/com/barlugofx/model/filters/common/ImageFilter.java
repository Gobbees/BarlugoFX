package com.barlugofx.model.filters.common;

import com.barlugofx.model.imageTools.Image;

/**
 * This interface represents a possible filter that can be applied to an image.
 *
 */
public interface ImageFilter {

    /**
     * Applies the filter to an Image. Note that after this function toApply will be modified.
     * @param toApply the image to which apply the filter
     * @return the edited image.
     * @throws IllegalStateException if there are not the necessary valid parameters.
     */
    Image applyFilter(Image toApply);

    /**
     * Stores the parameter value and his name to be used by the filter.
     * @param name the name of the parameter.
     * @param value the value of the parameter.
     * @throws IllegalArgumentException if the name or value is not appropriate for the filter. Please check on filter docs
     * that your are passing the right argument.
     */
    void addParameter(String name, Parameter<?> value);

    /**
     * Remove the parameter value and his name from the filter.
     * @param name the name of the parameter to be removed.
     * @throws IllegalArgumentException if the name of the parameter is not present.
     */
    void removeParameter(String name);
}
