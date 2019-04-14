package barlugofx.model.tools.common;

import java.util.Optional;

import barlugofx.model.imagetools.Image;
import barlugofx.model.tools.Tools;

/**
 * This interface models any tool or modification that can be applied to an {@link Image}.
 *
 */
public interface ImageTool {

    /**
     * Applies the tool to toApply and returns the result in a new Image.
     * @param toApply the image to which apply the filter
     * @return the edited image.
     * @throws IllegalStateException if there are not the necessary valid parameters or the parameter is not of the proper type.
     */
    Image applyTool(Image toApply);

    /**
     * Stores the parameter value and his name to be used by the filter.
     * @param name the name of the parameter.
     * @param value the value of the parameter.
     * @throws IllegalArgumentException if the name is not appropriate for the filter or the parameter is already present.
     * Please check on each filter documentation that your are passing the right argument.
     */
    void addParameter(ParameterName name, Parameter<? extends Number> value);

    /**
     * This function return (optionally) the parameter associated with the name.
     * @param name the name of the parameter to get
     * @return the parameter
     *
     */
    Optional<Parameter<? extends Number>> getParameter(ParameterName name);

    /**
     * Remove the parameter value and his name from the filter.
     * @param name the name of the parameter to be removed.
     */
    void removeParameter(ParameterName name);

    /**
     * Return the Name of the tool as an Enumerator.
     * @return the enumerator tool.
     */
    Tools getToolType();
}
