package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import org.apache.log4j.Logger
import java.util.Random
import tools.vitruv.framework.userinteraction.UserInteractionOptions.NotificationType
import tools.vitruv.framework.userinteraction.UserInteractionOptions.InputValidator
import tools.vitruv.framework.userinteraction.InteractionResultProvider

/**
 * A predefined result provider that also simulates a think time on each request.
 * @see PredefinedInteractionResultProviderImpl
 * 
 * @author Heiko Klare
 */
class PredefinedThinktimeSimulatingInteractionResultProviderImpl extends PredefinedInteractionResultProviderImpl {
	static final Logger logger = Logger.getLogger(PredefinedThinktimeSimulatingInteractionResultProviderImpl);
	final Random random;
	final int minWaittime;
	final int maxWaittime;
	final int waitTimeRange;

	/**
	 * {@inheritDoc}
	 * @param minWaittime - the minimum time to wait in milliseconds 
	 * @param maxWaittime - the maximum time to wait in milliseconds
	 */
	new(InteractionResultProvider interactionProviderFallback, int minWaittime, int maxWaittime) {
		super(interactionProviderFallback)
		if (minWaittime > maxWaittime) {
			throw new RuntimeException(
				"Configure min and max waittime properly: Min" + minWaittime + " Max: " + maxWaittime);
		}
		this.minWaittime = minWaittime;
		this.maxWaittime = maxWaittime;
		this.waitTimeRange = maxWaittime - minWaittime;
		this.random = new Random();
	}

	private def void simulateUserThinktime() {
		if (-1 < this.maxWaittime) {
			val int currentWaittime = this.random.nextInt(this.waitTimeRange + 1) + this.minWaittime;
			try {
				Thread.sleep(currentWaittime);
			} catch (InterruptedException e) {
				logger.trace("User think time simulation thread interrupted: " + e, e);
			}
		}
	}

	override getConfirmationInteractionResult(WindowModality windowModality, String title, String message,
		String positiveDecisionText, String negativeDecisionText, String cancelDecisionText) {
		simulateUserThinktime()
		super.getConfirmationInteractionResult(windowModality, title, message, positiveDecisionText,
			negativeDecisionText, cancelDecisionText)
	}

	override getNotificationInteractionResult(WindowModality windowModality, String title, String message,
		String positiveDecisionText, NotificationType notificationType) {
		simulateUserThinktime()
		super.getNotificationInteractionResult(windowModality, title, message, positiveDecisionText, notificationType)
	}

	override getTextInputInteractionResult(WindowModality windowModality, String title, String message,
		String positiveDecisionText, String cancelDecisionText, InputValidator inputValidator) {
		simulateUserThinktime()
		super.getTextInputInteractionResult(windowModality, title, message, positiveDecisionText, cancelDecisionText,
			inputValidator)
	}

	override getMultipleChoiceSingleSelectionInteractionResult(WindowModality windowModality, String title,
		String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices) {
		simulateUserThinktime()
		super.getMultipleChoiceSingleSelectionInteractionResult(windowModality, title, message, positiveDecisionText,
			cancelDecisionText, choices)
	}

	override getMultipleChoiceMultipleSelectionInteractionResult(WindowModality windowModality, String title,
		String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices) {
		simulateUserThinktime()
		super.getMultipleChoiceMultipleSelectionInteractionResult(windowModality, title, message, positiveDecisionText,
			cancelDecisionText, choices)
	}

}
