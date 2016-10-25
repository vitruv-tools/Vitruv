package tools.vitruv.extensions.dslsruntime.reactions.effects

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager

class RetrieveReactionElementState extends AbstractReactionElementState {
		
	new(EObject element, CorrespondenceModel correspondenceModel) {
		super(element, correspondenceModel);
		TuidManager.instance.registerObjectUnderModification(element);
	}
	
}