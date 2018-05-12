package tools.vitruv.extensions.dslsruntime.reactions.correspondenceFailHandler

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.userinteraction.WindowModality

class CorrespondenceFailCustomDialog extends AbstractCorrespondenceFailHandler {
	private final boolean abortEffect;
	private final String message;
	
	public new(boolean abortEffect, String message) {
		this.abortEffect = abortEffect;
		this.message = message;
	}
	
	override handle(Iterable<? extends EObject> foundObjects, EObject sourceElement, Class<?> expectedType, UserInteractor userInteractor) {
		logFail(foundObjects, sourceElement, expectedType);
		logger.debug("Show user dialog with message: " + message);
		userInteractor.notificationDialogBuilder.message(message).windowModality(WindowModality.MODAL).startInteraction();
		return abortEffect;
	}
}