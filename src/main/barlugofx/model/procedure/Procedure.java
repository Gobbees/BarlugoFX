package barlugofx.model.procedure;

import java.util.Optional;

import barlugofx.model.tools.common.Parameter;
import barlugofx.model.tools.common.ParameterName;
import barlugofx.utils.Tools;
import barlugofx.model.imagetools.Image;

/**
 * This interface model the contract of the Procedure object.
 * The procedure is the ordered container of Adjustments made to the Image.
 * You can add, remove and edit the Adjustments.
 * Each time you add, remove or edit your change is saved in the integrated History,
 * which allows you to undo and redo your actions.
 */
public interface Procedure {

    /**
     * Add an adjustment to the Procedure.
     * 
     * @param adjustment
     * The Adjustment you want to add.
     * @return the processed image resulting from the add.
     * @throws AdjustmentAlreadyPresentException
     * when you try to add an Adjustment with a Tool already in use.
     */
    Image add(Adjustment adjustment) throws AdjustmentAlreadyPresentException;

    /**
     * @param index
     * Index of the adjustment you want to delete.
     * @return the processed image resulting from the remove.
     */
    Image remove(int index);

    /**
     * @param type
     * The type of tool you want to remove.
     * @return the processed image resulting from the remove.
     */
    Image remove(Tools type);

    /**
     * @param index
     * Index of the adjustment you want to disable.
     * @return the processed image resulting from the disable.
     */
    Image disable(int index);

    /**
     * @param index
     * Index of the adjustment you want to enable.
     * @return the processed image resulting from the enable.
     */
    Image enable(int index);

    /**
     * This function returns the enabled state of adjustment.
     * @param index
     * Index of the adjustment of which I want the state
     * @return true if the adjustment is enabled, false otherwise.
     */
    boolean isAdjustmentEnabled(int index);

    /**
     * @param type
     * The type of tool used in the adjustment.
     * @return
     * The index of the tool, if present, -1 otherwise.
     */
    int findByType(Tools type);

    /**
     * Edit an Adjustment knowing it's type in the procedure.
     * @param type
     * The type of tool used in the adjustment you want to edit.
     * @param adjustment
     * The new adjustment.
     * @return the processed image resulting from the edit.
     */
    Image edit(Tools type, Adjustment adjustment);

    /**
     * Edit an Adjustment knowing it's index in the procedure.
     * @param index
     * Index of the adjustment you want to edit.
     * @param adjustment
     * New adjustment that is going to replace the adjustment at index.
     * @return the processed image resulting from the edit.
     */
    Image edit(int index, Adjustment adjustment);

    /**
     * Name of the adjustment of which you want the value.
     * @param index
     * Index of the adjustment of which you want the value.
     * @param name
     * The name of the parameter you want to get.
     * @return value of the adjustment with name "name"
     */
    Optional<Parameter<? extends Number>> getValue(int index, ParameterName name);

    /**
     * Name of the adjustment of which you want the value.
     * @param tool
     * Tool that contains the requested parameter;
     * @param name
     * The name of the parameter you want to get.
     * @return value of the adjustment with name "name"
     */
    Optional<Parameter<? extends Number>> getValue(Tools tool, ParameterName name);

    /**
     * Tells you if you can add an adjustment using a tool of type toolType.
     * @param toolType
     * The type of tool used in the adjustment you want to add.
     * @return true if you can add the adjustment, false otherwise.
     */
    boolean canAdd(Tools toolType);

    /**
     * @throws NoMoreActionsException
     * In case there are no more actions to undo.
     * @throws AdjustmentAlreadyPresentException 
     * @return the processed image resulting from the undo.
     */
    Image undo() throws NoMoreActionsException, AdjustmentAlreadyPresentException;

    /**
     * @throws NoMoreActionsException
     * In case there are no more actions to redo.
     * @throws AdjustmentAlreadyPresentException 
     * @return the processed image resulting from the redo.
     */
    Image redo() throws NoMoreActionsException, AdjustmentAlreadyPresentException;

    /**
     * @return String representation of the history.
     */
    String[] getHistoryStringRep();

    /**
     * @return the current number of actions saved in the procedure.
     */
    int getHistorySize();

    /**
     * Clears the procedure, resetting it to an "as new" state.
     */
    void clear();

    /**
     * Applies the adjustments to the startImage and returns it.
     * @param index the index from which to start the processing.
     * @return Image.
     */
    Image processImage(int index);
}
