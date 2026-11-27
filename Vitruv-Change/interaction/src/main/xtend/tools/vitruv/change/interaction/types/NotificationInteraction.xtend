package tools.vitruv.change.interaction.types

import tools.vitruv.change.interaction.UserInteractionOptions.NotificationType
import tools.vitruv.change.interaction.NotificationUserInteraction
import tools.vitruv.change.interaction.InteractionFactory
import tools.vitruv.change.interaction.UserInteractionOptions.WindowModality
import tools.vitruv.change.interaction.InteractionResultProvider

/**
 * An interaction to notify the user about something.
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class NotificationInteraction extends BaseInteraction<NotificationUserInteraction> {
	static val DEFAULT_TITLE = "NOTIFICATION";
	static val DEFAULT_MESSAGE = "";
	NotificationType notificationType;

	protected new(InteractionResultProvider interactionResultProvider, WindowModality windowModality) {
		super(interactionResultProvider, windowModality, DEFAULT_TITLE, DEFAULT_MESSAGE)
		this.notificationType = NotificationType.INFORMATION;
		setPositiveButtonText("Okay")
	}

	def NotificationType getNotificationType() { notificationType }

	def setNotificationType(NotificationType type) { this.notificationType = type }

	override startInteraction() {
		interactionResultProvider.getNotificationInteractionResult(windowModality, title, message, positiveButtonText,
			notificationType);
		val userInput = InteractionFactory.eINSTANCE.createNotificationUserInteraction()
		userInput.message = message
		return userInput;
	}

}
