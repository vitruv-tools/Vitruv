package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import de.uka.ipd.sdq.pcm.repository.DataType
import de.uka.ipd.sdq.pcm.repository.OperationInterface
import de.uka.ipd.sdq.pcm.repository.OperationSignature
import de.uka.ipd.sdq.pcm.repository.Parameter
import de.uka.ipd.sdq.pcm.repository.Repository
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
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
		val operationInterfaces = correspondenceInstance.getCorrespondingEObjectsByType(interfaceClassifier,
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

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		return JaMoPP2PCMUtils.updateNameAsSingleValuedEAttribute(affectedEObject, affectedAttribute, oldValue,
			newValue, featureCorrespondenceMap, correspondenceInstance)
	}

	/**
	 * called when the return type has been changed
	 */
	override replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue) {
		val tcr = TransformationUtils.createEmptyTransformationChangeResult
		if (oldAffectedEObject instanceof Method &&
			affectedReference.name.equals(PCMJaMoPPNamespace.JaMoPP.JAMOPP_REFERENCE_TYPE_REFERENCE) &&
			newValue instanceof TypeReference) {
			val correspondingPCMSignatures = correspondenceInstance.
				getCorrespondingEObjectsByType(oldAffectedEObject, OperationSignature)
			if (correspondingPCMSignatures.nullOrEmpty) {
				return tcr
			}
			for (correspondingSignature : correspondingPCMSignatures) {
				val Repository repo = correspondingSignature.interface__OperationSignature.repository__Interface
				val oldTUID = correspondenceInstance.calculateTUIDFromEObject(correspondingSignature)
				val parrentCorrespondence = JaMoPP2PCMUtils.
					findMainParrentCorrepsondenceForPCMElement(correspondingSignature, correspondenceInstance)
				val DataType newReturnValue = TypeReferenceCorrespondenceHelper.
					getCorrespondingPCMDataTypeForTypeReference(newValue as TypeReference, correspondenceInstance,
						userInteracting, repo, tcr)
				correspondingSignature.returnType__OperationSignature = newReturnValue
				tcr.existingObjectsToSave.add(correspondingSignature)

				// guess this is not necessary since the id stays the same
				tcr.addCorrespondenceToUpdate(correspondenceInstance, oldTUID, correspondingSignature,
					parrentCorrespondence)
			}
		}
		return tcr
	}

	/**
     *  called when a parameter has been added
     *  If the type of the parameter is a component interface or another 
     *  component an OperationRrovidedRole is added 
     */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		if (!newCorrespondingEObjects.nullOrEmpty) {
			val operationSignatues = correspondenceInstance.getCorrespondingEObjectsByType(oldAffectedEObject,
				OperationSignature)
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

		val tcr = JaMoPP2PCMUtils.
			createTransformationChangeResultForNewCorrespondingEObjects(newValue, newCorrespondingEObjects,
				correspondenceInstance)
		if (newValue instanceof TypedElement) {
			val newCorrespondingOperationProvidedRoles = JaMoPP2PCMUtils.
				checkAndAddOperationRequiredRole(newValue as TypedElement, correspondenceInstance, userInteracting)
			tcr.addChangeResult(
				JaMoPP2PCMUtils.
					createTransformationChangeResultForNewCorrespondingEObjects(newValue,
						newCorrespondingOperationProvidedRoles, correspondenceInstance))
		}
		return tcr
	}

	/**
	 * called when a parameter has been deleted/changed
	 * If the parameter had a correspondence to an OperationRequiredRole delete it as well
	 */
	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		val tcr = TransformationUtils.createEmptyTransformationChangeResult
		if (!oldCorrespondingEObjectsToDelete.nullOrEmpty) {
			for (oldCorrdpondingEObject : oldCorrespondingEObjectsToDelete) {
				val tuidToRemove = correspondenceInstance.calculateTUIDFromEObject(oldCorrdpondingEObject)
				tcr.addCorrespondenceToDelete(correspondenceInstance, tuidToRemove)
				EcoreUtil.delete(oldCorrdpondingEObject)
			}
		}
		return tcr
	}

}
