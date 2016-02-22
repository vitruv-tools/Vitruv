package edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment

import edu.kit.ipd.sdq.vitruvius.dsls.response.api.interfaces.IResponseRealization
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import java.util.Collections
import org.eclipse.emf.ecore.util.EcoreUtil

abstract class AbstractResponseRealization implements IResponseRealization {
	protected abstract def Logger getLogger();
	
	protected def void deleteElement(EObject element) {
		if (element.eContainer() == null) {
			if (element.eResource() != null) {
				logger.debug("Deleting root object: " + element);
				element.eResource().delete(Collections.EMPTY_MAP);
			} else {
				logger.warn("The element to delete was already removed: " + element);
			}
		} else {
			logger.debug("Removing non-root object: " + element);
			EcoreUtil.remove(element);
		}
	}
}