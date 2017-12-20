package tools.vitruv.testutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;

import edu.kit.ipd.sdq.commons.util.java.lang.StringUtil;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.userinteraction.UserInteractionType;

/**
 * The {@link TestUserInteractor} can be used in tests to simulate UserInteracting. It has a queue
 * of next selections. If the queue is empty the {@link TestUserInteractor} decides randomly the
 * next selection. It also allows to simulate the thinking time for a user.
 *
 */
public class TestUserInteractor implements UserInteracting {

    private static final Logger logger = Logger.getLogger(TestUserInteractor.class);

    private final ConcurrentLinkedQueue<Integer> concurrentIntLinkedQueue;
    private final ConcurrentLinkedQueue<String> concurrentStringLinkedQueue;
    private final ConcurrentLinkedQueue<URI> concurrentURILinkedQueue;
    private final Random random;
    private final int minWaittime;
    private final int maxWaittime;
    private final int waitTimeRange;

	private Collection<String> messageLog;

    public TestUserInteractor(final int minWaittime, final int maxWaittime) {
        if (minWaittime > maxWaittime) {
            throw new RuntimeException(
                    "Configure min and max waittime properly: Min" + minWaittime + " Max: " + maxWaittime);
        }
        this.minWaittime = minWaittime;
        this.maxWaittime = maxWaittime;
        this.waitTimeRange = maxWaittime - minWaittime;
        this.concurrentIntLinkedQueue = new ConcurrentLinkedQueue<Integer>();
        this.concurrentStringLinkedQueue = new ConcurrentLinkedQueue<String>();
        this.concurrentURILinkedQueue = new ConcurrentLinkedQueue<URI>();
        this.random = new Random();
        this.messageLog = new ArrayList<String>();
    }

    public TestUserInteractor() {
        this(-1, -1);
    }

    public void addNextSelections(final Integer... nextSelections) {
        this.concurrentIntLinkedQueue.clear();
        this.concurrentIntLinkedQueue.addAll(Arrays.asList(nextSelections));
    }

    public void addNextSelections(final String... nextSelections) {
        this.concurrentStringLinkedQueue.clear();
        this.concurrentStringLinkedQueue.addAll(Arrays.asList(nextSelections));
    }
    
    public void addNextSelections(final URI... nextSelections) {
        this.concurrentURILinkedQueue.clear();
        this.concurrentURILinkedQueue.addAll(Arrays.asList(nextSelections));
    }

    @Override
    public void showMessage(final UserInteractionType type, final String message) {
        logger.info("showMessage: " + message + " Type: " + type);
        this.messageLog.add(message);
    }

    @Override
    public int selectFromMessage(final UserInteractionType type, final String message,
            final String... selectionDescriptions) {
        logger.info("selectFromMessage: " + message + " Type: " + type + " Choices: "
                + StringUtil.join(selectionDescriptions, ", "));
        return this.selectFromMessage(selectionDescriptions.length);
    }

    private int selectFromMessage(final int maxLength) {
        this.simulateUserThinktime();

        int currentSelection;
        if (!this.concurrentIntLinkedQueue.isEmpty()) {
            currentSelection = this.concurrentIntLinkedQueue.poll();
            if (currentSelection >= maxLength) {
                logger.warn("currentSelection>maxLength - could lead to array out of bounds exception later on.");
            }
        } else {
            throw new IllegalStateException("No user interaction integer selection specified");
        }
        logger.info(TestUserInteractor.class.getSimpleName() + " selected " + currentSelection);
        return currentSelection;
    }

    private void simulateUserThinktime() {
        if (-1 < this.maxWaittime) {
            final int currentWaittime = this.random.nextInt(this.waitTimeRange + 1) + this.minWaittime;
            try {
                Thread.sleep(currentWaittime);
            } catch (final InterruptedException e) {
                logger.trace("User think time simulation thread interrupted: " + e, e);
            }
        }
    }

    @Override
    public String getTextInput(final String msg) {
        this.simulateUserThinktime();
        String text = "";
        if (!this.concurrentStringLinkedQueue.isEmpty()) {
            text = this.concurrentStringLinkedQueue.poll();
        } else {
        	throw new IllegalStateException("No user interaction integer selection specified");
        }
        logger.info(TestUserInteractor.class.getSimpleName() + " selecteded " + text);
        return text;
    }

	@Override
	public URI selectURI(String message) {
		if (this.concurrentURILinkedQueue.isEmpty()) {
			throw new IllegalStateException("No URI found in " + TestUserInteractor.class.getSimpleName() + " for message " + message);
		}
		
		final URI result = this.concurrentURILinkedQueue.poll();
		logger.info(TestUserInteractor.class.getSimpleName() + " selected " + result.toString());
		return result;
	}
	
	public boolean isResourceQueueEmpty() {
		return this.concurrentURILinkedQueue.isEmpty();
	}
	
	public Collection<String> getMessageLog(){
		return this.messageLog;
	}
	
}
