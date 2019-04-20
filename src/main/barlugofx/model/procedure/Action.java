package barlugofx.model.procedure;

import java.util.Optional;

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
    Optional<Integer> getIndex();
}
