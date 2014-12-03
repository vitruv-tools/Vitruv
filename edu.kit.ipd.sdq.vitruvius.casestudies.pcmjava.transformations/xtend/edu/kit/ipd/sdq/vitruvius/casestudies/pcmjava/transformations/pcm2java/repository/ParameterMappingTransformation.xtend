package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import de.uka.ipd.sdq.pcm.repository.DataType
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
		val TypeReference typeReference = DataTypeCorrespondenceHelper.
			claimUniqueCorrespondingJaMoPPDataTypeReference(parameter.dataType__Parameter, correspondenceInstance)
		if (null == typeReference) {
			logger.warn(
				"No corresponding for data type " + parameter.dataType__Parameter +
					" found. Can not create correspondence for the parameter " + parameter)
			return #[]
		}
		jaMoPPParam.setTypeReference(typeReference)
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
		if (correspondingObjects.nullOrEmpty || correspondingObjects.filter(typeof(org.emftext.language.java.parameters.Parameter)).nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		if (affectedAttribute.name.equals(PCMJaMoPPNamespace.PCM.PCM_ATTRIBUTE_ENTITY_NAME)) {
			val boolean markFilesOfChangedEObjectsAsFilesToSave = true
			val tcr = PCM2JaMoPPUtils.updateNameAttribute(correspondingObjects, newValue, affectedAttribute,
				featureCorrespondenceMap, correspondenceInstance, markFilesOfChangedEObjectsAsFilesToSave)
			return tcr
		}
		return TransformationUtils.createEmptyTransformationChangeResult
	}

	/**
	 * Reacts to a data type change
	 */
	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		val Set<EObject> correspondingEObjects = PCM2JaMoPPUtils.
			checkKeyAndCorrespondingObjects(affectedEObject, affectedReference, featureCorrespondenceMap,
				correspondenceInstance)
		if (correspondingEObjects.nullOrEmpty ||
			correspondingEObjects.filter(typeof(org.emftext.language.java.parameters.Parameter)).nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val correspondingParameter = correspondingEObjects.filter(typeof(org.emftext.language.java.parameters.Parameter)).
			get(0)
		if (affectedReference.name.equals(PCMJaMoPPNamespace.PCM.PCM_PARAMETER_ATTRIBUTE_DATA_TYPE) &&
			newValue instanceof DataType) {
			try {
				val TypeReference typeReference = DataTypeCorrespondenceHelper.
					claimUniqueCorrespondingJaMoPPDataTypeReference(newValue as DataType, correspondenceInstance)
				correspondingParameter.setTypeReference(typeReference)
				val oldTUID = correspondenceInstance.calculateTUIDFromEObject(correspondingParameter)
				val tcr = TransformationUtils.createTransformationChangeResultForEObjectsToSave(correspondingParameter.toArray)
				tcr.addCorrespondenceToUpdate(correspondenceInstance, oldTUID, correspondingParameter, null)
				return tcr
			} catch (RuntimeException e) {
				logger.warn("Could not find correspondence for PCM data type " + oldValue + " . " + e)
			}
		}
		return TransformationUtils.createEmptyTransformationChangeResult
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
			PCMJaMoPPNamespace.JaMoPP.JAMOPP_PARAMETER_ATTRIBUTE_TYPE_REFERENCE, jaMoPPDummyParam)
		val EStructuralFeature pcmParameterNameAttribute = TransformationUtils.getAttributeByNameFromEObject(PCMJaMoPPNamespace.PCM.PCM_ATTRIBUTE_ENTITY_NAME, pcmDummyParam)
		val EStructuralFeature jaMoPPParammeterNameAttribute = TransformationUtils.getAttributeByNameFromEObject(PCMJaMoPPNamespace.JaMoPP.JAMOPP_ATTRIBUTE_NAME, jaMoPPDummyParam)
		featureCorrespondenceMap.put(pcmDataTypeAttribute, jaMoPPTypeReference)
		featureCorrespondenceMap.put(pcmParameterNameAttribute, jaMoPPParammeterNameAttribute)
	}

}
