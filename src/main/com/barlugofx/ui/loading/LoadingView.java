package com.barlugofx.ui.loading;

import java.awt.Dimension;
import java.io.IOException;

import com.barlugofx.ui.AbstractView;
import com.barlugofx.ui.Animations;

import javafx.animation.FadeTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *  This class switches from the past scene to a new loading scene. It must be called by its constructor method.
 */
public class LoadingView extends AbstractView {
    //private constant fields
    private static final int ANIM_MILLIS = 700;
    /**
     * The constructor that initializes the scene and updates the stage.
     * @param stage the input stage where the new scene will be updated
     */
    public LoadingView(final Stage stage) {
        super("Loading...", "file:res/img/logo.png", stage, new Dimension((int) stage.getScene().getWidth(), (int) stage.getScene().getHeight()));
        this.getStage().setResizable(false);
        try {
            this.loadFXML("file:res/fxml/FXMLLoading.fxml");
        } catch (IOException e) {
            //log !!!!!
            e.printStackTrace();
        }
        this.getController().setStage(this.getStage());
        final FadeTransition ft = Animations.fadeInTransition(Duration.millis(ANIM_MILLIS), this.getScene().getRoot());
        ft.play();
        this.getStage().setScene(this.getScene());
    }
    /**
     * Stops the loadingview.
     */
    public void stop() { 
        ((LoadingController) this.getController()).stop();
    }
}
