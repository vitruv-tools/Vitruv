package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.DataTypeCorrespondenceHelper
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
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
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.RepositoryFactory

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*

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
		val TypeReference typeReference = DataTypeCorrespondenceHelper.
			claimUniqueCorrespondingJaMoPPDataTypeReference(parameter.dataType__Parameter, blackboard.correspondenceInstance)
		if (null == typeReference) {
			logger.warn(
				"No corresponding for data type " + parameter.dataType__Parameter +
					" found. Can not create correspondence for the parameter " + parameter)
			return #[]
		}
		jaMoPPParam.setTypeReference(typeReference)
		return jaMoPPParam.toList;
	}

	/**
	 * called when Parameter is removed:
	 */
	override removeEObject(EObject eObject) {
		null
	}

	/**
	 * Reacts to either a name change
	 */
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val transformationResult = new TransformationResult
		val Set<EObject> correspondingObjects = PCM2JaMoPPUtils.
			checkKeyAndCorrespondingObjects(affectedEObject, affectedAttribute, featureCorrespondenceMap,
				blackboard.correspondenceInstance)
		if (correspondingObjects.nullOrEmpty || correspondingObjects.filter(typeof(org.emftext.language.java.parameters.Parameter)).nullOrEmpty) {
			return transformationResult
		}
		if (affectedAttribute.name.equals(PCMJaMoPPNamespace.PCM.PCM_ATTRIBUTE_ENTITY_NAME)) {
			val boolean saveFilesOfChangedEObjects = true
			PCM2JaMoPPUtils.updateNameAttribute(correspondingObjects, newValue, affectedAttribute,
				featureCorrespondenceMap, blackboard.correspondenceInstance, saveFilesOfChangedEObjects)
		}
		return transformationResult
	}

	/**
	 * Reacts to a data type change
	 */
	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		val transformationResult = new TransformationResult
		val Set<EObject> correspondingEObjects = PCM2JaMoPPUtils.
			checkKeyAndCorrespondingObjects(affectedEObject, affectedReference, featureCorrespondenceMap,
				blackboard.correspondenceInstance)
		if (correspondingEObjects.nullOrEmpty ||
			correspondingEObjects.filter(typeof(org.emftext.language.java.parameters.Parameter)).nullOrEmpty) {
			return transformationResult
		}
		val correspondingParameter = correspondingEObjects.filter(typeof(org.emftext.language.java.parameters.Parameter)).
			get(0)
		if (affectedReference.name.equals(PCMJaMoPPNamespace.PCM.PCM_PARAMETER_ATTRIBUTE_DATA_TYPE) &&
			newValue instanceof DataType) {
			try {
				val TypeReference typeReference = DataTypeCorrespondenceHelper.
					claimUniqueCorrespondingJaMoPPDataTypeReference(newValue as DataType, blackboard.correspondenceInstance)
				correspondingParameter.setTypeReference(typeReference)
				val oldTUID = blackboard.correspondenceInstance.calculateTUIDFromEObject(correspondingParameter)
				blackboard.correspondenceInstance.updateTUID(oldTUID, correspondingParameter)
			} catch (RuntimeException e) {
				logger.warn("Could not find correspondence for PCM data type " + oldValue + " . " + e)
			}
		}
		transformationResult
	}

	/**
	 * add type Correspondence
	 */
	override setCorrespondenceForFeatures() {
		val pcmDummyParam = RepositoryFactory.eINSTANCE.createParameter
		val jaMoPPDummyParam = ParametersFactory.eINSTANCE.createOrdinaryParameter
		val EStructuralFeature pcmDataTypeAttribute = TransformationUtils.
			getReferenceByNameFromEObject(PCMJaMoPPNamespace.PCM.PCM_PARAMETER_ATTRIBUTE_DATA_TYPE, pcmDummyParam);
		val jaMoPPTypeReference = TransformationUtils.getReferenceByNameFromEObject(
			PCMJaMoPPNamespace.JaMoPP.JAMOPP_REFERENCE_TYPE_REFERENCE, jaMoPPDummyParam)
		val EStructuralFeature pcmParameterNameAttribute = TransformationUtils.getAttributeByNameFromEObject(PCMJaMoPPNamespace.PCM.PCM_ATTRIBUTE_ENTITY_NAME, pcmDummyParam)
		val EStructuralFeature jaMoPPParammeterNameAttribute = TransformationUtils.getAttributeByNameFromEObject(PCMJaMoPPNamespace.JaMoPP.JAMOPP_ATTRIBUTE_NAME, jaMoPPDummyParam)
		featureCorrespondenceMap.put(pcmDataTypeAttribute, jaMoPPTypeReference)
		featureCorrespondenceMap.put(pcmParameterNameAttribute, jaMoPPParammeterNameAttribute)
	}

}
