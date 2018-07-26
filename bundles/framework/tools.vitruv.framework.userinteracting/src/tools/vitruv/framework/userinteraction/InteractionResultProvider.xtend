package tools.vitruv.framework.userinteraction

import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.UserInteractionOptions.NotificationType
import tools.vitruv.framework.userinteraction.UserInteractionOptions.InputValidator

/**
 * A provider for interaction results. Implementations can define how the result for a certain kind of input
 * can be generated. This can, for example, be a dialog or a predefined value.
 * 
 * @author Heiko Klare
 */
interface InteractionResultProvider {
	def boolean getConfirmationInteractionResult(WindowModality windowModality, String title, String message,
		String positiveDecisionText, String negativeDecisionText, String cancelDecisionText);

	def void getNotificationInteractionResult(WindowModality windowModality, String title, String message,
		String positiveDecisionText, NotificationType notificationType);

	def String getTextInputInteractionResult(WindowModality windowModality, String title, String message,
		String positiveDecisionText, String cancelDecisionText, InputValidator inputValidator);

	def int getMultipleChoiceSingleSelectionInteractionResult(WindowModality windowModality, String title,
		String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices);

	def Iterable<Integer> getMultipleChoiceMultipleSelectionInteractionResult(WindowModality windowModality,
		String title, String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices);

	def InteractionResultProvider getDecoratedInteractionResultProvider();
}
