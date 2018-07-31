package tools.vitruv.framework.userinteraction.types

import tools.vitruv.framework.change.interaction.ConfirmationUserInteraction
import tools.vitruv.framework.change.interaction.impl.InteractionFactoryImpl
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.InteractionResultProvider

/**
 * An interaction for asking the user to provide a positive or negative answer to some kind of request.
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class ConfirmationInteraction extends BaseInteraction<ConfirmationUserInteraction> {
	private static val DEFAULT_TITLE = "Please Confirm";
	private static val DEFAULT_MESSAGE = "";

	protected new(InteractionResultProvider interactionResultProvider, WindowModality windowModality) {
		super(interactionResultProvider, windowModality, DEFAULT_TITLE, DEFAULT_MESSAGE)
	}

	override startInteraction() {
		val result = interactionResultProvider.getConfirmationInteractionResult(windowModality, title, message,
			positiveButtonText, negativeButtonText, cancelButtonText);
		var userInput = InteractionFactoryImpl.eINSTANCE.createConfirmationUserInteraction()
		userInput.message = message
		userInput.confirmed = result;
		return userInput;
	}

}
