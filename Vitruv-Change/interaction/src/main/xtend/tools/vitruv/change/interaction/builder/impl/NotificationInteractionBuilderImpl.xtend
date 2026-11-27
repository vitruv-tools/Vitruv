package tools.vitruv.change.interaction.builder.impl

import tools.vitruv.change.interaction.builder.NotificationInteractionBuilder
import tools.vitruv.change.interaction.UserInteractionOptions.NotificationType
import tools.vitruv.change.interaction.builder.NotificationInteractionBuilder.OptionalSteps
import tools.vitruv.change.interaction.types.NotificationInteraction
import tools.vitruv.change.interaction.types.InteractionFactory
import tools.vitruv.change.interaction.UserInteractionListener

/**
 * Builder class for {@link NotificationInteraction}s.
 * Creates a dialog with a notification message and an icon depicting the severity.<br>
 * <br>
 * For further info on the rationale behind the ...InteractionBuilder implementation, see the {@link tools.vitruv.change.interaction.builder.InteractionBuilder} javadoc.
 * @see NotificationInteractionBuilder
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class NotificationInteractionBuilderImpl extends BaseInteractionBuilder<Void, NotificationInteraction, OptionalSteps> implements NotificationInteractionBuilder, OptionalSteps {

	new(InteractionFactory interactionFactory, Iterable<UserInteractionListener> userInteractionListener) {
		super(interactionFactory, userInteractionListener)
	}

	override notificationType(NotificationType notificationType) {
		if (notificationType !== null) {
			interactionToBuild.notificationType = notificationType
		}
		return this
	}

	override startInteraction() {
		val result = interactionToBuild.startInteraction();
		notifyUserInputReceived(result);
		return null // notifications do not have any form of user input
	}

	override createUserInteraction() {
		return interactionFactory.createNotificationInteraction()
	}

	override protected getSelf() {
		return this;
	}

	override message(String message) {
		setMessage(message)
		return this
	}

}
