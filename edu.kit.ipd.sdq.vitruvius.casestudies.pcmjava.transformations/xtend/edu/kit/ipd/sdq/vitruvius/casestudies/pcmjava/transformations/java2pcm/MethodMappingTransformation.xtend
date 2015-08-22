package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.members.Method
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypedElement
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory

class MethodMappingTransformation extends EmptyEObjectMappingTransformation {

	override getClassOfMappedEObject() {
		return Method
	}

	override setCorrespondenceForFeatures() {
		JaMoPP2PCMUtils.addName2EntityNameCorrespondence(featureCorrespondenceMap)
	}

	/**
	 * called when a Method has been added:
	 * creates a corresponding OperationInterface if the method is in an interface and 
	 * the interface has corresponding OperationInterface(s)
	 */
	override createEObject(EObject eObject) {
		val interfaceMethod = eObject as Method
		val interfaceClassifier = interfaceMethod.containingConcreteClassifier
		val operationInterfaces = blackboard.correspondenceInstance.getCorrespondingEObjectsByType(interfaceClassifier,
			OperationInterface)
		if (!operationInterfaces.nullOrEmpty) {
			for (opInterface : operationInterfaces) {
				userInteracting.showMessage(UserInteractionType.MODELESS,
					"Creating an OperationSignature in OperationInterface  " + opInterface.entityName +
						System.getProperty("line.separator") + " for method " + interfaceMethod.name)
				var OperationSignature opSig = RepositoryFactory.eINSTANCE.createOperationSignature
				opSig.entityName = interfaceMethod.name
				opSig.interface__OperationSignature = opInterface
				return opSig.toArray
			}
		}
		return null
	}

	override removeEObject(EObject eObject) {
		val correspondingObjects = blackboard.correspondenceInstance.getAllCorrespondingEObjects(eObject)
		return correspondingObjects
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		JaMoPP2PCMUtils.updateNameAsSingleValuedEAttribute(affectedEObject, affectedAttribute, oldValue, newValue,
			featureCorrespondenceMap, blackboard)
	}

	/**
	 * called when the return type has been changed
	 */
	override replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue) {
		if (oldAffectedEObject instanceof Method &&
			affectedReference.name.equals(PCMJaMoPPNamespace.JaMoPP.JAMOPP_REFERENCE_TYPE_REFERENCE) &&
			newValue instanceof TypeReference) {
			val correspondingPCMSignatures = blackboard.correspondenceInstance.
				getCorrespondingEObjectsByType(oldAffectedEObject, OperationSignature)
			if (correspondingPCMSignatures.nullOrEmpty) {
				return
			}
			for (correspondingSignature : correspondingPCMSignatures) {
				val Repository repo = correspondingSignature.interface__OperationSignature.repository__Interface
				val oldTUID = blackboard.correspondenceInstance.calculateTUIDFromEObject(correspondingSignature)
				val DataType newReturnValue = TypeReferenceCorrespondenceHelper.
					getCorrespondingPCMDataTypeForTypeReference(newValue as TypeReference,
						blackboard.correspondenceInstance, userInteracting, repo)
				correspondingSignature.returnType__OperationSignature = newReturnValue
				PCMJaMoPPUtils.saveNonRootEObject(correspondingSignature)

				// guess this is not necessary since the id stay the same
				blackboard.correspondenceInstance.update(oldTUID, correspondingSignature)
			}
		}
	}

	/**
	 *  called when a parameter has been added
	 *  If the type of the parameter is a component interface or another 
	 *  component an OperationRrovidedRole is added 
	 */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		if (!newCorrespondingEObjects.nullOrEmpty) {
			val operationSignatues = blackboard.correspondenceInstance.
				getCorrespondingEObjectsByType(oldAffectedEObject, OperationSignature)
			val pcmParameters = newCorrespondingEObjects.filter(typeof(Parameter))
			if (!operationSignatues.nullOrEmpty) {
				for (opSig : operationSignatues) {
					for (pcmParameter : pcmParameters) {
						if (null == pcmParameter.dataType__Parameter.repository__DataType) {
							pcmParameter.dataType__Parameter.repository__DataType = opSig.interface__OperationSignature.
								repository__Interface
						}
						pcmParameter.operationSignature__Parameter = opSig
					}
				}
			}
		}

		JaMoPP2PCMUtils.createNewCorrespondingEObjects(newValue, newCorrespondingEObjects, blackboard)
		if (newValue instanceof TypedElement) {
			val newCorrespondingOperationProvidedRoles = JaMoPP2PCMUtils.
				checkAndAddOperationRequiredRole(newValue as TypedElement, blackboard.correspondenceInstance,
					userInteracting)
			JaMoPP2PCMUtils.createNewCorrespondingEObjects(newValue, newCorrespondingOperationProvidedRoles, blackboard)
		}
	}

	/**
	 * called when a parameter has been deleted/changed
	 * If the parameter had a correspondence to an OperationRequiredRole delete it as well
	 */
	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		if (!oldCorrespondingEObjectsToDelete.nullOrEmpty) {
			TransformationUtils.removeCorrespondenceAndAllObjects(oldCorrespondingEObjectsToDelete, blackboard)
		}
	}

}
