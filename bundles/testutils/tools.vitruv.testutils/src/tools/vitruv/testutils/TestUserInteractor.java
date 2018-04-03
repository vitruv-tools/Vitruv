package tools.vitruv.testutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;

import tools.vitruv.framework.change.interaction.UserInputBase;
import tools.vitruv.framework.userinteraction.ConfirmationDialogBuilder;
import tools.vitruv.framework.userinteraction.DialogBuilder;
import tools.vitruv.framework.userinteraction.InputFieldType;
import tools.vitruv.framework.userinteraction.MultipleChoiceSelectionDialogBuilder;
import tools.vitruv.framework.userinteraction.NotificationDialogBuilder;
import tools.vitruv.framework.userinteraction.NotificationType;
import tools.vitruv.framework.userinteraction.SelectionType;
import tools.vitruv.framework.userinteraction.TextInputDialogBuilder;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.userinteraction.WindowModality;
import tools.vitruv.framework.userinteraction.impl.TextInputDialog.InputValidator;

/**
 * The {@link TestUserInteractor} can be used in tests to simulate UserInteracting. It has a queue
 * of next selections. If the queue is empty the {@link TestUserInteractor} decides randomly the
 * next selection. It also allows to simulate the thinking time for a user.
 *
 */
public class TestUserInteractor implements UserInteracting, UserInteracting.UserInputListener {
    private static final Logger logger = Logger.getLogger(TestUserInteractor.class);
    private final ConcurrentLinkedQueue<URI> concurrentURILinkedQueue;
    private final ConcurrentLinkedQueue<Boolean> concurrentConfirmationLinkedQueue;
    private final ConcurrentLinkedQueue<String> concurrentFreeTextLinkedQueue;
    private final ConcurrentLinkedQueue<Integer> concurrentSingleSelectionLinkedQueue;
    private final ConcurrentLinkedQueue<Integer[]> concurrentMultiSelectionLinkedQueue;
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
        concurrentURILinkedQueue = new ConcurrentLinkedQueue<URI>();
        concurrentConfirmationLinkedQueue = new ConcurrentLinkedQueue<Boolean>();
        concurrentFreeTextLinkedQueue = new ConcurrentLinkedQueue<String>();
        concurrentSingleSelectionLinkedQueue = new ConcurrentLinkedQueue<Integer>();
        concurrentMultiSelectionLinkedQueue = new ConcurrentLinkedQueue<Integer[]>();
        // TODO: both random and messageLog never used?! -> random mentioned in javadoc, doesn't fit actual usage
        this.random = new Random();
        this.messageLog = new ArrayList<String>();
    }

    public TestUserInteractor() {
        this(-1, -1);
    }
    
    public void addNextConfirmationInputs(final boolean... nextConfirmations) {
    	this.concurrentConfirmationLinkedQueue.clear();
    	for (int i = 0; i < nextConfirmations.length; i++) {
    		this.concurrentConfirmationLinkedQueue.add(nextConfirmations[i]);
    	}
    }
    
    public void addNextTextInputs(final String... nextFreeTexts) {
    	this.concurrentFreeTextLinkedQueue.clear();
    	this.concurrentFreeTextLinkedQueue.addAll(Arrays.asList(nextFreeTexts));
    }
    
    public void addNextSingleSelections(final int... nextSingleSelections) {
    	this.concurrentSingleSelectionLinkedQueue.clear();
    	for (int i = 0; i < nextSingleSelections.length; i++) {
    		this.concurrentSingleSelectionLinkedQueue.add(nextSingleSelections[i]);
    	}
    }
    
    public void addNextMultiSelections(final int[]... nextMultiSelections) {
    	this.concurrentMultiSelectionLinkedQueue.clear();
    	for (int i = 0; i < nextMultiSelections.length; i++) {
    		Integer[] currentMultiSelection = Arrays.stream(nextMultiSelections[i]).boxed().toArray(Integer[]::new);
    		this.concurrentMultiSelectionLinkedQueue.add(currentMultiSelection);
    	}
    }
    
    public void addNextUriSelections(final URI... nextSelections) {
        this.concurrentURILinkedQueue.clear();
        this.concurrentURILinkedQueue.addAll(Arrays.asList(nextSelections));
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

	@Override
	public NotificationDialogBuilder getNotificationDialogBuilder() {
		return new TestNotificationDialogBuilder(this);
	}
	
	public void simulateNotification(String logMessage) {
		logger.info(logMessage);
	}

	@Override
	public ConfirmationDialogBuilder getConfirmationDialogBuilder() {
		return new TestConfirmationDialogBuilder(this);
	}
	
	public boolean simulateConfirmation(String logMessage) {
		logger.info(logMessage);
		this.simulateUserThinktime();
		if (!this.concurrentConfirmationLinkedQueue.isEmpty()) {
            boolean userConfirmed = this.concurrentConfirmationLinkedQueue.poll();
            logger.info(TestUserInteractor.class.getSimpleName() + " selected " + userConfirmed);
            /*ConfirmationUserInput result = InteractionFactory.eINSTANCE.createConfirmationUserInput();
        	result.setConfirmed(userConfirmed);*/
            return userConfirmed;
        } else {
        	throw new IllegalStateException("No user interaction confirmation specified!");
        }       
	}

	@Override
	public TextInputDialogBuilder getTextInputDialogBuilder() {
		return new TestTextInputDialogBuilder(this);
	}
	
	public String simulateTextInput(String logMessage) {
		logger.info(logMessage);
		this.simulateUserThinktime();
		if (!this.concurrentFreeTextLinkedQueue.isEmpty()) {
			String input = concurrentFreeTextLinkedQueue.poll();
			logger.info(TestUserInteractor.class.getSimpleName() + " got input \"" + input + "\"");
			return input;
		} else {
        	throw new IllegalStateException("No user interaction text input specified!");
        }
	}

	@Override
	public MultipleChoiceSelectionDialogBuilder getMultipleChoiceSelectionDialogBuilder() {
		return new TestMultipleChoiceSelectionDialogBuilder(this);
	}
	
	public int simulateSingleSelect(String logMessage, int choicesCount) {
		logger.info(logMessage);
		this.simulateUserThinktime();
		if (!this.concurrentSingleSelectionLinkedQueue.isEmpty()) {
			int selectedIndex = concurrentSingleSelectionLinkedQueue.poll();
			if (selectedIndex >= choicesCount) {
                logger.warn("selectedIndex > choicesCount - could lead to array out of bounds exception later on.");
            }
			logger.info(TestUserInteractor.class.getSimpleName() + " selected index " + selectedIndex);
			return selectedIndex;
		} else {
        	throw new IllegalStateException("No user interaction single select index specified!");
        }
	}
	
	public int[] simulateMultiSelect(String logMessage, int choicesCount) {
		logger.info(logMessage);
		this.simulateUserThinktime();
		if (!this.concurrentMultiSelectionLinkedQueue.isEmpty()) {
			Stream<Integer> choicesStream = Arrays.stream(concurrentMultiSelectionLinkedQueue.poll());
			int[] selectedIndices = choicesStream.mapToInt(Integer::intValue).toArray();
			if (choicesStream.anyMatch(index -> index > choicesCount)) {
				logger.warn("selectedIndices contains index > choicesCount - could lead to array out of bounds exception later on.");
			}
			logger.info(TestUserInteractor.class.getSimpleName() + " selected indices " + Arrays.toString(selectedIndices));
			return selectedIndices;
		} else {
        	throw new IllegalStateException("No user interaction multi select indices specified!");
        }
	}

	@Override
	public void onUserInputReceived(UserInputBase input) {
		// TODO DK: add some sort of bookkeeping here like in UserInteractor?
	}
	
}


