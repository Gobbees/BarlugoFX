package com.barlugofx.ui.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.barlugofx.ui.Animations;
import com.barlugofx.ui.loading.LoadingView;

import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class creates the main view. It must be called by its constructor method.
 */
public class MainView {
    //private constant fields
    private static final String STAGE_TITLE = "BarlugoFX";
    private static final double ANIM_MILLIS = 1000.0;
    private static final double ANIM_STEP = 10.0;

    private Stage stage;
    private Parent root;
    private Scene scene;
    private final Dimension screenDimension;
    /**
     * @param stage
     * @param file
     * @throws IOException 
     */
    public MainView(final Stage stage, final File file) throws IOException {
        //init stage
        this.stage = stage;
        stage.setTitle(STAGE_TITLE);
        //
        screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        LoadingView t = new LoadingView(stage);
        //load file, do initial operations (history init, ecc) by controller (to do)
        t.stop();
        root = FXMLLoader.load(new URL("file:res/fxml/FXMLMain.fxml"));
        scene = new Scene(root, screenDimension.getWidth(), screenDimension.getHeight());
        Timeline tl = Animations.resizeToFullScreen(Duration.millis(ANIM_MILLIS), stage, ANIM_STEP, screenDimension);
        tl.setOnFinished(e -> {
            stage.setScene(scene);
        });
        tl.play();
        stage.show();
    }
}
