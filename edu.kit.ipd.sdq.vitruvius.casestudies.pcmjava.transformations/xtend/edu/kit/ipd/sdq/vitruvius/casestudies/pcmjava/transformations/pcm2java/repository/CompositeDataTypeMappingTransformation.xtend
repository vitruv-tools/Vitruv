package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import de.uka.ipd.sdq.pcm.repository.CompositeDataType
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation

/**
 * Maps a composite DataType to a class in the data types package.
 * 
 */
class CompositeDataTypeMappingTransformation extends EmptyEObjectMappingTransformation{
	
	override getClassOfMappedEObject() {
		return CompositeDataType
	}
	
	override setCorrespondenceForFeatures() {
		
	}
	
}