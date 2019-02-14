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
    protected Optional<Parameter<?>> getParameter(final ParametersName name) {
        return Optional.ofNullable(parameters.get(name));
    }

    @Override
    public final void addParameter(final ParametersName name, final Parameter<?> value) {
        if (isAccepted(name) && !parameters.containsKey(name)) {
            parameters.put(name, value);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public final void removeParameter(final ParametersName name) {
        if (!parameters.containsKey(name)) {
            throw new IllegalArgumentException();
        }
        parameters.remove(name);
    }

    /**
     * a function that returns true if and only if the parameter name is valid.
     * @param name the parameter name to test.
     * @return true if valid.
     */
    protected abstract boolean isAccepted(ParametersName name);
}
