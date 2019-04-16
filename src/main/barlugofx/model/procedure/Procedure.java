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
     * @param adjustment
     * The Adjustment you want to add.
     * @throws AdjustmentAlreadyPresentException
     * when you try to add an Adjustment with a Tool already in use.
     */
    void addAdjustment(Adjustment adjustment) throws AdjustmentAlreadyPresentException;

    /**
     * 
     * @param index
     * Index of the adjustment you want to delete.
     */
    void removeAdjustment(int index);

    /**
     * 
     * @param index
     * Index of the adjustment you want to disable.
     */
    void disableAdjustment(int index);

    /**
     * 
     * @param index
     * Index of the adjustment you want to enable.
     */
    void enableAdjustment(int index);

    /**
     * This function returns the enabled state of adjustment.
     * @param index
     * Index of the adjustment of which I want the state
     * @return true if the adjustment is enabled, false otherwise.
     */
    boolean isAdjustmentEnabled(int index);

    /**
     * 
     * @param adjustmentName
     * Name of the adjustment of which you want the index.
     * @return index of the adjustment.
     */
    int findByName(String adjustmentName);

    /**
     * 
     * @param index
     * Index of the adjustment you want to edit.
     * @param adjustment
     * New adjustment that is going to replace the adjustment at index.
     */
    void editAdjustment(int index, Adjustment adjustment);

    /**
     * 
     * @param name
     * Name of the adjustment of which you want the value.
     * @param index
     * index of the adjustment of which you want the value.
     * @return value of the adjustment with name "name"
     */
    Optional<Parameter<? extends Number>> getValue(int index, ParameterName name);

    /**
     * 
     * @param toolType the type of adjustment you want to add.
     * @return true if you can add another adjustment, false otherwise.
     */
    boolean canAdd(Tools toolType);
}
