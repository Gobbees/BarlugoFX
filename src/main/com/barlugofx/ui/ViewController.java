package com.barlugofx.ui;

import javafx.stage.Stage;

/**
 *  Interface for view related controllers (for FXML components and events).
 */
public interface ViewController {
    /**
     * Sets the private field stage.
     * @param stage the stage
     */
    void setStage(Stage stage);
}
