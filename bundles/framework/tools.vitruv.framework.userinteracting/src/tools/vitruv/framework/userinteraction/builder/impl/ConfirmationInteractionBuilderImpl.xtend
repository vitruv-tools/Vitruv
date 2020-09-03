package tools.vitruv.framework.userinteraction.builder.impl

import tools.vitruv.framework.userinteraction.builder.ConfirmationInteractionBuilder.OptionalSteps
import tools.vitruv.framework.userinteraction.UserInteractionListener
import tools.vitruv.framework.userinteraction.types.ConfirmationInteraction
import tools.vitruv.framework.userinteraction.types.InteractionFactory
import tools.vitruv.framework.userinteraction.builder.ConfirmationInteractionBuilder

/**
 * Builder class for {@link ConfirmationInteraction}s.
 * Creates a dialog with a question and buttons to give a positive or negative answer.<br>
 * <br>
 * For further info on the rationale behind the ...InteractionBuilder implementation, see the {@link InteractionBuilder} javadoc.
 * 
 * @see ConfirmationInteractionBuilder
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class ConfirmationInteractionBuilderImpl extends BaseInteractionBuilder<Boolean, ConfirmationInteraction, OptionalSteps> implements ConfirmationInteractionBuilder, OptionalSteps {
	new(InteractionFactory interactionFactory, Iterable<UserInteractionListener> userInteractionListener) {
		super(interactionFactory, userInteractionListener)
	}

	override startInteraction() {
		val result = interactionToBuild.startInteraction();
		notifyUserInputReceived(result);
		return result.confirmed;
	}

	override message(String message) {
		setMessage(message)
		return this
	}

	override createUserInteraction() {
		return interactionFactory.createConfirmationInteraction();
	}

	override protected getSelf() {
		return this;
	}

}
