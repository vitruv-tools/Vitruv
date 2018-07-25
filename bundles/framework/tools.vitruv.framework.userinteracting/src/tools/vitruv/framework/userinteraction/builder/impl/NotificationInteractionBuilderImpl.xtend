package tools.vitruv.framework.userinteraction.builder.impl

import tools.vitruv.framework.userinteraction.builder.NotificationInteractionBuilder
import tools.vitruv.framework.userinteraction.NotificationType
import tools.vitruv.framework.userinteraction.builder.NotificationInteractionBuilder.OptionalSteps
import tools.vitruv.framework.userinteraction.types.NotificationInteraction
import tools.vitruv.framework.userinteraction.types.InteractionFactory
import tools.vitruv.framework.userinteraction.UserInteractionListener

/**
 * Builder class for {@link NotificationInteraction}s.
 * Creates a dialog with a notification message and an icon depicting the severity.<br>
 * <br>
 * For further info on the rationale behind the ...InteractionBuilder implementation, see the {@link InteractionBuilder} javadoc.
 * @see NotificationInteractionBuilder
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
public class NotificationInteractionBuilderImpl extends BaseInteractionBuilder<Void, NotificationInteraction, OptionalSteps> implements NotificationInteractionBuilder, OptionalSteps {
	
	new(InteractionFactory interactionFactory, Iterable<UserInteractionListener> userInteractionListener) {
		super(interactionFactory, userInteractionListener)
	}

	override notificationType(NotificationType notificationType) {
		if (notificationType !== null) {
			interactionToBuild.notificationType = notificationType
		}
		return this
	}

	override def startInteraction() {
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
