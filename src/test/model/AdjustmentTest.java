package model;

import org.junit.Assert;
import org.junit.Test;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.procedure.Adjustment;
import barlugofx.model.procedure.AdjustmentImpl;
import barlugofx.model.tools.BlackAndWhite;
import barlugofx.model.tools.common.ImageTool;


/**
 * A simple test class for sequence Node.
 *
 */
public final class AdjustmentTest {
    private static final Image DEFAULT_IMAGE = ImageImpl.buildFromPixels(new int[2][2]);
    private static final ImageTool DEFAULT_TOOL = BlackAndWhite.createBlackAndWhite();

    /**
     * Testing inizialization.
     */
    @Test
    public void checkInizialization() {
        final Adjustment adjustment = new AdjustmentImpl(DEFAULT_TOOL);
        Assert.assertTrue(adjustment.isEnabled());
        Assert.assertSame(DEFAULT_TOOL, adjustment.getTool());
    }

    /**
     * Testing set and get.
     */
    @Test
    public void testSetAndGet() {
        final Adjustment adjustment = new AdjustmentImpl(DEFAULT_TOOL);
        try {
            adjustment.setStartImage(null);
            Assert.fail("Node should not accept null values");
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (final Exception e) {
            Assert.fail();
        }
        adjustment.disable();
        Assert.assertFalse(adjustment.isEnabled());
        adjustment.enable();
        Assert.assertTrue(adjustment.isEnabled());

        Assert.assertFalse(adjustment.isStartImagePresent());
        Assert.assertFalse(adjustment.isEndImagePresent());

        adjustment.setEndImage(DEFAULT_IMAGE);
        Assert.assertSame(DEFAULT_IMAGE, adjustment.getEndImage());
        Assert.assertTrue(adjustment.isEndImagePresent());
        Assert.assertFalse(adjustment.isStartImagePresent());

        adjustment.setStartImage(DEFAULT_IMAGE);
        Assert.assertSame(DEFAULT_IMAGE, adjustment.getStartImage());
        Assert.assertNull(adjustment.getEndImage());
        Assert.assertFalse(adjustment.isEndImagePresent());
        Assert.assertTrue(adjustment.isStartImagePresent());

        adjustment.setEndImage(DEFAULT_IMAGE);
        adjustment.removeEndImage();
        Assert.assertFalse(adjustment.isEndImagePresent());

        adjustment.removeStartImage();
        Assert.assertFalse(adjustment.isStartImagePresent());

        Assert.assertSame(DEFAULT_TOOL.getToolType(), adjustment.getToolType());
    }

    /**
     * Testing null values.
     */
    @Test
    public void testNullValues() {
        try {
            new AdjustmentImpl(null);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        final Adjustment adjustment = new AdjustmentImpl(DEFAULT_TOOL);
        try {
            adjustment.setStartImage(null);
            Assert.fail("Shouldn't be able to set null image.");
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            adjustment.setEndImage(null);
            Assert.fail("Shouldn't be able to set null image.");
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

}
