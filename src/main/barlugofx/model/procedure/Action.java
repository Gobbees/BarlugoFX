package barlugofx.model.procedure;

/**
 * 
 *
 *
 */
public interface Action {
    /**
     * 
     * @return the enumeration type of the Action.
     */
    Actions getType();

    /**
     * 
     * @return the Adjustment you need to restore in the Procedure.
     */
    Adjustment getAdjustment();

    /**
     * Not always needed.
     * @return the index in which you want to restore the Adjustment.
     */
    int getIndex();
}
