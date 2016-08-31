package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.transformations.repository

import com.google.common.collect.Sets
import edu.kit.ipd.sdq.vitruvius.framework.util.command.TransformationResult
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.packagemapping.javaimplementation.util.transformationexecutor.EmptyEObjectMappingTransformation
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.InterfaceMethod
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.RepositoryFactory

import static extension edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import edu.kit.ipd.sdq.vitruvius.casestudies.pcm.util.PCMNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.java.util.JaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.DataTypeCorrespondenceHelper
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.PCMJaMoPPUtils
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.EObjectUtil

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
				claimUniqueCorrespondingJaMoPPDataTypeReference(opSig.returnType__OperationSignature,
					correspondenceModel)
			ifMethod.typeReference = retTypeRef
		}
		return ifMethod.toList
	}

	/**
	 * Returns all null
	 * --> the deletion has do be done in the next step
	 */
	override removeEObject(EObject eObject) {
		return null
	}

	/**
	 * called when a parameter was added
	 */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingParameter) {
		val transformationResult = new TransformationResult
		if (newCorrespondingParameter.nullOrEmpty) {
			return transformationResult
		}
		val Set<InterfaceMethod> jaMoPPIfMethods = correspondenceModel.
			getCorrespondingEObjectsByType(newAffectedEObject, InterfaceMethod)
		if (jaMoPPIfMethods.nullOrEmpty) {
			logger.warn("No corresponding InterfaceMethod was found for OperationSignature: " + newAffectedEObject +
				" Returning empty transformation result")
			return transformationResult
		}
		val Parameter newParameter = newCorrespondingParameter.get(0) as Parameter
		val paramType = newParameter.typeReference
		for (interfaceMethod : jaMoPPIfMethods) {
			val oldTUID = correspondenceModel.calculateTUIDFromEObject(interfaceMethod)

			// add import if paramType is namespaceclassifier reference
			if (paramType instanceof NamespaceClassifierReference) {
				PCM2JaMoPPUtils.addImportToCompilationUnitOfClassifier(interfaceMethod.containingConcreteClassifier,
					paramType)
			}
			interfaceMethod.parameters.add(index, newParameter)
			correspondenceModel.createAndAddCorrespondence(newValue, newParameter)
			correspondenceModel.updateTUID(oldTUID, interfaceMethod)
		}
		transformationResult
	}

	/**
	 * called when a parameter was removed
	 */
	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		return PCMJaMoPPUtils.deleteNonRootEObjectInList(oldAffectedEObject, oldValue, correspondenceModel)
	}

	/**
	 * update the name attribute
	 */
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val transformationResult = new TransformationResult
		var Set<EObject> correspondingEObjects = PCM2JaMoPPUtils.checkKeyAndCorrespondingObjects(affectedEObject,
			affectedAttribute, featureCorrespondenceMap, correspondenceModel);
		if (correspondingEObjects.nullOrEmpty) {
			return transformationResult
		}
		val boolean saveFilesOfChangedEObjects = true;
		PCM2JaMoPPUtils.updateNameAttribute(correspondingEObjects, newValue, affectedAttribute,
			featureCorrespondenceMap, correspondenceModel, saveFilesOfChangedEObjects)
		// also update the name attribute of implementing SEFFs (if any)
		val opSig = affectedEObject as OperationSignature
		val opInterface = opSig.interface__OperationSignature
		// get implementing components
		val implementingComponents = Sets.newHashSet
		opInterface.repository__Interface.components__Repository.forEach [ comp |
			val opProvRoles = comp.providedRoles_InterfaceProvidingEntity.filter(OperationProvidedRole)
			opProvRoles.filter[it.providedInterface__OperationProvidedRole.id == opInterface.id].forEach [ opProRole |
				implementingComponents.add(opProRole.providingEntity_ProvidedRole)
			]
		]
		val basicComponents = implementingComponents.filter(BasicComponent)
		basicComponents.forEach[
			it.serviceEffectSpecifications__BasicComponent.forEach[
				val correspondingClassMethods = correspondenceModel.getCorrespondingEObjectsByType(it, ClassMethod)
				if(!correspondingClassMethods.nullOrEmpty){
					correspondingClassMethods.forEach[
						val oldTUID = correspondenceModel.calculateTUIDFromEObject(it)
						it.name = newValue.toString
						correspondenceModel.updateTUID(oldTUID, it)
					]
				}	
			]
		]
			

		transformationResult
	}

	/**
	 * called when the return type changed
	 */
	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		val transformationResult = new TransformationResult
		val Set<EObject> correspondingEObjects = PCM2JaMoPPUtils.checkKeyAndCorrespondingObjects(affectedEObject,
			affectedReference, featureCorrespondenceMap, correspondenceModel)
		if (newValue == oldValue) {
			// type not really changed
			return transformationResult
		}
		if (correspondingEObjects.nullOrEmpty || (false == newValue instanceof DataType)) {
			return transformationResult
		}
		val InterfaceMethod correspondingInterfaceMethod = correspondenceModel.
			getCorrespondingEObjectsByType(affectedEObject, InterfaceMethod).claimOne
		val TypeReference newTypeReference = DataTypeCorrespondenceHelper.
			claimUniqueCorrespondingJaMoPPDataTypeReference(newValue as DataType, correspondenceModel)
		val oldTUID = correspondenceModel.calculateTUIDFromEObject(correspondingInterfaceMethod)
		correspondingInterfaceMethod.typeReference = newTypeReference;
		correspondenceModel.updateTUID(oldTUID, correspondingInterfaceMethod)
		if (newTypeReference instanceof NamespaceClassifierReference) {
			PCM2JaMoPPUtils.addImportToCompilationUnitOfClassifier(
				correspondingInterfaceMethod.containingConcreteClassifier, newTypeReference)
		}
		return transformationResult
	}

	/**
	 * set correspondence for the name attribute
	 */
	override setCorrespondenceForFeatures() {
		PCM2JaMoPPUtils.addEntityName2NameCorrespondence(featureCorrespondenceMap)
		val OperationSignature pcmDummyOpSig = RepositoryFactory.eINSTANCE.createOperationSignature
		val InterfaceMethod jaMoPPDummyInterfaceMethod = MembersFactory.eINSTANCE.createInterfaceMethod
		val EReference pcmOpSigDataTypeReference = EObjectUtil.getReferenceByName(
			pcmDummyOpSig, PCMNamespace.PCM_OPERATION_SIGNATURE_RETURN_TYPE)
		val EReference jaMoPPInterfaceMethodTypeReference = EObjectUtil.getReferenceByName(
			jaMoPPDummyInterfaceMethod, JaMoPPNamespace.JAMOPP_REFERENCE_TYPE_REFERENCE)
		featureCorrespondenceMap.put(pcmOpSigDataTypeReference, jaMoPPInterfaceMethodTypeReference)
	}
}
