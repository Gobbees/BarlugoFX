package com.barlugofx.model.tools;

import com.barlugofx.model.imageTools.Color;
import com.barlugofx.model.imageTools.ColorImpl;
import com.barlugofx.model.imageTools.Image;
import com.barlugofx.model.imageTools.ImageImpl;
import com.barlugofx.model.tools.common.ImageFilterImpl;
import com.barlugofx.model.tools.common.ParametersName;

/**
 * A contrast class that allows to change an image contrast. It only accepts one parameter, Contrast, which must be between
 * -255 and 255. Eventual other value will result in an {@link IllegalStateException}.
 *
 *
 */
public final class Contrast extends ImageFilterImpl {
    private static final double MAXVALUE = 255;
    private static final int TRANSLATION = 128;

    private Contrast() {
    }
    /**
     * Creates a new Contrast class.
     * @return a new Contrast element.
     */
    public static Contrast createContrast() {
        return new Contrast();
    }

    @Override
    public Image applyFilter(final Image toApply) {
        int value = 0;
        try {
            value = (int) super.getParameter(ParametersName.CONTRAST).getValue();
        } catch (final ClassCastException e) {
            throw new IllegalStateException("The parameter Contrast should be an int");
        }
        if (value < -MAXVALUE || value > MAXVALUE) {
            throw new IllegalStateException("The parameter Contrast exceed the range of accepted value");
        }
        final double contrastCorrectionFactor = (MAXVALUE + 4) * (value + MAXVALUE)
                / (MAXVALUE * (MAXVALUE + 4 - value));

        final Color setter = ColorImpl.createColorExtractor();
        final int[][] pixels = toApply.getImageRGBvalues();
        final int[][] newPixels = new int[pixels.length][pixels[0].length];
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                newPixels[i][j] = pixels[i][j];
                newPixels[i][j] = setter.setBlue(newPixels[i][j],
                        (int) (contrastCorrectionFactor * (setter.getBlue(pixels[i][j]) - TRANSLATION) + TRANSLATION));
                newPixels[i][j] = setter.setGreen(newPixels[i][j],
                        (int) (contrastCorrectionFactor * (setter.getGreen(pixels[i][j]) - TRANSLATION) + TRANSLATION));
                newPixels[i][j] = setter.setRed(newPixels[i][j],
                        (int) (contrastCorrectionFactor * (setter.getRed(pixels[i][j]) - TRANSLATION) + TRANSLATION));
            }
        }
        return ImageImpl.buildFromPixels(newPixels);
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return name == ParametersName.CONTRAST;
    }

}