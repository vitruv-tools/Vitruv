package tools.vitruvius.applications.jmljava.helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * Utility class for parallel algorithms.
 * 
 * @author Stephan Seifermann
 *
 */
public class ParallelAlgorithms {

    private static final Logger LOGGER = Logger.getLogger(ParallelAlgorithms.class);

    /**
     * Base interface for all parallel operations.
     */
    public interface ParallelOperation {

    }

    /**
     * Interface for operations, which can be used in parallel for loops.
     * 
     * @param <T>
     *            The type of the iterated object.
     */
    public interface ParallelForOperation<T> extends ParallelOperation {
       
        /**
         * Applies the operation to the given object.
         * 
         * @param obj
         *            The object.
         */
        public void apply(T obj);
    }

    /**
     * Iterates over the given iterable objects in parallel and applies the given operation to each
     * of them.
     * 
     * @param objects
     *            The objects to iterate over.
     * @param operation
     *            The operation to apply.
     * @param <T>
     *            the type of the objects.
     */
    public static <T> void parallelFor(final Iterable<T> objects, final ParallelForOperation<T> operation) {
        final long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (final T object : objects) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    operation.apply(object);
                }
            });
        }
        try {
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            LOGGER.error("Processing was interrupted before completion.", e);
            return;
        }
        final long duration = System.currentTimeMillis() - startTime;
        LOGGER.trace("Execution time of parallelFor: " + duration + " ms.");
    }

}
