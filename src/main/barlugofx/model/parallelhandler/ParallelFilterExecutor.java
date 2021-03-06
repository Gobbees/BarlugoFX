package barlugofx.model.parallelhandler;

import java.awt.Point;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.utils.MutablePair;

/**
 * This class allows the usage of the image tools in a parallel way. The number of thread to instantiate is automatically chosen.
 * Notice that your should always call the function @see ParallelFilterExecutor#shouldYouParallelize() to check
 * if the parallelization is actually worth it.
 *
 * Only one instance of this class is allowed.
 */
public final class ParallelFilterExecutor {
    private final int nSubtask;
    private final ExecutorService exec;


    private static class LazyInizialization {
        private static final ParallelFilterExecutor SINGLETON = new ParallelFilterExecutor(); //Exactly like in Viroli's slides.
    }

    private ParallelFilterExecutor() {
        nSubtask = Runtime.getRuntime().availableProcessors() + 1; //This is  empirical
        exec = Executors.newCachedThreadPool();
    }

    /**
     * This function returns the ParallelFilterExecutor instance to which append the work to do.
     * @return the instantiated Executor.
     */
    public static ParallelFilterExecutor executor() {
        return LazyInizialization.SINGLETON;
    }

    /**
     * This function returns true if the {@link ParallelFilterExecutor} is worth to be used on the given Image.
     * @param target the Image to check.
     * @return a boolean, true or false
     */
    public static boolean shouldYouParallelize(final Image target) {
        /*
         * In una deployed application, questa funzione deve essere riscritta facendo opportuni studi sul tempo di esecuzione.
         * Deve comunque essere logicamente presente pertanto ho deciso di lasciarla.
         */
        return true;
    }

    /**
     * Given a ParallelizableImageTool, this function applies it to the target image in a parallel way.
     * @param tool the tool to apply to the image
     * @param target the image to update
     * @return the resulting image in which the filter has been applied
     */
    public Image applyTool(final ParallelizableImageTool tool, final Image target) {
        tool.inizializeTool();
        final int[][] pixels = target.getImageRGBvalues();
        final int[][] newPixels = new int[pixels.length][pixels[0].length];
        final CountDownLatch latch = new CountDownLatch(nSubtask);
        divideImage(target).stream().forEach(pair -> {
            exec.execute(() -> {
                tool.executeTool(pixels, newPixels, pair.getFirst(), pair.getSecond());
                latch.countDown();
            });
        });
        try {
            latch.await();
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Unexpected interruption of parallel filtering algorithm", e);
        }
        return ImageImpl.buildFromPixels(newPixels);
    }

    private Collection<MutablePair<Point, Point>> divideImage(final Image target) {
        final int width = target.getWidth();
        final int height = target.getHeight();
        final int bandWidth = width / nSubtask;

        final Collection<MutablePair<Point, Point>> dividedImage = new LinkedList<>();

        int currPixel = 0;
        for (int i = 0; i < nSubtask - 1; i++) {
            final Point begin = new Point(currPixel, 0);
            currPixel += bandWidth;
            final Point end = new Point(currPixel, height);
            dividedImage.add(new MutablePair<>(begin, end));
        }
        dividedImage.add(new MutablePair<>(new Point(currPixel, 0), new Point(width, height)));
        return dividedImage;
    }

    /* test for divideImage. JUnit was an overkill.
    public static void main(final String[] args) {
        final Image temp = ImageImpl.buildFromPixels(new int[1920][1080]);
        final ParallelFilterExecutor exec = ParallelFilterExecutor.executor();
        final Queue<MutablePair<Point, Point>> que = exec.divideImage(temp);
        System.out.println(que.size());
        que.stream().forEach(x -> System.out.println(x.getFirst().y + " " + x.getSecond().y));
    }
     */
}
