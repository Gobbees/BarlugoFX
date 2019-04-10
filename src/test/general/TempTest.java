package general;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.imagetools.ImageUtils;
import barlugofx.model.tools.HSBModifier;
import barlugofx.model.tools.Vibrance;
import barlugofx.model.tools.common.ImageTool;
import barlugofx.model.tools.common.ParameterImpl;
import barlugofx.model.tools.common.ParameterName;

/**
 * A Rapid TEST.
 *
 *
 */
public final class TempTest {
    private static final float PARAMETER = 0.2f;
    private static final int TIME_DIVIDER = 1000000;
    private TempTest() { }
    /**
     * TEST.
     * @param args a.
     * @throws IOException a.
     */
    public static void main(final String[] args) throws IOException {
        final File file  = new File("/*Insert Image path here.*/");
        final BufferedImage image = ImageIO.read(file);
        final Image toWorkWith = ImageImpl.buildFromBufferedImage(image);
        final ImageTool vibrance = Vibrance.createVibrance();
        final ImageTool hsbTrue = HSBModifier.createHSB();
        long time = System.nanoTime();
        hsbTrue.addParameter(ParameterName.SATURATION, new ParameterImpl<>(PARAMETER));
        vibrance.addParameter(ParameterName.VIBRANCE_INCREMENT, new ParameterImpl<>(PARAMETER));
        final BufferedImage output = ImageUtils.convertImageToBufferedImageWithAlpha(hsbTrue.applyTool(toWorkWith));

        final BufferedImage outputB = ImageUtils.convertImageToBufferedImageWithAlpha(vibrance.applyTool(toWorkWith));
        time = System.nanoTime() - time;
        ImageIO.write(output, "png", new File("Insert Image path here."));
        ImageIO.write(outputB, "png", new File("Insert Image path here."));
        System.out.println("Execution time in milliseconds : " + time / TIME_DIVIDER);
    }
}
