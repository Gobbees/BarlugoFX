package barlugofx.model.tools.common;

/**
 * A simple implementation of Parameter.
 *
 * @param <T> the parameter to pass.
 */
public final class ParameterImpl<T> implements Parameter<T> {
    private final T value;

    /**
     * Builds the parameter.
     * @param value the value that need to be stored into the Parameter.
     */
    public ParameterImpl(final T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

}
