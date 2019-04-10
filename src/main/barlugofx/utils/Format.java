package barlugofx.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This enum lists all the possible formats supported by BarlugoFX.
 *
 */
public enum Format {
    /**
     * PNG Format.
     */
    PNG(".png", "png", "*.png", "*.PNG"),
    /**
     * JPEG Format.
     */
    JPEG(".jpeg", "jpeg", "*.jpg", "*.JPG", "*.jpeg", "*.JPEG"),
    /**
     * GIF Format.
     */
    GIF(".gif", "gif", "*.gif", "*.GIF");
    private final String extension;
    private final String outputForm;
    private final List<String> possibleInputs;

    Format(final String extension, final String outputForm, final String... possibleInputs) {
        this.extension = extension;
        this.outputForm = outputForm;
        this.possibleInputs = new ArrayList<>(Arrays.asList(possibleInputs));
    }
    /**
     * Returns the format's extension.
     * @return the extension
     */
    public String toExtension() {
        return this.extension;
    }
    /**
     * Returns the format's output form (used by ImageIO library).
     * @return the output form
     */
    public String toOutputForm() {
        return this.outputForm;
    }
    /**
     * Returns the possible input formats.
     * @return the input formats.
     */
    public List<String> getPossibleInputs() {
        return Collections.unmodifiableList(possibleInputs);
    }
    /**
     * This function returns all the possible input formats that barlugofx accepts.
     * @return the list of all possible input formats
     */
    public static List<String> getAllPossibleInputs() {
        final List<String> output = new ArrayList<>();
        output.addAll(PNG.possibleInputs);
        output.addAll(JPEG.possibleInputs);
        output.addAll(GIF.possibleInputs);
        return output;
    }
}
