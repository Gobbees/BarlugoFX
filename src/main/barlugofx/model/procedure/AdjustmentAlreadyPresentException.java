package barlugofx.model.procedure;

/**
 *  This Exception gets thrown when you try to add an Adjustment
 *  to the Procedure and another Adjustment, with the same tool,
 *  is already present. 
 */
@SuppressWarnings("serial")
public class AdjustmentAlreadyPresentException extends Exception {

    /**
     * @param message 
     * Message to print on stderr.
     */
    public AdjustmentAlreadyPresentException(final String message) {
        super(message);
    }

}
