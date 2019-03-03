package com.barlugofx.view;

import javafx.stage.Stage;

/**
 *  Interface for view related controllers (for FXML components and events).
 */
public interface ViewController {
    /**
     * Sets the private field stage.
     * @param stage the input stage
     */
    void setStage(Stage stage);
    /**
     * This method resizes all the view components.
     * @param width : the resized width
     * @param height : the resized height
     */
    void resizeComponents(int width, int height);
}
