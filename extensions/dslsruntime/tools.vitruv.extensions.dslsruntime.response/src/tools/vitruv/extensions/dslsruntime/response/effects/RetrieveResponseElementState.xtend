package tools.vitruv.extensions.dslsruntime.response.effects

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.command.TransformationResult

class RetrieveResponseElementState extends AbstractResponseElementState {
		
	new(EObject element, CorrespondenceModel correspondenceModel, TransformationResult transformationResult) {
		super(element, correspondenceModel, transformationResult);
		TuidManager.instance.registerObjectUnderModification(element);
	}
	
}