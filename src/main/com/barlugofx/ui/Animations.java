package com.barlugofx.ui;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * This utility class provides static functions to animate a node.
 */
public final class Animations {

    private Animations() { }

    /**
     * Play a FadeOut transition.
     * @param duration the duration of the animation
     * @param node  the node that will be "animated"
     * @return the FadeTransition
     */
    public static FadeTransition playFadeInTransition(final Duration duration, final  Node node) {
        final FadeTransition ft = new FadeTransition(duration, node);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        return ft;
    }

    /**
     * Play a FadeIn transition.
     * @param duration the duration of the animation
     * @param node  the node that will be "animated"
     * @return the FadeTransition
     */
    public static FadeTransition playFadeOutTransition(final Duration duration, final Node node) {
        final FadeTransition ft = new FadeTransition(duration, node);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
        return ft;
    }

}
