package barlugofx.model.procedure;

import java.util.Optional;

/**
 * 
 *
 *
 */
public class ActionImpl implements Action {
    private final Optional<Integer> index;
    private final Adjustment adjustment;
    private final Actions type;

    /**
     * 
     * @param type
     * Enumeration representing the type of action performed.
     * @param adjustment
     * The adjustment you'll have to restore when you decide to undo this action.
     * @param index
     * Optional, the index at which you need to restore the Adjustment.
     */
    public ActionImpl(final Actions type, final Adjustment adjustment, final Optional<Integer> index) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument is null.");
        }
        if (adjustment == null) {
            throw new IllegalArgumentException("Adjustment argument is null");
        }
        if (index == null) {
            throw new IllegalArgumentException("Index argument is null, should be Optional<Integer>.");
        }
        this.type = type;
        this.adjustment = adjustment;
        this.index = index;
    }

    /**
     * 
     */
    @Override
    public Actions getType() {
        return this.type;
    }

    /**
     *
     */
    @Override
    public Adjustment getAdjustment() {
        return this.adjustment;
    }

    /**
     * 
     */
    @Override
    public Optional<Integer> getIndex() {
        return this.index;
    }
}
