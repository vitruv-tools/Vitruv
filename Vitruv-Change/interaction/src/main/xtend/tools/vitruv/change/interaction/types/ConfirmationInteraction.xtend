package tools.vitruv.change.interaction.types

import tools.vitruv.change.interaction.ConfirmationUserInteraction
import tools.vitruv.change.interaction.InteractionFactory
import tools.vitruv.change.interaction.UserInteractionOptions.WindowModality
import tools.vitruv.change.interaction.InteractionResultProvider

/**
 * An interaction for asking the user to provide a positive or negative answer to some kind of request.
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class ConfirmationInteraction extends BaseInteraction<ConfirmationUserInteraction> {
	static val DEFAULT_TITLE = "Please Confirm";
	static val DEFAULT_MESSAGE = "";

	protected new(InteractionResultProvider interactionResultProvider, WindowModality windowModality) {
		super(interactionResultProvider, windowModality, DEFAULT_TITLE, DEFAULT_MESSAGE)
	}

	override startInteraction() {
		val result = interactionResultProvider.getConfirmationInteractionResult(windowModality, title, message,
			positiveButtonText, negativeButtonText, cancelButtonText);
		var userInput = InteractionFactory.eINSTANCE.createConfirmationUserInteraction()
		userInput.message = message
		userInput.confirmed = result;
		return userInput;
	}

}
