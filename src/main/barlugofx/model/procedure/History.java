package barlugofx.model.procedure;

/**
 * The History interface models the behaviour of a history object which allows the user to save an action,
 * and also rewind or replay the saved actions.
 */
public interface History {
    /**
     * 
     * @param action
     * The Action you want to add to the history.
     */
    void addAction(Action action);

    /**
     * Get the Action you have to restore and moves the cursor back.
     * @return the action you need to undo.
     * @throws NoMoreActionsException if there are no more actions to redo.
     */
    Action undoAction() throws NoMoreActionsException;

    /**
     * @return The action you need to redo.
     * @throws NoMoreActionsException if there are no more actions to redo.
     * 
     */
    Action redoAction() throws NoMoreActionsException;

    /**
     * 
     * @return number of actions currently saved in the history.
     */
    int getSize();

    /**
     * 
     * @return string representation of the history object.
     */
    String[] getActionList();

}
