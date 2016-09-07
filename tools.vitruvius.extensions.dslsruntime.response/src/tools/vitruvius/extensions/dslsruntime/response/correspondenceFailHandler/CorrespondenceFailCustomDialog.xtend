package tools.vitruvius.extensions.dslsruntime.response.correspondenceFailHandler

import org.eclipse.emf.ecore.EObject
import tools.vitruvius.framework.userinteraction.UserInteracting
import tools.vitruvius.framework.userinteraction.UserInteractionType

class CorrespondenceFailCustomDialog extends AbstractCorrespondenceFailHandler {
	private final boolean abortEffect;
	private final String message;
	
	public new(boolean abortEffect, String message) {
		this.abortEffect = abortEffect;
		this.message = message;
	}
	
	override handle(Iterable<? extends EObject> foundObjects, EObject sourceElement, Class<?> expectedType, UserInteracting userInteracting) {
		logFail(foundObjects, sourceElement, expectedType);
		logger.debug("Show user dialog with message: " + message);
		userInteracting.showMessage(UserInteractionType.MODAL, message);
		return abortEffect;
	}
}