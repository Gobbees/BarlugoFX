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
    private Image startImage;
    private boolean enabled;
    private String nodeName;
    private final ImageTool tool;

    /**
     * Some javadoc to write.
     * @param nodeName a.
     * @param tool b.
     * @param startImage c.
     */
    public SequenceNodeImpl(final String nodeName, final ImageTool tool, final Image startImage) {
        this.startImage = startImage;
        enabled = true;
        this.nodeName = nodeName;
        this.tool = tool;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.SequenceNode#getStartImage()
     */
    @Override
    public Image getStartImage() {
        return startImage;
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
        return enabled;
    }

    /**
     *  Enables the tool.
     */
    @Override
    public void enable() {
        enabled = true;
    }

    /**
     *  Disable the tool.
     */
    @Override
    public void disable() {
        enabled = false;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.SequenceNode#getNodeName()
     */
    @Override
    public String getNodeName() {
        return nodeName;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.SequenceNode#setNodeName(java.lang.String)
     */
    @Override
    public void setNodeName(final String name) {
        if (name == null || name.length() == 0) {
            // TODO throw IllegalArgumentException
        }
        nodeName = name;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.history.SequenceNode#getTool()
     */
    @Override
    public ImageTool getTool() {
        return tool;
    }

}
