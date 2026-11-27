package tools.vitruv.change.interaction.types

import tools.vitruv.change.interaction.InteractionFactory
import tools.vitruv.change.interaction.MultipleChoiceSingleSelectionUserInteraction
import tools.vitruv.change.interaction.UserInteractionOptions.WindowModality
import tools.vitruv.change.interaction.InteractionResultProvider

/**
 * Implementation of an interaction providing a list of choices for the user to select a single one.
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class MultipleChoiceSingleSelectionInteraction extends MultipleChoiceSelectionInteraction<MultipleChoiceSingleSelectionUserInteraction> {

	protected new(InteractionResultProvider interactionResultProvider, WindowModality windowModality) {
		super(interactionResultProvider, windowModality)
	}

	override startInteraction() {
		val result = interactionResultProvider.
			getMultipleChoiceSingleSelectionInteractionResult(windowModality, title, message, positiveButtonText,
				cancelButtonText, choices)
		val userInput = InteractionFactory.eINSTANCE.createMultipleChoiceSingleSelectionUserInteraction()
		userInput.message = message
		userInput.choices.addAll(choices)
		userInput.selectedIndex = result
		return userInput
	}

}
