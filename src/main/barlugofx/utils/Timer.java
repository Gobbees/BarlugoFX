package barlugofx.utils;

/**
 * A simple timer for measuring performance time.
 *
 */
public class Timer {
    private long time;
    private static final int TOMILLISECONDS = 1000000;
    /**
     * Starts the timer.
     */
    public void start() {
        time = System.nanoTime();
    }
    /**
     * Stop the timer.
     * @return the time passed in milliseconds.
     */
    public long stop() {
        return (System.nanoTime() - time) / TOMILLISECONDS;
    }
}
