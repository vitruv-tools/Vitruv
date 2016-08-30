package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.correspondenceFailHandler

import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteractionType

class CorrespondenceFailDefaultDialog extends AbstractCorrespondenceFailHandler {
	private final boolean abortEffect;
	
	public new(boolean abortEffect) {
		this.abortEffect = abortEffect;
	}
	
	override handle(Iterable<? extends EObject> foundObjects, EObject sourceElement, Class<?> expectedType, UserInteracting userInteracting) {
		logFail(foundObjects, sourceElement, expectedType);
		logger.debug("Show user dialog default message");
		userInteracting.showMessage(UserInteractionType.MODAL, "There were (" + foundObjects.size + 
			") corresponding elements of type " +	expectedType.getSimpleName() + "although one was expected for: " + sourceElement);
		return abortEffect;
	}
}