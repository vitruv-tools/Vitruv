package tools.vitruv.extensions.dslsruntime.reactions.effects

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.correspondence.CorrespondenceModel

class CreateReactionElementState extends AbstractReactionElementState {
	
	new(EObject element, CorrespondenceModel correspondenceModel) {
		super(element, correspondenceModel)
	}
	
}