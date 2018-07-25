package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.UserInteractionOptions.NotificationType
import tools.vitruv.framework.userinteraction.UserInteractionOptions.InputValidator
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.userinteraction.InteractionResultProvider
import tools.vitruv.framework.userinteraction.PredefinedInteractionResultProvider

/**
 * An interaction result provider using predefined inputs, given using the 
 * {@link PredefinedInteractionResultProvider#addUserInteractions(UserInteractionBase...) addUserInteractions(UserInteractionBase...)}
 * method. The fallback result provider specified in the constructor is used, whenever no matching predefined input for a 
 * request is found.
 * @author Heiko Klare
 */
public class PredefinedInteractionResultProviderImpl extends PredefinedInteractionResultProvider {
	private final PredefinedInteractionMatcher predefinedInteractionMatcher;
	
	/**
	 * Instantiates a new provider.
	 * The <code>fallbackResultProvider</code> is used whenever no matching input for a request was predefined.
	 * If no fallback provider is specified, an exception is thrown when no matching predefined input is found.
	 * 
	 * @param fallbackResultProvider - the result provider to use when not matching predefined input is found
	 */
	new (InteractionResultProvider fallbackResultProvider) {
		super(fallbackResultProvider)
		this.predefinedInteractionMatcher = new PredefinedInteractionMatcher();
	}
	
	public override void addUserInteractions(UserInteractionBase... interactions) {
		interactions.forEach[predefinedInteractionMatcher.addInteraction(it)]
	}
	
	override getConfirmationInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String negativeDecisionText, String cancelDecisionText) {
		val reusableInput = predefinedInteractionMatcher.getConfirmationResult(message);
		if (reusableInput !== null) {
			return reusableInput
		} else if (decoratedInteractionResultProvider !== null) {
			return decoratedInteractionResultProvider.getConfirmationInteractionResult(windowModality, title, message, positiveDecisionText, negativeDecisionText, cancelDecisionText);
		} else {
			throw new IllegalStateException("No input given for confirmation " + title + ": " + message);
		}
	}
	
	override getNotificationInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, NotificationType notificationType) {
		val reusableInput = predefinedInteractionMatcher.getNotificationResult(message);
		if (reusableInput !== null) {
			return;
		} else if (decoratedInteractionResultProvider !== null) {
			decoratedInteractionResultProvider.getNotificationInteractionResult(windowModality, title, message, positiveDecisionText, notificationType);
		} else {
			//throw new IllegalStateException("No input given for notification " + title + ": " + message);
		}
	}
	
	override getTextInputInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String cancelDecisionText, InputValidator inputValidator) {
		val reusableInput = predefinedInteractionMatcher.getTextInputResult(message);
		if (reusableInput !== null) {
			return reusableInput;
		} else if (decoratedInteractionResultProvider !== null) {
			return decoratedInteractionResultProvider.getTextInputInteractionResult(windowModality, title, message, positiveDecisionText, cancelDecisionText, inputValidator);
		} else {
			throw new IllegalStateException("No input given for text input " + title + ": " + message);
		}
	}
	
	override getMultipleChoiceSingleSelectionInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices) {
		val reusableInput = predefinedInteractionMatcher.getSingleSelectionResult(message, choices);
		if (reusableInput !== null) {
			return reusableInput;
		} else if (decoratedInteractionResultProvider !== null) {
			return decoratedInteractionResultProvider.getMultipleChoiceSingleSelectionInteractionResult(windowModality, title, message, positiveDecisionText, cancelDecisionText, choices);
		} else {
			throw new IllegalStateException("No input given for single selection " + title + ": " + message);
		}
	}

	override getMultipleChoiceMultipleSelectionInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices) {
		val reusableInput = predefinedInteractionMatcher.getMultiSelectionResult(message, choices);
		if (reusableInput !== null) {
			return reusableInput;
		} else if (decoratedInteractionResultProvider !== null) {
			return decoratedInteractionResultProvider.getMultipleChoiceMultipleSelectionInteractionResult(windowModality, title, message, positiveDecisionText, cancelDecisionText, choices);
		} else {
			throw new IllegalStateException("No input given for multiple selection " + title + ": " + message);
		}
	}
	
}