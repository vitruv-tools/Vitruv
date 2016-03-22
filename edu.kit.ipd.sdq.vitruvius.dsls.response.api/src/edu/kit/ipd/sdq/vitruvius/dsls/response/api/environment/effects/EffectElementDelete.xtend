package edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.effects

import org.eclipse.emf.ecore.EObject
import java.util.Collections
import org.eclipse.emf.ecore.util.EcoreUtil
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseExecutionState
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseRuntimeHelper

class EffectElementDelete extends EffectElement {
	
	new(EObject element, EObject correspondenceSource, ResponseExecutionState executionState) {
		super(element, correspondenceSource, executionState)
	}
	
	private def removeCorrespondence() {
		ResponseRuntimeHelper.removeCorrespondence(blackboard.getCorrespondenceInstance(), correspondenceSource, null, element, null);
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

	override protected preProcess() {
		// TODO HK Is this correct? What if we delete an element and its correspondences and afterwards make it for a
		// contained element? Will there be an exception?
		removeCorrespondence();
		delete();
	}
		
	override protected postProcess() {
		// Do nothing
	}
	
	override protected updateTUIDs() {
		// Do nothing
	}
	
}