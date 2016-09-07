package tools.vitruvius.extensions.dslsruntime.response.correspondenceFailHandler

import tools.vitruvius.extensions.dslsruntime.response.structure.Loggable
import tools.vitruvius.extensions.dslsruntime.response.CorrespondenceFailHandler
import org.eclipse.emf.ecore.EObject

abstract class AbstractCorrespondenceFailHandler extends Loggable implements CorrespondenceFailHandler {
	def logFail(Iterable<? extends EObject> foundObjects, EObject sourceElement, Class<?> expectedType) {
		logger.debug("There were (" + foundObjects.size + ") corresponding elements of type " +
				expectedType.getSimpleName() + " for: " + sourceElement)
		for (obj : foundObjects) {
			logger.debug("    " + obj);
		}
	}
}