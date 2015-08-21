package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.ComposedProvidingRequiringEntityMappingTransformation
import org.eclipse.emf.ecore.EObject
import org.palladiosimulator.pcm.repository.CompositeComponent
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils

class CompositeComponentMappingTransformation extends ComposedProvidingRequiringEntityMappingTransformation {
	
	override getClassOfMappedEObject() {
		return CompositeComponent
	}
	
	override getParentPackage(EObject eObject) {
		if(eObject instanceof CompositeComponent){
			val compositeComponent = eObject as CompositeComponent
			val repository = compositeComponent.repository__RepositoryComponent
			return PCM2JaMoPPUtils.findCorrespondingPackageByName(repository.entityName, blackboard.correspondenceInstance, repository)
		}
		return null
	}
	
}