package tools.vitruv.testutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.PlatformUI;

import tools.vitruv.framework.change.interaction.ConfirmationUserInteraction;
import tools.vitruv.framework.change.interaction.FreeTextUserInteraction;
import tools.vitruv.framework.change.interaction.InteractionFactory;
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction;
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInteraction;
import tools.vitruv.framework.change.interaction.UserInteractionBase;
import tools.vitruv.framework.userinteraction.impl.NormalUserInteractor;
import tools.vitruv.framework.userinteraction.impl.PredefinedInputHandlerImpl;
import tools.vitruv.framework.userinteraction.PredefinedInputHandler;
import tools.vitruv.framework.userinteraction.UserInteractionListener;
import tools.vitruv.framework.userinteraction.impl.PredefinedInputInteractor;

/**
 * The {@link TestUserInteractor} can be used in tests to simulate UserInteractor. It has a queue
 * of next selections. If the queue is empty an {@link IllegalStateException} is thrown. It also allows to simulate the
 * thinking time for a user.
 *
 */
public class TestUserInteractor extends PredefinedInputInteractor {
	private final PredefinedTestInputHandler predefinedInputHandler;
	private UserInteractionListener userInputListener;
    
    public TestUserInteractor(final int minWaittime, final int maxWaittime) {
    	super(new ArrayList<UserInteractionBase>(), null, null /*PlatformUI.getWorkbench().getDisplay() TODO DK: throws IllegalStateEx: Workbench has not been created yet!*/);
    	predefinedInputHandler = new PredefinedTestInputHandler(minWaittime, maxWaittime, this);
    }
    
    public TestUserInteractor() {
        this(-1, -1);
    }
    
    public void addNextConfirmationInput(final boolean nextConfirmation) {
		predefinedInputHandler.addNextConfirmationInput(nextConfirmation);
    }
    
    public void addNextTextInput(String nextInput) {
    	predefinedInputHandler.addNextTextInput(nextInput);
    }
    
    public void addNextSingleSelection(final int nextSelection) {
    	predefinedInputHandler.addNextSingleSelection(nextSelection);
    }
    
    public void addNextMultiSelection(final int[] nextSelection) {
    	predefinedInputHandler.addNextMultiSelection(nextSelection);
    }
    
    public void addNextUriSelection(final URI nextSelection) {
    	predefinedInputHandler.addNextUriSelection(nextSelection);
    }

	@Override
	public PredefinedInputHandler getPredefinedInputHandler() {
		return predefinedInputHandler;
	}

	@Override
	public void registerUserInputListener(UserInteractionListener listener) {
		this.userInputListener = listener;
	}
	
	public UserInteractionListener getUserInputListener() {
		return userInputListener;
	}
}


class PredefinedTestInputHandler extends PredefinedInputHandlerImpl {
	private static final Logger logger = Logger.getLogger(TestUserInteractor.class);
	private final Random random;
	private final int minWaittime;
    private final int maxWaittime;
    private final int waitTimeRange;
    private final PredefinedInputInteractor interactor;
    
    public PredefinedTestInputHandler(final int minWaittime, final int maxWaittime, TestUserInteractor interactor) {
    	if (minWaittime > maxWaittime) {
            throw new RuntimeException(
                    "Configure min and max waittime properly: Min" + minWaittime + " Max: " + maxWaittime);
        }
        this.minWaittime = minWaittime;
        this.maxWaittime = maxWaittime;
        this.waitTimeRange = maxWaittime - minWaittime;
        this.random = new Random();
        this.interactor = interactor;
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
	
	public void addNextConfirmationInput(final boolean nextConfirmation) {
		Collection<UserInteractionBase> userInputs = getUserInteractions();
		ConfirmationUserInteraction input = InteractionFactory.eINSTANCE.createConfirmationUserInteraction();
		input.setConfirmed(nextConfirmation);
		userInputs.add(input);
    }
    
    public void addNextTextInput(String nextInput) {
    	Collection<UserInteractionBase> userInputs = getUserInteractions();
		FreeTextUserInteraction input = InteractionFactory.eINSTANCE.createFreeTextUserInteraction();
		input.setText(nextInput);
		userInputs.add(input);
    }
    
    public void addNextSingleSelection(final int nextSelection) {
    	Collection<UserInteractionBase> userInputs = getUserInteractions();
		MultipleChoiceSingleSelectionUserInteraction input = InteractionFactory.eINSTANCE.createMultipleChoiceSingleSelectionUserInteraction();
		input.setSelectedIndex(nextSelection);
		userInputs.add(input);
    }
    
    public void addNextMultiSelection(final int[] nextSelection) {
    	Collection<UserInteractionBase> userInputs = getUserInteractions();
		MultipleChoiceMultiSelectionUserInteraction input = InteractionFactory.eINSTANCE.createMultipleChoiceMultiSelectionUserInteraction();
		for (int selection : nextSelection) {
			input.getSelectedIndices().add(selection);
		}
		userInputs.add(input);
    }
    
    public void addNextUriSelection(final URI nextSelection) {
        /*this.uriQueue.clear();
        this.uriQueue.addAll(Arrays.asList(nextSelections));*/ // TODO DK: URI stuff
    }
	
	@Override
	public <T> T handleNothingPredefined(NormalUserInteractor<T> dialogBuilder) {
		throw new IllegalStateException("Missing predefined input");
	}
    
    @Override
    public boolean handleConfirmation(String message, NormalUserInteractor<Boolean> dialogBuilder) {
    	simulateUserThinktime();
    	ConfirmationUserInteraction predefinedInput = getMatchingInput(message, ConfirmationUserInteraction.class).iterator().next();
    	boolean confirmation = super.handleConfirmation(message, dialogBuilder);
    	interactor.getUserInputListener().onUserInteractionReceived(predefinedInput);
    	return confirmation;
    }
    
    @Override
    public String handleTextInput(String message, NormalUserInteractor<String> dialogBuilder) {
    	simulateUserThinktime();
    	FreeTextUserInteraction predefinedInput = getMatchingInput(message, FreeTextUserInteraction.class).iterator().next();
    	String text = super.handleTextInput(message, dialogBuilder);
    	interactor.getUserInputListener().onUserInteractionReceived(predefinedInput);
    	return text;
    }
    
    @Override
    public int handleSingleSelectionInput(String message, String[] choices, NormalUserInteractor<Integer> dialogBuilder) {
    	simulateUserThinktime();
    	MultipleChoiceSingleSelectionUserInteraction predefinedInput = getMatchingInput(message, MultipleChoiceSingleSelectionUserInteraction.class).iterator().next();
    	int selectedIndex = super.handleSingleSelectionInput(message, choices, dialogBuilder);
    	interactor.getUserInputListener().onUserInteractionReceived(predefinedInput);
    	return selectedIndex;
    }
    
    @Override
    public Collection<Integer> handleMultiSelectionInput(String message, String[] choices, NormalUserInteractor<Collection<Integer>> dialogBuilder) {
    	simulateUserThinktime();
    	MultipleChoiceSingleSelectionUserInteraction predefinedInput = getMatchingInput(message, MultipleChoiceSingleSelectionUserInteraction.class).iterator().next();
    	Collection<Integer> selectedIndices = super.handleMultiSelectionInput(message, choices, dialogBuilder);
    	interactor.getUserInputListener().onUserInteractionReceived(predefinedInput);
    	return selectedIndices;
    }
}
