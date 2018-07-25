package tools.vitruv.framework.userinteraction.types

/**
 * A factory for creating interactions.
 * 
 * @author Heiko Klare
 */
interface InteractionFactory {
	abstract def ConfirmationInteraction createConfirmationInteraction();
	abstract def NotificationInteraction createNotificationInteraction();
	abstract def TextInputInteraction createTextInputInteraction();
	abstract def MultipleChoiceSingleSelectionInteraction createMultipleChoiceSingleSelectionInteraction();
	abstract def MultipleChoiceMultipleSelectionInteraction createMultipleChoiceMultipleSelectionInteraction();
}