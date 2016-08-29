package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.packagemapping.javaimplementation.util.transformationexecutor

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel

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

	def TransformationResult createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects)

	def TransformationResult deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete)

	def TransformationResult replaceRoot(EObject oldRootEObject, EObject newRootEObject,
		EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects)

	def TransformationResult createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects)

	def TransformationResult deleteNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject[] eObjectsToDelete)

	def TransformationResult createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference,
		EObject newValue, EObject[] newCorrespondingEObjects)

	def TransformationResult replaceNonRootEObjectInList(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue, int index, EObject[] oldCorrespondingEObjectsToDelete,
		EObject[] newCorrespondingEObjects)

	def TransformationResult deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete)

	def TransformationResult replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue)

	def TransformationResult permuteContainmentEReferenceValues(EObject affectedEObject,
		EReference affectedReference, EList<Integer> newIndexForElementAt)

	def TransformationResult insertNonContaimentEReference(EObject affectedEObject, EReference affectedReference,
		EObject newValue, int index)

	def TransformationResult updateSingleValuedNonContainmentEReference(EObject affectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue)

	def TransformationResult permuteNonContainmentEReferenceValues(EObject affectedEObject,
		EReference affectedReference, EList<Integer> newIndexForElementAt)

	def TransformationResult replaceNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue, int index)

	def TransformationResult removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, int index)

	def TransformationResult updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, Object newValue)

	def TransformationResult removeEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, int index)

	def TransformationResult insertEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object newValue, int index)

	def TransformationResult unsetEAttribute(EObject affectedEObject, EStructuralFeature affectedFeature,
		Object oldValue)

	def TransformationResult unsetContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject[] oldCorrespondingEObjectsToDelete)

	def TransformationResult unsetNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue)

	def TransformationResult replaceEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, Object newValue, int index)

	def TransformationResult permuteEAttributeValues(EObject affectedEObject, EAttribute affectedAttribute,
		EList<Integer> newIndexForElementAt)

	def TransformationResult insertNonRootEObjectInContainmentList(EObject oldAffectedEObject,
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
