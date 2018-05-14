package tools.vitruv.testutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.PlatformUI;

import tools.vitruv.framework.change.interaction.ConfirmationUserInput;
import tools.vitruv.framework.change.interaction.FreeTextUserInput;
import tools.vitruv.framework.change.interaction.InteractionFactory;
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInput;
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInput;
import tools.vitruv.framework.change.interaction.UserInputBase;
import tools.vitruv.framework.userinteraction.impl.NormalUserInteractor;
import tools.vitruv.framework.userinteraction.impl.PredefinedInputHandlerImpl;
import tools.vitruv.framework.userinteraction.PredefinedInputHandler;
import tools.vitruv.framework.userinteraction.UserInputListener;
import tools.vitruv.framework.userinteraction.impl.PredefinedInputInteractor;

/**
 * The {@link TestUserInteractor} can be used in tests to simulate UserInteractor. It has a queue
 * of next selections. If the queue is empty an {@link IllegalStateException} is thrown. It also allows to simulate the
 * thinking time for a user.
 *
 */
public class TestUserInteractor extends PredefinedInputInteractor {
	private final PredefinedTestInputHandler predefinedInputHandler;
	private UserInputListener userInputListener;
    
    public TestUserInteractor(final int minWaittime, final int maxWaittime) {
    	super(new ArrayList<UserInputBase>(), null, PlatformUI.getWorkbench().getDisplay());
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
	public void registerUserInputListener(UserInputListener listener) {
		this.userInputListener = listener;
	}
	
	public UserInputListener getUserInputListener() {
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
		Collection<UserInputBase> userInputs = getUserInputs();
		ConfirmationUserInput input = InteractionFactory.eINSTANCE.createConfirmationUserInput();
		input.setConfirmed(nextConfirmation);
		userInputs.add(input);
    }
    
    public void addNextTextInput(String nextInput) {
    	Collection<UserInputBase> userInputs = getUserInputs();
		FreeTextUserInput input = InteractionFactory.eINSTANCE.createFreeTextUserInput();
		input.setText(nextInput);
		userInputs.add(input);
    }
    
    public void addNextSingleSelection(final int nextSelection) {
    	Collection<UserInputBase> userInputs = getUserInputs();
		MultipleChoiceSingleSelectionUserInput input = InteractionFactory.eINSTANCE.createMultipleChoiceSingleSelectionUserInput();
		input.setSelectedIndex(nextSelection);
		userInputs.add(input);
    }
    
    public void addNextMultiSelection(final int[] nextSelection) {
    	Collection<UserInputBase> userInputs = getUserInputs();
		MultipleChoiceMultiSelectionUserInput input = InteractionFactory.eINSTANCE.createMultipleChoiceMultiSelectionUserInput();
		for (int selection : nextSelection) {
			input.getSelectedIndices().add(selection);
		}
		userInputs.add(input);
    }
    
    public void addNextUriSelection(final URI nextSelection) {
        /*this.uriQueue.clear();
        this.uriQueue.addAll(Arrays.asList(nextSelections));*/ // TODO DK
    }
	
	@Override
	public <T> T handleNothingPredefined(NormalUserInteractor<T> dialogBuilder) {
		throw new IllegalStateException("Missing predefined input"); // TODO DK: somehow parameterize the exception with the type of input missing?
	}
    
    @Override
    public boolean handleConfirmation(String message, NormalUserInteractor<Boolean> dialogBuilder) {
    	simulateUserThinktime();
    	ConfirmationUserInput predefinedInput = getMatchingInput(message, ConfirmationUserInput.class).iterator().next();
    	boolean confirmation = super.handleConfirmation(message, dialogBuilder);
    	interactor.getUserInputListener().onUserInputReceived(predefinedInput);
    	return confirmation;
    }
    
    @Override
    public String handleTextInput(String message, NormalUserInteractor<String> dialogBuilder) {
    	simulateUserThinktime();
    	FreeTextUserInput predefinedInput = getMatchingInput(message, FreeTextUserInput.class).iterator().next();
    	String text = super.handleTextInput(message, dialogBuilder);
    	interactor.getUserInputListener().onUserInputReceived(predefinedInput);
    	return text;
    }
    
    @Override
    public int handleSingleSelectionInput(String message, String[] choices, NormalUserInteractor<Integer> dialogBuilder) {
    	simulateUserThinktime();
    	MultipleChoiceSingleSelectionUserInput predefinedInput = getMatchingInput(message, MultipleChoiceSingleSelectionUserInput.class).iterator().next();
    	int selectedIndex = super.handleSingleSelectionInput(message, choices, dialogBuilder);
    	interactor.getUserInputListener().onUserInputReceived(predefinedInput);
    	return selectedIndex;
    }
    
    @Override
    public Collection<Integer> handleMultiSelectionInput(String message, String[] choices, NormalUserInteractor<Collection<Integer>> dialogBuilder) {
    	simulateUserThinktime();
    	MultipleChoiceSingleSelectionUserInput predefinedInput = getMatchingInput(message, MultipleChoiceSingleSelectionUserInput.class).iterator().next();
    	Collection<Integer> selectedIndices = super.handleMultiSelectionInput(message, choices, dialogBuilder);
    	interactor.getUserInputListener().onUserInputReceived(predefinedInput);
    	return selectedIndices;
    }
}
