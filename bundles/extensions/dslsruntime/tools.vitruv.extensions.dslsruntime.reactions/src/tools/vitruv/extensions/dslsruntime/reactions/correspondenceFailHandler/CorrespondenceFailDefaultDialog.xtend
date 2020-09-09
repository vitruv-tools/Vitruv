package tools.vitruv.extensions.dslsruntime.reactions.correspondenceFailHandler

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality

class CorrespondenceFailDefaultDialog extends AbstractCorrespondenceFailHandler {
	final boolean abortEffect;
	
	new(boolean abortEffect) {
		this.abortEffect = abortEffect;
	}
	
	override handle(Iterable<? extends EObject> foundObjects, EObject sourceElement, Class<?> expectedType, UserInteractor userInteractor) {
		logFail(foundObjects, sourceElement, expectedType);
		logger.debug("Show user dialog default message");
		val message = "There were (" + foundObjects.size + ") corresponding elements of type "
		    + expectedType.getSimpleName() + "although one was expected for: " + sourceElement
		userInteractor.notificationDialogBuilder.message(message).windowModality(WindowModality.MODAL).startInteraction();
		return abortEffect;
	}
}