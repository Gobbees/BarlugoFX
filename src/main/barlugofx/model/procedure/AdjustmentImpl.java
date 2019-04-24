package barlugofx.model.procedure;

import barlugofx.model.imagetools.Image;
import barlugofx.model.tools.common.ImageTool;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.Tools;

/**
 * Adjustment implementation.
 * Has the ImageTool used for the change and an internal image for caching.
 */
public final class AdjustmentImpl implements Adjustment {
    private Image startImage;
    private boolean enabled;
    private String adjustmentName; // name given by the user
    private final ImageTool tool;
    private final ParallelizableImageTool parallelizableTool;
    private final boolean isParallelizable;

    /**
     * Adjustment constructor with unparallelizable image tool.
     * @param adjustmentName chosen by the user.
     * @param tool the tool used to edit the image.
     * @param startImage the (cache) image, before the tool is applied.
     */ 
    public AdjustmentImpl(final String adjustmentName, final ImageTool tool, final Image startImage) {
        if (tool == null) {
            throw new java.lang.IllegalArgumentException("Tool reference is null");
        }
        if (adjustmentName == null) {
            throw new java.lang.IllegalArgumentException("Name reference is null");
        }
        if (startImage == null) {
            throw new java.lang.IllegalArgumentException("startImage reference is null");
        }
        this.startImage = startImage;
        this.enabled = true;
        this.adjustmentName = adjustmentName;
        this.isParallelizable = false;
        this.tool = tool;
        this.parallelizableTool = null;
    }

    /**
     * Adjustment constructor with parallelizable image tool.
     * @param adjustmentName chosen by the user.
     * @param tool the (parallelizable) tool used to create the image
     * @param startImage the (cache) image, before the tool is applied.
     */
    public AdjustmentImpl(final String adjustmentName, final ParallelizableImageTool tool, final Image startImage) {
        if (tool == null) {
            throw new java.lang.IllegalArgumentException("Tool reference is null");
        }
        if (adjustmentName == null) {
            throw new java.lang.IllegalArgumentException("Name reference is null");
        }
        if (startImage == null) {
            throw new java.lang.IllegalArgumentException("startImage reference is null");
        }
        this.startImage = startImage;
        this.enabled = true;
        this.adjustmentName = adjustmentName;
        this.isParallelizable = true;
        this.parallelizableTool = tool;
        this.tool = null;
    }

    @Override
    public Image getStartImage() {
        return this.startImage;
    }

    @Override
    public void setStartImage(final Image startImage) {
        if (startImage == null) {
            throw new java.lang.IllegalArgumentException("Image null reference");
        }
        this.startImage = startImage;
    }

    @Override
    public void removeStartImage() {
        this.startImage = null;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void enable() {
        this.enabled = true;
    }

    @Override
    public void disable() {
        this.enabled = false;
    }

    @Override
    public String getName() {
        return this.adjustmentName;
    }

    @Override
    public void setName(final String name) {
        if (name == null || name.length() == 0) {
            throw new java.lang.IllegalArgumentException("Name is either null or empty");
        }
        this.adjustmentName = name;
    }

    @Override
    public boolean isParallelizable() {
        return this.isParallelizable;
    }

    @Override
    public ImageTool getTool() {
        return this.tool;
    }

    @Override
    public ParallelizableImageTool getParallelizableTool() {
        return this.parallelizableTool;
    }

    @Override
    public Tools getToolType() {
        return this.tool.getToolType();
    }
}
