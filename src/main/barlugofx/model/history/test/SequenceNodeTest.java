package barlugofx.model.history.test;

import org.junit.Assert;
import org.junit.Test;

import barlugofx.model.history.SequenceNode;
import barlugofx.model.history.SequenceNodeImpl;
import barlugofx.model.imagetools.Image;
import barlugofx.model.tools.common.ImageTool;


/**
 * A simple test class for sequence Node.
 *
 */
public final class SequenceNodeTest {
    private static final String DEFAULT_NAME = "TEST";
    private static final Image DEFAULT_IMAGE = null;
    private static final ImageTool DEFAULT_TOOL = null;

    /**
     * Testing inizialization.
     */
    @Test
    public void checkInizialization() {
        final SequenceNode node = new SequenceNodeImpl(DEFAULT_NAME, DEFAULT_TOOL, DEFAULT_IMAGE);
        Assert.assertTrue(node.isActive());
        Assert.assertSame(DEFAULT_NAME, node.getNodeName());
        Assert.assertSame(DEFAULT_IMAGE, node.getStartImage());
        Assert.assertSame(DEFAULT_TOOL, node.getTool());
    }



}
