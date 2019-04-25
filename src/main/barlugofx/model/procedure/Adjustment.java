package barlugofx.model.procedure;

import barlugofx.model.imagetools.Image;
import barlugofx.model.tools.Tools;
import barlugofx.model.tools.common.ImageTool;
import barlugofx.model.tools.common.ParallelizableImageTool;
/**
 * This interface models an Adjustment applied to an Image.
 * An Adjustment is a change to the Image; each change it's made using an ImageFilter, which is accessible through the method.
 * An Adjustment caches its start image inside, to improve performance.
 */
public interface Adjustment {
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
     * Returns the adjustment name as string representation of Enum.
     * @return adjustment name
     */
    String getName();


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

    /**
     * @return true if the tool is parallelizable, false otherwise.
     */
    boolean isParallelizable();

    /**
     * @return the parallelizable tool.
     */
    ParallelizableImageTool getParallelizableTool();

    /**
     * Returns the start image.
     * @return Image before the tool is applied.
     */
    Image getStartImage();

    /**
     * @param startImage 
     * Sets the cached Image before the tool is applied.
     * Also deletes the end image, as it must be recalculated.
     */
    void setStartImage(Image startImage);
 
    /**
     * Drops both start and end image from the Adjustment.
     * When the start image changes, automatically the end image is outdated.
     */
    void removeStartImage();

    /**
     * 
     * @return true if the image is present, false otherwise.
     */
    boolean isStartImagePresent();

    /**
     * Returns the image after the application of the adjustment.
     * @return Image after the tool execution.
     */
    Image getEndImage();

    /**
     * Saves the image after the tool execution as cache.
     * @param endImage the image that resulted from the tool execution.
     */
    void setEndImage(Image endImage);

    /**
     * Removes the cached end image.
     */
    void removeEndImage();

    /**
     * Returns true if the end image is present.
     * @return true if the image is present, false otherwise.
     */
    boolean isEndImagePresent();
}
