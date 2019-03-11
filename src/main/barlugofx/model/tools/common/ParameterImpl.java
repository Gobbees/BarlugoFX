package barlugofx.model.tools.common;

/**
 * A simple implementation of Parameter.
 *
 * @param <T> the parameter to pass.
 */
public final class ParameterImpl<T extends Number> implements Parameter<T> {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ParameterImpl)) {
            return false;
        }
        final ParameterImpl<T> other = (ParameterImpl<T>) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }





}
