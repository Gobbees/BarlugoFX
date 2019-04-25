package barlugofx.model.procedure;

import barlugofx.model.tools.common.Parameter;
import barlugofx.model.tools.common.ParameterName;
import barlugofx.utils.Tools;
import barlugofx.model.imagetools.Image;
import barlugofx.model.parallelhandler.ParallelFilterExecutor;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 */
public final class ProcedureImpl implements Procedure {
    private final int totalToolCount = Tools.values().length;
    private Adjustment[] adjustments = new Adjustment[totalToolCount];
    private final Map<Tools, Integer> toolMap = new HashMap<Tools, Integer>();
    private final Map<String, Integer> nameMap = new HashMap<String, Integer>();
    private final History history = new HistoryImpl();
    private final Image baseImage;
    private int nextIndex;
    private final boolean canParallelize;
    private final ParallelFilterExecutor executor;

    /**
     * Wrapper of History's MAX_SIZE.
     */
    public static final int HISTORY_MAX_SIZE = HistoryImpl.MAX_SIZE;

    /**
     * Creates a new procedure object, setting it's base image.
     * @param baseImage baseImage the base image.
     * @param canParallelize canParallelize true if the procedure can parallelize, false otherwise.
     */
    public ProcedureImpl(final Image baseImage, final boolean canParallelize) {
        this.nextIndex = 0;
        this.baseImage = baseImage;
        this.canParallelize = canParallelize;
        if (this.canParallelize) {
            this.executor = ParallelFilterExecutor.executor();
        } else {
            this.executor = null;
        }
    }

    @Override
    public Image add(final Adjustment adjustment) throws AdjustmentAlreadyPresentException {
        final int index = this.nextIndex;
        final Image image = this.insert(index, adjustment);
        this.history.addAction(new ActionImpl(Actions.ADD, index, adjustment));
        return image;
    }

    /*
     * Performs the insertion of an Adjustment in the adjustments container.
     * This method doesn't save an Action to the History, just adds the Adjustment.
     * This method is used by add(), undo() and redo().
     */
    private Image insert(final int index, final Adjustment adjustment) throws AdjustmentAlreadyPresentException {
        if (adjustment == null) {
            throw new IllegalArgumentException("Adjustment reference is null.");
        }
        if (index < 0 || index > this.nextIndex) {
            throw new IllegalArgumentException("Invalid index.");
        }
        if (this.toolMap.containsKey((adjustment.getToolType()))) {
            throw new AdjustmentAlreadyPresentException("Can't add another tool with type " + adjustment.getToolType().toString());
        }
        if (this.nameMap.containsKey(adjustment.getName())) {
           throw new IllegalArgumentException("Adjustment name is already in use.");
        }

        for (int i = this.nextIndex; i > index; i--) {
            this.adjustments[i] = this.adjustments[i - 1];
        }
        this.adjustments[index] = adjustment;
        this.nameMap.put(adjustment.getName(), index);
        this.toolMap.put(adjustment.getToolType(), index);
        this.nextIndex++;
        this.adjustments[index].setStartImage(index > 0 ? this.adjustments[index - 1].getEndImage() : this.baseImage);
        return this.processImage(index);
    }

    @Override
    public Image remove(final Tools type) {
        if (type == null) {
            throw new IllegalArgumentException("Type reference is null.");
        }
        return this.remove(this.findByType(type));
    }

    @Override
    public Image remove(final int index) {
        this.history.addAction(new ActionImpl(Actions.REMOVE, index, this.adjustments[index]));
        return this.delete(index);
    }

