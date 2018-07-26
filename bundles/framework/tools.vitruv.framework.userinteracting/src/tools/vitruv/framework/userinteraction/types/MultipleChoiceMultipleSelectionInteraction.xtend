package tools.vitruv.framework.userinteraction.types

import tools.vitruv.framework.change.interaction.impl.InteractionFactoryImpl;
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.InteractionResultProvider

/**
 * Implementation of an interaction providing a list of choices for the user to select multiple ones
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class MultipleChoiceMultipleSelectionInteraction extends MultipleChoiceSelectionInteraction<MultipleChoiceMultiSelectionUserInteraction> {

	protected new(InteractionResultProvider interactionResultProvider, WindowModality windowModality) {
		super(interactionResultProvider, windowModality)
	}

	override startInteraction() {
		val result = interactionResultProvider.
			getMultipleChoiceMultipleSelectionInteractionResult(windowModality, title, message, positiveButtonText,
				cancelButtonText, choices)
		val userInput = InteractionFactoryImpl.eINSTANCE.createMultipleChoiceMultiSelectionUserInteraction()
		userInput.message = message
		userInput.choices.addAll(choices)
		userInput.selectedIndices.addAll(result)
		return userInput
	}

}
