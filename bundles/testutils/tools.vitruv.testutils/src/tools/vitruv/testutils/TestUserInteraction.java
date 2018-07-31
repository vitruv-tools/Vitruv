package tools.vitruv.testutils;

import tools.vitruv.framework.change.interaction.ConfirmationUserInteraction;
import tools.vitruv.framework.change.interaction.FreeTextUserInteraction;
import tools.vitruv.framework.change.interaction.InteractionFactory;
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction;
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInteraction;
import tools.vitruv.framework.userinteraction.PredefinedInteractionResultProvider;

public class TestUserInteraction {
	private final PredefinedInteractionResultProvider interactionProvider;

	public TestUserInteraction(PredefinedInteractionResultProvider interactionProvider) {
		this.interactionProvider = interactionProvider;
	}

	public void addNextConfirmationInput(final boolean nextConfirmation) {
		ConfirmationUserInteraction input = InteractionFactory.eINSTANCE.createConfirmationUserInteraction();
		input.setConfirmed(nextConfirmation);
		interactionProvider.addUserInteractions(input);
	}

	public void addNextTextInput(String nextInput) {
		FreeTextUserInteraction input = InteractionFactory.eINSTANCE.createFreeTextUserInteraction();
		input.setText(nextInput);
		interactionProvider.addUserInteractions(input);
	}

	public void addNextSingleSelection(final int nextSelection) {
		MultipleChoiceSingleSelectionUserInteraction input = InteractionFactory.eINSTANCE
				.createMultipleChoiceSingleSelectionUserInteraction();
		input.setSelectedIndex(nextSelection);
		interactionProvider.addUserInteractions(input);
	}

	public void addNextMultiSelection(final int[] nextSelection) {
		MultipleChoiceMultiSelectionUserInteraction input = InteractionFactory.eINSTANCE
				.createMultipleChoiceMultiSelectionUserInteraction();
		for (int selection : nextSelection) {
			input.getSelectedIndices().add(selection);
		}
		interactionProvider.addUserInteractions(input);
	}
}
