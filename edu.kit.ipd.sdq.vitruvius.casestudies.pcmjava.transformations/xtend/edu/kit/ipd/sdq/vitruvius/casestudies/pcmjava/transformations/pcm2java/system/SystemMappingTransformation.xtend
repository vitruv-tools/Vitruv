package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.system

import de.uka.ipd.sdq.pcm.system.System
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.ComposedProvidingRequiringEntityMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.eclipse.emf.ecore.EObject

class SystemMappingTransformation extends ComposedProvidingRequiringEntityMappingTransformation{
	
	/**
	 * called when a system has been created.
	 * system has no parent package --> return null
	 */
	override getParentPackage(EObject eObject) {
		return null
	}
	
	override getClassOfMappedEObject() {
		return System
	}
	
	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects) {
		if (newCorrespondingEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val transResult = TransformationUtils.
			createTransformationChangeResultForNewRootEObjects(newCorrespondingEObjects)
		for (correspondingEObject : newCorrespondingEObjects) {
			transResult.addNewCorrespondence(correspondenceInstance, newRootEObject, correspondingEObject, null)
		}
		return transResult 
	}

	
}