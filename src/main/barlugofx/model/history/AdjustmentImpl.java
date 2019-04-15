/**
 *
 */
package barlugofx.model.history;

import barlugofx.model.imagetools.Image;
import barlugofx.model.tools.common.ImageTool;
import barlugofx.model.tools.Tools;

/**
 *
 *
 */
public class AdjustmentImpl implements Adjustment {
    private Image startImage;
    private boolean enabled;
    private String adjustmentName; // name given by the user
    private final ImageTool tool;

    /**
     * Adjustment constructor.
     * @param nodeName chosen by the user.
     * @param tool the tool used to edit the image.
     * @param startImage the (cache) image, before the tool is applied.
     */
    public AdjustmentImpl(final String nodeName, final ImageTool tool, final Image startImage) {
        if (nodeName == null) {
            throw new java.lang.IllegalArgumentException("Name reference is null");
        }
        if (tool == null) {
            throw new java.lang.IllegalArgumentException("Tool reference is null");
        }
        if (startImage == null) {
            throw new java.lang.IllegalArgumentException("startImage reference is null");
        }
        this.startImage = startImage;
        this.enabled = true;
        this.adjustmentName = nodeName;
        this.tool = tool;
    }

    /**
     * @see barlugofx.model.history.Adjustment#getStartImage()
     */
    @Override
    public Image getStartImage() {
        return this.startImage;
    }

    /**
     * @see barlugofx.model.history.Adjustment#setStartImage()
     */
    @Override
    public void setStartImage(final Image startImage) {
        if (startImage == null) {
            throw new java.lang.IllegalArgumentException("Image null reference");
        }
        this.startImage = startImage;
    }

    /**
     * @see barlugofx.model.history.Adjustment#removeStartImage()
     */
    @Override
    public void removeStartImage() {
        this.startImage = null;
    }

    /**
     * @see barlugofx.model.history.Adjustment#isEnabled()
     */
    @Override
    public boolean isEnabled() {
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

    /**
     * @see barlugofx.model.history.Adjustment#getNodeName()
     */
    @Override
    public String getName() {
        return this.adjustmentName;
    }

    /**
     * @see barlugofx.model.history.Adjustment#setNodeName(java.lang.String)
     */
    @Override
    public void setName(final String name) {
        if (name == null || name.length() == 0) {
            throw new java.lang.IllegalArgumentException("Name is either null or empty");
        }
        this.adjustmentName = name;
    }

    /**
     * @see barlugofx.model.history.Adjustment#getTool()
     */
    @Override
    public ImageTool getTool() {
        return this.tool;
    }

    /**
     *  Returns the Tool type as Enumerator. 
     * @return tool type
     */
    @Override
    public Tools getToolType() {
        return this.tool.getToolType();
    }
}
