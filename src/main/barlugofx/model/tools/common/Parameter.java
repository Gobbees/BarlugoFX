package barlugofx.model.tools.common;

/**
 *
 * A simple wrapper for any possible parameter of the tools.
 *
 * @param <T> they object type.
 */
public interface Parameter<T extends Number> {
    //This modeling was inspired by Danilo Pianini.

    /**
     * Gets the value contained in Parameter.
     * @return the value.
     */
    T getValue();
}
