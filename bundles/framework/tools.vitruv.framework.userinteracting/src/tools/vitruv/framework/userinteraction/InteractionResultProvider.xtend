package tools.vitruv.framework.userinteraction

import tools.vitruv.framework.userinteraction.WindowModality
import tools.vitruv.framework.userinteraction.types.TextInputInteraction.InputValidator
import tools.vitruv.framework.userinteraction.NotificationType

/**
 * @author Heiko Klare
 */
interface InteractionResultProvider {
	def boolean getConfirmationInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText,
		String negativeDecisionText, String cancelDecisionText);
	def void getNotificationInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, NotificationType notificationType);
	def String getTextInputInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String cancelDecisionText, InputValidator inputValidator);
	def int getMultipleChoiceSingleSelectionInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices);
	def Iterable<Integer> getMultipleChoiceMultipleSelectionInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices);
	
	def InteractionResultProvider getDecoratedInteractionResultProvider();
}