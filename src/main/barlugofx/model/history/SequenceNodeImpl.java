/**
 *
 */
package barlugofx.model.history;

import barlugofx.model.imagetools.Image;
import barlugofx.model.tools.common.ImageTool;

/**
 *
 *
 */
public class SequenceNodeImpl implements SequenceNode {
    private final Image startImage;
    private Boolean isActive;
    private final String nodeName;
    private final ImageTool tool;

    /**
     * Some javadoc to write.
     * @param nodeName a.
     * @param tool b.
     * @param startImage c.
     */
    public SequenceNodeImpl(final String nodeName, final ImageTool tool, final Image startImage){
        this.startImage = startImage;
        isActive = true;
        this.nodeName = nodeName;
        this.tool = tool;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.SequenceNode#getStartImage()
     */
    @Override
    public Image getStartImage() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.SequenceNode#setStartImage()
     */
    @Override
    public void setStartImage() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.SequenceNode#isActive()
     */
    @Override
    public Boolean isActive() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *  Enables the tool.
     */
    @Override
    public void enable() {
        isActive = true;
    }

    /**
     *  Disable the tool.
     */
    @Override
    public void disable() {
        isActive = false;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.SequenceNode#getNodeName()
     */
    @Override
    public String getNodeName() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.SequenceNode#setNodeName(java.lang.String)
     */
    @Override
    public void setNodeName(final String name) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.SequenceNode#getTool()
     */
    @Override
    public ImageTool getTool() {
        // TODO Auto-generated method stub
        return null;
    }

}
