package barlugofx.model.test;

import org.junit.Assert;
import org.junit.Test;

import barlugofx.model.history.SequenceNode;
import barlugofx.model.history.SequenceNodeImpl;
import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.tools.BlackAndWhite;
import barlugofx.model.tools.common.ImageTool;


/**
 * A simple test class for sequence Node.
 *
 */
public final class SequenceNodeTest {
    private static final String DEFAULT_NAME = "TEST";
    private static final Image DEFAULT_IMAGE = ImageImpl.buildFromPixels(new int[2][2]);
    private static final ImageTool DEFAULT_TOOL = BlackAndWhite.createBlackAndWhite();

    /**
     * Testing inizialization.
     */
    @Test
    public void checkInizialization() {
        /*
         *TODO : If this test passed than all the equals are implemented correctly
         */
        final SequenceNode node = new SequenceNodeImpl(DEFAULT_NAME, DEFAULT_TOOL, DEFAULT_IMAGE);
        Assert.assertTrue(node.isEnabled());
        Assert.assertSame(DEFAULT_NAME, node.getNodeName());
        Assert.assertSame(DEFAULT_IMAGE, node.getStartImage());
        Assert.assertSame(DEFAULT_TOOL, node.getTool());
    }

    /**
     * Testing set and get.
     */
    @Test
    public void testSetAndGet() {
        final SequenceNode node = new SequenceNodeImpl(DEFAULT_NAME, DEFAULT_TOOL, DEFAULT_IMAGE);
        node.setNodeName("CIAO");
        Assert.assertTrue(node.getNodeName().equals("CIAO"));
        final Image newS = ImageImpl.buildFromPixels(new int[3][3]);
        node.setStartImage(newS);
        Assert.assertSame(node.getStartImage(), newS);
        try {
            node.setStartImage(null);
            Assert.fail("Node should not accept null values");
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (final Exception e) {
            Assert.fail();
        }
        node.disable();
        Assert.assertFalse(node.isEnabled());
        node.enable();
        Assert.assertTrue(node.isEnabled());
    }

    /**
     * Testing null values.
     */
    @Test
    public void testNullValues() {
        try {
            final SequenceNode node = new SequenceNodeImpl(null, DEFAULT_TOOL, DEFAULT_IMAGE);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            final SequenceNode node = new SequenceNodeImpl(DEFAULT_NAME, DEFAULT_TOOL, null);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            final SequenceNode node = new SequenceNodeImpl(DEFAULT_NAME, null, DEFAULT_IMAGE);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

}
