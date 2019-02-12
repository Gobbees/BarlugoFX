package com.barlugofx.ui.loading;

import com.barlugofx.ui.Animations;
import com.barlugofx.ui.ViewController;
import com.jfoenix.controls.JFXSpinner;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *  This class sets the view sizes and manages its components.
 */
public class LoadingController implements ViewController {
    //private constant fields (nodes multipliers)
    private static final double IMG_MULTIPLIER = 0.40;
    private static final double SEP_MULTIPLIER = 0.33;
    private static final int ANIM_MILLIS = 300;
    @FXML
    private BorderPane bpaneMain;
    @FXML
    private VBox vboxCenter;
    @FXML
    private ImageView iviewLogo;
    @FXML
    private Separator bottomSeparator;
    @FXML
    private JFXSpinner spinner;
    private Stage stage;

    /**
     * Sets the private field stage (and sets the general sizes of components).
     * @param s the input stage(needed to take scene sizes)
     */
    public void setStage(final Stage s) {
        this.stage = s;
        iviewLogo.setFitWidth(stage.getScene().widthProperty().get() * IMG_MULTIPLIER);
        iviewLogo.setFitHeight(stage.getScene().heightProperty().get() * IMG_MULTIPLIER);
        bottomSeparator.setVisible(false);
        bottomSeparator.setPrefHeight(stage.getScene().heightProperty().get() * SEP_MULTIPLIER);
    }
    /**
     * Effectively stops the view with a fadeout animation.
     */
    public void stop() {
        FadeTransition ft = Animations.playFadeOutTransition(Duration.millis(ANIM_MILLIS), stage.getScene().getRoot());
        ft.setOnFinished(e -> stage.setScene(null));
    }
}
