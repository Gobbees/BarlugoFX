package model;

import org.junit.Assert;
import org.junit.Test;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.procedure.Adjustment;
import barlugofx.model.procedure.AdjustmentAlreadyPresentException;
import barlugofx.model.procedure.AdjustmentImpl;
import barlugofx.model.procedure.Procedure;
import barlugofx.model.procedure.ProcedureImpl;
import barlugofx.model.tools.BlackAndWhite;
import barlugofx.model.tools.Brightness;
import barlugofx.model.tools.Contrast;
import barlugofx.model.tools.Tools;
import barlugofx.model.tools.common.ImageTool;
import barlugofx.model.tools.common.ParameterImpl;
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
            procedure.removeAdjustment(0);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            procedure.disableAdjustment(0);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            procedure.enableAdjustment(0);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            procedure.editAdjustment(0, null);
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
            procedure.addAdjustment(new AdjustmentImpl(DEFAULT_NAME, DEFAULT_TOOL, DEFAULT_IMAGE));
            Assert.assertTrue(true);
        } catch (final IllegalArgumentException e) {
            Assert.fail("I should be able to add the adjustment.");
        } catch (final Exception e) {
            Assert.fail("I should be able to add the adjustment.");
        }

        try {
            procedure.addAdjustment(new AdjustmentImpl("giovanni", DEFAULT_TOOL, DEFAULT_IMAGE));
            Assert.fail("I can't add two adjustment using the same tool.");
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }

        try {
            procedure.addAdjustment(new AdjustmentImpl(DEFAULT_NAME, Brightness.createBrightness(), DEFAULT_IMAGE));
            Assert.fail("I shouldn't be able to add two Adjustment with the same name.");
        } catch (final IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }
        Assert.assertTrue(procedure.findByName(DEFAULT_NAME) == 0);

        // debuggg
        System.out.println(procedure.adjustmentsNamesToString());

        procedure.removeAdjustment(0);

        System.out.println(procedure.adjustmentsNamesToString());

        Assert.assertTrue(procedure.findByName(DEFAULT_NAME) == -1);
        Assert.assertTrue(procedure.findByName("CASA") == 0);
        procedure.disableAdjustment(0);
        Assert.assertFalse(procedure.isAdjustmentEnabled(0));
        procedure.enableAdjustment(0);
        Assert.assertTrue(procedure.isAdjustmentEnabled(0));

        procedure.disableAdjustment(0);
        procedure.editAdjustment(0, new AdjustmentImpl("CASTA", DEFAULT_TOOL, DEFAULT_IMAGE));
        Assert.assertTrue(procedure.findByName("CASA") == -1);
        Assert.assertTrue(procedure.findByName("CASTA") == 0);
        Assert.assertTrue(procedure.isAdjustmentEnabled(0));

        DEFAULT_TOOL.addParameter(ParameterName.WRED, new ParameterImpl<>(10));
        try {
            procedure.addAdjustment(new AdjustmentImpl("CAVALA", DEFAULT_TOOL, DEFAULT_IMAGE));
            Assert.fail();
        } catch (final AdjustmentAlreadyPresentException e) {
            Assert.assertTrue(true); 
        }

        Assert.assertTrue(procedure.getValue(1, ParameterName.WRED).isPresent());
        Assert.assertFalse(procedure.getValue(1, ParameterName.ANGLE).isPresent());

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
            procedure.addAdjustment(adjustment);
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
            procedure.addAdjustment(adj);
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the adjustment.");
        }
        try {
            procedure.addAdjustment(adj1);
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        }
        try {
            procedure.addAdjustment(adj2);
        } catch (AdjustmentAlreadyPresentException e) {
            Assert.fail("I should be able to add the tool.");
        }
        System.out.println(procedure.toString());
        System.out.println(procedure.adjustmentsNamesToString());
        Assert.assertTrue(true);
    }
}
