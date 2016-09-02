package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.system

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.ComposedProvidingRequiringEntityMappingTransformation
import org.eclipse.emf.ecore.EObject
import org.palladiosimulator.pcm.system.System
import edu.kit.ipd.sdq.vitruvius.framework.util.command.TransformationResult
import org.emftext.language.java.containers.JavaRoot

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil.*
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.util.PCMJaMoPPUtils

class SystemMappingTransformation extends ComposedProvidingRequiringEntityMappingTransformation {

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
		val transformationResult = new TransformationResult
		if (newCorrespondingEObjects.nullOrEmpty) {
			return transformationResult
		}
		newCorrespondingEObjects.filter(JavaRoot).forEach [ newCorrespondingEObject |
			PCMJaMoPPUtils.addRootChangeToTransformationResult(newCorrespondingEObject, correspondenceModel,
				PCMJaMoPPUtils.getSourceModelVURI(newRootEObject), transformationResult)
		]
		for (correspondingEObject : newCorrespondingEObjects) {
			correspondenceModel.createAndAddCorrespondence(newRootEObject, correspondingEObject)
		}
		return transformationResult
	}

}