package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import org.eclipse.emf.ecore.EObject
import java.util.Collections
import org.eclipse.emf.ecore.util.EcoreUtil
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.helper.CorrespondenceHelper
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState

class EffectCorrespondenceDeletion extends EffectCorrespondenceModification {
	private final boolean deleteFirst;
	private final boolean deleteSecond;
	
	new(EObject firstElement, boolean deleteFirst, EObject secondElement, boolean deleteSecond, ResponseExecutionState executionState) {
		super(firstElement, secondElement, executionState);
		this.deleteFirst = deleteFirst;
		this.deleteSecond = deleteSecond;
	}
	
	private def removeCorrespondence() {
		CorrespondenceHelper.removeCorrespondence(blackboard.getCorrespondenceInstance(), firstElement, null, secondElement, null);
	}
	
	private def void delete(EObject element) {
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
		if (deleteFirst) {
			delete(firstElement);
		}
		if (deleteSecond) {
			delete(secondElement);
		}
	}
		
	override public postProcess() {
		// Do nothing
	}
	
}