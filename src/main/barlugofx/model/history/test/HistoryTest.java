package barlugofx.model.history.test;

import org.junit.Assert;
import org.junit.Test;

import barlugofx.model.history.History;
import barlugofx.model.history.HistoryImpl;
import barlugofx.model.history.SequenceNodeImpl;
import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.tools.BlackAndWhite;
import barlugofx.model.tools.common.ImageTool;
import barlugofx.model.tools.common.ParameterImpl;
import barlugofx.model.tools.common.ParametersName;

/**
 * A simple class that test History.
 *
 */
public final class HistoryTest {
    private static final String DEFAULT_NAME = "TEST";
    private static final Image DEFAULT_IMAGE = ImageImpl.buildFromPixels(new int[2][2]);
    private static final ImageTool DEFAULT_TOOL = BlackAndWhite.createBlackAndWhite();
    /**
     * Test history Inizialiazion.
     */
    @Test
    public void testInizialiation() {
        final History hist = new HistoryImpl();
        try {
            hist.deleteTool(0);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            hist.disableTool(0);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            hist.enableTool(0);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            hist.editTool(0, null);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(hist.canAdd());

    }

    /**
     * Miscellaneous tests.
     */
    @Test
    public void testWorking() {
        final History hist = new HistoryImpl();
        hist.addTool(new SequenceNodeImpl(DEFAULT_NAME, DEFAULT_TOOL, DEFAULT_IMAGE));
        try {
            hist.addTool(new SequenceNodeImpl(DEFAULT_NAME, DEFAULT_TOOL, DEFAULT_IMAGE));
            Assert.fail("I can't add two filter with the same name");
        }
        catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        hist.addTool(new SequenceNodeImpl("CASA", DEFAULT_TOOL, DEFAULT_IMAGE));
        Assert.assertTrue(hist.findByName(DEFAULT_NAME) == 0);
        hist.deleteTool(0);
        Assert.assertTrue(hist.findByName(DEFAULT_NAME) == -1);
        Assert.assertTrue(hist.findByName("CASA") == 0);
        hist.disableTool(0);
        Assert.assertFalse(hist.isToolEnalbed(0));
        hist.enableTool(0);
        Assert.assertTrue(hist.isToolEnalbed(0));

        hist.disableTool(0);
        hist.editTool(0, new SequenceNodeImpl("CASTA", DEFAULT_TOOL, DEFAULT_IMAGE));
        Assert.assertTrue(hist.findByName("CASA") == -1);
        Assert.assertTrue(hist.findByName("CASTA") == 0);
        Assert.assertTrue(hist.isToolEnalbed(0));

        DEFAULT_TOOL.addParameter(ParametersName.WRED, new ParameterImpl<>(10));
        hist.addTool(new SequenceNodeImpl("CAVALA", DEFAULT_TOOL, DEFAULT_IMAGE));
        Assert.assertTrue(hist.getValue(1, ParametersName.WRED).isPresent());
        Assert.assertFalse(hist.getValue(1, ParametersName.ANGLE).isPresent());
        try {
            hist.getValue(2, ParametersName.WRED);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }
}
