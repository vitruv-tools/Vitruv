package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations

import edu.kit.ipd.sdq.vitruvius.framework.util.command.TransformationResult
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.util.transformationexecutor.EmptyEObjectMappingTransformation
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.parameters.Parameter
import org.palladiosimulator.pcm.repository.RepositoryFactory

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.util.java2pcm.JaMoPP2PCMUtils
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper

class ParameterMappingTransformation extends EmptyEObjectMappingTransformation {

	override getClassOfMappedEObject() {
		Parameter
	}

	override setCorrespondenceForFeatures() {
		JaMoPP2PCMUtils.addName2EntityNameCorrespondence(featureCorrespondenceMap)
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val transformationResult = new TransformationResult
		JaMoPP2PCMUtils.updateNameAsSingleValuedEAttribute(affectedEObject, affectedAttribute, oldValue, newValue,
			featureCorrespondenceMap, correspondenceModel, transformationResult)
		return transformationResult
	}

	/**
	 * called when a parameter has been created
	 */
	override createEObject(EObject eObject) {
		var jaMoPPParam = eObject as Parameter
		val pcmParameter = RepositoryFactory.eINSTANCE.createParameter
		pcmParameter.dataType__Parameter = TypeReferenceCorrespondenceHelper.
			getCorrespondingPCMDataTypeForTypeReference(jaMoPPParam.typeReference, correspondenceModel,
				userInteracting, null, jaMoPPParam.arrayDimension)
		pcmParameter.entityName = jaMoPPParam.name
		return pcmParameter.toList
	}

	/**
	 * called when a parameter type has been changed
	 */
	 override removeEObject(EObject eObject){
	 	return null 
	 }
}
