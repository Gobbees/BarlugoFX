/**
 * 
 */
package barlugofx.model.procedure;

import barlugofx.model.tools.common.Parameter;
import barlugofx.model.tools.common.ParameterName;
import barlugofx.model.tools.Tools;

import java.util.Optional;
import java.util.HashMap;

/**
 *
 *
 */
public class ProcedureImpl implements Procedure {
    private final int totalToolCount = Tools.values().length;
    private Adjustment[] adjustments = new Adjustment[totalToolCount];
    private int nextIndex;
    private HashMap<Tools, Integer> toolMap = new HashMap<Tools, Integer>();
    private HashMap<String, Integer> nameMap = new HashMap<String, Integer>();

    /**
     * 
     */
    public ProcedureImpl() {
        this.nextIndex = 0;
    }

    /**
     * @see barlugofx.model.procedure.Procedure#addAdjustment(barlugofx.model.procedure.SequenceNode)
     */
    @Override
    public void addAdjustment(final Adjustment adjustment) throws AdjustmentAlreadyPresentException {
        if (adjustment == null) {
            throw new java.lang.IllegalArgumentException("Adjustment reference is null");
        }
        if (this.nameMap.containsKey(adjustment.getName())) {
            throw new java.lang.IllegalArgumentException("Adjustment name is already in use");
        }
        if (this.toolMap.containsKey(adjustment.getToolType())) {
            throw new AdjustmentAlreadyPresentException("Procedure is full, can't add any more tools");
        }

        this.nameMap.put(adjustment.getName(), this.nextIndex);
        this.adjustments[this.nextIndex] = adjustment;
        this.nextIndex++;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.procedure.Procedure#removeAdjustment(int)
     */
    @Override
    public void removeAdjustment(final int index) {
        if (index < 0 || index >= this.nextIndex) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big)");
        }
        this.nameMap.remove(this.adjustments[index].getName());
        this.toolMap.remove(this.adjustments[index].getToolType());
        this.adjustments[index] = null;
        // shifting back and updating indexes into the map
        for (int i = index; i < this.nextIndex - 1; i++) {
            this.adjustments[i] = this.adjustments[i + 1];
            this.nameMap.replace(this.adjustments[i].getName(), i);
            this.toolMap.replace(this.adjustments[i].getToolType(), i);
        }
        this.nextIndex--;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.procedure.Procedure#disableAdjustment(int)
     */
    @Override
    public void disableAdjustment(final int index) {
        if (index < 0 || index >= this.nextIndex) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big)");
        }
        this.adjustments[index].disable();
    }

    /* (non-Javadoc)
     * @see barlugofx.model.procedure.Procedure#enableAdjustment(int)
     */
    @Override
    public void enableAdjustment(final int index) {
        if (index < 0 || index >= this.nextIndex) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big)");
        }
        this.adjustments[index].enable();
    }

    /* (non-Javadoc)
     * @see barlugofx.model.procedure.Procedure#findByName(java.lang.String)
     */
    @Override
    public int findByName(final String adjustmentName) {
        Integer index = this.nameMap.get(adjustmentName);
        if (index == null) {
            return -1;
        }
        return (int) index;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.procedure.Procedure#editAdjustment(int, barlugofx.model.procedure.SequenceNode)
     */
    @Override
    public void editAdjustment(final int index, final Adjustment adjustment) {
        if (index < 0 || index >= this.nextIndex) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big)");
        }
        if (adjustment == null) {
            throw new java.lang.IllegalArgumentException("Adjustment reference is null");
        }
        if (this.adjustments[index].getToolType() != adjustment.getToolType()) {
            throw new java.lang.IllegalArgumentException("Adjustment tool type isn't the same.");
        }

        this.nameMap.remove(this.adjustments[index].getName());
        this.nameMap.put(adjustment.getName(), index);
        this.adjustments[index] = adjustment;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.procedure.Procedure#getValue(java.lang.String)
     */
    @Override
    public Optional<Parameter<? extends Number>> getValue(final int index, final ParameterName name) {
        if (index < 0 || index >= this.nextIndex) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big)");
        }
        if (name == null) {
            throw new java.lang.IllegalArgumentException("name reference is null");
        }
        return this.adjustments[index].getTool().getParameter(name);
    }

    @Override
    /**
     * @param toolType the type of tool you want to add.
     * @return true if you can add an Adjustment based on that tool, false otherwise.
     */
    public boolean canAdd(final Tools toolType) {
        return (this.toolMap.get(toolType) == null);
    }

    /**
     * 
     */
    @Override
    public boolean isAdjustmentEnabled(final int index) {
        if (index < 0 || index >= this.nextIndex) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big)");
        }
        return this.adjustments[index].isEnabled();
    }

    /**
     * 
     * @return string representation of adjustments array
     */
    public String adjustmentsNamesToString() {
        String res = "";
        for (int i = 0; i < this.nextIndex; i++) {
            res = res + "(" + i + ")" + this.adjustments[i].getName();
            if (i < this.nextIndex - 1) {
                res += ",";
            }
        }
        return res;
    }

    /**
     * @return string representation of Procedure Object
     */
    @Override
    public String toString() {
        String res = "Procedure{size="
                + this.totalToolCount
                + ",nextIndex="
                + this.nextIndex
                + ",adjustments=["
                + this.adjustmentsNamesToString()
                + "]}";
        return res;
    }
}
