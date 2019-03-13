package barlugofx.utils;

/**
 * This class implements a mutable pair, so it is possible to change both its
 * values. Be careful though that this Pair is not safe for any set.
 *
 * Note: Great care must be exercised if mutable objects are used as set
 * elements. The behavior of a set is not specified if the value of an object is
 * changed in a manner that affects equals comparisons while the object is an
 * element in the set. A special case of this prohibition is that it is not
 * permissible for a set to contain itself as an element.
 *
 * @param <F> the first value type
 * @param <S> the second value type
 */
public final class MutablePair<F, S> {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (first == null ? 0 : first.hashCode());
        result = prime * result + (second == null ? 0 : second.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MutablePair)) {
            return false;
        }
        @SuppressWarnings("unchecked")
        final MutablePair<F, S> other = (MutablePair<F, S>) obj;
        if (first == null) {
            if (other.first != null) {
                return false;
            }
        } else if (!first.equals(other.first)) {
            return false;
        }
        if (second == null) {
            if (other.second != null) {
                return false;
            }
        } else if (!second.equals(other.second)) {
            return false;
        }
        return true;
    }
}
