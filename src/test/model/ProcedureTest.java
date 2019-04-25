package model;

import org.junit.Assert;
import org.junit.Test;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.procedure.Adjustment;
import barlugofx.model.procedure.AdjustmentAlreadyPresentException;
import barlugofx.model.procedure.AdjustmentImpl;
import barlugofx.model.procedure.NoMoreActionsException;
import barlugofx.model.procedure.Procedure;
import barlugofx.model.procedure.ProcedureImpl;
import barlugofx.model.tools.BlackAndWhite;
import barlugofx.model.tools.Brightness;
import barlugofx.model.tools.Contrast;
import barlugofx.model.tools.Hue;
import barlugofx.model.tools.Saturation;
import barlugofx.model.tools.Tools;
import barlugofx.model.tools.Vibrance;
import barlugofx.model.tools.common.ImageTool;
import barlugofx.model.tools.common.ParameterName;

/**
 * A simple class that test Procedure.
 *
 */
public final class ProcedureTest {
    private static final Image DEFAULT_IMAGE = ImageImpl.buildFromPixels(new int[2][2]);
    private static final ImageTool DEFAULT_TOOL = BlackAndWhite.createBlackAndWhite();
    /**
     * Test history Inizialiazion.
     */
    @Test
    public void testInizialiation() {
        final Procedure procedure = new ProcedureImpl(DEFAULT_IMAGE, true);
        try {
            procedure.remove(0);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            procedure.disable(0);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            procedure.enable(0);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            procedure.edit(0, null);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            procedure.undo();
            Assert.fail();
        } catch (final NoMoreActionsException e) {
            Assert.assertTrue(true);
        }
        try {
            procedure.redo();
            Assert.fail();
        } catch (final NoMoreActionsException e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(procedure.canAdd(Tools.CONTRAST));
    }

    /**
     * Miscellaneous tests.
     */
    @Test
    public void testWorking() {
        final ProcedureImpl procedure = new ProcedureImpl(DEFAULT_IMAGE, true);

        // add default adjustment
        try {
            procedure.add(new AdjustmentImpl(DEFAULT_TOOL));
            Assert.assertTrue(true);
        } catch (final IllegalArgumentException e) {
            Assert.fail("I should be able to add the adjustment.");
        } catch (final Exception e) {
            Assert.fail("I should be able to add the adjustment.");
        }

        // try to re-add the same type of adjustment
        // must fail
        try {
            procedure.add(new AdjustmentImpl(DEFAULT_TOOL));
            Assert.fail("I can't add two adjustment using the same tool.");
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }
 
        Assert.assertTrue(procedure.getHistorySize() == 1);

        // add Brightness adjustment
        try {
            procedure.add(new AdjustmentImpl(Brightness.createBrightness()));
        } catch (final IllegalArgumentException e) {
            Assert.fail();
        } catch (final Exception e) {
            Assert.fail();
        }

        Assert.assertTrue(procedure.getHistorySize() == 2);

        procedure.remove(DEFAULT_TOOL.getToolType());

        Assert.assertTrue(procedure.getHistorySize() == 3);

        // disabling the remaining adjustment
        try {
            procedure.disable(0);
        } catch (IllegalArgumentException e) {
            Assert.fail("Should be able to disable.");
        }

        // check if it's actually disabled
        Assert.assertFalse(procedure.isAdjustmentEnabled(0));

        // enabling the remaining adjustment
        try {
            procedure.enable(0);
        } catch (IllegalArgumentException e) {
            Assert.fail("Should be able to enable.");
        }

        // adding back another adjustment with default tool
        try {
            procedure.add(new AdjustmentImpl(DEFAULT_TOOL));
        } catch (IllegalArgumentException e) {
            Assert.fail("Should be able to add the adjustment.");
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("Should be able to add the adjustment.");
        }

        Assert.assertTrue(procedure.getHistorySize() == 4);

        // disable the default adjustment
        try {
            procedure.disable(1);
        } catch (IllegalArgumentException e) {
            Assert.fail("I should be able to disable the adjustment.");
        }

        Assert.assertFalse(procedure.isAdjustmentEnabled(1));

        // enable it
        try {
            procedure.enable(1);
        } catch (IllegalArgumentException e) {
            Assert.fail("I should be able to disable the adjustment.");
        }

        // check again the enabled status
        Assert.assertTrue(procedure.isAdjustmentEnabled(1));
        Assert.assertTrue(procedure.isAdjustmentEnabled(DEFAULT_TOOL.getToolType()));

        procedure.disable(0);

        // edit, should replace and set enabled
        procedure.edit(1, new AdjustmentImpl(DEFAULT_TOOL));

        Assert.assertTrue(procedure.isAdjustmentEnabled(1));
        Assert.assertTrue(procedure.getHistorySize() == 5);

        // try to add after edit, must fail
        try {
            procedure.add(new AdjustmentImpl(DEFAULT_TOOL));
            Assert.fail();
        } catch (final AdjustmentAlreadyPresentException e) {
            Assert.assertTrue(true); 
        }

        Assert.assertTrue(procedure.getHistorySize() == 5);

        // pull out a value, must fail
        try {
            procedure.getValue(2, ParameterName.WRED);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * Test to see the output of procedure toString().
     */
    @Test
    public void testToString() {
        System.out.println("# TEST -> toString");
        final ProcedureImpl procedure = new ProcedureImpl(DEFAULT_IMAGE, true);
        final Adjustment adj = new AdjustmentImpl(DEFAULT_TOOL);
        final Adjustment adj1 = new AdjustmentImpl(Brightness.createBrightness());
        final Adjustment adj2 = new AdjustmentImpl(Contrast.createContrast());
        try {
            procedure.add(adj);
            procedure.add(adj1);
            procedure.add(adj2);
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the adjustment.");
        }
        try {
            procedure.edit(Tools.BRIGHTNESS, new AdjustmentImpl(Brightness.createBrightness()));
        } catch (IllegalArgumentException e) {
            Assert.fail("I should be able to edit the adjustment.");
        }
        try {
            procedure.add(new AdjustmentImpl(Hue.createHue()));
            procedure.remove(Tools.HUE);
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the adjustment");
        } catch (IllegalArgumentException e) {
            Assert.fail("I should be able to remove the adjustment.");
        }
        System.out.println(procedure);
        System.out.println("<<");
    }

    /**
     * Test to check if the history gets correctly updated when adding, removing or editing Adjustments.
     */
    @Test
    public void testHistoryUpdate() {
        System.out.println("# TEST -> History Update:");
        final Procedure procedure = new ProcedureImpl(DEFAULT_IMAGE, true);
        Assert.assertTrue(procedure.getHistorySize() == 0);
        try {
            procedure.add(new AdjustmentImpl(Brightness.createBrightness()));
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        } catch (Exception e) {
            Assert.fail("I should be able to add the tool.");
        }

        Assert.assertTrue(procedure.getHistorySize() == 1);

        try {
            procedure.add(new AdjustmentImpl(Contrast.createContrast()));
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        } catch (Exception e) {
            Assert.fail("I should be able to add the tool.");
        }
        Assert.assertTrue(procedure.getHistorySize() == 2);

        try {
            procedure.edit(Tools.BRIGHTNESS, new AdjustmentImpl(Brightness.createBrightness()));
        } catch (IllegalArgumentException e) {
            Assert.fail("I should be able to edit the tool.");
        } catch (Exception e) {
            Assert.fail("I should be able to edit the tool.");
        }
        Assert.assertTrue(procedure.getHistorySize() == 3);

        try {
            procedure.remove(Tools.BRIGHTNESS);
            procedure.remove(Tools.CONTRAST);
        } catch (IllegalArgumentException e) {
            Assert.fail("I should be able to remove the tool.");
        }
        Assert.assertTrue(procedure.getHistorySize() == 5);

        System.out.println(procedure);
        System.out.println("<<");
    }

    /**
     * 
     */
    @Test
    public void testProcedureAddUndoRedo() {
        System.out.println("# TEST -> Procedure ADD Undo Redo");
        final Procedure procedure = new ProcedureImpl(DEFAULT_IMAGE, true);
        try {
            procedure.add(new AdjustmentImpl(Brightness.createBrightness()));
            procedure.add(new AdjustmentImpl(Contrast.createContrast()));
            procedure.add(new AdjustmentImpl(Vibrance.createVibrance()));
            procedure.add(new AdjustmentImpl(Saturation.createSaturation()));
            procedure.add(new AdjustmentImpl(Hue.createHue()));
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        } catch (Exception e) {
            Assert.fail("I should be able to add the tool.");
        }

        System.out.println(procedure);
        Assert.assertFalse(procedure.canAdd(Tools.HUE));
        Assert.assertFalse(procedure.canAdd(Tools.SATURATION));
        Assert.assertFalse(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));
        Assert.assertTrue(procedure.getHistorySize() == 5);

        // undo ADD HUE
        try {
            procedure.undo();
        } catch (NoMoreActionsException e) {
            Assert.fail("I should be able to undo the action.");
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail("I should be able to undo the action.");
        }

        Assert.assertTrue(procedure.getHistorySize() == 5);
        Assert.assertTrue(procedure.canAdd(Tools.HUE));
        Assert.assertFalse(procedure.canAdd(Tools.SATURATION));
        Assert.assertFalse(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));

        // undo ADD SATURATION
        try {
            procedure.undo();
        } catch (NoMoreActionsException e) {
            Assert.fail("I should be able to undo the action.");
        } catch (Exception e) {
            Assert.fail("I should be able to undo the action.");
        }

        Assert.assertTrue(procedure.getHistorySize() == 5);
        Assert.assertTrue(procedure.canAdd(Tools.SATURATION));
        Assert.assertTrue(procedure.canAdd(Tools.HUE));
        Assert.assertFalse(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));

        try {
            for (int i = 0; i < 100; i++) {
                procedure.undo();
            }
            Assert.fail("Shouldn't be able to undo this much!");
        } catch (NoMoreActionsException e) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail("Unexpected exception, something is wrong.");
        }

