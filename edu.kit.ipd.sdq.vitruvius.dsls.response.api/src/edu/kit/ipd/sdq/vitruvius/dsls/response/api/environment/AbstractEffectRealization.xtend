package edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment

import org.eclipse.emf.ecore.util.EcoreUtil
import java.util.Collections
import org.eclipse.emf.ecore.EObject
import org.apache.log4j.Logger
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult

abstract class AbstractEffectRealization {
	protected val UserInteracting userInteracting;
	protected val Blackboard blackboard;
	protected val TransformationResult transformationResult;
	
	public new(UserInteracting userInteracting, Blackboard blackboard, TransformationResult transformationResult) {
		this.userInteracting = userInteracting;
		this.blackboard = blackboard;
		this.transformationResult = transformationResult;
	}
	
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