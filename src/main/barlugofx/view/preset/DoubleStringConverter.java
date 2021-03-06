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
 * Converts between user-edited strings and {@link Double} values. Accepts an
 * optional {@link Runnable} that resets the editor on
 * {@link NumberFormatException} or {@link InputOutOfBoundException}, or a
 * {@link TextField} or {@link Spinner} that is preemptively monitored for
 * invalid input during typing, and restricts valid input to a specified range
 * when committed.
 */
public class DoubleStringConverter extends StringConverter<Double> {

    private static final double MAX_LENGTH = 6;

    private Runnable reset;

    /**
     * Creates a {@link DoubleStringConverter}. Swallows
     * {@link NumberFormatException} but does nothing in response until
     * {@link #setReset} is defined.
     */
    public DoubleStringConverter() {
        super();
    }

    /**
     * Creates a {@link DoubleStringConverter} with an editor reset callback.
     * Specifying {@code null} has the same effect as the default constructor.
     * 
     * @param reset the {@link Runnable} to call upon {@link NumberFormatException}
     */
    public DoubleStringConverter(final Runnable reset) {
        super();
        this.reset = reset;
    }

    /**
     * Creates a {@link DoubleStringConverter} with the specified input range.
     * Preemptively monitors {@code input} to reject any invalid characters during
     * typing, restricts {@code input} to [{@code min}, {@code max}] (inclusive)
     * when valid text is committed, and resets {@code input} to the closest value
     * to zero within [{@code min}, {@code max}] when invalid text is committed.
     * 
     * @param input the {@link TextField} providing user-edited strings
     * @param min   the smallest valid {@link Double} value
     * @param max   the greatest valid {@link Double} value
     * @throws NullPointerException if {@code input} is {@code null}
     */
    public DoubleStringConverter(final TextField input, final double min, final double max) {
        super();
        if (input == null) {
            throw new IllegalStateException("input");
        }
        final double resetValue = Math.min(Math.max(0, min), max);
        reset = () -> input.setText(Double.toString(resetValue));

        // bound JavaFX properties cannot be explicitly set
        if (!input.tooltipProperty().isBound()) {
            input.setTooltip(new Tooltip(String.format("Enter a value between " + min + " and " + max)));
        }
        // restrict direct input to valid numerical characters
        input.textProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                return;
            }
            if (input.getLength() == MAX_LENGTH) {

                Platform.runLater(() -> input.setText(oldValue));
            }
            // special case: minus sign if negative values allowed
            if (min < 0 && newValue.endsWith("-")) {
                if (newValue.length() > 1) {
                    Platform.runLater(() -> input.setText("-"));
                }
                return;
            }
            try {
                final double value = Double.parseDouble(newValue); // tries to convert newValue to a double
                if (value < min || value > max) { // if value is not in the range this will throw an exception
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
            final double value = fromString(input.getText());

            // redundant for Spinner but not harmful
            final double restricted = Math.min(Math.max(value, min), max);
            if (value != restricted) {
                input.setText(Double.toString(restricted));
            }
            // required for Spinner which handles onAction
            if (oldHandler != null) {
                oldHandler.handle(t);
            }
        });
    }

    /**
     * Creates a {@link DoubleStringConverter} for the specified {@link Spinner}.
     * Uses the {@link TextField} and minimum and maximum values of the specified
     * {@link Spinner} for construction, and also sets the new
     * {@link DoubleStringConverter} on its
     * {@link SpinnerValueFactory.DoubleSpinnerValueFactory}.
     * 
     * @param spinner the {@link Spinner} to create a {@link DoubleStringConverter}
     *                for
     * @return the new {@link DoubleStringConverter}
     * @throws NullPointerException if {@code spinner} is {@code null}
     */
    public static DoubleStringConverter createFor(final Spinner<Double> spinner) {
        final SpinnerValueFactory.DoubleSpinnerValueFactory factory = (SpinnerValueFactory.DoubleSpinnerValueFactory) spinner
                .getValueFactory();

        final DoubleStringConverter converter = new DoubleStringConverter(spinner.getEditor(), factory.getMin(),
                factory.getMax());

        factory.setConverter(converter);
        spinner.setTooltip(
                new Tooltip(String.format("Enter a value between " + factory.getMin() + " and " + factory.getMax())));

        return converter;
    }

    /**
     * Sets the editor reset callback. Specify {@code null} to clear a previously
     * set {@link Runnable}. When creating a {@link DoubleStringConverter} for a
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
     * Converts the specified {@link String} into its {@link Double} value. A
     * {@code null}, empty, or otherwise invalid argument returns zero and also
     * executes the editor reset callback, if any.
     * 
     * @param s the {@link String} to convert
     * @return the {@link Double} value of {@code s}
     * @see #setReset
     */
    @Override
    public Double fromString(final String s) {
        if (s == null || s.isEmpty()) {
            if (reset != null) {
                reset.run();
            }
            return 0.0;
        }

        try {
            return Double.valueOf(s);
        } catch (NumberFormatException e) {
            if (reset != null) {
                reset.run();
            }
            return 0.0;
        }
    }

    /**
     * * Converts the specified {@link Double} into its {@link String} form. A
     * {@code null} argument is converted into the literal string "0".
     * 
     * @param value the {@link Double} to convert
     * @return the {@link String} form of {@code value}
     */
    @Override
    public String toString(final Double value) {
        if (value == null) {
            return "0";
        }
        return Double.toString(value);
    }

}