        Assert.assertTrue(procedure.canAdd(Tools.HUE));
        Assert.assertTrue(procedure.canAdd(Tools.SATURATION));
        Assert.assertTrue(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertTrue(procedure.canAdd(Tools.CONTRAST));
        Assert.assertTrue(procedure.canAdd(Tools.BRIGHTNESS));
        Assert.assertTrue(procedure.getHistorySize() == 5);

        // the procedure is empty, redo:
        // ADD BRIGHTNESS
        // ADD CONTRAST
        try {
            procedure.redo();
            procedure.redo();
        } catch (NoMoreActionsException e) {
            Assert.fail("I should be able to redo the actions.");
        } catch (Exception e) {
            Assert.fail("I should be able to redo the actions.");
        }
        Assert.assertTrue(procedure.getHistorySize() == 5);

        Assert.assertTrue(procedure.canAdd(Tools.HUE));
        Assert.assertTrue(procedure.canAdd(Tools.SATURATION));
        Assert.assertTrue(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));

        try {
            for (int i = 0; i < 100; i++) {
                procedure.redo();
            }
        } catch (NoMoreActionsException e) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail("Unexpected exception, something is wrong.");
        }

        Assert.assertFalse(procedure.canAdd(Tools.HUE));
        Assert.assertFalse(procedure.canAdd(Tools.SATURATION));
        Assert.assertFalse(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));
        Assert.assertTrue(procedure.getHistorySize() == 5);

        System.out.println("<<");
    }

    /**
     * 
     */
    @Test
    public void testProcedureRemoveUndoRedo() {
        System.out.println("# TEST -> Procedure REMOVE Undo Redo");
        final Procedure procedure = new ProcedureImpl(DEFAULT_IMAGE, true);
        try {
            procedure.add(new AdjustmentImpl(Brightness.createBrightness()));
            procedure.add(new AdjustmentImpl(Contrast.createContrast()));
            procedure.add(new AdjustmentImpl(Vibrance.createVibrance()));
            procedure.add(new AdjustmentImpl(Saturation.createSaturation()));
            procedure.add(new AdjustmentImpl(Hue.createHue()));
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        } catch (Exception e) {
            Assert.fail("I should be able to add the tool.");
        }

        try {
            procedure.remove(Tools.CONTRAST);
            procedure.remove(Tools.SATURATION);
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail("I should be able to remove those adjustments");
        }

        Assert.assertTrue(procedure.canAdd(Tools.CONTRAST));
        Assert.assertTrue(procedure.canAdd(Tools.SATURATION));
        Assert.assertFalse(procedure.canAdd(Tools.HUE));
        Assert.assertFalse(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));

        // undo REMOVE SATURATION
        try {
            procedure.undo();
        } catch (NoMoreActionsException e) {
            Assert.fail("I should be able to undo the action.");
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail("I should be able to undo the action.");
        }

        Assert.assertTrue(procedure.getHistorySize() == 7);
        Assert.assertTrue(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.SATURATION));
        Assert.assertFalse(procedure.canAdd(Tools.HUE));
        Assert.assertFalse(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));

        // undo REMOVE CONTRAST
        try {
            procedure.undo();
        } catch (NoMoreActionsException e) {
            Assert.fail("I should be able to undo the action.");
        } catch (Exception e) {
            Assert.fail("I should be able to undo the action.");
        }
        Assert.assertTrue(procedure.getHistorySize() == 7);
        Assert.assertFalse(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.SATURATION));
        Assert.assertFalse(procedure.canAdd(Tools.HUE));
        Assert.assertFalse(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));

        try {
            for (int i = 0; i < 100; i++) {
                procedure.undo();
            }
            Assert.fail("Shouldn't be able to undo this much!");
        } catch (NoMoreActionsException e) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail("Unexpected exception, something is wrong.");
        }

        Assert.assertTrue(procedure.canAdd(Tools.HUE));
        Assert.assertTrue(procedure.canAdd(Tools.SATURATION));
        Assert.assertTrue(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertTrue(procedure.canAdd(Tools.CONTRAST));
        Assert.assertTrue(procedure.canAdd(Tools.BRIGHTNESS));
        Assert.assertTrue(procedure.getHistorySize() == 7);

        // redo ADD BRIGHTNESS
        // redo ADD CONTRAST
        try {
            procedure.redo();
            procedure.redo();
        } catch (NoMoreActionsException e) {
            Assert.fail("I should be able to redo the actions.");
        } catch (Exception e) {
            Assert.fail("I should be able to redo the actions.");
        }
        Assert.assertTrue(procedure.getHistorySize() == 7);

        Assert.assertTrue(procedure.canAdd(Tools.HUE));
        Assert.assertTrue(procedure.canAdd(Tools.SATURATION));
        Assert.assertTrue(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));
        Assert.assertTrue(procedure.getHistorySize() == 7);

        try {
            for (int i = 0; i < 100; i++) {
                procedure.redo();
            }
        } catch (NoMoreActionsException e) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail("Unexpected exception, something is wrong.");
        }

        Assert.assertTrue(procedure.canAdd(Tools.CONTRAST));
        Assert.assertTrue(procedure.canAdd(Tools.SATURATION));
        Assert.assertFalse(procedure.canAdd(Tools.HUE));
        Assert.assertFalse(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));
        Assert.assertTrue(procedure.getHistorySize() == 7);

        System.out.println("<<");
    }

    /**
     * 
     */
    @Test
    public void testProcedureEditUndoRedo() {
        System.out.println("# TEST -> Procedure REMOVE Undo Redo");
        final ProcedureImpl procedure = new ProcedureImpl(DEFAULT_IMAGE, true);
        try {
            procedure.add(new AdjustmentImpl(Brightness.createBrightness()));
            procedure.add(new AdjustmentImpl(Contrast.createContrast()));
            procedure.add(new AdjustmentImpl(Vibrance.createVibrance()));
            procedure.add(new AdjustmentImpl(Saturation.createSaturation()));
            procedure.add(new AdjustmentImpl(Hue.createHue()));
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail("I should be able to add the tool.");
        }

        try {
            procedure.edit(Tools.CONTRAST, new AdjustmentImpl(Contrast.createContrast()));
            procedure.edit(Tools.SATURATION, new AdjustmentImpl(Saturation.createSaturation()));
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail("I should be able to remove those adjustments");
        }

        Assert.assertFalse(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.SATURATION));
        Assert.assertFalse(procedure.canAdd(Tools.HUE));
        Assert.assertFalse(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));

        // undo EDIT SATURATION
        try {
            procedure.undo();
        } catch (NoMoreActionsException e) {
            Assert.fail("I should be able to undo the action.");
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail("I should be able to undo the action.");
        }
        Assert.assertTrue(procedure.getHistorySize() == 7);
        Assert.assertFalse(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.SATURATION));
        Assert.assertFalse(procedure.canAdd(Tools.HUE));
        Assert.assertFalse(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));

        // undo EDIT CONTRAST
        try {
            procedure.undo();
        } catch (NoMoreActionsException e) {
            Assert.fail("I should be able to undo the action.");
        } catch (Exception e) {
            Assert.fail("I should be able to undo the action.");
        }
        Assert.assertTrue(procedure.getHistorySize() == 7);
        Assert.assertFalse(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.SATURATION));
        Assert.assertFalse(procedure.canAdd(Tools.HUE));
        Assert.assertFalse(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));

        try {
            for (int i = 0; i < 100; i++) {
                procedure.undo();
            }
            Assert.fail("Shouldn't be able to undo this much!");
        } catch (NoMoreActionsException e) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail("Unexpected exception, something is wrong.");
        }

        Assert.assertTrue(procedure.canAdd(Tools.HUE));
        Assert.assertTrue(procedure.canAdd(Tools.SATURATION));
        Assert.assertTrue(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertTrue(procedure.canAdd(Tools.CONTRAST));
        Assert.assertTrue(procedure.canAdd(Tools.BRIGHTNESS));
        Assert.assertTrue(procedure.getHistorySize() == 7);

        try {
            for (int i = 0; i < 100; i++) {
                procedure.redo();
            }
        } catch (NoMoreActionsException e) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail("Unexpected exception, something is wrong.");
        }

        Assert.assertFalse(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.SATURATION));
        Assert.assertFalse(procedure.canAdd(Tools.HUE));
        Assert.assertFalse(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));
        Assert.assertTrue(procedure.getHistorySize() == 7);

        System.out.println("<<");
    }
}
