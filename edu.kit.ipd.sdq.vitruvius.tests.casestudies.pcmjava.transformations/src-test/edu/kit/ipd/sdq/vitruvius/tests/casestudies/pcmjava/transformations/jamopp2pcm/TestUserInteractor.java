package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.jamopp2pcm;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;

/**
 * The {@link TestUserInteractor} can be used in tests to simulate UserInteracting. It has a queue
 * of next selections. If the queue is empty the {@link TestUserInteractor} decides randomly the
 * next selection. It also allows to simulate the thinking time for a user.
 *
 */
public class TestUserInteractor implements UserInteracting {

    private static final Logger logger = Logger.getLogger(TestUserInteractor.class);

    private final ConcurrentLinkedQueue<Integer> concurrentLinkedQueue;
    private final Random random;
    private final int minWaittime;
    private final int maxWaittime;
    private final int waitTimeRange;

    public TestUserInteractor(final int minWaittime, final int maxWaittime) {
        if (minWaittime > maxWaittime) {
            throw new RuntimeException("Configure min and max waittime properly: Min" + minWaittime + " Max: "
                    + maxWaittime);
        }
        this.minWaittime = minWaittime;
        this.maxWaittime = maxWaittime;
        this.waitTimeRange = maxWaittime - minWaittime;
        this.concurrentLinkedQueue = new ConcurrentLinkedQueue<Integer>();
        this.random = new Random();
    }

    public TestUserInteractor() {
        this(-1, -1);
    }

    public void addNextSelections(final Integer... nextSelections) {
        this.concurrentLinkedQueue.addAll(Arrays.asList(nextSelections));
    }

    @Override
    public void showMessage(final UserInteractionType type, final String message) {
        logger.info("showMessage: " + message + " Type: " + type);
    }

    @Override
    public int selectFromMessage(final UserInteractionType type, final String message,
            final String... selectionDescriptions) {
        logger.info("selectFromMessage: " + message + " Type: " + type + " Choices: "
                + StringUtils.join(selectionDescriptions, ", "));
        return this.selectFromMessage(selectionDescriptions.length);
    }

    @Override
    public int selectFromModel(final UserInteractionType type, final String message,
            final ModelInstance... modelInstances) {
        logger.info("selectFromModel: " + message + " Type: " + type + " Choices: "
                + StringUtils.join(modelInstances, ", "));
        return this.selectFromMessage(modelInstances.length);
    }

    private int selectFromMessage(final int maxLength) {
        if (-1 < this.maxWaittime) {
            this.simulateUserThinktime();
        }
        int currentSelection;
        String randomly = "";
        if (!this.concurrentLinkedQueue.isEmpty()) {
            currentSelection = this.concurrentLinkedQueue.poll();
            if (currentSelection >= maxLength) {
                logger.warn("currentSelection>maxLength - could lead to array out of bounds exception later on.");
            }
        } else {
            currentSelection = this.random.nextInt(maxLength);
            randomly = " randomly";
        }
        logger.info(TestUserInteractor.class.getSimpleName() + randomly + " selecteded " + currentSelection);
        return currentSelection;
    }

    private void simulateUserThinktime() {
        final int currentWaittime = this.random.nextInt(this.waitTimeRange + 1) + this.minWaittime;
        try {
            Thread.sleep(currentWaittime);
        } catch (final InterruptedException e) {
            logger.trace("User think time simulation thread interrupted: " + e, e);
        }
    }

}
