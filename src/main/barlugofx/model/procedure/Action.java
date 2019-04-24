package barlugofx.model.procedure;

/**
 * An Action represents the action of either adding, removing or editing an Adjustment on the image.
 * Every time such change is applied, the Action object is created and saved in the History.
 * For Add and Remove actions, only one version of the Adjustment is needed.
 * For Edit actions, both the Before and After version of the Adjustment are needed.
 */
public interface Action {
    /**
     * 
     * @return the enumeration type of the Action.
     */
    Actions getType();

    /**
     * @return the Adjustment you need to restore in the Procedure.
     */
    Adjustment getAdjustment();

    /**
     * Used only when undoing EDIT Actions.
     * @return the adjustment present before the edit action.
     */
    Adjustment getAdjustmentBefore();

    /**
     * Not always needed.
     * @return the index in which you want to restore the Adjustment.
     */
    int getIndex();
}
