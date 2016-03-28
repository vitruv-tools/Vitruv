package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import org.eclipse.emf.ecore.EObject
import java.util.Collections
import org.eclipse.emf.ecore.util.EcoreUtil
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.helper.CorrespondenceHelper
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState

class EffectElementDelete extends EffectElement {
	
	new(EObject element, EObject correspondenceSource, ResponseExecutionState executionState) {
		super(element, correspondenceSource, executionState)
	}
	
	private def removeCorrespondence() {
		CorrespondenceHelper.removeCorrespondence(blackboard.getCorrespondenceInstance(), correspondenceSource, null, element, null);
	}
	
	private def void delete() {
		if (element == null) {
			return;
		}
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

	override public preProcess() {
		removeCorrespondence();
		delete();
	}
		
	override public postProcess() {
		// Do nothing
	}
	
	override public updateTUIDs() {
		// Do nothing
	}
	
}