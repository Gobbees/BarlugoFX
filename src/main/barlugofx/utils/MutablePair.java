package barlugofx.utils;

/**
 * This class implements a mutable pair, so it is possible to change both its values.
 *
 * @param <F> the first value type
 * @param <S> the second value type
 */
public class MutablePair<F, S> {
    private F first;
    private S second;
    /**
     * The class constructor.
     * @param first the first value of the pair
     * @param second the second value of the pair
     */
    public MutablePair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }
    /**
     * @return the first value of the pair
     */
    public F getFirst() {
        return first;
    }
    /**
     * Sets the first value of the pair.
     * @param first the first value
     */
    public void setFirst(final F first) {
        this.first = first;
    }
    /**
     * @return the second value of the pair
     */
    public S getSecond() {
        return second;
    }
    /**
     * Sets the second value of the pair.
     * @param second the first value
     */
    public void setSecond(final S second) {
        this.second = second;
    }
}
