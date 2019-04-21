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
    public ActionImpl(final Actions type, final int index, final Adjustment... adjustment) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument is null.");
        }
        if ((type == Actions.ADD || type == Actions.REMOVE) && adjustment.length != 1) {
            String msg = "For ADD and REMOVE actions you must pass only one ajustment.";
            throw new IllegalArgumentException(msg);
        }
        if (type == Actions.EDIT && adjustment.length != 2) {
            String msg = "For EDIT actions you must pass exactly two adjustments.";
            throw new IllegalArgumentException(msg);
        }
        if (type == Actions.EDIT && adjustment[0].getToolType() != adjustment[1].getToolType()) {
            throw new IllegalArgumentException("Adjustment types don't match.");
        }
        this.type = type;
        this.adjustmentAfter = adjustment[0];
        this.adjustmentBefore = (type == Actions.EDIT) ? adjustment[1] : null;
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
