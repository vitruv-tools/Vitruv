package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState

class EffectElementRetrieve extends EffectElement {
	protected final EObject element;
	protected final EObject correspondenceSource;
	private final TUID oldTUID;
	private boolean updateTUID = true;
	
	new(EObject element, EObject correspondenceSource, ResponseExecutionState executionState) {
		super(executionState);
		this.oldTUID = blackboard.correspondenceInstance.calculateTUIDFromEObject(element);
		this.element = element;
		this.correspondenceSource = correspondenceSource;
	}
	
	private def void updateTUID() {
		if (element != null) {
			blackboard.correspondenceInstance.updateTUID(oldTUID, element);
		}
	}
	
	override public preProcess() {
		// Do nothing
	}
	
	override public postProcess() {
		// Do nothing
	}
	
	public def updateTUIDs() {
		if (updateTUID) 
			updateTUID();
	}
	
	public def disableTUIDUpdate() {
		updateTUID = false;
	}
	
}