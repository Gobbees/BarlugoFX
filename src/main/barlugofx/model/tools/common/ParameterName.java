package barlugofx.model.tools.common;

/**
 * This enum contains the possible parameters a tool can have. Note that each tool have a specific parameter that accepts.
 * You should look on each tool documentation in order to find what parameters are necessary.
 *
 */
public enum ParameterName {
    /**
     * parameter for CONTRAST filter.
     */
    CONTRAST,
    /**
     * parameter for SATURATION filter.
     */
    SATURATION,
    /**
     *  parameter for HUE filter.
     */
    HUE,
    /**
     * parameter for RGBCHANGES filter.
     */
    RED,
    /**
     * parameter for RGBCHANGES filter.
     */
    BLUE,
    /**
     * parameter for RGBCHANGES filter.
     */
    GREEN,
    /**
     * parameter for BlackAndWhite filter.
     */
    WBLUE,
    /**
     * parameter for BlackAndWhite filter.
     */
    WGREEN,
    /**
     * parameter for BlackAndWhite filter.
     */
    WRED,
    /**
     * parameter for EXPOSURE (BRIGHTNESS in HSB) filter.
     */
    EXPOSURE,
    /**
     * parameter for WHITEBALANCE filter.
     */
    WHITEBALANCE,
    /**
     * parameter for CROP filter.
     */
    X1,
    /**
     * parameter for CROP filter.
     */
    X2,
    /**
     * parameter for CROP filter.
     */
    Y1,
    /**
     * parameter for CROP filter.
     */
    Y2,
    /**
     * parameter for ROTATE filter.
     */
    ANGLE,
    /**
     * specify the increment of the VIBRANCE.
     */
    VIBRANCE_INCREMENT,
    /**
     * parameter for BRIGHTNESS filter.
     */
    BRIGHTNESS;
}
