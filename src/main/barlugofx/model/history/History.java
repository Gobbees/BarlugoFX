package barlugofx.model.history;

import barlugofx.model.tools.common.Parameter;

/**
 * 
 *
 *
 */

public interface History {
    /**
     * 
     * @param node
     * Sequence node you want to add.
     */
    void addTool(SequenceNode node);

    /**
     * 
     * @param index
     * Index of the tool you want to delete.
     */
    void deleteTool(int index);

    /**
     * 
     * @param index
     * Index of the tool you want to disable.
     */
    void disableTool(int index);

    /**
     * 
     * @param index
     * Index of the tool you want to enable.
     */
    void enableTool(int index);

    /**
     * 
     * @param toolName
     * Name of the tool of which you want the index.
     * @return index of the tool.
     */
    int findByName(String toolName);

    /**
     * 
     * @param index
     * Index of the tool you want to edit.
     * @param node
     * New node that is going to replace the node at index.
     */
    void editTool(int index, SequenceNode node);

    /**
     * 
     * @param name
     * Name of the tool of which you want the value.
     * @return value of the tool with name "name"
     */
    Parameter<? extends Number> getValue(String name);
}
