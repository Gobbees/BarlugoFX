package barlugofx.view.preset;

import barlugofx.view.InputOutOfBoundException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.StringConverter;

/**
 * This class was originally made by Christoph Nahr.
 * Converts between user-edited strings and {@link Integer} values. Accepts an
 * optional {@link Runnable} that resets the editor on {@link NumberFormatException} 
 * or {@link InputOutOfBoundException},  or a {@link TextField} or {@link Spinner} 
 * that is preemptively monitored for invalid input during typing, and restricts
 * valid input to a specified range when committed.
 */
public class IntegerStringConverter extends StringConverter<Integer> {
    private Runnable reset;
    /**
     * Creates an {@link IntegerStringConverter}. Swallows
     * {@link NumberFormatException} but does nothing in response until
     * {@link #setReset} is defined.
     */
    public IntegerStringConverter() {
        super();
    }

    /**
     * Creates an {@link IntegerStringConverter} with an editor reset callback.
     * Specifying {@code null} has the same effect as the default constructor.
     * 
     * @param reset the {@link Runnable} to call upon {@link NumberFormatException}
     */
    public IntegerStringConverter(final Runnable reset) {
        super();
        this.reset = reset;
    }

    /**
     * Creates an {@link IntegerStringConverter} with the specified input range.
     * Preemptively monitors {@code input} to reject any invalid characters during
     * typing, restricts {@code input} to [{@code min}, {@code max}] (inclusive)
     * when valid text is committed, and resets {@code input} to the closest value
     * to zero within [{@code min}, {@code max}] when invalid text is committed.
     * 
     * @param input the {@link TextField} providing user-edited strings
     * @param min   the smallest valid {@link Integer} value
     * @param max   the greatest valid {@link Integer} value
     * @throws NullPointerException if {@code input} is {@code null}
     */
    public IntegerStringConverter(final TextField input, final int min, final int max) {
        super();
        if (input == null) {
            throw new IllegalStateException("input");
        }
        final int resetValue = Math.min(Math.max(0, min), max);
        reset = () -> input.setText(Integer.toString(resetValue));

        // bound JavaFX properties cannot be explicitly set
        if (!input.tooltipProperty().isBound()) {
            input.setTooltip(new Tooltip(String.format("Enter a value between %d and %d", min, max)));
        }
        // restrict direct input to valid numerical characters
        input.textProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                return;
            }
            // special case: minus sign if negative values allowed
            if (min < 0 && newValue.endsWith("-")) {
                if (newValue.length() > 1) {
                    Platform.runLater(() -> input.setText("-"));
                }
                return;
            }
            try {
                final int value = Integer.parseInt(newValue); //tries to convert newValue to an int 
                if (value < min || value > max) {             //if value is not in the range this will throw an exception
                    throw new InputOutOfBoundException(); 
                }
            } catch (NumberFormatException | InputOutOfBoundException e) {
                Platform.runLater(() -> input.setText(oldValue));
            }
        });

        // validate committed input and restrict to legal range
        final EventHandler<ActionEvent> oldHandler = input.getOnAction();
        input.setOnAction(t -> {
            // fromString performs input validation
            final int value = fromString(input.getText());

            // redundant for Spinner but not harmful
            final int restricted = Math.min(Math.max(value, min), max);
            if (value != restricted) {
                input.setText(Integer.toString(restricted));
            }
            // required for Spinner which handles onAction
            if (oldHandler != null) {
                oldHandler.handle(t);
            }
        });
    }

    /**
     * Creates an {@link IntegerStringConverter} for the specified {@link Spinner}.
     * Uses the {@link TextField} and minimum and maximum values of the specified
     * {@link Spinner} for construction, and also sets the new
     * {@link IntegerStringConverter} on its
     * {@link SpinnerValueFactory.IntegerSpinnerValueFactory}.
     * 
     * @param spinner the {@link Spinner} to create an
     *                {@link IntegerStringConverter} for
     * @return the new {@link IntegerStringConverter}
     * @throws NullPointerException if {@code spinner} is {@code null}
     */
    public static IntegerStringConverter createFor(final Spinner<Integer> spinner) {
        final SpinnerValueFactory.IntegerSpinnerValueFactory factory = (SpinnerValueFactory.IntegerSpinnerValueFactory) spinner
                .getValueFactory();

        final IntegerStringConverter converter = new IntegerStringConverter(spinner.getEditor(), factory.getMin(),
                factory.getMax());

        factory.setConverter(converter);
        spinner.setTooltip(
                new Tooltip(String.format("Enter a value between %d and %d", factory.getMin(), factory.getMax())));

        return converter;
    }

    /**
     * Sets the editor reset callback. Specify {@code null} to clear a previously
     * set {@link Runnable}. When creating an {@link IntegerStringConverter} for a
     * {@link TextField} or {@link Spinner}, this callback is automatically defined
     * to reset committed invalid input to the closest value to zero within the
     * legal range. Setting a different callback will overwrite this functionality.
     * 
     * @param reset the {@link Runnable} to call upon {@link NumberFormatException}
     * @see #fromString
     */
    public void setReset(final Runnable reset) {
        this.reset = reset;
    }

    /**
     * Converts the specified {@link String} into its {@link Integer} value. A
     * {@code null}, empty, or otherwise invalid argument returns zero and also
     * executes the editor reset callback, if any.
     * 
     * @param s the {@link String} to convert
     * @return the {@link Integer} value of {@code s}
     * @see #setReset
     */
    @Override
    public Integer fromString(final String s) {
        if (s == null || s.isEmpty()) {
            if (reset != null) {
                reset.run();
            }
            return 0;
        }

        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            if (reset != null) {
                reset.run();
            }
            return 0;
        }
    }

    /**
     * * Converts the specified {@link Integer} into its {@link String} form. A
     * {@code null} argument is converted into the literal string "0".
     * 
     * @param value the {@link Integer} to convert
     * @return the {@link String} form of {@code value}
     */
    @Override
    public String toString(final Integer value) {
        if (value == null) {
            return "0";
        }
        return Integer.toString(value);
    }
}
