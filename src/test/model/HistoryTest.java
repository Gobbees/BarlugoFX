package model;

import org.junit.Assert;
import org.junit.Test;

import barlugofx.model.history.ToolLimitReachedException;
import barlugofx.model.history.History;
import barlugofx.model.history.HistoryImpl;
import barlugofx.model.history.SequenceNode;
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
        final HistoryImpl hist = new HistoryImpl();
        try {
            hist.addTool(new SequenceNodeImpl(DEFAULT_NAME, DEFAULT_TOOL, DEFAULT_IMAGE));
            Assert.assertTrue(true);
        } catch (final IllegalArgumentException e) {
            Assert.fail("I should be able to add the tool");
        } catch (final Exception e) {
            Assert.fail("I should be able to add the tool.");
        }

        try {
            hist.addTool(new SequenceNodeImpl(DEFAULT_NAME, DEFAULT_TOOL, DEFAULT_IMAGE));
            Assert.fail("I can't add two filter with the same name");
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }

        try {
            hist.addTool(new SequenceNodeImpl("CASA", DEFAULT_TOOL, DEFAULT_IMAGE));
            Assert.assertTrue(true);
        } catch (final ToolLimitReachedException e) {
            Assert.fail("I should be able to add the tool.");
        } catch (final IllegalArgumentException e) {
            Assert.fail("I should be able to add the tool.");
        }
        Assert.assertTrue(hist.findByName(DEFAULT_NAME) == 0);

        // debuggg
        System.out.println(hist.nodeNamesToString());

        hist.deleteTool(0);

        System.out.println(hist.nodeNamesToString());

        Assert.assertTrue(hist.findByName(DEFAULT_NAME) == -1);
        Assert.assertTrue(hist.findByName("CASA") == 0);
        hist.disableTool(0);
        Assert.assertFalse(hist.isToolEnabled(0));
        hist.enableTool(0);
        Assert.assertTrue(hist.isToolEnabled(0));

        hist.disableTool(0);
        hist.editTool(0, new SequenceNodeImpl("CASTA", DEFAULT_TOOL, DEFAULT_IMAGE));
        Assert.assertTrue(hist.findByName("CASA") == -1);
        Assert.assertTrue(hist.findByName("CASTA") == 0);
        Assert.assertTrue(hist.isToolEnabled(0));

        DEFAULT_TOOL.addParameter(ParametersName.WRED, new ParameterImpl<>(10));
        try {
            hist.addTool(new SequenceNodeImpl("CAVALA", DEFAULT_TOOL, DEFAULT_IMAGE));
            Assert.assertTrue(true);
        } catch (final ToolLimitReachedException e) {
            Assert.fail("I should be able to add the tool.");
        }
        Assert.assertTrue(hist.getValue(1, ParametersName.WRED).isPresent());
        Assert.assertFalse(hist.getValue(1, ParametersName.ANGLE).isPresent());
        try {
            hist.getValue(2, ParametersName.WRED);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * Basic test to test encapsulation. Ugly but functional.
     */
    @Test
    public void testEncapsulation() {
        final History hist = new HistoryImpl();
        final SequenceNode node = new SequenceNodeImpl(DEFAULT_NAME, DEFAULT_TOOL, DEFAULT_IMAGE);
        try {
            hist.addTool(node);
        } catch (final ToolLimitReachedException e) {
            Assert.fail("I should be able to add tool.");
        }
        node.setNodeName("CASSARO"); //modifiche fuori dalla sequence non devono riflettersi sulla History.
        Assert.assertTrue(hist.findByName("CASSARO") == -1);
    }
}
