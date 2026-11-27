package tools.vitruv.change.interaction.builder.impl

import tools.vitruv.change.interaction.builder.ConfirmationInteractionBuilder.OptionalSteps
import tools.vitruv.change.interaction.UserInteractionListener
import tools.vitruv.change.interaction.types.ConfirmationInteraction
import tools.vitruv.change.interaction.types.InteractionFactory
import tools.vitruv.change.interaction.builder.ConfirmationInteractionBuilder

/**
 * Builder class for {@link ConfirmationInteraction}s.
 * Creates a dialog with a question and buttons to give a positive or negative answer.<br>
 * <br>
 * For further info on the rationale behind the ...InteractionBuilder implementation, see the {@link tools.vitruv.change.interaction.builder.InteractionBuilder} javadoc.
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
