package barlugofx.view;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
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
}
