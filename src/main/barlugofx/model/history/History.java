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
     * Sequence node you want to add
     */
    void addFilter(SequenceNode node);

    /**
     * 
     * @param index
     * Index of the filter you want to delete.
     */
    void deleteFilter(int index);

    /**
     * 
     * @param index
     * Index of the filter you want to hide.
     */
    void hideFilter(int index);

    /**
     * 
     * @param index
     * Index of the filter you want to show
     */
    void showFilter(int index);

    /**
     * 
     * @param filterName
     * Name of the filter of which you want the index.
     * @return index of the filter.
     */
    int findByName(String filterName);

    /**
     * 
     * @param index
     * Index of the filter you want to edit.
     * @param node
     * New node that is going to replace the node at index.
     */
    void editFilter(int index, SequenceNode node);

    /**
     * 
     * @param name
     * Name of the filter of which you want the value.
     * @return value of the filter with name "name"
     */
    Parameter<Double> getValue(String name);
}
