package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import de.uka.ipd.sdq.pcm.repository.OperationRequiredRole
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation

class OperationRequiredRoleMappingTransformation extends EmptyEObjectMappingTransformation {
	
	override getClassOfMappedEObject() {
		return OperationRequiredRole
	}
	
	override setCorrespondenceForFeatures() {
		//TODO:
	}
	
}