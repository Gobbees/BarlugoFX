/**
 * 
 */
package barlugofx.model.history;

import barlugofx.model.tools.common.Parameter;
import java.util.List;

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
    public void addTool(SequenceNode node) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#deleteTool(int)
     */
    @Override
    public void deleteTool(int index) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#hideTool(int)
     */
    @Override
    public void disableTool(int index) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#showTool(int)
     */
    @Override
    public void enableTool(int index) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#findByName(java.lang.String)
     */
    @Override
    public int findByName(String toolName) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#editTool(int, barlugofx.model.history.SequenceNode)
     */
    @Override
    public void editTool(int index, SequenceNode node) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#getValue(java.lang.String)
     */
    @Override
    public Parameter<? extends Number> getValue(String name) {
        // TODO Auto-generated method stub
        return null;
    }

}
