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
    public void addTool(final SequenceNode node) throws ToolLimitReachedException {
        if (node == null) {
            throw new java.lang.IllegalArgumentException("Node reference is null");
        }
        if (this.nameMap.containsKey(node.getNodeName())) {
            throw new java.lang.IllegalArgumentException("Node name is already in use");
        }
        if (this.nodes.size() >= HistoryImpl.TOOL_LIMIT) {
            throw new ToolLimitReachedException("History is full, can't add any more tools");
        }

        this.nameMap.put(node.getNodeName(), this.nodes.size());
        this.nodes.add(node);
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#deleteTool(int)
     */
    @Override
    public void deleteTool(final int index) {
        if (index < 0 || index >= this.nodes.size()) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big)");
        }
        this.nameMap.remove(this.nodes.get(index).getNodeName());
        this.nodes.remove(index);
        // updating indexes into the map
        for (int i = 0; i < this.nodes.size(); i++) {
            this.nameMap.replace(this.nodes.get(i).getNodeName(), i);
        }
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#hideTool(int)
     */
    @Override
    public void disableTool(final int index) {
        if (index < 0 || index >= this.nodes.size()) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big)");
        }
        this.nodes.get(index).disable();
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#showTool(int)
     */
    @Override
    public void enableTool(final int index) {
        if (index < 0 || index >= this.nodes.size()) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big)");
        }
        this.nodes.get(index).enable();
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#findByName(java.lang.String)
     */
    @Override
    public int findByName(final String toolName) {
        Integer index = this.nameMap.get(toolName);
        if (index == null) {
            return -1;
        }
        return (int) index;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#editTool(int, barlugofx.model.history.SequenceNode)
     */
    @Override
    public void editTool(final int index, final SequenceNode node) {
        if (index < 0 || index >= this.nodes.size()) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big)");
        }
        if (node == null) {
            throw new java.lang.IllegalArgumentException("node reference is null");
        }
        this.deleteTool(index);
        this.nodes.add(index, node);
        this.nameMap.put(node.getNodeName(), index);
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.History#getValue(java.lang.String)
     */
    @Override
    public Optional<Parameter<? extends Number>> getValue(final int index, final ParametersName name) {
        if (index < 0 || index >= this.nodes.size()) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big)");
        }
        if (name == null) {
            throw new java.lang.IllegalArgumentException("name reference is null");
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
        if (index < 0 || index >= this.nodes.size()) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big)");
        }
        return this.nodes.get(index).isEnabled();
    }

    /**
     * 
     * @return string representation of nodes array
     */
    public String nodeNamesToString() {
        String res = "";
        for (int i = 0; i < this.nodes.size(); i++) {
            res = res + "[" + i + "]" + this.nodes.get(i).getNodeName() + ",";
        }
        return res;
    }
}
