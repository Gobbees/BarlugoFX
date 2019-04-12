package barlugofx.view;

import barlugofx.controller.AppManager;

/**
 * Abstract skeleton of a ViewController class with manager.
 */
public abstract class AbstractViewControllerWithManager extends AbstractViewController {
    private AppManager manager;
    /**
     * Returns the manager.
     * @return the manager
     */
    protected AppManager getManager() {
        return manager;
    }
    /**
     * This function sets the app manager (controller). It must be called in order
     * to avoid future errors.
     * @param manager the input manager
     */
    public void setManager(final AppManager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("The manager must not be null");
        }
        this.manager = manager;
    }
    /**
     * Check if the manager is not null.
     * @throws IllegalStateException if the manager is null
     */
    protected void checkManager() throws IllegalStateException {
        if (manager == null) {
            throw new IllegalStateException("The manager is null");
        }
    }
}
