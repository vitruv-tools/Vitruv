package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import de.uka.ipd.sdq.pcm.repository.OperationProvidedRole

class OperationProvidedRoleMappingTransformation extends EmptyEObjectMappingTransformation {
	
	override getClassOfMappedEObject() {
		return OperationProvidedRole;
	}
	
	override setCorrespondenceForFeatures() {
		//TODO:
	}
	
}