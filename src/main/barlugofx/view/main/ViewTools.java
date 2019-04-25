package barlugofx.view.main;

/**
 * This enumeration contains all the supported tool names (view side).
 */
enum ViewTools {
    EXPOSURE(0), CONTRAST(0), BRIGHTNESS(0), WHITEBALANCE(50), SATURATION(0), HUE(0), VIBRANCE(0), SCR(0), SCG(0),
    SCB(0), BWR(50), BWG(50), BWB(50);
    private final int defaultValue;

    ViewTools(final int defVal) {
        this.defaultValue = defVal;
    }

    /**
     * Returns the Tool graphic default value.
     * 
     * @return the default value
     */
    public int getDefaultValue() {
        return defaultValue;
    }
}
