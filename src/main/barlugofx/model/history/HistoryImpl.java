/**
 * 
 */
package barlugofx.model.history;

import barlugofx.model.tools.common.Parameter;
import barlugofx.model.tools.common.ParametersName;

import java.util.List;
import java.util.Optional;

/**
 *
 *
 */
public class HistoryImpl implements History {
    private List<SequenceNode> tools;

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#addFilter(barlugofx.model.history.SequenceNode)
     */
    @Override
    public void addTool(final SequenceNode node) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#deleteTool(int)
     */
    @Override
    public void deleteTool(final int index) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#hideTool(int)
     */
    @Override
    public void disableTool(final int index) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#showTool(int)
     */
    @Override
    public void enableTool(final int index) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#findByName(java.lang.String)
     */
    @Override
    public int findByName(final String toolName) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#editTool(int, barlugofx.model.history.SequenceNode)
     */
    @Override
    public void editTool(final int index, final SequenceNode node) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#getValue(java.lang.String)
     */
    @Override
    public Optional<Parameter<? extends Number>> getValue(final int index, final ParametersName name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    /**
     * 
     * @return true if you can add another tool, false otherwise.
     */
    public boolean canAdd() {
        // TODO
        return false;
    }

    /**
     * 
     * @return the maximum number of tools you can have in the history.
     */
    @Override
    public int getLimit() {
        // TODO
        return 42;
    }

}
