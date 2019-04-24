package barlugofx.model.procedure;

/**
 * Implementation of the History object, features a fixed size.
 * Internally uses a standard array as a container.
 * When the container is full, the oldest action gets dropped to make new space.
 */
public final class HistoryImpl implements History {
    /**
     * The maximum number of Actions that can be saved at once in the History.
     * When the History is full and you try to add another, the container will be shifted left by one,
     * the leftmost element dropped and the new element added in the rightmost place.
     */
    public static final int MAX_SIZE = 32;
    private final Action[] history = new ActionImpl[HistoryImpl.MAX_SIZE];
    /*
     * currentActionIndex is like a cursor: all Actions from index 0 to currentActionIndex are applied.
     * lastActionIndex is the (timewise) last action added to the history.
     * Until undoAction() is called lastActionIndex equals currentActionIndex,
     * after the call currentActionIndex goes back one action and lastActionIndex remains the same.
     */
    private int currentActionIndex;
    private int lastActionIndex;

    /**
     * Single constructor with no parameters, initializes the indexes.
     */
    public HistoryImpl() {
        this.currentActionIndex = -1;
        this.lastActionIndex = -1;
    }

    @Override
    public void addAction(final Action action) { 
        if (this.currentActionIndex == HistoryImpl.MAX_SIZE - 1) {
            this.shiftLeftHistory();
        }
        for (int i = this.currentActionIndex + 1; i <= this.lastActionIndex; i++) {
            this.history[i] = null;
        }
        this.history[this.currentActionIndex + 1] = action;
        this.currentActionIndex++;
        this.lastActionIndex = this.currentActionIndex;
    }

    @Override
    public Action undoAction() throws NoMoreActionsException {
        if (this.currentActionIndex < 0) {
            throw new NoMoreActionsException("There are no more actions to undo.");
        }
        Action action = this.history[this.currentActionIndex];
        this.currentActionIndex--;
        return action;
    }

    @Override
    public Action redoAction() throws NoMoreActionsException {
        if (this.currentActionIndex >= this.lastActionIndex) {
            throw new NoMoreActionsException("There are no more actions to redo.");
        }
        this.currentActionIndex++;
        return this.history[this.currentActionIndex];
    }

    @Override
    public int getSize() {
        return this.lastActionIndex + 1;
    }

    @Override
    public String[] getActionList() {
        String[] stringRepresentation = new String[this.lastActionIndex + 1];
        for (int i = 0; i <= this.lastActionIndex; i++) {
            stringRepresentation[i] = this.history[i].getType().toString() + " " + this.history[i].getAdjustment().getToolType().toString();
        }
        return stringRepresentation;
    }

    /*
     * Shifts the history array one position to the left.
     * The leftmost element gets dropped.
     * This method is used to free up a space in the history array when it's full.
     */
    private void shiftLeftHistory() {
       for (int i = 1; i < HistoryImpl.MAX_SIZE; i++) {
           this.history[i - 1] = this.history[i];
       }
       this.currentActionIndex--;
       this.lastActionIndex--;
    }

    @Override
    public String toString() {
       String res = "History{size="
               + Integer.toString(this.lastActionIndex + 1)
               + ",curr="
               + this.currentActionIndex
               + ",last="
               + this.lastActionIndex
               + ",actions=[";
       for (int i = 0; i <= this.lastActionIndex; i++) {
           res += this.history[i];
           res += (i == this.currentActionIndex) ? "*" : "";
           res += (i != this.lastActionIndex) ? ", " : "";
       }
       res += "]}";
       return res;
    }
}
