/**
 * 
 */
package barlugofx.model.history;

import barlugofx.model.tools.common.Parameter;
import barlugofx.model.tools.common.ParametersName;

import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 *
 */
public class HistoryImpl implements History {
    private static final int TOOL_LIMIT = 16;
    private ArrayList<SequenceNode> nodes = new ArrayList<SequenceNode>();
    private HashMap<String, Integer> nameMap = new HashMap<String, Integer>();

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#addFilter(barlugofx.model.history.SequenceNode)
     */
    @Override
    public void addTool(final SequenceNode node) {
        if (node == null) {
            // TODO throw
        }
        if (this.nodes.size() < 16) {
            this.nodes.add(node);
            this.nameMap.put(node.getNodeName(), this.nodes.size());
        }
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#deleteTool(int)
     */
    @Override
    public void deleteTool(final int index) {
        if (index < 0 || index > this.nodes.size()) {
            // throw
        }
        this.nameMap.remove(this.nodes.get(index).getNodeName());
        this.nodes.remove(index);
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#hideTool(int)
     */
    @Override
    public void disableTool(final int index) {
        if (index < 0 || index > this.nodes.size()) {
            // throw
        }
        this.nodes.get(index).disable();
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#showTool(int)
     */
    @Override
    public void enableTool(final int index) {
        if (index < 0 || index > this.nodes.size()) {
            // TODO throw
        }
        this.nodes.get(index).enable();
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#findByName(java.lang.String)
     */
    @Override
    public int findByName(final String toolName) {
        return this.nameMap.get(toolName);
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#editTool(int, barlugofx.model.history.SequenceNode)
     */
    @Override
    public void editTool(final int index, final SequenceNode node) {
        if (index < 0 || index > this.nodes.size()) {
            // TODO throw
        }
        this.nodes.remove(index);
        this.nodes.add(index, node);
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#getValue(java.lang.String)
     */
    @Override
    public Optional<Parameter<? extends Number>> getValue(final int index, final ParametersName name) {
        if (index < 0 || index > this.nodes.size()) {
            // TODO throw
        }
        return this.nodes.get(index).getTool().getParameter(name);
    }

    @Override
    /**
     * 
     * @return true if you can add another tool, false otherwise.
     */
    public boolean canAdd() {
        return (this.nodes.size() < HistoryImpl.TOOL_LIMIT);
    }

    /**
     * 
     * @return the maximum number of tools you can have in the history.
     */
    @Override
    public int getLimit() {
        return HistoryImpl.TOOL_LIMIT;
    }

    /**
     * 
     */
    @Override
    public boolean isToolEnabled(final int index) {
        if (index < 0 || index > this.nodes.size()) {
            // TODO throw
        }
        return this.nodes.get(index).isEnabled();
    }

}