    private Image delete(final int index) {
        if (index < 0 || index >= this.nextIndex) {
            throw new IllegalArgumentException("Invalid index (either negative or too big).");
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
        return this.processImage(index);
    }

    @Override
    public Image edit(final Tools type, final Adjustment adjustment) {
        if (type == null) {
            throw new IllegalArgumentException("Type reference is null.");
        }
        return this.edit(this.findByType(type), adjustment);
    }

    @Override
    public Image edit(final int index, final Adjustment adjustment) {
        this.history.addAction(new ActionImpl(Actions.EDIT, index, adjustment, this.adjustments[index]));
        return this.replace(index, adjustment);
    }

    private Image replace(final int index, final Adjustment adjustment) {
        if (index < 0 || index >= this.nextIndex) {
            throw new IllegalArgumentException("Invalid index (either negative or too big)");
        }
        if (adjustment == null) {
            throw new IllegalArgumentException("Adjustment reference is null");
        }
        if (this.adjustments[index].getToolType() != adjustment.getToolType()) {
            throw new IllegalArgumentException("Adjustment tool type isn't the same.");
        }
        this.nameMap.remove(this.adjustments[index].getName());
        this.nameMap.put(adjustment.getName(), index);
        adjustment.setStartImage(this.adjustments[index].getStartImage());
        this.adjustments[index] = adjustment;
        return this.processImage(index);
    }

    @Override
    public Image disable(final int index) {
        if (index < 0 || index >= this.nextIndex) {
            throw new IllegalArgumentException("Invalid index (either negative or too big).");
        }
        this.adjustments[index].disable();
        return this.processImage(index);
    }

    @Override
    public Image enable(final int index) {
        if (index < 0 || index >= this.nextIndex) {
            throw new IllegalArgumentException("Invalid index (either negative or too big).");
        }
        this.adjustments[index].enable();
        return this.processImage(index);
    }

    @Override
    public int findByType(final Tools type) {
        if (type == null) {
            throw new IllegalArgumentException("type reference is null");
        }
        return this.toolMap.get(type);
    }

    @Override
    public Optional<Parameter<? extends Number>> getValue(final int index, final ParameterName name) {
        if (index < 0 || index >= this.nextIndex) {
            throw new IllegalArgumentException("Invalid index (either negative or too big)");
        }
        if (name == null) {
            throw new IllegalArgumentException("name reference is null");
        }
        if (this.adjustments[index].isParallelizable()) {
            return this.adjustments[index].getParallelizableTool().getParameter(name);
        }
        return this.adjustments[index].getTool().getParameter(name);
    }

    @Override
    public Optional<Parameter<? extends Number>> getValue(final Tools tool, final ParameterName name) {
        return this.getValue(this.findByType(tool), name);
    }

    @Override
    public boolean canAdd(final Tools toolType) {
        return (this.toolMap.get(toolType) == null);
    }

    @Override
    public boolean isAdjustmentEnabled(final int index) {
        if (index < 0 || index >= this.nextIndex) {
            throw new IllegalArgumentException("Invalid index (either negative or too big)");
        }
        return this.adjustments[index].isEnabled();
    }

    /**
     * 
     * @param type
     * the type of tool in the adjustment of which you wish to know the name.
     * @return
     * the name if present, "null" otherwise.
     */
    public String getAdjustmentName(final Tools type) {
        if (type == null) {
            throw new IllegalArgumentException("type reference is null");
        }
        final Integer index = this.toolMap.get(type);
        if (index == null) {
            return "null";
        }
        return this.adjustments[index].getName();
    }

    @Override
    public String toString() {
        return "Procedure{size="
                + this.totalToolCount
                + ",nextIndex="
                + this.nextIndex
                + ","
                + this.history.toString()
                + "}";
    }

    @Override
    public Image undo() throws NoMoreActionsException, AdjustmentAlreadyPresentException {
        final Action action = this.history.undoAction();

        switch (action.getType()) {
        case ADD:
            return this.delete(action.getIndex());
        case EDIT:
            return this.replace(action.getIndex(), action.getAdjustmentBefore());
        case REMOVE:
            return this.insert(action.getIndex(), action.getAdjustment());
        default:
            throw new IllegalArgumentException("Action type not recognized.");
        }
    }

    @Override
    public Image redo() throws NoMoreActionsException, AdjustmentAlreadyPresentException {
        final Action action = this.history.redoAction();

        switch (action.getType()) {
        case ADD:
            return this.insert(action.getIndex(), action.getAdjustment());
        case EDIT:
            return this.replace(action.getIndex(), action.getAdjustment());
        case REMOVE:
            return this.delete(action.getIndex());
        default:
            throw new IllegalArgumentException("Action type not recognized.");
        }
    }

    @Override
    public void clear() {
       for (int i = 0; i < this.totalToolCount; i++) {
           this.adjustments[i] = null;
       }
       this.nameMap.clear();
       this.toolMap.clear();
       this.history.clear();
       this.nextIndex = 0;
    }

    @Override
    public String[] getHistoryStringRep() {
        return this.history.getActionList();
    }

    @Override
    public int getHistorySize() {
        return this.history.getSize();
    }

    @Override
    public Image processImage(final int index) {
        for (int i = index; i < this.nextIndex; i++) {
            this.adjustments[i].setStartImage((i > 0) ? this.adjustments[i - 1].getEndImage() : this.baseImage);
            this.applyAdjustment(this.adjustments[i]);
        }
        // I get this bug warning but the code seems correct.
        // The index check is implemented.
        // Bug warning:
        // Array index is out of bounds: -1 [Scary(7), Normal confidence] 
        return (this.nextIndex > 0) ? this.adjustments[this.nextIndex - 1].getEndImage() : this.baseImage;
    }

    private void applyAdjustment(final Adjustment adjustment) {
        if (adjustment.getStartImage() == null) {
            throw new IllegalArgumentException("startImage reference is null.");
        }
        Image result = null;
        if (adjustment.isParallelizable() && this.canParallelize) {
            result = this.executor.applyTool(adjustment.getParallelizableTool(), adjustment.getStartImage());
        } else {
            result = adjustment.getTool().applyTool(adjustment.getStartImage());
        }
        adjustment.setEndImage(result);
    }
}
