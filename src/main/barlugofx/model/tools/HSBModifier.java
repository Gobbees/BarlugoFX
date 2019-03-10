package barlugofx.model.tools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.imagetools.ImageUtils;
import barlugofx.model.tools.common.ImageToolImpl;
import barlugofx.model.tools.common.ParametersName;

/**
 * This class handles the change of an image involving Hue, Saturation and Exposure (Brightness in HSB). This class accepts up
 * to three parameter: HUE, which must be a positive float number between -1 and 1, Saturation, which must be a float number between -1 and 1, and Exposure,
 * a float number between -1 and 1. By receiving more than one parameter, this class permits multiple changes to HSV limiting conversions,
 * resulting in more efficient changes.
 *
 */
public final class HSBModifier extends ImageToolImpl {
    private static final int MAX = 1;
    private static final int MIN = -1;
    private static final float DEFAULT_VALUE = 0f;
    private static final Set<ParametersName> ACCEPTED = new HashSet<>(
            Arrays.asList(ParametersName.EXPOSURE, ParametersName.HUE, ParametersName.SATURATION));

    private HSBModifier() {
        super();
    }
    /**
     * Creates a new HSBModifier.
     * @return the instantieted modifier.
     */
    public static HSBModifier createHSB() {
        return new HSBModifier();
    }

    @Override
    public Image applyFilter(final Image toApply) {
        final float hue = super.getValueFromParameter(ParametersName.HUE, MIN, MAX, DEFAULT_VALUE);
        final float saturation = getValueFromParameter(ParametersName.SATURATION, MIN, MAX, DEFAULT_VALUE);
        final float exposure = getValueFromParameter(ParametersName.EXPOSURE, MIN, MAX, DEFAULT_VALUE);

        final float[][][] hsv = ImageUtils.rgbToHsb(toApply.getImageRGBvalues());
        for (int i = 0; i < hsv.length; i++) {
            for (int j = 0; j < hsv[0].length; j++) {
                hsv[i][j][0] = hue == 0 ? hsv[i][j][0] : hsv[i][j][0] + hue; //truncate qui non e' necessario
                hsv[i][j][1] = saturation == 0 ? hsv[i][j][1] : truncateSum(hsv[i][j][1], saturation);
                hsv[i][j][2] = exposure == 0 ? hsv[i][j][2] : truncateSum(hsv[i][j][2], exposure);
            }
        }
        return ImageImpl.buildFromPixels(ImageUtils.hsbToRgb(hsv));
    }

    private float truncateSum(final float hsv, final float hue) {
        final float result = hsv + hue;
        if (result > MAX) {
            return MAX;
        } else {
            if (result < 0) {
                return 0;
            } else {
                return result;
            }
        }
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return ACCEPTED.contains(name);
    }

}
