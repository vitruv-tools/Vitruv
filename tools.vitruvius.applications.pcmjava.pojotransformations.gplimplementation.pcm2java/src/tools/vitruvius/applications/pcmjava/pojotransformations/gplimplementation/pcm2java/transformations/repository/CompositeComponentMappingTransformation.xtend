package tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.repository

import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.ComposedProvidingRequiringEntityMappingTransformation
import org.eclipse.emf.ecore.EObject
import org.palladiosimulator.pcm.repository.CompositeComponent
import tools.vitruvius.applications.pcmjava.util.pcm2java.PCM2JaMoPPUtils

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