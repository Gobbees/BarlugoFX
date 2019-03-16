package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Test;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.parallelhandler.ParallelFilterExecutor;
import barlugofx.model.tools.BlackAndWhite;
import barlugofx.model.tools.Brightness;
import barlugofx.model.tools.Contrast;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParameterImpl;
import barlugofx.model.tools.common.ParametersName;
import barlugofx.utils.Timer;

/**
 * Using Junit to test all filter.
 *
 */
public class ParallelTest {
    private final ParallelFilterExecutor exec = ParallelFilterExecutor.executor();
    private final Timer watch = new Timer();

    /**
     * Testing brightness.
     */
    @Test
    public void testBrightness() {
        final ParallelizableImageTool brightness = Brightness.createBrightness();
        brightness.addParameter(ParametersName.BRIGHTNESS, new ParameterImpl<>(1));
        testTool(brightness, "BRIGHTNESS");
    }
    /**
     * Testing contrast.
     */
    @Test
    public void testContrast() {
        final ParallelizableImageTool contrast = Contrast.createContrast();
        contrast.addParameter(ParametersName.CONTRAST, new ParameterImpl<>(1));
        testTool(contrast, "CONTRAST");
    }

    /**
     * Testing black and white.
     */
    @Test
    public void testBlackAndWait() {
        final ParallelizableImageTool bew = BlackAndWhite.createBlackAndWhite();
        bew.addParameter(ParametersName.WBLUE, new ParameterImpl<>(0.9));
        bew.addParameter(ParametersName.WRED, new ParameterImpl<>(1.2));
        testTool(bew, "Black and White");
    }

    private void testTool(final ParallelizableImageTool tool, final String text) {
        Image target = null, output1, output2;
        try {
            target = buildImage();
        } catch (final Exception e) {
            Assert.fail();
        }
        watch.start();
        output1 = tool.applyFilter(target);
        printExecutionTime(watch.stop(), text, false);

        watch.start();
        output2 = exec.applyTool(tool, target);
        printExecutionTime(watch.stop(), text, true);

        Assert.assertTrue(equalMatrix(output1.getImageRGBvalues(), output2.getImageRGBvalues()));
    }

    private boolean equalMatrix(final int[][] first, final int[]... second) {
        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < first[0].length; j++) {
                if (first[i][j] != second[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private Image buildImage() throws IOException {
        final File file = new File("/Users/gg_mbpro/Downloads/Prova.jpg");
        final BufferedImage image = ImageIO.read(file);
        return ImageImpl.buildFromBufferedImage(image);
    }

    private void printExecutionTime(final long time, final String test, final boolean paralized) {
        final String header = paralized ? "PARALLEL" : "NOT PARALLEL";
        System.out.println(header + " " + test + ": " + time);
    }
}
