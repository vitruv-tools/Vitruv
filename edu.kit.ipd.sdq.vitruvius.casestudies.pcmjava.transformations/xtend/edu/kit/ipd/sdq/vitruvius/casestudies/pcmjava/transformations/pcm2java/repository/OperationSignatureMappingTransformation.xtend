package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.members.InterfaceMethod
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory

class OperationSignatureMappingTransformation extends EmptyEObjectMappingTransformation {

	private static val Logger logger = Logger.getLogger(OperationSignatureMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return OperationSignature
	}

	/**
	 * creates an IntetfaceMethod for each signature that is created
	 * if the interface method already has a return type or parameters they have to be added 
	 * separately in later transformations 
	 */
	override createEObject(EObject eObject) {
		val OperationSignature opSig = eObject as OperationSignature
		if (null != opSig.returnType__OperationSignature || !opSig.parameters__OperationSignature.nullOrEmpty) {
			logger.debug("Operation signature either has return type or parameters directly after creating it.")
		}
		var InterfaceMethod ifMethod = MembersFactory.eINSTANCE.createInterfaceMethod
		ifMethod.setName(opSig.entityName)
		if (null == opSig.returnType__OperationSignature) {
			ifMethod.setTypeReference(TypesFactory.eINSTANCE.createVoid)
		} else {
			val retTypeRef = DataTypeCorrespondenceHelper.
				claimUniqueCorrespondingJaMoPPDataTypeReference(opSig.returnType__OperationSignature, correspondenceInstance)
			ifMethod.typeReference = retTypeRef
		}
		return ifMethod.toArray
	}

	/**
	 * Returns all corresponding eObjects that are corresponding to the operation signature,
	 * e.g the InterfaceMethod --> the deletion has do be done in the next step
	 */
	override removeEObject(EObject eObject) {
		val correspondingEObjects = correspondenceInstance.getAllCorrespondingEObjects(eObject)
		correspondenceInstance.removeAllCorrespondences(eObject)
		return correspondingEObjects

	}

	/**
	 * called when a parameter was added
	 */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingParameter) {
		if (newCorrespondingParameter.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val Set<InterfaceMethod> jaMoPPIfMethods = correspondenceInstance.
			getCorrespondingEObjectsByType(newAffectedEObject, InterfaceMethod)
		if (jaMoPPIfMethods.nullOrEmpty) {
			logger.warn(
				"No corresponding InterfaceMethod was found for OperationSignature: " + newAffectedEObject +
					" Returning empty transformation result")
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val parrentCorrespondence = correspondenceInstance.getAllCorrespondences(newAffectedEObject)
		val tcr = TransformationUtils.createTransformationChangeResultForEObjectsToSave(jaMoPPIfMethods)
		val Parameter newParameter = newCorrespondingParameter.get(0) as Parameter
		val paramType = newParameter.typeReference
		for (interfaceMethod : jaMoPPIfMethods) {
			val oldTUID = correspondenceInstance.calculateTUIDFromEObject(interfaceMethod)

			// add import if paramType is namespaceclassifier reference
			if (paramType instanceof NamespaceClassifierReference) {
				PCM2JaMoPPUtils.addImportToCompilationUnitOfClassifier(interfaceMethod.containingConcreteClassifier,
					paramType)
			}
			interfaceMethod.parameters.add(index, newParameter)
			tcr.addNewCorrespondence(correspondenceInstance, newValue, newParameter, parrentCorrespondence.get(0))
			tcr.addCorrespondenceToUpdate(correspondenceInstance, oldTUID, interfaceMethod, null)
		}
		return tcr
	}

	/**
	 * called when a parameter was removed
	 */
	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		val jaMoPPIfMethod = correspondenceInstance.getAllCorrespondingEObjects(newAffectedEObject)
		for (correspondingEObject : oldCorrespondingEObjectsToDelete) {
			EcoreUtil.delete(correspondingEObject, true)
		}
		return TransformationUtils.createTransformationChangeResultForEObjectsToSave(jaMoPPIfMethod)
	}

	/**
	 * update the name attribute
	 */
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		var Set<EObject> correspondingEObjects = PCM2JaMoPPUtils.
			checkKeyAndCorrespondingObjects(affectedEObject, affectedAttribute, featureCorrespondenceMap,
				correspondenceInstance);
		if (correspondingEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult;
		}
		val boolean markFilesOfChangedEObjectsAsFilesToSave = true;
		return PCM2JaMoPPUtils.updateNameAttribute(correspondingEObjects, newValue, affectedAttribute,
			featureCorrespondenceMap, correspondenceInstance, markFilesOfChangedEObjectsAsFilesToSave)
	}

	/**
	 * called when the return type changed
	 */
	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		val Set<EObject> correspondingEObjects = PCM2JaMoPPUtils.
			checkKeyAndCorrespondingObjects(affectedEObject, affectedReference, featureCorrespondenceMap,
				correspondenceInstance)
		if(newValue == oldValue){
			//type not really changed
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		if (correspondingEObjects.nullOrEmpty || (false == newValue instanceof DataType)) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val InterfaceMethod correspondingInterfaceMethod = correspondenceInstance.
			claimUniqueCorrespondingEObjectByType(affectedEObject, InterfaceMethod)
		val TypeReference newTypeReference = DataTypeCorrespondenceHelper.
			claimUniqueCorrespondingJaMoPPDataTypeReference(newValue as DataType, correspondenceInstance)
		val oldTUID = correspondenceInstance.calculateTUIDFromEObject(correspondingInterfaceMethod)
		correspondingInterfaceMethod.typeReference = newTypeReference;
		val tcr = TransformationUtils.
			createTransformationChangeResultForEObjectsToSave(correspondingInterfaceMethod.toArray)
		tcr.addCorrespondenceToUpdate(correspondenceInstance, oldTUID, correspondingInterfaceMethod, null)
		if(newTypeReference instanceof NamespaceClassifierReference){
			PCM2JaMoPPUtils.addImportToCompilationUnitOfClassifier(correspondingInterfaceMethod.containingConcreteClassifier, newTypeReference)
		}
		return tcr
	}

	/**
	 * set correspondence for the name attribute
	 */
	override setCorrespondenceForFeatures() {
		PCM2JaMoPPUtils.addEntityName2NameCorrespondence(featureCorrespondenceMap)
		val OperationSignature pcmDummyOpSig = RepositoryFactory.eINSTANCE.createOperationSignature
		val InterfaceMethod jaMoPPDummyInterfaceMethod = MembersFactory.eINSTANCE.createInterfaceMethod
		val EReference pcmOpSigDataTypeReference = TransformationUtils.
			getReferenceByNameFromEObject(PCMJaMoPPNamespace.PCM.PCM_OPERATION_SIGNATURE_RETURN_TYPE, pcmDummyOpSig)
		val EReference jaMoPPInterfaceMethodTypeReference = TransformationUtils.
			getReferenceByNameFromEObject(PCMJaMoPPNamespace.JaMoPP.JAMOPP_REFERENCE_TYPE_REFERENCE,
				jaMoPPDummyInterfaceMethod)
		featureCorrespondenceMap.put(pcmOpSigDataTypeReference, jaMoPPInterfaceMethodTypeReference)
	}
}
