package barlugofx.view;

import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Skeleton of a view class.
 *
 * @param <T> the controller class
 */
public class View<T extends ViewController> {

    private final Dimension sceneDims;
    private final FXMLLoader fxml;
    private final Stage stage;
    private T controller;
    private Scene scene;

    /**
     * Initializer of the main fields of the class.
     * 
     * @param stageName name of the stage
     * @param stage     input stage
     * @param dim       scene dimension
     */
    protected View(final String stageName, final Stage stage, final Dimension dim) {
        this.sceneDims = dim;
        this.stage = stage;
        this.stage.setTitle(stageName);
        this.fxml = new FXMLLoader();
    }
    /**
     * Displays an error alert.
     * 
     * @param message the alert message
     */
    public static void showErrorAlert(final String message) {
        Platform.runLater(() -> {
            final Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    /**
     * Loads fxml file and initializes the scene and the view controller.
     * 
     * @param url the fxml path
     * @throws IOException if the fxml path is wrong
     */
    protected void loadFXML(final URL url) throws IOException {
        fxml.setLocation(url);
        scene = new Scene(fxml.load(), sceneDims.getWidth(), sceneDims.getHeight());
        controller = fxml.getController();
        stage.sizeToScene();
    }

    /**
     * Returns the scene dimension.
     * 
     * @return the scene dimension
     */
    protected Dimension getSceneDims() {
        return sceneDims;
    }

    /**
     * Returns the scene.
     * 
     * @return the scene
     */
    protected Scene getScene() {
        return scene;
    }

    /**
     * Returns the view controller.
     * 
     * @return the view controller
     * @throws IllegalStateException if the controller is null
     */
    protected T getController() {
        if (this.controller == null) {
            throw new IllegalStateException("The controller is null");
        }
        return (T) this.controller;
    }

    /**
     * Returns the stage.
     * 
     * @return the stage
     */
    protected Stage getStage() {
        return stage;
    }
}
