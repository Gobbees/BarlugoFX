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
    private static final String DEFAULT_NAME = "TEST";
    private static final Image DEFAULT_IMAGE = ImageImpl.buildFromPixels(new int[2][2]);
    private static final ImageTool DEFAULT_TOOL = BlackAndWhite.createBlackAndWhite();
    /**
     * Test history Inizialiazion.
     */
    @Test
    public void testInizialiation() {
        final Procedure procedure = new ProcedureImpl();
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
        Assert.assertTrue(procedure.canAdd(Tools.CONTRAST));
    }

    /**
     * Miscellaneous tests.
     */
    @Test
    public void testWorking() {
        final ProcedureImpl procedure = new ProcedureImpl();
        try {
            procedure.add(new AdjustmentImpl(DEFAULT_NAME, DEFAULT_TOOL, DEFAULT_IMAGE));
            Assert.assertTrue(true);
        } catch (final IllegalArgumentException e) {
            Assert.fail("I should be able to add the adjustment.");
        } catch (final Exception e) {
            Assert.fail("I should be able to add the adjustment.");
        }

        try {
            procedure.add(new AdjustmentImpl("giovanni", DEFAULT_TOOL, DEFAULT_IMAGE));
            Assert.fail("I can't add two adjustment using the same tool.");
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }

        try {
            procedure.add(new AdjustmentImpl(DEFAULT_NAME, Brightness.createBrightness(), DEFAULT_IMAGE));
            Assert.fail("I shouldn't be able to add two Adjustment with the same name.");
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(procedure.findByName(DEFAULT_NAME) == 0);
        Assert.assertTrue(procedure.getHistorySize() == 1);

        // debuggg
        System.out.println(procedure.adjustmentsNamesToString());

        procedure.remove(0);

        System.out.println(procedure.adjustmentsNamesToString());

        Assert.assertTrue(procedure.findByName(DEFAULT_NAME) == -1);

        try {
            procedure.disable(0);
            Assert.fail("Invalid index, should throw.");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            procedure.enable(0);
            Assert.fail("Invalid index, should throw");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }

        try {
            procedure.add(new AdjustmentImpl(DEFAULT_NAME, DEFAULT_TOOL, DEFAULT_IMAGE));
        } catch (IllegalArgumentException e) {
            Assert.fail("Should be able to add the adjustment.");
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("Should be able to add the adjustment.");
        }
        try {
            procedure.disable(0);
        } catch (IllegalArgumentException e) {
            Assert.fail("I should be able to disable the adjustment.");
        }
        Assert.assertFalse(procedure.isAdjustmentEnabled(0));
        try {
            procedure.enable(0);
        } catch (IllegalArgumentException e) {
            Assert.fail("I should be able to disable the adjustment.");
        }
        Assert.assertTrue(procedure.isAdjustmentEnabled(0));

        procedure.disable(0);
        procedure.edit(0, new AdjustmentImpl("CASTA", DEFAULT_TOOL, DEFAULT_IMAGE));
        Assert.assertTrue(procedure.findByName("CASA") == -1);
        Assert.assertTrue(procedure.findByName("CASTA") == 0);
        Assert.assertTrue(procedure.isAdjustmentEnabled(0));
        try {
            procedure.add(new AdjustmentImpl("CAVALA", DEFAULT_TOOL, DEFAULT_IMAGE));
            Assert.fail();
        } catch (final AdjustmentAlreadyPresentException e) {
            Assert.assertTrue(true); 
        }

        // debuggg
        System.out.println(">" + procedure);

//        Assert.assertTrue(procedure.getValue(0, ParameterName.WRED).isPresent());
//        Assert.assertFalse(procedure.getValue(0, ParameterName.ANGLE).isPresent());

        try {
            procedure.getValue(2, ParameterName.WRED);
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
        final Procedure procedure = new ProcedureImpl();
        final Adjustment adjustment = new AdjustmentImpl(DEFAULT_NAME, DEFAULT_TOOL, DEFAULT_IMAGE);
        try {
            procedure.add(adjustment);
        } catch (final AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add tool.");
        }
        adjustment.setName("CASSARO"); //modifiche fuori dalla Procedure non devono riflettersi sulla Procedure.
        Assert.assertTrue(procedure.findByName("CASSARO") == -1);
    }

    /**
     * Test to see the output of toString() and nodeNamesToString().
     */
    @Test
    public void testToString() {
        final ProcedureImpl procedure = new ProcedureImpl();
        final Adjustment adj = new AdjustmentImpl(DEFAULT_NAME, DEFAULT_TOOL, DEFAULT_IMAGE);
        final Adjustment adj1 = new AdjustmentImpl("giovanni", Brightness.createBrightness(), DEFAULT_IMAGE);
        final Adjustment adj2 = new AdjustmentImpl("barlughi", Contrast.createContrast(), DEFAULT_IMAGE);
        try {
            procedure.add(adj);
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the adjustment.");
        }
        try {
            procedure.add(adj1);
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        }
        try {
            procedure.add(adj2);
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        }
        System.out.println(procedure);
        System.out.println(procedure.adjustmentsNamesToString());
        Assert.assertTrue(true);
    }

    /**
     * Test to check if the history gets correctly updated when adding, removing or editing Adjustments.
     */
    @Test
    public void testHistoryUpdate() {
        System.out.println("# TEST -> History Update:");
        final Procedure procedure = new ProcedureImpl();
        Assert.assertTrue(procedure.getHistorySize() == 0);
        try {
            procedure.add(new AdjustmentImpl("giovanni", Brightness.createBrightness(), DEFAULT_IMAGE));
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        } catch (Exception e) {
            Assert.fail("I should be able to add the tool.");
        }

        Assert.assertTrue(procedure.getHistorySize() == 1);

        try {
            procedure.add(new AdjustmentImpl("dore", Contrast.createContrast(), DEFAULT_IMAGE));
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        } catch (Exception e) {
            Assert.fail("I should be able to add the tool.");
        }
        Assert.assertTrue(procedure.getHistorySize() == 2);

        System.out.println(procedure);
    }

    /**
     * 
     */
    @Test
    public void testProcedureAddUndoRedo() {
        System.out.println("# TEST -> Procedure ADD Undo Redo");
        final Procedure procedure = new ProcedureImpl();
        try {
            procedure.add(new AdjustmentImpl("giovanni", Brightness.createBrightness(), DEFAULT_IMAGE));
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        } catch (java.lang.Exception e) {
            Assert.fail("I should be able to add the tool.");
        }

        try {
            procedure.add(new AdjustmentImpl("dore", Contrast.createContrast(), DEFAULT_IMAGE));
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        } catch (Exception e) {
            Assert.fail("I should be able to add the tool.");
        }

        try {
            procedure.add(new AdjustmentImpl("immenso", Vibrance.createVibrance(), DEFAULT_IMAGE));
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        } catch (Exception e) {
            Assert.fail("I should be able to add the tool.");
        }

        try {
            procedure.add(new AdjustmentImpl("nel mio", Saturation.createSaturation(), DEFAULT_IMAGE));
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        } catch (Exception e) {
            Assert.fail("I should be able to add the tool.");
        }

        try {
            procedure.add(new AdjustmentImpl("cuore", Hue.createHue(), DEFAULT_IMAGE));
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
        try {
            procedure.undo();
        } catch (NoMoreActionsException e) {
            Assert.fail("I should be able to undo the action.");
        } catch (java.lang.Exception e) {
            System.out.println(e);
            Assert.fail("I should be able to undo the action.");
        }
        Assert.assertTrue(procedure.getHistorySize() == 5);
        Assert.assertTrue(procedure.canAdd(Tools.HUE));

        Assert.assertFalse(procedure.canAdd(Tools.SATURATION));
        try {
            procedure.undo();
        } catch (NoMoreActionsException e) {
            Assert.fail("I should be able to undo the action.");
        } catch (java.lang.Exception e) {
            Assert.fail("I should be able to undo the action.");
        }
        Assert.assertTrue(procedure.getHistorySize() == 5);
        Assert.assertTrue(procedure.canAdd(Tools.SATURATION));

        try {
            for (int i = 0; i < 10; i++) {
                procedure.undo();
            }
            Assert.fail("Shouldn't be able to undo this much!");
        } catch (NoMoreActionsException e) {
            Assert.assertTrue(true);
        } catch (java.lang.Exception e) {
            System.out.println(e);
            Assert.fail("Unexpected exception, something is wrong.");
        }

        Assert.assertTrue(procedure.canAdd(Tools.HUE));
        Assert.assertTrue(procedure.canAdd(Tools.SATURATION));
        Assert.assertTrue(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertTrue(procedure.canAdd(Tools.CONTRAST));
        Assert.assertTrue(procedure.canAdd(Tools.BRIGHTNESS));
        Assert.assertTrue(procedure.getHistorySize() == 5);

        try {
            procedure.redo();
            procedure.redo();
        } catch (NoMoreActionsException e) {
            Assert.fail("I should be able to redo the actions.");
        } catch (java.lang.Exception e) {
            Assert.fail("I should be able to redo the actions.");
        }
        Assert.assertTrue(procedure.getHistorySize() == 5);

        Assert.assertTrue(procedure.canAdd(Tools.HUE));
        Assert.assertTrue(procedure.canAdd(Tools.SATURATION));
        Assert.assertTrue(procedure.canAdd(Tools.VIBRANCE));
        Assert.assertFalse(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.BRIGHTNESS));
        Assert.assertTrue(procedure.getHistorySize() == 5);

        try {
            for (int i = 0; i < 10; i++) {
                procedure.redo();
            }
        } catch (NoMoreActionsException e) {
            Assert.assertTrue(true);
        } catch (java.lang.Exception e) {
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
        final Procedure procedure = new ProcedureImpl();
        try {
            procedure.add(new AdjustmentImpl("giovanni", Brightness.createBrightness(), DEFAULT_IMAGE));
            procedure.add(new AdjustmentImpl("dore", Contrast.createContrast(), DEFAULT_IMAGE));
            procedure.add(new AdjustmentImpl("immenso", Vibrance.createVibrance(), DEFAULT_IMAGE));
            procedure.add(new AdjustmentImpl("nel mio", Saturation.createSaturation(), DEFAULT_IMAGE));
            procedure.add(new AdjustmentImpl("cuore", Hue.createHue(), DEFAULT_IMAGE));
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        } catch (java.lang.Exception e) {
            System.out.println(e);
            Assert.fail("I should be able to add the tool.");
        }

        Assert.assertFalse(procedure.canAdd(Tools.SATURATION));
        Assert.assertFalse(procedure.canAdd(Tools.CONTRAST));

        try {
            procedure.remove(Tools.CONTRAST);
            procedure.remove(Tools.SATURATION);
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail("I should be able to remove those adjustments");
        }

        Assert.assertTrue(procedure.canAdd(Tools.CONTRAST));
        Assert.assertTrue(procedure.canAdd(Tools.SATURATION));

        try {
            procedure.undo();
        } catch (NoMoreActionsException e) {
            Assert.fail("I should be able to undo the action.");
        } catch (java.lang.Exception e) {
            System.out.println(e);
            Assert.fail("I should be able to undo the action.");
        }
        Assert.assertTrue(procedure.getHistorySize() == 7);
        Assert.assertTrue(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.SATURATION));

        try {
            procedure.undo();
        } catch (NoMoreActionsException e) {
            Assert.fail("I should be able to undo the action.");
        } catch (java.lang.Exception e) {
            Assert.fail("I should be able to undo the action.");
        }
        Assert.assertTrue(procedure.getHistorySize() == 7);
        Assert.assertFalse(procedure.canAdd(Tools.CONTRAST));
        Assert.assertFalse(procedure.canAdd(Tools.SATURATION));

        try {
            for (int i = 0; i < 10; i++) {
                procedure.undo();
            }
            Assert.fail("Shouldn't be able to undo this much!");
        } catch (NoMoreActionsException e) {
            Assert.assertTrue(true);
        } catch (java.lang.Exception e) {
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
            procedure.redo();
            procedure.redo();
        } catch (NoMoreActionsException e) {
            Assert.fail("I should be able to redo the actions.");
        } catch (java.lang.Exception e) {
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
            for (int i = 0; i < 10; i++) {
                procedure.redo();
            }
        } catch (NoMoreActionsException e) {
            Assert.assertTrue(true);
        } catch (java.lang.Exception e) {
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
}
