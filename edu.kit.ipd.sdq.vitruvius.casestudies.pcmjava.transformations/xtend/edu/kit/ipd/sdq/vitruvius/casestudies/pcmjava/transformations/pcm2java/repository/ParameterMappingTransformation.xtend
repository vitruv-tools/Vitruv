package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import de.uka.ipd.sdq.pcm.repository.Parameter
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.emftext.language.java.parameters.OrdinaryParameter
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.types.TypeReference

class ParameterMappingTransformation extends EmptyEObjectMappingTransformation {

	val private static Logger logger = Logger.getLogger(ParameterMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return Parameter
	}

	/**
	 * called when Parameter is created:
	 * Create similar JaMoPP parameter
	 */
	override createEObject(EObject eObject) {
		val Parameter parameter = eObject as Parameter
		var OrdinaryParameter jaMoPPParam = ParametersFactory.eINSTANCE.createOrdinaryParameter
		jaMoPPParam.setName(parameter.parameterName)
		val typeReferences = correspondenceInstance.getCorrespondingEObjectsByType(parameter.dataType__Parameter,
			TypeReference)
		if (null == typeReferences) {
			logger.warn(
				"No corresponding for data type " + parameter.dataType__Parameter +
					" found. Can not create correspondence for the parameter " + parameter)
			return #[]
		}
		jaMoPPParam.setTypeReference(typeReferences.get(0))
		return jaMoPPParam.toArray;
	}

	/**
	 * called when Parameter is removed:
	 * Get JaMoPP Parameter to remove
	 */
	override removeEObject(EObject eObject) {
		val ret = correspondenceInstance.getAllCorrespondences(eObject)
		correspondenceInstance.removeAllCorrespondences(eObject)
		return ret
	}

	/**
	 * Reacts to either a name change
	 */
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val Set<EObject> correspondingObjects = PCM2JaMoPPUtils.
			checkKeyAndCorrespondingObjects(affectedEObject, affectedAttribute, featureCorrespondenceMap,
				correspondenceInstance)
		if (correspondingObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		if (affectedAttribute.name.equals(PCMJaMoPPNamespace.PCM.PCM_PARAMETER_ATTRIBUTE_PARAMETER_NAME)) {
			val boolean markFilesOfChangedEObjectsAsFilesToSave = true
			return PCM2JaMoPPUtils.updateNameAttribute(correspondingObjects, newValue, affectedAttribute,
				featureCorrespondenceMap, correspondenceInstance, markFilesOfChangedEObjectsAsFilesToSave)
		}
	}

	/**
	 * Reacts to a data type change
	 */
	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		val Set<EObject> correspondingEObjects = PCM2JaMoPPUtils.
			checkKeyAndCorrespondingObjects(affectedEObject, affectedReference, featureCorrespondenceMap,
				correspondenceInstance)
		if (correspondingEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		if (affectedReference.name.equals(PCMJaMoPPNamespace.PCM.PCM_PARAMETER_ATTRIBUTE_DATA_TYPE)) {
			try {
				val TypeReference typeReference = correspondenceInstance.
					claimUniqueCorrespondingEObjectByType(newValue, TypeReference)
				val Set<org.emftext.language.java.parameters.Parameter> parameters = correspondenceInstance.
					getCorrespondingEObjectsByType(affectedEObject, org.emftext.language.java.parameters.Parameter)
				for (parameter : parameters) {
					parameter.setTypeReference(typeReference)
				}
				return TransformationUtils.createTransformationChangeResultForEObjectsToSave(affectedEObject.toArray)
			} catch (RuntimeException e) {
				logger.warn("Could not find correspondence for PCM data type " + oldValue + " . " + e)
			}
		}
		return TransformationUtils.createEmptyTransformationChangeResult
	}

	/**
	 * add type Correspondence
	 */
	//TODO: check whether it is possible to add correspondence also for parameter name
	override setCorrespondenceForFeatures() {
		val pcmDummyParam = RepositoryFactory.eINSTANCE.createParameter
		val jaMoPPDummyParam = ParametersFactory.eINSTANCE.createOrdinaryParameter
		val EStructuralFeature pcmDataTypeAttribute = TransformationUtils.
			getReferenceByNameFromEObject(PCMJaMoPPNamespace.PCM.PCM_PARAMETER_ATTRIBUTE_DATA_TYPE, pcmDummyParam);
		val jaMoPPTypeReference = TransformationUtils.getReferenceByNameFromEObject(
			PCMJaMoPPNamespace.JaMoPP.JAMOPP_PARAMETER_ATTRIBUTE_TYPE_REFERENCE, jaMoPPDummyParam)
		featureCorrespondenceMap.put(pcmDataTypeAttribute, jaMoPPTypeReference)
	}

}
