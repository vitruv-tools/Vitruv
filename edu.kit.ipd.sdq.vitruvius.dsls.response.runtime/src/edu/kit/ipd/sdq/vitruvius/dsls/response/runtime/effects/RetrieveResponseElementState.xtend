package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel

class RetrieveResponseElementState extends AbstractResponseElementState {
	private final TUID oldTUID;
		
	new(EObject element, CorrespondenceModel correspondenceInstance) {
		super(element, correspondenceInstance);
		this.oldTUID = correspondenceInstance.calculateTUIDFromEObject(element);
	}
	
	public override void updateTUID() {
		if (element != null && !delete) {
			correspondenceInstance.updateTUID(oldTUID, element);
		}
	}
	
}