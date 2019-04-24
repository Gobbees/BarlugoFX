package barlugofx.model.tools;

/**
 * This enumeration contains all the supported tool names (model side).
 */
public enum Tools {
    /**
     * Enum for the Exposure tool.
     */
    EXPOSURE("Exposure"),

    /**
     * Enum for the Contrast tool.
     */
    CONTRAST("Contrast"),

    /**
     * Enum for the Brightness tool.
     */
    BRIGHTNESS("Brightness"),

    /**
     * Enum for the White Balance tool.
     */
    WHITEBALANCE("White Balance"),

    /**
     * Enum for the Saturation tool.
     */
    SATURATION("Saturation"),

    /**
     * Enum for the Hue tool.
     */
    HUE("Hue"),

    /**
     * Enum for the Vibrance tool.
     */
    VIBRANCE("Vibrance"),

    /**
     * Enum for the SelectiveRGBChanger tool.
     */
    SELECTIVECOLOR("Selective Colour"),

    /**
     * Enum for the Black and White tool.
     */
    BLACKANDWHITE("Black and White"),

    /**
     * Enum for Crop tool.
     */
    CROPPER("Cropper"),

    /**
     * Enum for rotate tool.
     */
    ROTATOR("Rotator");

    private final String name;
    Tools(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
}
