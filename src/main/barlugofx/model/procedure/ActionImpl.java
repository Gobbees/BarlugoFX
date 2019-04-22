package barlugofx.model.procedure;

import java.util.Optional;

/**
 * 
 *
 *
 */
public class ActionImpl implements Action {
    private final int index;
    private final Adjustment adjustmentAfter;
    private final Adjustment adjustmentBefore;
    private final Actions type;

    /**
     * 
     * @param type
     * Enumeration representing the type of action performed.
     * @param index
     * Optional, the index at which you need to restore the Adjustment.
     * @param adjustment
     * First the adjustment you'll have to restore when you decide to undo this action.
     * Second (used only with EDIT actions), the adjustment you are replacing.
     */
    public ActionImpl(final Actions type, final int index, final Adjustment adjustment) {
        this(type, index, adjustment, null);
    }

    /**
     * 
     * @param type
     * The type of the Action
     * @param index
     * The index of the Action in the Procedure.
     * @param adjustmentAfter
     * The new adjustment
     * @param adjustmentBefore
     * The old adjustment, useful only when applying an Edit.
     */
    public ActionImpl(final Actions type, final int index, final Adjustment adjustmentAfter, final Adjustment adjustmentBefore) {
        if (adjustmentAfter == null) {
            throw new java.lang.IllegalArgumentException("First adjustment reference is null!");
        }
        if (type == Actions.EDIT && adjustmentBefore == null) {
            throw new java.lang.IllegalArgumentException("EDIT action with only one valid adjustment reference.");
        }
        if (type != Actions.EDIT && adjustmentBefore != null) {
            throw new java.lang.IllegalArgumentException(type.toString() + " action with second adjustment reference not null.");
        }
        this.type = type;
        this.adjustmentAfter = adjustmentAfter;
        this.adjustmentBefore = adjustmentBefore;
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
        return this.adjustmentAfter;
    }

    /**
     * 
     * @return
     */
    @Override
    public Adjustment getAdjustmentBefore() {
        return this.adjustmentBefore;
    }

    /**
     * 
     */
    @Override
    public int getIndex() {
        return this.index;
    }
}
