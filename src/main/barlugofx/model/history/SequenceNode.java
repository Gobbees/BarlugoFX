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
     * 
     */
    void setStartImage();

    /**
     * 
     * @return true if the tool is active.
     */
    Boolean isActive();

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
