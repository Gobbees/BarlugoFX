/**
 * 
 */
package barlugofx.model.procedure;

/**
 * 
 *
 */
public class HistoryImpl implements History {
    /**
     */
    public static final int MAX_SIZE = 32;
    private final Action[] history = new ActionImpl[HistoryImpl.MAX_SIZE];
    /*
     * currentActionIndex is like a cursor: all Actions from index 0 to currentActionIndex are applied.
     * lastActionIndex is the (timewise) last action added to the history.
     * Until undoAction() is called lastActionIndex equals currentActionIndex,
     * after the call currentActionIndex goes back one action and lastActionIndex remains the same.
     */
    private int currentActionIndex = 0;
    private int lastActionIndex = 0;

    /**
     * 
     */
    public HistoryImpl() {
        this.currentActionIndex = 0;
        this.lastActionIndex = 0;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.procedure.History#addAction(barlugofx.model.procedure.Action)
     */
    @Override
    public void addAction(final Action action) { 
        if (this.currentActionIndex == HistoryImpl.MAX_SIZE - 1) {
            this.shiftLeftHistory();
        }
        this.history[this.currentActionIndex + 1] = action;
        this.currentActionIndex++;
        this.lastActionIndex = this.currentActionIndex;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.procedure.History#undoAction()
     */
    @Override
    public Action undoAction() throws NoMoreActionsException {
        if (this.currentActionIndex <= 0) {
            throw new NoMoreActionsException("There are no more actions to undo.");
        }
        Action action = this.history[this.currentActionIndex];
        this.currentActionIndex--;
        return action;
    }

    /* (non-Javadoc)
     * @see barlugofx.model.procedure.History#redoAction()
     */
    @Override
    public Action redoAction() throws NoMoreActionsException {
        if (this.currentActionIndex >= this.lastActionIndex) {
            throw new NoMoreActionsException("There are no more actions to redo.");
        }
        this.currentActionIndex++;
        return this.history[this.currentActionIndex];
    }

    /* (non-Javadoc)
     * @see barlugofx.model.procedure.History#getSize()
     */
    @Override
    public int getSize() {
        return this.lastActionIndex;
    }

    /* (non-Javadoc)
     * Returns an array of Strings, each one representing an Action in the History.
     * Each Action is represented with the action type and the tool type.
     */
    @Override
    public String[] getActionList() {
        String[] stringRepresentation = new String[this.lastActionIndex];
        for (int i = 0; i < this.lastActionIndex; i++) {
            stringRepresentation[i] = this.history[i].getType().toString() + " " + this.history[i].getAdjustment().getToolType().toString();
        }
        return stringRepresentation;
    }

    private void shiftLeftHistory() {
       for (int i = 1; i < HistoryImpl.MAX_SIZE; i++) {
           this.history[i - 1] = this.history[i];
       }
       this.currentActionIndex--;
       this.lastActionIndex--;
    }
}
