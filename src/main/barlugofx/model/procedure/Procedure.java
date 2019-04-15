package barlugofx.model.procedure;

import java.util.Optional;

import barlugofx.model.tools.common.Parameter;
import barlugofx.model.tools.common.ParameterName;
import barlugofx.model.tools.Tools;

/**
 * 
 *
 *
 */

public interface Procedure {
    /**
     * 
     * @param node
     * Sequence node you want to add.
     * @throws AdjustmentAlreadyPresentException
     * when you try to add a tool and the history is already full.
     */
    void addAdjustment(Adjustment node) throws AdjustmentAlreadyPresentException;

    /**
     * 
     * @param index
     * Index of the tool you want to delete.
     */
    void removeAdjustment(int index);

    /**
     * 
     * @param index
     * Index of the tool you want to disable.
     */
    void disableAdjustment(int index);

    /**
     * 
     * @param index
     * Index of the tool you want to enable.
     */
    void enableAdjustment(int index);

    /**
     * This function returns the enabled state of tool.
     * @param index
     * Index of the tool of which I want the state
     * @return true if the tool is enabled, false otherwise.
     */
    boolean isToolEnabled(int index);

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
    void editAdjustment(int index, Adjustment node);

    /**
     * 
     * @param name
     * Name of the tool of which you want the value.
     * @param index
     * index of the tool of which you want the value.
     * @return value of the tool with name "name"
     */
    Optional<Parameter<? extends Number>> getValue(int index, ParameterName name);

    /**
     * 
     * @param toolType the type of tool you want to add.
     * @return true if you can add another tool, false otherwise.
     */
    boolean canAdd(Tools toolType);
}
