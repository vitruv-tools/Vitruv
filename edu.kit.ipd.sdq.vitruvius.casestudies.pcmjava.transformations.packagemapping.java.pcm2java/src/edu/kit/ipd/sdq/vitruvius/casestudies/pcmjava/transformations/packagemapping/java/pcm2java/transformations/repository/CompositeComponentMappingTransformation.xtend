package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.transformations.repository

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.transformations.ComposedProvidingRequiringEntityMappingTransformation
import org.eclipse.emf.ecore.EObject
import org.palladiosimulator.pcm.repository.CompositeComponent
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.PCM2JaMoPPUtils

class CompositeComponentMappingTransformation extends ComposedProvidingRequiringEntityMappingTransformation {
	
	override getClassOfMappedEObject() {
		return CompositeComponent
	}
	
	override getParentPackage(EObject eObject) {
		if(eObject instanceof CompositeComponent){
			val compositeComponent = eObject as CompositeComponent
			val repository = compositeComponent.repository__RepositoryComponent
			return PCM2JaMoPPUtils.findCorrespondingPackageByName(repository.entityName, correspondenceModel, repository)
		}
		return null
	}
	
}