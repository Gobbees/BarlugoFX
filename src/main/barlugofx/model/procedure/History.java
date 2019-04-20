package barlugofx.model.procedure;

/**
 * 
 *
 */
public interface History {
    /**
     * 
     * @param action
     * The Action you want to add.
     */
    void addAction(Action action);

    /**
     * Get the Action you have to restore and moves the cursor back.
     * @return the action you need to restore.
     * @throws NoMoreActionsException 
     */
    Action undoAction() throws NoMoreActionsException;

    /**
     * @return Action
     * @throws NoMoreActionsException 
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
     * @return string representation of the history content.
     */
    String[] getActionList();

}
