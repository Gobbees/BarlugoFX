package barlugofx.model.history;

import barlugofx.model.imagetools.Image;
import barlugofx.model.tools.common.ImageTool;
import barlugofx.model.tools.Tools;
/**
 * 
 * 
 *
 */
public interface Adjustment {
    /**
     *
     * @return Image before the tool is applied.
     */
    Image getStartImage();

    /**
     * @param startImage 
     * Sets the Image before the tool is applied.
     */
    void setStartImage(Image startImage);
 
    /**
     * Removes start image from the Adjustment.
     * This method is needed for lighter storage in the modification History.
     */
    void removeStartImage();

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
     *  Disables the tool.
     */
    void disable();

    /**
     * Returns the adjustment name.
     * The name is chosen by the User.
     * @return adjustment name
     */
    String getName();

    /**
     * 
     * @param name 
     * Sets the Adjustment name.
     * The name is choosen by the User.
     */
    void setName(String name);

    /**
     * 
     * @return ImageTool used in the Adjustment.
     */
    ImageTool getTool();

    /**
     * @return Tool type as Tools Enumerator.
     */
    Tools getToolType();
}
