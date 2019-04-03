package barlugofx.view.main;

import javafx.scene.layout.Pane;

/**
 * This interface defines a new node composed by many JavaFX Nodes.
 */
public interface ComplexNode {
    /**
     * Adds all the components of the ComplexNode to pane.
     * @param pane the input pane
     */
    void addToPane(Pane pane);
    /**
     * Removes all the components of the ComplexNode from pane.
     * @param pane the input pane
     */
    void removeFromPane(Pane pane);
}
