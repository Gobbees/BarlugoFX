package com.barlugofx.ui.loading;

import java.io.IOException;
import java.net.URL;

import com.barlugofx.ui.Animations;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *  This class switches from the past scene to a new loading scene. It must be called by its constructor method.
 */
public class LoadingView {
    //private constant fields
    private static final String FXML_FILE_PATH = "file:res/fxml/FXMLLoading.fxml";
    private static final int ANIM_MILLIS = 3000;
    private static final String STAGE_TITLE = "Loading...";
    //private UI fields
    private final Stage stage;
    private final Scene scene;
    private final Parent root;
    private final LoadingController vController;
    /**
     * The constructor that initializes the scene and updates the stage.
     * @param s the input stage where the new scene will be updated
     * @throws IOException if the loader file URL is invalid.
     */
    public LoadingView(final Stage s) throws IOException {
        //stage operations
        this.stage = s;
        stage.setResizable(false);
        stage.setTitle(STAGE_TITLE);
        //init root
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL(FXML_FILE_PATH));
        root = loader.load();
        //init viewcontroller
        vController = loader.getController();
        vController.setStage(stage);
        //init scene
        scene = new Scene(root, stage.getScene().widthProperty().get(), stage.getScene().heightProperty().get());
        Animations.playFadeInTransition(Duration.millis(ANIM_MILLIS), root);
        //changes the scene
        stage.setScene(scene);
    }
    /**
     * Stops the loadingview.
     */
    public void stop() { 
        vController.stop();
    }
}
