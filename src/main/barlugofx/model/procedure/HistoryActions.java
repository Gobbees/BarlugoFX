package barlugofx.model.procedure;

/**
 * Enumeration representing the type of actions performed on the history.
 */
public enum HistoryActions {
    /**
     * Added an Action to the History.
     */
    ADD,

    /**
     * Undone an Action in the History.
     */
    UNDO,

    /**
     * Redone an Action in the History.
     */
    REDO
}
