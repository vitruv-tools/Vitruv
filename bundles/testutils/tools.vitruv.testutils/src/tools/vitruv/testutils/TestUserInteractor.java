package tools.vitruv.testutils;

import tools.vitruv.framework.change.interaction.ConfirmationUserInteraction;
import tools.vitruv.framework.change.interaction.FreeTextUserInteraction;
import tools.vitruv.framework.change.interaction.InteractionFactory;
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction;
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInteraction;
import tools.vitruv.framework.userinteraction.impl.PredefinedThinktimeUserInputInteractorImpl;

/**
 * The {@link TestUserInteractor} can be used in tests to simulate UserInteractor. It has a queue
 * of next selections. If the queue is empty an {@link IllegalStateException} is thrown. It also allows to simulate the
 * thinking time for a user.
 *
 */
public class TestUserInteractor extends PredefinedThinktimeUserInputInteractorImpl {
    public TestUserInteractor(final int minWaittime, final int maxWaittime) {
    	super(null, minWaittime, maxWaittime);
    }
    
    public TestUserInteractor() {
        this(-1, -1);
    }
    
    public void addNextConfirmationInput(final boolean nextConfirmation) {
    	ConfirmationUserInteraction input = InteractionFactory.eINSTANCE.createConfirmationUserInteraction();
		input.setConfirmed(nextConfirmation);
		addUserInteraction(input);
    }
    
    public void addNextTextInput(String nextInput) {
		FreeTextUserInteraction input = InteractionFactory.eINSTANCE.createFreeTextUserInteraction();
		input.setText(nextInput);
		addUserInteraction(input);
    }
    
    public void addNextSingleSelection(final int nextSelection) {
    	MultipleChoiceSingleSelectionUserInteraction input = InteractionFactory.eINSTANCE.createMultipleChoiceSingleSelectionUserInteraction();
		input.setSelectedIndex(nextSelection);
		addUserInteraction(input);
    }
    
    public void addNextMultiSelection(final int[] nextSelection) {
    	MultipleChoiceMultiSelectionUserInteraction input = InteractionFactory.eINSTANCE.createMultipleChoiceMultiSelectionUserInteraction();
		for (int selection : nextSelection) {
			input.getSelectedIndices().add(selection);
		}
		addUserInteraction(input);
    }
    
}
