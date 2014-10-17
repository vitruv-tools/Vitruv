package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.system

import de.uka.ipd.sdq.pcm.core.composition.AssemblyConnector
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation

class AssemblyConnectorMappingTransformation extends EmptyEObjectMappingTransformation {
	
	override getClassOfMappedEObject() {
		return AssemblyConnector
	}
	
	override setCorrespondenceForFeatures() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}