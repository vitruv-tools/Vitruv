package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor

import tools.vitruv.framework.userinteraction.UserInteractionType
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.datatypes.ClaimableHashMap
import tools.vitruv.framework.util.datatypes.ClaimableMap
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.ChangePropagationResult

abstract class EObjectMappingTransformation {

	var protected ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap
	var private UserInteracting userInteracting

	new() {
		featureCorrespondenceMap = new ClaimableHashMap<EStructuralFeature, EStructuralFeature>();
	}

	var protected CorrespondenceModel correspondenceModel

	def Class<?> getClassOfMappedEObject()

	def EObject[] createEObject(EObject eObject)

	def EObject[] removeEObject(EObject eObject)

	def ChangePropagationResult createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects)

	def ChangePropagationResult deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete)

	def ChangePropagationResult replaceRoot(EObject oldRootEObject, EObject newRootEObject,
		EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects)

	def ChangePropagationResult createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects)

	def ChangePropagationResult deleteNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject[] eObjectsToDelete)

	def ChangePropagationResult createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference,
		EObject newValue, EObject[] newCorrespondingEObjects)

	def ChangePropagationResult replaceNonRootEObjectInList(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue, int index, EObject[] oldCorrespondingEObjectsToDelete,
		EObject[] newCorrespondingEObjects)

	def ChangePropagationResult deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete)

	def ChangePropagationResult replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue)

	def ChangePropagationResult permuteContainmentEReferenceValues(EObject affectedEObject,
		EReference affectedReference, EList<Integer> newIndexForElementAt)

	def ChangePropagationResult insertNonContaimentEReference(EObject affectedEObject, EReference affectedReference,
		EObject newValue, int index)

	def ChangePropagationResult updateSingleValuedNonContainmentEReference(EObject affectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue)

	def ChangePropagationResult permuteNonContainmentEReferenceValues(EObject affectedEObject,
		EReference affectedReference, EList<Integer> newIndexForElementAt)

	def ChangePropagationResult replaceNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue, int index)

	def ChangePropagationResult removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, int index)

	def ChangePropagationResult updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, Object newValue)

	def ChangePropagationResult removeEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, int index)

	def ChangePropagationResult insertEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object newValue, int index)

	def ChangePropagationResult unsetEAttribute(EObject affectedEObject, EStructuralFeature affectedFeature,
		Object oldValue)

	def ChangePropagationResult unsetContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject[] oldCorrespondingEObjectsToDelete)

	def ChangePropagationResult unsetNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue)

	def ChangePropagationResult replaceEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, Object newValue, int index)

	def ChangePropagationResult permuteEAttributeValues(EObject affectedEObject, EAttribute affectedAttribute,
		EList<Integer> newIndexForElementAt)

	def ChangePropagationResult insertNonRootEObjectInContainmentList(EObject oldAffectedEObject,
		EObject newAffectedEObject, EReference reference, EObject newValue)

	def void setCorrespondenceForFeatures()

	def void setCorrespondenceModel(CorrespondenceModel correspondenceModel) {
		this.correspondenceModel = correspondenceModel
		if (null != correspondenceModel) {
			setCorrespondenceForFeatures()
		}
	}

	def void setUserInteracting(UserInteracting userInteracting) {
		this.userInteracting = userInteracting
	}

	def protected UserInteracting getUserInteracting() {
		if (null == userInteracting) {
			throw new RuntimeException(
				"getUserInteracting failed: Reason: UserInteracting is not set (==null)." +
					"UserInteracting has to be set before using it.")
		}
		return userInteracting
	}

	def protected int modalTextUserinteracting(String msg, String... selections) {
		return userInteracting.selectFromMessage(UserInteractionType.MODAL, msg, selections)
	}

	def protected boolean modalTextYesNoUserInteracting(String msg) {
		return 0 == modalTextUserinteracting(msg, "Yes", "No")
	}

}
