package barlugofx.model.procedure;

/**
 * Implementation of Action.
 * An Action is the act of adding, removing or replacing a change in the project.
 * Allows the user to undo and redo add/remove actions as well as edit actions.
 */
public final class ActionImpl implements Action {
    private final int index;
    private final Adjustment adjustmentAfter;
    private final Adjustment adjustmentBefore;
    private final Actions type;

    /**
     * @param type Enumeration representing the type of action performed.
     * @param index The index at which the Adjustment is being added to or removed from.
     * @param adjustment the Adjustment you are adding or removing.
     * 
     * Creates a new Action with the specified adjustment and a null reference as "before" version.
     */
    public ActionImpl(final Actions type, final int index, final Adjustment adjustment) {
        this(type, index, adjustment, null);
    }

    /**
     * 
     * @param type The type of Action
     * @param index The index of the Adjustment in the Procedure.
     * @param adjustmentAfter the new Adjustment.
     * @param adjustmentBefore The old adjustment, the one that gets replaced with the EDIT action.
     * 
     * Creates a new Action with the specified adjustments as "before" and "after" version of the adjustment.
     */
    public ActionImpl(final Actions type, final int index, final Adjustment adjustmentAfter, final Adjustment adjustmentBefore) {
        if (adjustmentAfter == null) {
            throw new IllegalArgumentException("First adjustment reference is null!");
        }
        if (type == Actions.EDIT && adjustmentBefore == null) {
            throw new IllegalArgumentException("EDIT action with only one valid adjustment reference.");
        }
        if (type != Actions.EDIT && adjustmentBefore != null) {
            throw new IllegalArgumentException(type.toString() + " action with second adjustment reference not null.");
        }
        this.type = type;
        this.adjustmentAfter = adjustmentAfter;
        this.adjustmentBefore = adjustmentBefore;
        this.index = index;
    }

    @Override
    public Actions getType() {
        return this.type;
    }

    @Override
    public Adjustment getAdjustment() {
        return this.adjustmentAfter;
    }

    @Override
    public Adjustment getAdjustmentBefore() {
        return this.adjustmentBefore;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        return this.type.name();
    }
}
