package barlugofx.model.tools.parallelHandler;

import barlugofx.model.imageTools.Image;

/**
 * This class allows the usage of the image tools in a parallel way. The number of thread to instantiate is automatically chosen.
 * Notice that your should always call the function @see ParallelFilterExecutor#shouldYouParallelize() to check
 * if the paralization is actually worth it. In your system, you should have only one instance of this class.
 */
public final class ParallelFilterExecutor {
    private static final long THRESHOLD = 4000000L;

    private static class LazyInizialization {
        private static final ParallelFilterExecutor SINGLETON = new ParallelFilterExecutor();
    }

    private ParallelFilterExecutor() {

    }

    /**
     * This function returns the ParallelFilterExecutor instance to which append the work to do.
     * @return the instanciated Executor.
     */
    public static ParallelFilterExecutor executor() {
        return LazyInizialization.SINGLETON;
    }

    /**
     * This function returns true  if the Image is big enough that is worth parallelize, false otherwise.
     * @param target the Image to check.
     * @return a boolean, true or false
     */
    public static boolean shouldYouParallelize(final Image target) {
        /*
         * In una deployed application, questa funzione deve essere riscritta. In questo
         * caso il threshold selezionato e' molto grossolano e puo' essere decisamente
         * migliorato.
         */
        return (long) target.getHeight() * target.getWidth() > THRESHOLD;
    }
}
