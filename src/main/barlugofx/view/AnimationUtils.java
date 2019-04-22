package barlugofx.view;

import java.awt.Dimension;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This utility class provides static functions to return a node animation.
 */
public final class AnimationUtils {

    private AnimationUtils() {
    }

    /**
     * Returns a FadeOut transition.
     * 
     * @param duration the duration of the animation
     * @param node     the node that will be "animated"
     * @return the FadeTransition
     */
    public static FadeTransition fadeInTransition(final Duration duration, final Node node) {
        final FadeTransition ft = new FadeTransition(duration, node);
        ft.setFromValue(0);
        ft.setToValue(1);
        return ft;
    }

    /**
     * Returns a FadeIn transition.
     * 
     * @param duration the duration of the animation
     * @param node     the node that will be "animated"
     * @return the FadeTransition
     */
    public static FadeTransition fadeOutTransition(final Duration duration, final Node node) {
        final FadeTransition ft = new FadeTransition(duration, node);
        ft.setFromValue(1);
        ft.setToValue(0);
        return ft;
    }

    /**
     * Returns a Timeline that resizes the stage to a certain dimension.
     * 
     * @param duration       the duration of the timeline
     * @param stage          the stage that will be resized
     * @param step           the step of each increment
     * @param finalDimension the final dimension of the stage after the resize
     * @return the animation Timeline
     */
    public static Timeline resizeToFullScreen(final Duration duration, final Stage stage, final double step,
            final Dimension finalDimension) {
        final WritableValue<Double> writableWidth = new WritableValue<Double>() {
            @Override
            public Double getValue() {
                return stage.getWidth();
            }

            @Override
            public void setValue(final Double value) {
                stage.setWidth(value);
                stage.centerOnScreen();
            }
        };
        final WritableValue<Double> writableHeight = new WritableValue<Double>() {
            @Override
            public Double getValue() {
                return stage.getHeight();
            }

            @Override
            public void setValue(final Double value) {
                stage.setHeight(value);
                stage.centerOnScreen();
            }
        };
        final Timeline tl = new Timeline();
        tl.getKeyFrames()
                .addAll(new KeyFrame(Duration.ZERO, new KeyValue(writableWidth, writableWidth.getValue() + step),
                        new KeyValue(writableHeight, writableHeight.getValue() + step)),
                        new KeyFrame(duration, new KeyValue(writableWidth, finalDimension.getWidth()),
                                new KeyValue(writableHeight, finalDimension.getHeight())));
        return tl;
    }
}
