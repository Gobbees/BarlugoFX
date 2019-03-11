package barlugofx.model.tools.common;

import java.util.Optional;

import barlugofx.model.imagetools.Image;

/**
 * This interface represents a possible filter that can be applied to an image.
 *
 */
public interface ImageTool {

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
    void addParameter(ParametersName name, Parameter<? extends Number> value);

    /**
     * This function return (optionally) the parameter associated with the name.
     * @param name the name of the parameter to get
     * @return the parameter
     *
     */
    Optional<Parameter<? extends Number>> getParameter(ParametersName name);

    /**
     * Remove the parameter value and his name from the filter.
     * @param name the name of the parameter to be removed.
     */
    void removeParameter(ParametersName name);
}
