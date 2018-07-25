package tools.vitruv.framework.userinteraction.types

import tools.vitruv.framework.userinteraction.NotificationType
import tools.vitruv.framework.change.interaction.NotificationUserInteraction
import tools.vitruv.framework.change.interaction.impl.InteractionFactoryImpl
import tools.vitruv.framework.userinteraction.WindowModality
import tools.vitruv.framework.userinteraction.InteractionResultProvider

/**
 * An interaction to notify the user about something.
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
public class NotificationInteraction extends BaseInteraction<NotificationUserInteraction> {
	private static val DEFAULT_TITLE = "NOTIFICATION";
	private static val DEFAULT_MESSAGE = "";
	private NotificationType notificationType;
	
	protected new(InteractionResultProvider interactionResultProvider, WindowModality windowModality) {
		super(interactionResultProvider, windowModality, DEFAULT_TITLE, DEFAULT_MESSAGE)
		this.notificationType = NotificationType.INFORMATION;
		setPositiveButtonText("Okay")
	}
	
	def NotificationType getNotificationType() { notificationType }
	def setNotificationType(NotificationType type) { this.notificationType = type }
	
	override startInteraction() {
		interactionResultProvider.getNotificationInteractionResult(windowModality, title, message, positiveButtonText, notificationType);
        val userInput = InteractionFactoryImpl.eINSTANCE.createNotificationUserInteraction()
        userInput.message = message
        return userInput;
	}
	
}
