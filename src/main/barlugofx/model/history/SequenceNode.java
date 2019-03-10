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
     * dafd.
     * @return Image before the filter is applied.
     */
    Image getStartImage();

    /**
     * 
     */
    void setStartImage();

    /**
     * 
     * @return true if the filter is active
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
     * nome del filtro
     */
    void setNodeName(String name);

    /**
     * 
     * @return filter
     */
    ImageTool getFilter();
}
