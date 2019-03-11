package barlugofx.model.tools.common;

/**
 *
 * A simple interface that allows a class to wrap an element.
 *
 * @param <T> they object type.
 */
public interface Parameter<T extends Number> {

    /**
     * Gets the value contained in Parameter.
     * @return the value.
     */
    T getValue();
}
