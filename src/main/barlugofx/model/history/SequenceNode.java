package barlugofx.model.history;

import barlugofx.model.imagetools.Image;
import barlugofx.model.tools.common.ImageTool;

/**
 * 
 * 
 *
 */
public interface SequenceNode {
    /**
     *
     * @return Image before the tool is applied.
     */
    Image getStartImage();

    /**
     * @param startImage 
     * Image before the tool il applied.
     */
    void setStartImage(Image startImage);

    /**
     * 
     * @return true if the tool is enabled, false otherwise.
     */
    boolean isEnabled();

    /**
     *  Enables the tool.
     */
    void enable();

    /**
     *  Disable the tool.
     */
    void disable();

    /**
     * 
     * @return node name
     */
    String getNodeName();

    /**
     * 
     * @param name 
     * nome del tool
     */
    void setNodeName(String name);

    /**
     * 
     * @return tool
     */
    ImageTool getTool();
}
