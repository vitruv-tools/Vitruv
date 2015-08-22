package edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

abstract class EObjectMappingTransformation {

	var protected ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap
	var private UserInteracting userInteracting

	new() {
		featureCorrespondenceMap = new ClaimableHashMap<EStructuralFeature, EStructuralFeature>();
	}

	var protected Blackboard blackboard

	def Class<?> getClassOfMappedEObject()

	def EObject[] createEObject(EObject eObject)

	def EObject[] removeEObject(EObject eObject)

	def void createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects)

	def void deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete)

	def void replaceRoot(EObject oldRootEObject, EObject newRootEObject,
		EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects)

	def void createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects)

	def void deleteNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject[] eObjectsToDelete)

	def void createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference,
		EObject newValue, EObject[] newCorrespondingEObjects)

	def void replaceNonRootEObjectInList(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue, int index, EObject[] oldCorrespondingEObjectsToDelete,
		EObject[] newCorrespondingEObjects)

	def void deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete)

	def void replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue)

	def void permuteContainmentEReferenceValues(EObject affectedEObject,
		EReference affectedReference, EList<Integer> newIndexForElementAt)

	def void insertNonContaimentEReference(EObject affectedEObject, EReference affectedReference,
		EObject newValue, int index)

	def void updateSingleValuedNonContainmentEReference(EObject affectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue)

	def void permuteNonContainmentEReferenceValues(EObject affectedEObject,
		EReference affectedReference, EList<Integer> newIndexForElementAt)

	def void replaceNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue, int index)

	def void removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, int index)

	def void updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, Object newValue)

	def void removeEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, int index)

	def void insertEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object newValue, int index)

	def void unsetEAttribute(EObject affectedEObject, EStructuralFeature affectedFeature,
		Object oldValue)

	def void unsetContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject[] oldCorrespondingEObjectsToDelete)

	def void unsetNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue)

	def void replaceEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, Object newValue, int index)

	def void permuteEAttributeValues(EObject affectedEObject, EAttribute affectedAttribute,
		EList<Integer> newIndexForElementAt)

	def void insertNonRootEObjectInContainmentList(EObject oldAffectedEObject,
		EObject newAffectedEObject, EReference reference, EObject newValue)

	def void setCorrespondenceForFeatures()

	def void setBlackboard(Blackboard blackboard) {
		this.blackboard = blackboard
		if (null != blackboard) {
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

	/**
	 * Utility function that wraps a single EObject into an array. 
	 * Is defined here to enable calls like eObject.toArray
	 */
	def protected toArray(EObject eObject) {
		if (null == eObject) {
			return null
		}
		#[eObject]
	}

	def protected int modalTextUserinteracting(String msg, String... selections) {
		return userInteracting.selectFromMessage(UserInteractionType.MODAL, msg, selections)
	}

	def protected boolean modalTextYesNoUserInteracting(String msg) {
		return 0 == modalTextUserinteracting(msg, "Yes", "No")
	}

}
