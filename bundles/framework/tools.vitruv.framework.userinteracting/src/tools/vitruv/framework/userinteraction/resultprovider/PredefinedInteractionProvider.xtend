package tools.vitruv.framework.userinteraction.resultprovider

import tools.vitruv.framework.userinteraction.WindowModality
import tools.vitruv.framework.userinteraction.NotificationType
import tools.vitruv.framework.userinteraction.types.TextInputInteraction.InputValidator
import tools.vitruv.framework.change.interaction.UserInteractionBase

/**
 * @author Heiko Klare
 */
class PredefinedInteractionProvider implements InteractionResultProvider {
	private final PredefinedInteractionMatcher predefinedInteractionMatcher;
	private final InteractionResultProvider fallbackResultProvider;
	
	new (InteractionResultProvider fallbackResultProvider) {
		this.predefinedInteractionMatcher = new PredefinedInteractionMatcher();
		this.fallbackResultProvider = fallbackResultProvider;
	}
	
	public def void addUserInteractions(UserInteractionBase... interactions) {
		interactions.forEach[predefinedInteractionMatcher.addInteraction(it)]
	}
	
	override getConfirmationInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String negativeDecisionText, String cancelDecisionText) {
		val reusableInput = predefinedInteractionMatcher.getConfirmationResult(message);
		if (reusableInput !== null) {
			return reusableInput
		} else if (fallbackResultProvider !== null) {
			return fallbackResultProvider.getConfirmationInteractionResult(windowModality, title, message, positiveDecisionText, negativeDecisionText, cancelDecisionText);
		} else {
			throw new IllegalStateException("No input given for confirmation " + title + ": " + message);
		}
	}
	
	override getNotificationInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, NotificationType notificationType) {
		val reusableInput = predefinedInteractionMatcher.getNotificationResult(message);
		if (reusableInput !== null) {
			return;
		} else if (fallbackResultProvider !== null) {
			fallbackResultProvider.getNotificationInteractionResult(windowModality, title, message, positiveDecisionText, notificationType);
		} else {
			//throw new IllegalStateException("No input given for notification " + title + ": " + message);
		}
	}
	
	override getTextInputInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String cancelDecisionText, InputValidator inputValidator) {
		val reusableInput = predefinedInteractionMatcher.getTextInputResult(message);
		if (reusableInput !== null) {
			return reusableInput;
		} else if (fallbackResultProvider !== null) {
			return fallbackResultProvider.getTextInputInteractionResult(windowModality, title, message, positiveDecisionText, cancelDecisionText, inputValidator);
		} else {
			throw new IllegalStateException("No input given for text input " + title + ": " + message);
		}
	}
	
	override getMultipleChoiceSingleSelectionInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices) {
		val reusableInput = predefinedInteractionMatcher.getSingleSelectionResult(message, choices);
		if (reusableInput !== null) {
			return reusableInput;
		} else if (fallbackResultProvider !== null) {
			return fallbackResultProvider.getMultipleChoiceSingleSelectionInteractionResult(windowModality, title, message, positiveDecisionText, cancelDecisionText, choices);
		} else {
			throw new IllegalStateException("No input given for single selection " + title + ": " + message);
		}
	}

	override getMultipleChoiceMultipleSelectionInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices) {
		val reusableInput = predefinedInteractionMatcher.getMultiSelectionResult(message, choices);
		if (reusableInput !== null) {
			return reusableInput;
		} else if (fallbackResultProvider !== null) {
			return fallbackResultProvider.getMultipleChoiceMultipleSelectionInteractionResult(windowModality, title, message, positiveDecisionText, cancelDecisionText, choices);
		} else {
			throw new IllegalStateException("No input given for multiple selection " + title + ": " + message);
		}
	}
	
}