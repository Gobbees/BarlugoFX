package com.barlugofx.model.tools.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This abstact class implements the common function to all filters implementing ImageFilter.
 *
 */
public abstract class ImageFilterImpl implements ImageFilter {
    private final Map<ParametersName, Parameter<?>> parameters = new HashMap<>();

    /**
     * This function return (optionally) the parameter associated with the name.
     * @param name the name of the parameter to get
     * @return the parameter
     *
     */
    private Optional<Parameter<?>> getParameter(final ParametersName name) {
        return Optional.ofNullable(parameters.get(name));
    }

    @Override
    public final void addParameter(final ParametersName name, final Parameter<?> value) {
        if (isAccepted(name) && !parameters.containsKey(name)) {
            parameters.put(name, value);
        } else {
            throw new IllegalArgumentException("A parameter is already present, please remove it.");
        }
    }

    @Override
    public final void removeParameter(final ParametersName name) {
        if (!parameters.containsKey(name)) {
            throw new IllegalArgumentException("The parameter is not present");
        }
        parameters.remove(name);
    }

    /**
     * a function that returns true if and only if the parameter name is valid.
     * @param name the parameter name to test.
     * @return true if valid.
     */
    protected abstract boolean isAccepted(ParametersName name);

    /**
     * This function returns the value associated with the Parameter or the default value if the parameter is not present.
     * @param <T> the class type of the value we want to obtain
     * @param name the name of the parameter to find.
     * @param min the minimum value accepted (inclusive)
     * @param max the maximum value accepted (inclusive)
     * @param defaultVal the default value to return if the parameter is not present
     * @return the value or the default value
     * @throws IllegalArgumentException if the boundaries are not respected or if the parameter is not of the correct type.
     */
    @SuppressWarnings("unchecked")
    protected <T extends Number> T getValueFromParameter(final ParametersName name, final double min, final double max, final T defaultVal) {
        final Optional<Parameter<?>> param = getParameter(name);
        T result = defaultVal;
        if (param.isPresent()) {
            try {
                result = (T) param.get().getValue();
            } catch (final ClassCastException e) {
                throw new IllegalArgumentException("The " + name  + " parameter is not a " + result.getClass().getName());
            }
        }
        if (result.doubleValue() > max || result.doubleValue() < min) {
            throw new IllegalArgumentException("The " + name + " parameter does not respect the restrition specified by the class");
        }
        return result;
    }
}
