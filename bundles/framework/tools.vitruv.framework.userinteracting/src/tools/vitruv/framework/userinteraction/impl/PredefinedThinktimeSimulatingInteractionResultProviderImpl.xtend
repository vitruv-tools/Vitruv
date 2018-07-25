package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.WindowModality
import org.apache.log4j.Logger
import java.util.Random
import tools.vitruv.framework.userinteraction.NotificationType
import tools.vitruv.framework.userinteraction.types.TextInputInteraction.InputValidator
import tools.vitruv.framework.userinteraction.InteractionResultProvider

/**
 * @author Heiko Klare
 */
public class PredefinedThinktimeSimulatingInteractionResultProviderImpl extends PredefinedInteractionResultProviderImpl {
	private static final Logger logger = Logger.getLogger(PredefinedThinktimeSimulatingInteractionResultProviderImpl);
	private final Random random;
	private final int minWaittime;
    private final int maxWaittime;
    private final int waitTimeRange;
    
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

	override getConfirmationInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String negativeDecisionText, String cancelDecisionText) {
		simulateUserThinktime()	
		super.getConfirmationInteractionResult(windowModality, title, message, positiveDecisionText, negativeDecisionText, cancelDecisionText)
	}
	
	override getNotificationInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, NotificationType notificationType) {
		simulateUserThinktime()
		super.getNotificationInteractionResult(windowModality, title, message, positiveDecisionText, notificationType)
	}
	
	override getTextInputInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String cancelDecisionText, InputValidator inputValidator) {
		simulateUserThinktime()
		super.getTextInputInteractionResult(windowModality, title, message, positiveDecisionText, cancelDecisionText, inputValidator)
	}
	
	override getMultipleChoiceSingleSelectionInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices) {
		simulateUserThinktime()
		super.getMultipleChoiceSingleSelectionInteractionResult(windowModality, title, message, positiveDecisionText, cancelDecisionText, choices)
	}
	
	override getMultipleChoiceMultipleSelectionInteractionResult(WindowModality windowModality, String title, String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices) {
		simulateUserThinktime()
		super.getMultipleChoiceMultipleSelectionInteractionResult(windowModality, title, message, positiveDecisionText, cancelDecisionText, choices)
	}
	
}