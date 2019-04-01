package barlugofx.view;

import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
     * @param stageName name of the stage
     * @param iconPath path of the icon image
     * @param stage input stage
     * @param dim scene dimension
     */
    public AbstractView(final String stageName, final String iconPath, final Stage stage, final Dimension dim) {
    	this.sceneDims = dim;
        this.stage = stage;
        this.stage.setTitle(stageName);
        this.stage.getIcons().add(new Image(iconPath));
        this.fxml = new FXMLLoader();
    }
    /**
     * Loads fxml file and initializes the scene and the view controller.
     * @param iconPath the fxml path
     * @throws IOException if the fxml path is wrong
     */
    protected void loadFXML(final String iconPath) throws IOException {
        fxml.setLocation(new URL(iconPath));
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
