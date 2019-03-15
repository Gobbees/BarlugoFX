package general;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.imagetools.ImageUtils;
import barlugofx.model.parallelhandler.ParallelFilterExecutor;
import barlugofx.model.tools.Brightness;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParameterImpl;
import barlugofx.model.tools.common.ParametersName;

/**
 * A Rapid TEST.
 *
 *
 */
public final class TempParallelTest {
    private static final int PARAMETER = 150;
    private static final int TIME_DIVIDER = 1000000;
    private TempParallelTest() { }
    /**
     * TEST.
     * @param args a.
     * @throws IOException a.
     */
    public static void main(final String[] args) throws IOException {
        final File file  = new File("/*Insert Image path here.*/");
        final BufferedImage image = ImageIO.read(file);
        final Image toWorkWith = ImageImpl.buildFromBufferedImage(image);
        final ParallelizableImageTool brightness = Brightness.createBrightness();
        brightness.addParameter(ParametersName.BRIGHTNESS, new ParameterImpl<>(PARAMETER));

        long time1 = System.nanoTime();
        final BufferedImage output = ImageUtils.convertImageToBufferedImageWithAlpha(brightness.applyFilter(toWorkWith));
        time1 = System.nanoTime() - time1;

        final ParallelFilterExecutor exec = ParallelFilterExecutor.executor();
        System.out.println(ParallelFilterExecutor.shouldYouParallelize(toWorkWith));
        long time2 = System.nanoTime();
        final BufferedImage output2 = ImageUtils.convertImageToBufferedImageWithAlpha(exec.applyTool(brightness, toWorkWith));
        time2 = System.nanoTime() - time2;
        System.out.println("NO parallel : " + time1 / TIME_DIVIDER);
        System.out.println("YES parallel : " + time2 / TIME_DIVIDER);
        ImageIO.write(output, "png", new File("/*Insert Image path here.*/"));
        ImageIO.write(output2, "png", new File("/*Insert Image path here.*/"));
    }
}

