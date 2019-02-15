package com.barlugofx.model.tools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.barlugofx.model.imageTools.Image;
import com.barlugofx.model.imageTools.ImageImpl;
import com.barlugofx.model.imageTools.ImageUtilities;
import com.barlugofx.model.tools.common.ImageFilterImpl;
import com.barlugofx.model.tools.common.ParametersName;

/**
 * This class handles the change of an image involving Hue, Saturation and Exposure (Brightness in HSB). This class accepts up
 * to three parameter: HUE, which must be a positive float number, Saturation, which must be a float number between -1 and 1, and Exposure,
 * a float number between -1 and 1. By receiving more than one parameter, this class permits multiple changes to HSV limiting conversions,
 * resulting in more efficient changes.
 *
 */
public final class HSBModifier extends ImageFilterImpl {
    private static final int MAX = 1;
    private static final int MIN = -1;
    private static final Set<ParametersName> ACCEPTED = new HashSet<>(
            Arrays.asList(ParametersName.EXPOSURE, ParametersName.HUE, ParametersName.SATURATION));

    private HSBModifier() {
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
        final float hue = super.getValueFromParameter(ParametersName.HUE, 0, Float.MAX_VALUE, 0f);
        final float saturation = getValueFromParameter(ParametersName.SATURATION, 0, Float.MAX_VALUE, 0f);
        final float exposure = getValueFromParameter(ParametersName.EXPOSURE, 0, Float.MAX_VALUE, 0f);

        if (saturation < MIN || saturation > MAX) {
            throw new IllegalStateException("Saturation should be between -1 and 1");
        }
        if (exposure < MIN || exposure > MAX) {
            throw new IllegalStateException("Exposure should be between -1 and 1");
        }

        final float[][][] hsv = ImageUtilities.rgbToHsb(toApply.getImageRGBvalues());
        for (int i = 0; i < hsv.length; i++) {
            for (int j = 0; j < hsv[0].length; j++) {
                hsv[i][j][0] = hue == 0 ? hsv[i][j][0] : truncateSum(hsv[i][j][0], hue);
                hsv[i][j][1] = saturation == 0 ? hsv[i][j][1] : truncateSum(hsv[i][j][1], saturation);
                hsv[i][j][2] = exposure == 0 ? hsv[i][j][2] : truncateSum(hsv[i][j][2], exposure);
            }
        }
        return ImageImpl.buildFromPixels(ImageUtilities.hsbToRgb(hsv));
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
