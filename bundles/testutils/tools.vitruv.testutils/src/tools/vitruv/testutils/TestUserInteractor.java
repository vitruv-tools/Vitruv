package tools.vitruv.testutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;

import tools.vitruv.framework.change.interaction.ConfirmationUserInput;
import tools.vitruv.framework.change.interaction.FreeTextUserInput;
import tools.vitruv.framework.change.interaction.InteractionFactory;
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInput;
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInput;
import tools.vitruv.framework.change.interaction.UserInputBase;
import tools.vitruv.framework.userinteraction.NotificationDialogBuilder;
import tools.vitruv.framework.userinteraction.impl.NormalUserInteracting;
import tools.vitruv.framework.userinteraction.impl.PredefinedInputInteractor;

/**
 * The {@link TestUserInteractor} can be used in tests to simulate UserInteracting. It has a queue
 * of next selections. If the queue is empty an {@link IllegalStateException} is thrown. It also allows to simulate the
 * thinking time for a user.
 *
 */
public class TestUserInteractor extends PredefinedInputInteractor {
	private static final Logger logger = Logger.getLogger(TestUserInteractor.class);
	private final Random random;
    private final int minWaittime;
    private final int maxWaittime;
    private final int waitTimeRange;
    
    public TestUserInteractor(final int minWaittime, final int maxWaittime) {
    	super(new ArrayList<UserInputBase>(), null);
        if (minWaittime > maxWaittime) {
            throw new RuntimeException(
                    "Configure min and max waittime properly: Min" + minWaittime + " Max: " + maxWaittime);
        }
        this.minWaittime = minWaittime;
        this.maxWaittime = maxWaittime;
        this.waitTimeRange = maxWaittime - minWaittime;
        this.random = new Random();
    }
	
	@Override
	public <T> T handleNothingPredefined(NormalUserInteracting<T> dialogBuilder) {
		throw new IllegalStateException("Missing predefined input"); // TODO DK: somehow parameterize the exception with the type of input missing?
	}
	
	public void addNextConfirmationInput(final boolean nextConfirmation) {
		Collection<UserInputBase> userInputs = getUserInputs();
		//userInputs.removeIf(input -> input.getClass() == ConfirmationUserInput.class); // TODO DK
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
    	//userInputs.removeIf(input -> input.getClass() == MultipleChoiceSingleSelectionUserInput.class);
		MultipleChoiceSingleSelectionUserInput input = InteractionFactory.eINSTANCE.createMultipleChoiceSingleSelectionUserInput();
		input.setSelectedIndex(nextSelection);
		userInputs.add(input);
    }
    
    public void addNextMultiSelection(final int[] nextSelection) {
    	Collection<UserInputBase> userInputs = getUserInputs();
    	//userInputs.removeIf(input -> input.getClass() == MultipleChoiceSingleSelectionUserInput.class);
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

    private void simulateUserThinktime() { // TODO DK
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
    public NotificationDialogBuilder getNotificationDialogBuilder() {
    	simulateUserThinktime(); // TODO DK: necessary?
    	return super.getNotificationDialogBuilder();
    }
}
