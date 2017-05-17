package tools.vitruv.extensions.dslsruntime.reactions.correspondenceFailHandler

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.userinteraction.UserInteracting

class CorrespondenceFailDoNothing extends AbstractCorrespondenceFailHandler {
	val boolean abortEffect

	new(boolean abortEffect) {
		this.abortEffect = abortEffect
	}

	override handle(Iterable<? extends EObject> foundObjects, EObject sourceElement, Class<?> expectedType,
		UserInteracting userInteracting) {
		logFail(foundObjects, sourceElement, expectedType)
		logger.debug("And nothing is done")
		abortEffect
	}
}
