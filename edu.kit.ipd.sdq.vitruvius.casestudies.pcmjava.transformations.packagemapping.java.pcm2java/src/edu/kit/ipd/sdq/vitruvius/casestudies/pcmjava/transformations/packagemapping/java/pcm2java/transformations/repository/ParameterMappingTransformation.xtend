package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.transformations.repository

import edu.kit.ipd.sdq.vitruvius.framework.command.TransformationResult
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.packagemapping.javaimplementation.util.transformationexecutor.EmptyEObjectMappingTransformation
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
import edu.kit.ipd.sdq.vitruvius.casestudies.pcm.util.PCMNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.java.util.JaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.DataTypeCorrespondenceHelper
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.EObjectUtil

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
			claimUniqueCorrespondingJaMoPPDataTypeReference(parameter.dataType__Parameter, correspondenceModel)
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
				correspondenceModel)
		if (correspondingObjects.nullOrEmpty || correspondingObjects.filter(typeof(org.emftext.language.java.parameters.Parameter)).nullOrEmpty) {
			return transformationResult
		}
		if (affectedAttribute.name.equals(PCMNamespace.PCM_ATTRIBUTE_ENTITY_NAME)) {
			val boolean saveFilesOfChangedEObjects = true
			PCM2JaMoPPUtils.updateNameAttribute(correspondingObjects, newValue, affectedAttribute,
				featureCorrespondenceMap, correspondenceModel, saveFilesOfChangedEObjects)
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
				correspondenceModel)
		if (correspondingEObjects.nullOrEmpty ||
			correspondingEObjects.filter(typeof(org.emftext.language.java.parameters.Parameter)).nullOrEmpty) {
			return transformationResult
		}
		val correspondingParameter = correspondingEObjects.filter(typeof(org.emftext.language.java.parameters.Parameter)).
			get(0)
		if (affectedReference.name.equals(PCMNamespace.PCM_PARAMETER_ATTRIBUTE_DATA_TYPE) &&
			newValue instanceof DataType) {
			try {
				val TypeReference typeReference = DataTypeCorrespondenceHelper.
					claimUniqueCorrespondingJaMoPPDataTypeReference(newValue as DataType, correspondenceModel)
				correspondingParameter.setTypeReference(typeReference)
				val oldTUID = correspondenceModel.calculateTUIDFromEObject(correspondingParameter)
				correspondenceModel.updateTUID(oldTUID, correspondingParameter)
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
		val EStructuralFeature pcmDataTypeAttribute = EObjectUtil.
			getReferenceByName(pcmDummyParam, PCMNamespace.PCM_PARAMETER_ATTRIBUTE_DATA_TYPE);
		val jaMoPPTypeReference = EObjectUtil.getReferenceByName(
			jaMoPPDummyParam, JaMoPPNamespace.JAMOPP_REFERENCE_TYPE_REFERENCE)
		val EStructuralFeature pcmParameterNameAttribute = EObjectUtil.getAttributeByName(pcmDummyParam, PCMNamespace.PCM_ATTRIBUTE_ENTITY_NAME)
		val EStructuralFeature jaMoPPParammeterNameAttribute = EObjectUtil.getAttributeByName(jaMoPPDummyParam, JaMoPPNamespace.JAMOPP_ATTRIBUTE_NAME)
		featureCorrespondenceMap.put(pcmDataTypeAttribute, jaMoPPTypeReference)
		featureCorrespondenceMap.put(pcmParameterNameAttribute, jaMoPPParammeterNameAttribute)
	}

}
