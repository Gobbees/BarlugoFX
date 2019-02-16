package com.barlugofx.ui;

/**
 * This exception is thrown when the input of a textfield is out of the slider min/max range.
 */
@SuppressWarnings("serial")
public class InputOutOfBoundException extends Exception {
    /**
     * No parameters constructor.
     */
    public InputOutOfBoundException() {
        super();
    }
    /**
     * Constructor with a message.
     * @param message the exception message
     */
    public InputOutOfBoundException(final String message) {
        super(message);
    }

}
