package tools.vitruvius.extensions.dslsruntime.response.correspondenceFailHandler

import org.eclipse.emf.ecore.EObject
import tools.vitruvius.framework.userinteraction.UserInteracting

class CorrespondenceFailDoNothing extends AbstractCorrespondenceFailHandler {
	private final boolean abortEffect;
	
	public new(boolean abortEffect) {
		this.abortEffect = abortEffect;
	}
	
	override handle(Iterable<? extends EObject> foundObjects, EObject sourceElement, Class<?> expectedType, UserInteracting userInteracting) {
		logFail(foundObjects, sourceElement, expectedType);
		logger.debug("And nothing ist done");
		return abortEffect;
	}
}