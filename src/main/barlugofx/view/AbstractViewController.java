package barlugofx.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Abstract skeleton of a ViewController class without manager.
 */
public abstract class AbstractViewController implements ViewController {
    private Scene scene;
    private Stage stage;
    /**
     * Returns the scene.
     * @return the scene
     */
    protected Scene getScene() {
        return scene;
    }
    /**
     * Returns the stage.
     * @return the stage
     */
    protected Stage getStage() {
        return stage;
    }
    /* (non-Javadoc)
     * @see barlugofx.view.ViewController#setStage(javafx.stage.Stage)
     */
    @Override
    public void setStage(final Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("The stage must not be null");
        }
        this.stage = stage;
        this.scene = stage.getScene();
    }
    /**
     * Check if the stage is not null.
     * @throws IllegalStateException if the stage is null
     */
    protected void checkStage() {
        if (stage == null) {
            throw new IllegalStateException("The stage is null");
        }
    }
}
