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
    private final History history = new HistoryImpl();

    /**
     * Wrapper of History MAX_SIZE.
     */
    public static final int HISTORY_MAX_SIZE = HistoryImpl.MAX_SIZE;

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
    public void add(final Adjustment adjustment) throws AdjustmentAlreadyPresentException {
        final int index = this.nextIndex;
        this.insert(index, adjustment);
        this.history.addAction(new ActionImpl(Actions.ADD, index, adjustment));
    }

    private void insert(final int index, final Adjustment adjustment) throws AdjustmentAlreadyPresentException {
        if (adjustment == null) {
            throw new java.lang.IllegalArgumentException("Adjustment reference is null.");
        }
        if (index < 0 || index > this.nextIndex) {
            throw new java.lang.IllegalArgumentException("Invalid index.");
        }
        if (this.toolMap.containsKey((adjustment.getToolType()))) {
            throw new AdjustmentAlreadyPresentException("Can't add another tool with type " + adjustment.getToolType().toString());
        }
        if (this.nameMap.containsKey(adjustment.getName())) {
           throw new java.lang.IllegalArgumentException("Adjustment name is already in use.");
        }

        for (int i = this.nextIndex; i > index; i--) {
            this.adjustments[i] = this.adjustments[i - 1];
        }
        this.adjustments[index] = adjustment;
        this.nameMap.put(adjustment.getName(), index);
        this.toolMap.put(adjustment.getToolType(), index);
        this.nextIndex++;
    }

    /**
     * 
     */
    @Override
    public void remove(final Tools type) {
        if (type == null) {
            throw new java.lang.IllegalArgumentException("Type reference is null.");
        }
        this.remove(this.findByType(type));
    }

    /**
     * 
     */
    @Override
    public void remove(final String adjustmentName) {
        if (adjustmentName == null) {
            throw new java.lang.IllegalArgumentException("Name reference is null.");
        }
        this.remove(this.findByName(adjustmentName));
    }

    /**
     * 
     */
    @Override
    public void remove(final int index) {
        this.history.addAction(new ActionImpl(Actions.REMOVE, index, this.adjustments[index]));
        this.delete(index);
    }

    private void delete(final int index) {
        if (index < 0 || index >= this.nextIndex) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big).");
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

    /**
     * 
     * @param adjustmentName
     * @param adjustment
     */
    @Override
    public void edit(final String adjustmentName, final Adjustment adjustment) {
        if (adjustmentName == null) {
            throw new java.lang.IllegalArgumentException("AdjustmentName reference is null.");
        }
        this.edit(this.findByName(adjustmentName), adjustment);
    }

    /**
     * 
     * @param type
     * @param adjustment
     */
    @Override
    public void edit(final Tools type, final Adjustment adjustment) {
        if (type == null) {
            throw new java.lang.IllegalArgumentException("Type reference is null.");
        }
        this.edit(this.findByType(type), adjustment);
    }

    /**
     * 
     */
    @Override
    public void edit(final int index, final Adjustment adjustment) {
        this.replace(index, adjustment);
        this.history.addAction(new ActionImpl(Actions.EDIT, index, adjustment, this.adjustments[index]));
    }

    private void replace(final int index, final Adjustment adjustment) {
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
     * @see barlugofx.model.procedure.Procedure#disableAdjustment(int)
     */
    @Override
    public void disable(final int index) {
        if (index < 0 || index >= this.nextIndex) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big).");
        }
        this.adjustments[index].disable();
    }

    /* (non-Javadoc)
     * @see barlugofx.model.procedure.Procedure#enableAdjustment(int)
     */
    @Override
    public void enable(final int index) {
        if (index < 0 || index >= this.nextIndex) {
            throw new java.lang.IllegalArgumentException("Invalid index (either negative or too big).");
        }
        this.adjustments[index].enable();
    }

    /* (non-Javadoc)
     * @see barlugofx.model.procedure.Procedure#findByName(java.lang.String)
     */
    @Override
    public int findByName(final String adjustmentName) {
        if (adjustmentName == null) {
            throw new java.lang.IllegalArgumentException("adjustmentName reference is null");
        }
        Integer index = this.nameMap.get(adjustmentName);
        if (index == null) {
            return -1;
        }
        return (int) index;
    }

    /**
     * 
     * @param type
     * @return
     */
    @Override
    public int findByType(final Tools type) {
        if (type == null) {
            throw new java.lang.IllegalArgumentException("type reference is null");
        }
        Integer index = this.toolMap.get(type);
        return index;
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
                + "], "
                + this.history.toString()
                + "}";
        return res;
    }

    /**
     * @throws NoMoreActionsException 
     * @throws AdjustmentAlreadyPresentException 
     * 
     */
    @Override
    public void undo() throws NoMoreActionsException, AdjustmentAlreadyPresentException {
        Action action = this.history.undoAction();

        switch (action.getType()) {
        case ADD:
            this.delete(action.getIndex());
            break;
        case EDIT:
            this.replace(action.getIndex(), action.getAdjustmentBefore());
            break;
        case REMOVE:
            this.insert(action.getIndex(), action.getAdjustment());
            break;
        default:
            throw new java.lang.IllegalArgumentException("Action type not recognized.");
        }
    }

    /**
     * 
     */
    @Override
    public void redo() throws NoMoreActionsException, AdjustmentAlreadyPresentException {
        Action action = this.history.redoAction();

        switch (action.getType()) {
        case ADD:
            this.insert(action.getIndex(), action.getAdjustment());
            break;
        case EDIT:
            this.replace(action.getIndex(), action.getAdjustment());
            break;
        case REMOVE:
            this.delete(action.getIndex());
            break;
        default:
            throw new java.lang.IllegalArgumentException("Action type not recognized.");
        }
    }

    /**
     * 
     */
    @Override
    public String[] getHistoryStringRep() {
        return this.history.getActionList();
    }

    /**
     * 
     */
    @Override
    public int getHistorySize() {
        return this.history.getSize();
    }
}
