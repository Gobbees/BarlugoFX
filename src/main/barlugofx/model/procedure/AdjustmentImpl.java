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
    private Image endImage;
    private boolean enabled;
    private String adjustmentName; // name given by the user
    private final ImageTool tool;
    private final ParallelizableImageTool parallelizableTool;
    private final boolean parallelizable;

    /**
     * Adjustment constructor with unparallelizable image tool.
     * @param adjustmentName chosen by the user.
     * @param tool the tool used to edit the image.
     */ 
    public AdjustmentImpl(final String adjustmentName, final ImageTool tool) {
        if (tool == null) {
            throw new IllegalArgumentException("Tool reference is null");
        }
        if (adjustmentName == null) {
            throw new IllegalArgumentException("Name reference is null");
        }
        this.enabled = true;
        this.adjustmentName = adjustmentName;
        this.parallelizable = false;
        this.tool = tool;
        this.parallelizableTool = null;
        this.startImage = null;
        this.endImage = null;
    }

    /**
     * Adjustment constructor with parallelizable image tool.
     * @param adjustmentName chosen by the user.
     * @param tool the (parallelizable) tool used to create the image
     */
    public AdjustmentImpl(final String adjustmentName, final ParallelizableImageTool tool) {
        if (tool == null) {
            throw new IllegalArgumentException("Tool reference is null");
        }
        if (adjustmentName == null) {
            throw new IllegalArgumentException("Name reference is null");
        }
        this.enabled = true;
        this.adjustmentName = adjustmentName;
        this.parallelizable = true;
        this.parallelizableTool = tool;
        this.tool = null;
        this.startImage = null;
        this.endImage = null;
    }

    @Override
    public Image getStartImage() {
        return this.startImage;
    }

    @Override
    public void setStartImage(final Image startImage) {
        if (startImage == null) {
            throw new IllegalArgumentException("Image null reference");
        }
        this.startImage = startImage;
        this.removeEndImage();
    }

    @Override
    public void removeStartImage() {
        this.startImage = null;
        this.removeEndImage();
    }

    @Override
    public boolean isStartImagePresent() {
        return (this.startImage != null);
    }

    @Override
    public Image getEndImage() {
        return this.endImage;
    }

    @Override
    public void setEndImage(final Image endImage) {
        if (endImage == null) {
            throw new IllegalArgumentException("Image null reference");
        }
        this.endImage = endImage;
    }

    @Override
    public void removeEndImage() {
        this.endImage = null;
    }

    @Override
    public boolean isEndImagePresent() {
        return (this.endImage != null);
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
            throw new IllegalArgumentException("Name is either null or empty");
        }
        this.adjustmentName = name;
    }

    @Override
    public boolean isParallelizable() {
        return this.parallelizable;
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
