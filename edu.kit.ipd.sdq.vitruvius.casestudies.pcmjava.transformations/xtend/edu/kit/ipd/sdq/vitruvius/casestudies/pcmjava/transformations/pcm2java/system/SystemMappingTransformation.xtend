package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.system

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.ComposedProvidingRequiringEntityMappingTransformation
import org.eclipse.emf.ecore.EObject
import org.palladiosimulator.pcm.system.System

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
			return 
		}
		PCMJaMoPPUtils.saveEObjects(newCorrespondingEObjects, blackboard, PCMJaMoPPUtils.getSourceModelVURI(newRootEObject))
		for (correspondingEObject : newCorrespondingEObjects) {
			blackboard.correspondenceInstance.createAndAddEObjectCorrespondence(newRootEObject, correspondingEObject)
		}
	}

	
}