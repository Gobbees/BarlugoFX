package com.barlugofx.ui.welcome;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 *  This class shows the welcome view of barlugofx program. It must be called by its constructor method.
 *  WelcomeView loads the view from FXMLWelcome.fxml file and sets the stage and scene sizes, icons and titles.
 */
public class WelcomeView {
    //private constant fields
    private static final String STAGE_NAME = "Welcome to BarlugoFX";
    private static final Image STAGE_ICON = new Image("file:res/img/logo.png");
    private static final String FXML_FILE_PATH = "file:res/fxml/FXMLWelcome.fxml";
    private static final Dimension DEFAULT_SCREEN = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension MIN_DIMENSION = new Dimension((int) DEFAULT_SCREEN.getWidth() / 5, (int) DEFAULT_SCREEN.getHeight() / 5);
    //private UI fields
    private final Scene scene;
    private final WelcomeController vController;
    /**
     * The constructor that initializes all the fields and show the stage.
     * @throws IOException if the loader file URL is invalid.
     */
    public WelcomeView() throws IOException {
        final Stage stage;
        final Parent root;
        //init stage
        stage = new Stage();
        stage.setTitle(STAGE_NAME);
        stage.getIcons().add(STAGE_ICON);
        stage.centerOnScreen();
        stage.setMinWidth(MIN_DIMENSION.getWidth());
        stage.setMinHeight(MIN_DIMENSION.getHeight());
        //init root
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL(FXML_FILE_PATH));
        root = loader.load();
        //init controller
        vController = loader.getController();
        vController.setStage(stage);
        //init scene
        scene = new Scene(root, DEFAULT_SCREEN.getWidth() / 2, DEFAULT_SCREEN.getHeight() / 2);
        vController.resizeComponents((int) scene.widthProperty().get(), (int) scene.heightProperty().get());
        //add resize listeners
        scene.widthProperty().addListener((obs, oldVal, newVal) -> vController.resizeComponents(newVal.intValue(), (int) scene.heightProperty().get()));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> vController.resizeComponents((int) scene.widthProperty().get(), newVal.intValue()));
        //add scene to the stage and show
        stage.setScene(scene);
        stage.show();
    }

}
