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
 * 
 * This is a quick copy of the "IntegerStringConverter" class appropriately modified
 * and it will be changed in the near future.
 */
public class DoubleStringConverter extends StringConverter<Double> {

        private Runnable reset;
        /**
         * Creates an {@link IntegerStringConverter}. Swallows
         * {@link NumberFormatException} but does nothing in response until
         * {@link #setReset} is defined.
         */
        public DoubleStringConverter() {
            super();
        }

        /**
         * Creates an {@link IntegerStringConverter} with an editor reset callback.
         * Specifying {@code null} has the same effect as the default constructor.
         * 
         * @param reset the {@link Runnable} to call upon {@link NumberFormatException}
         */
        public DoubleStringConverter(final Runnable reset) {
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
/*                ///TODO better length check
                //restrict the size max input
                if (input.getLength() == 5) {
                    Platform.runLater(() -> input.setText(oldValue));
                }*/
                // special case: minus sign if negative values allowed
                if (min < 0 && newValue.endsWith("-")) {
                    if (newValue.length() > 1) {
                        Platform.runLater(() -> input.setText("-"));
                    }
                    return;
                }
                try {
                    final double value = Double.parseDouble(newValue); //tries to convert newValue to a double 
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
         * * Converts the specified {@link Integer} into its {@link String} form. A
         * {@code null} argument is converted into the literal string "0".
         * 
         * @param value the {@link Integer} to convert
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
