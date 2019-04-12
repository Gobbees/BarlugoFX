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
 * Abstract skeleton of a view class.
 *
 * @param <T>
 */
public abstract class AbstractView<T extends ViewController> {

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
    public AbstractView(final String stageName, final Stage stage, final Dimension dim) {
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
        final Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(message);
        Platform.runLater(() -> alert.showAndWait());
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
     * @return the scene dimesion
     */
    protected Dimension getSceneDims() {
        return sceneDims;
    }

    /**
     * @return the scene
     */
    protected Scene getScene() {
        return scene;
    }

    /**
     * @return the view controller
     * @throws IllegalStateException if the controller is null
     */
    protected T getController() throws IllegalStateException {
        if (this.controller == null) {
            throw new IllegalStateException("The controller is null");
        }
        return (T) this.controller;
    }

    /**
     * @return the stage
     */
    protected Stage getStage() {
        return stage;
    }
}
