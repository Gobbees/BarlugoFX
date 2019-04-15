package barlugofx.view;

import javafx.stage.Stage;

/**
 *  Interface for view related controllers (for FXML components and events).
 */
public interface ViewController {
    /**
     * Sets the private field stage.
     * @param stage the input stage
     * @throws IllegalArgumentException if the input parameter is null
     */
    void setStage(Stage stage);
}
