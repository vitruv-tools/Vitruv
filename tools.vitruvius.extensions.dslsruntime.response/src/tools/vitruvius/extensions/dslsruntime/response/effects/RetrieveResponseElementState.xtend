package tools.vitruvius.extensions.dslsruntime.response.effects

import org.eclipse.emf.ecore.EObject
import tools.vitruvius.framework.correspondence.CorrespondenceModel
import tools.vitruvius.framework.tuid.TUID

class RetrieveResponseElementState extends AbstractResponseElementState {
	private final TUID oldTUID;
		
	new(EObject element, CorrespondenceModel correspondenceModel) {
		super(element, correspondenceModel);
		this.oldTUID = correspondenceModel.calculateTUIDFromEObject(element);
	}
	
	public override void updateTUID() {
		if (element != null && !delete) {
			correspondenceModel.updateTUID(oldTUID, element);
		}
	}
	
}