abstract class TestBaseDialogBuilder<T> implements DialogBuilder<T> {
	protected Map<String, String> dialogProperties = new HashMap<>();
	protected TestUserInteractor testUserInteractor;
	
	public TestBaseDialogBuilder(TestUserInteractor testUserInteractor) {
		this.testUserInteractor = testUserInteractor;
	}
	
    @Override
    abstract public T showDialogAndGetUserInput(); 
    
    protected void openDialog() { }
    
    @Override
    public DialogBuilder<T> title(String title) {
    	dialogProperties.put("title", title);
        return this;
    }
    
    @Override
    public DialogBuilder<T> windowModality(WindowModality windowModality) {
    	dialogProperties.put("window modality", windowModality.name());
        return this;
    }
    
    @Override
    public DialogBuilder<T> positiveButtonText(String text) {
    	dialogProperties.put("positive button text", text);
        return this;
    }
    
    @Override
    public DialogBuilder<T> negativeButtonText(String text) {
    	dialogProperties.put("negative button text", text);
        return this;
    }
    
    @Override
    public DialogBuilder<T> cancelButtonText(String text) {
    	dialogProperties.put("cancel button text", text);
        return this;
    }
}


class TestNotificationDialogBuilder extends TestBaseDialogBuilder<Void> implements NotificationDialogBuilder,
		NotificationDialogBuilder.OptionalSteps {
	
	public TestNotificationDialogBuilder(TestUserInteractor testUserInteractor) {
		super(testUserInteractor);
	}
    
    @Override
    public NotificationDialogBuilder.OptionalSteps notificationType(NotificationType notificationType) {
    	dialogProperties.put("notification type", notificationType.name());
        return this;
    }

    @Override
    public Void showDialogAndGetUserInput() {
    	String dialogSummary = dialogProperties.entrySet().stream().map(Object::toString).reduce("",
    			((a, b) -> a + ", " + b));
    	testUserInteractor.simulateNotification("Showing NotificationDialog: " + dialogSummary);
        return null; // notifications don't have any form of user input
    }
    
    @Override
    public NotificationDialogBuilder.OptionalSteps message(String message) {
    	dialogProperties.put("message", message);
        return this;
    }
}


