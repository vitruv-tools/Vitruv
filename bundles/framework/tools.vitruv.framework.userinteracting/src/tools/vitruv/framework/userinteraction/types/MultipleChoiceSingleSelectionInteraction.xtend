package tools.vitruv.framework.userinteraction.types

import tools.vitruv.framework.change.interaction.impl.InteractionFactoryImpl;
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInteraction
import tools.vitruv.framework.userinteraction.WindowModality
import tools.vitruv.framework.userinteraction.resultprovider.InteractionResultProvider

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
		val userInput = InteractionFactoryImpl.eINSTANCE.createMultipleChoiceSingleSelectionUserInteraction()
		userInput.message = message
		userInput.choices.addAll(choices)
		userInput.selectedIndex = result
		return userInput
	}

}
