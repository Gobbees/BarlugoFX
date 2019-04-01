package barlugofx.model.history;

/**
 * 
 * This exception is thrown when the user tries to add a tool to an already full history.
 *
 */
@SuppressWarnings("serial")
public class ToolLimitReachedException extends Exception {
    /**
     * @param message
     * the message to print on stderr 
     */
    public ToolLimitReachedException(final String message) {
        super(message);
    }
}
