package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.correspondenceFailHandler

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.Loggable
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandler
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