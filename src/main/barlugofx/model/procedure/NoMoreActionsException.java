package barlugofx.model.procedure;

/**
 * This exception gets thrown whenever the user tries to undo() when there are no actions to undo,
 * or tries to redo() when there are no actions to redo.
 *
 */
@SuppressWarnings("serial")
public class NoMoreActionsException extends Exception {
    /**
     * @param message
     * Exception cause explanation message.
     */
    public NoMoreActionsException(final String message) {
        super(message);
    }
}
