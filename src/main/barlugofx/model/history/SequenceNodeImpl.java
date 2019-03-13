/**
 *
 */
package barlugofx.model.history;

import barlugofx.model.imageTools.Image;
import barlugofx.model.tools.common.ImageTool;

/**
 *
 *
 */
public class SequenceNodeImpl implements SequenceNode {
    private Image startImage;
    private boolean enabled;
    private String nodeName;
    private ImageTool tool;

    /**
     * Some javadoc to write.
     * @param nodeName a.
     * @param tool b.
     * @param startImage c.
     */
    public SequenceNodeImpl(final String nodeName, final ImageTool tool, final Image startImage){
        this.startImage = startImage;
        this.enabled = true;
        this.nodeName = nodeName;
        this.tool = tool;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.SequenceNode#getStartImage()
     */
    @Override
    public Image getStartImage() {
        return this.startImage;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.SequenceNode#setStartImage()
     */
    @Override
    public void setStartImage(final Image startImage) {
        if (startImage == null) {
            // TODO throw IllegalArgumentException
        }
        this.startImage = startImage;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.SequenceNode#isActive()
     */
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return this.enabled;
    }

    /**
     *  Enables the tool.
     */
    @Override
    public void enable() {
        this.enabled = true;
    }

    /**
     *  Disable the tool.
     */
    @Override
    public void disable() {
        this.enabled = false;
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
