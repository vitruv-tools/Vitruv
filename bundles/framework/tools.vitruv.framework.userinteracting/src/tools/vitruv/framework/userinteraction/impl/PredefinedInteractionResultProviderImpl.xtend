package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.UserInteractionOptions.NotificationType
import tools.vitruv.framework.userinteraction.UserInteractionOptions.InputValidator
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.userinteraction.InteractionResultProvider
import tools.vitruv.framework.userinteraction.PredefinedInteractionResultProvider
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

/**
 * An interaction result provider using predefined inputs, given using the 
 * {@link PredefinedInteractionResultProvider#addUserInteractions(UserInteractionBase...) addUserInteractions(UserInteractionBase...)}
 * method. The fallback result provider specified in the constructor is used, whenever no matching predefined input for a 
 * request is found.
 * @author Heiko Klare
 */
 @FinalFieldsConstructor
class PredefinedInteractionResultProviderImpl implements PredefinedInteractionResultProvider {
	val InteractionResultProvider fallback 
	val PredefinedInteractionMatcher predefinedInteractionMatcher = new PredefinedInteractionMatcher();

	override void addUserInteractions(UserInteractionBase... interactions) {
		interactions.forEach[predefinedInteractionMatcher.addInteraction(it)]
	}

	override getConfirmationInteractionResult(WindowModality windowModality, String title, String message,
		String positiveDecisionText, String negativeDecisionText, String cancelDecisionText) {
		var result = predefinedInteractionMatcher.getConfirmationResult(message)
		if (result === null && fallback !== null) {
			result = fallback.getConfirmationInteractionResult(windowModality, title, message, positiveDecisionText,
				negativeDecisionText, cancelDecisionText)
		}
		return result.ifNullThrow [
			"No input given for confirmation:" + printInteraction(title, message)
		]
	}

	override getNotificationInteractionResult(WindowModality windowModality, String title, String message,
		String positiveDecisionText, NotificationType notificationType) {
		val result = predefinedInteractionMatcher.getNotificationResult(message)
		if (result === null) {
			fallback?.getNotificationInteractionResult(windowModality, title, message, positiveDecisionText, notificationType)
		}
	}

	override getTextInputInteractionResult(WindowModality windowModality, String title, String message,
		String positiveDecisionText, String cancelDecisionText, InputValidator inputValidator) {
		predefinedInteractionMatcher.getTextInputResult(message)
			?: fallback?.getTextInputInteractionResult(windowModality, title, message, positiveDecisionText,
				cancelDecisionText, inputValidator)
			.ifNullThrow [
				"No input given for text input: " + printInteraction(title, message)
			]
	}

	override getMultipleChoiceSingleSelectionInteractionResult(WindowModality windowModality, String title,
		String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices) {
		var result = predefinedInteractionMatcher.getSingleSelectionResult(message, choices)
		if (result === null && fallback !== null) {
			result = fallback.getMultipleChoiceSingleSelectionInteractionResult(windowModality, title, message,
				positiveDecisionText, cancelDecisionText, choices)
		}
		return result.ifNullThrow [
			'No input given for single selection:' + printSelection(title, message, choices)
		]
	}

	override getMultipleChoiceMultipleSelectionInteractionResult(WindowModality windowModality, String title,
		String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices) {
		predefinedInteractionMatcher.getMultiSelectionResult(message, choices)
			?: fallback?.getMultipleChoiceMultipleSelectionInteractionResult(windowModality, title, message,
				positiveDecisionText, cancelDecisionText, choices)
			.ifNullThrow [
				'No input given for multiple selection:' + printSelection(title, message, choices)
			]
	}

	private def printInteraction(String title, String message) {
		System.lineSeparator + '''«title»: «message»'''
	}

	private def printSelection(String title, String message, Iterable<String> choices) {
		printInteraction(title, message) + System.lineSeparator +
			'''«FOR c : choices SEPARATOR System.lineSeparator»  «'\u2219' /* bullet point */» «c»«ENDFOR»'''
	}

	private def <T> T ifNullThrow(T value, () => String messageProducer) {
		if (value === null) {
			throw new IllegalStateException(messageProducer.apply())
		} else {
			return value
		}
	}
}
