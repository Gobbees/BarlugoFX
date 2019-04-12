package barlugofx.model.tools;

/**
 * This enumeration contains all the supported tool names (model side).
 */
public enum Tools {
    /**
     * Enum for the Exposure tool.
     */
    EXPOSURE,

    /**
     * Enum for the Contrast tool.
     */
    CONTRAST,

    /**
     * Enum for the Brightness tool.
     */
    BRIGHTNESS,

    /**
     * Enum for the White Balance tool.
     */
    WHITEBALANCE,

    /**
     * Enum for the Saturation tool.
     */
    SATURATION,

    /**
     * Enum for the Hue tool.
     */
    HUE,

    /**
     * Enum for the Vibrance tool.
     */
    VIBRANCE,

    /**
     * Enum for the SelectiveColor tool.
     */
    SELECTIVECOLOR,

    /**
     * Enum for the Black and White tool.
     */
    BLACKANDWHITE;

    private final int size;

    /**
     * Get the number of constants in the enum.
     * @return the number of constants in the enum.
     */
    public int getSize() {
        return this.size;
    }

    Tools() {
        int fieldCounter = 0;
        for (Tools t : Tools.values()) {
            fieldCounter++;
        }
        this.size = fieldCounter;
    }
}
