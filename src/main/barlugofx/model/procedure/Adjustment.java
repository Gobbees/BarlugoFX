package barlugofx.model.procedure;

import barlugofx.model.imagetools.Image;

import barlugofx.model.tools.common.ImageTool;
import barlugofx.model.tools.Tools;
/**
 * This interface models an Adjustment applied to an Image.
 * An Adjustment is a change to the Image; each change it's made using an ImageFilter, which is accessible through the method.
 * An Adjustment caches its start image inside, to improve performance.
 */
public interface Adjustment {
    /**
     * Returns the start image.
     * @return Image before the tool is applied.
     */
    Image getStartImage();

    /**
     * @param startImage 
     * Sets the Image before the tool is applied.
     */
    void setStartImage(Image startImage);
 
    /**
     * Drops start image from the Adjustment.
     */
    void removeStartImage();

    /**
     * Get the state of the adjustment.
     * @return true if the adjustment is enabled, false otherwise.
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
     * Sets the name of the Adjustment.
     * @param name 
     * Sets the Adjustment name.
     * The name is choosen by the User.
     */
    void setName(String name);

    /**
     * Returns the ImageTool used by the Adjustment.
     * @return the ImageTool used in the Adjustment.
     */
    ImageTool getTool();

    /**
     * Returns the Tool type.
     * @return Tool type as Tools Enumerator.
     */
    Tools getToolType();
}
