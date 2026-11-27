package tools.vitruv.change.interaction.types

import tools.vitruv.change.interaction.InteractionFactory
import tools.vitruv.change.interaction.MultipleChoiceMultiSelectionUserInteraction
import tools.vitruv.change.interaction.UserInteractionOptions.WindowModality
import tools.vitruv.change.interaction.InteractionResultProvider

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
		val userInput = InteractionFactory.eINSTANCE.createMultipleChoiceMultiSelectionUserInteraction()
		userInput.message = message
		userInput.choices.addAll(choices)
		userInput.selectedIndices.addAll(result)
		return userInput
	}

}