class TestConfirmationDialogBuilder extends TestBaseDialogBuilder<Boolean> implements ConfirmationDialogBuilder {
	
	public TestConfirmationDialogBuilder(TestUserInteractor testUserInteractor) {
		super(testUserInteractor);
	}
	
	@Override
	public Boolean showDialogAndGetUserInput() {
		String dialogSummary = dialogProperties.entrySet().stream().map(Object::toString).reduce("", ((a, b) -> a + ", " + b));
		return testUserInteractor.simulateConfirmation("Showing ConfirmationDialog: " + dialogSummary);
	}
	
	@Override
	public DialogBuilder<Boolean> message(String message) {
		dialogProperties.put("message", message);
		return this;
	}
}


class TestTextInputDialogBuilder extends TestBaseDialogBuilder<String> implements TextInputDialogBuilder,
		TextInputDialogBuilder.OptionalSteps {
	
	public TestTextInputDialogBuilder(TestUserInteractor testUserInteractor) {
		super(testUserInteractor);
	}
	
	@Override
	public String showDialogAndGetUserInput() {
		String dialogSummary = dialogProperties.entrySet().stream().map(Object::toString).reduce("", ((a, b) -> a + ", " + b));
		return testUserInteractor.simulateTextInput("Showing TextInputDialog: " + dialogSummary);
	}
	
	@Override
	public OptionalSteps message(String message) {
		dialogProperties.put("message", message);
		return this;
	}

	@Override
	public OptionalSteps inputValidator(InputValidator inputValidator) {
		dialogProperties.put("inputValidator", inputValidator.getInvalidInputMessage("[input]"));
		return this;
	}

	@Override
	public OptionalSteps inputValidator(Function<String, Boolean> validatorFunction, String invalidInputMessage) {
		dialogProperties.put("inputValidator", invalidInputMessage);
		return this;
	}

	@Override
	public TextInputDialogBuilder inputFieldType(InputFieldType inputFieldType) {
		dialogProperties.put("inputFieldType", inputFieldType.name());
		return this;
	}
}


class TestMultipleChoiceSelectionDialogBuilder extends TestBaseDialogBuilder<Collection<Integer>> implements
	MultipleChoiceSelectionDialogBuilder, MultipleChoiceSelectionDialogBuilder.ChoicesStep,
	MultipleChoiceSelectionDialogBuilder.OptionalSteps {
	private boolean isSingleSelect = true;
	private String[] choices;
	
	public TestMultipleChoiceSelectionDialogBuilder(TestUserInteractor testUserInteractor) {
		super(testUserInteractor);
	}
	
	@Override
	public Collection<Integer> showDialogAndGetUserInput() {
		String dialogSummary = dialogProperties.entrySet().stream().map(Object::toString).reduce("", ((a, b) -> a + ", " + b));
		Collection<Integer> selection = new ArrayList<>();
		if (isSingleSelect) {
			selection.add(testUserInteractor.simulateSingleSelect(
					"Showing MultipleChoiceSelectionDialog (single selection mode): " + dialogSummary, choices.length));
		} else {
			Arrays.stream(testUserInteractor.simulateMultiSelect(
					"Showing MultipleChoiceSelectionDialog (multi selection mode): " + dialogSummary, choices.length))
					.forEach(e -> selection.add(e));
		}
		return selection;
	}
	
	@Override
	public ChoicesStep message(String message) {
		dialogProperties.put("message", message);
		return this;
	}
	
	@Override
	public OptionalSteps choices(String[] choices) {
		this.choices = choices;
		dialogProperties.put("choices", choices.toString());
		return this;
	}
	
	@Override
	public OptionalSteps selectionType(SelectionType selectionType) {
		isSingleSelect = (selectionType == SelectionType.SINGLE_SELECT);
		dialogProperties.put("selectionType", selectionType.name());
		return this;
	}
}
