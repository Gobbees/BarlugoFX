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
import barlugofx.model.tools.Exposure;
import barlugofx.model.tools.Hue;
import barlugofx.model.tools.Saturation;
import barlugofx.model.tools.SelectiveRGBChanger;
import barlugofx.model.tools.Vibrance;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.ParameterImpl;
import barlugofx.model.tools.common.ParameterName;
import barlugofx.utils.Timer;

/**
 * Using Junit to test all filter.
 *
 */
public final class ParallelTest {
    private final ParallelFilterExecutor exec = ParallelFilterExecutor.executor();
    private final Timer watch = new Timer();
    private static final float FLOAT_DEFAULT_VALUE = 0.9f;
    private static final double DOUBLE_DEFAULT_VALUE = 0.9f;
    private static final int INT_DEFAULT_VALUE = 120;

    /**
     * Testing brightness.
     */
    @Test
    public void testBrightness() {
        final ParallelizableImageTool brightness = Brightness.createBrightness();
        brightness.addParameter(ParameterName.BRIGHTNESS, new ParameterImpl<>(1));
        testTool(brightness, "BRIGHTNESS");
        Assert.assertTrue(true); //PMD such a nice program.
    }
    /**
     * Testing contrast.
     */
    @Test
    public void testContrast() {
        final ParallelizableImageTool contrast = Contrast.createContrast();
        contrast.addParameter(ParameterName.CONTRAST, new ParameterImpl<>(1));
        testTool(contrast, "CONTRAST");
        Assert.assertTrue(true); //PMD such a nice program.
    }

    /**
     * Testing black and white.
     */
    @Test
    public void testBlackAndWhite() {
        final ParallelizableImageTool bew = BlackAndWhite.createBlackAndWhite();
        bew.addParameter(ParameterName.WBLUE, new ParameterImpl<>(DOUBLE_DEFAULT_VALUE));
        bew.addParameter(ParameterName.WRED, new ParameterImpl<>(DOUBLE_DEFAULT_VALUE));
        testTool(bew, "Black and White");
        Assert.assertTrue(true); //PMD such a nice program.
    }

    /**
     * Testing Saturation.
     */
    @Test
    public void testSaturation() {
        final ParallelizableImageTool bew = Saturation.createSaturation();
        bew.addParameter(ParameterName.SATURATION, new ParameterImpl<>(FLOAT_DEFAULT_VALUE));
        testTool(bew, "HUE");
        Assert.assertTrue(true); //PMD such a nice program.
    }

    /**
     * Testing Exposure.
     */
    @Test
    public void testExposure() {
        final ParallelizableImageTool bew = Exposure.createExposure();
        bew.addParameter(ParameterName.EXPOSURE, new ParameterImpl<>(FLOAT_DEFAULT_VALUE));
        testTool(bew, "Saturation");
        Assert.assertTrue(true); //PMD such a nice program.
    }

    /**
     * Testing Hue.
     */
    @Test
    public void testHue() {
        final ParallelizableImageTool bew = Hue.createHue();
        bew.addParameter(ParameterName.HUE, new ParameterImpl<>(FLOAT_DEFAULT_VALUE));
        testTool(bew, "EXPOSURE");
        Assert.assertTrue(true); //PMD such a nice program.
    }

    /**
     * Testing Selective RGB.
     */
    @Test
    public void testRgb() {
        final ParallelizableImageTool bew = SelectiveRGBChanger.createSelective();
        bew.addParameter(ParameterName.RED, new ParameterImpl<>(INT_DEFAULT_VALUE));
        bew.addParameter(ParameterName.BLUE, new ParameterImpl<>(INT_DEFAULT_VALUE));
        testTool(bew, "SELECTIVE RGB");
        Assert.assertTrue(true); //PMD such a nice program.
    }

    /**
     * Testing Vibrance.
     */
    @Test
    public void testVibrance() {
        final ParallelizableImageTool bew = Vibrance.createVibrance();
        bew.addParameter(ParameterName.VIBRANCE_INCREMENT, new ParameterImpl<>(FLOAT_DEFAULT_VALUE));
        testTool(bew, "VIBRANCE");
        Assert.assertTrue(true); //PMD such a nice program.
    }

    private void testTool(final ParallelizableImageTool tool, final String text) {
        Image target = null;
        Image output1 = null;
        Image output2;
        try {
            target = buildImage();
        } catch (final Exception e) {
            Assert.fail();
        }
        watch.start();
        output1 = tool.applyTool(target);
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
        final File file = new File("/home/matteo/Desktop/Prova.jpg");
        final BufferedImage image = ImageIO.read(file);
        return ImageImpl.buildFromBufferedImage(image);
    }

    private void printExecutionTime(final long time, final String test, final boolean paralized) {
        final String header = paralized ? "PARALLEL" : "NOT PARALLEL";
        System.out.println(header + " " + test + ": " + time);
    }
}
