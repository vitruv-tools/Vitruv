package edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
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

	var protected CorrespondenceInstance correspondenceInstance

	def Class<?> getClassOfMappedEObject()

	def EObject[] createEObject(EObject eObject)

	def EObject[] removeEObject(EObject eObject)

	def TransformationChangeResult createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects)

	def TransformationChangeResult deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete)

	def TransformationChangeResult replaceRoot(EObject oldRootEObject, EObject newRootEObject,
		EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects)

	def TransformationChangeResult createNonRootEObjectInList(EObject affectedEObject, EReference affectedReference,
		EObject newValue, int index, EObject[] newCorrespondingEObjects)

	def TransformationChangeResult deleteNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject[] eObjectsToDelete)

	def TransformationChangeResult createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference,
		EObject newValue, EObject[] newCorrespondingEObjects)

	def TransformationChangeResult replaceNonRootEObjectInList(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue, int index, EObject[] oldCorrespondingEObjectsToDelete,
		EObject[] newCorrespondingEObjects)

	def TransformationChangeResult deleteNonRootEObjectInList(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete)

	def TransformationChangeResult replaceNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue, EObject[] oldCorrespondingEObjectsToDelete,
		EObject[] newCorrespondingEObjects)

	def TransformationChangeResult permuteContainmentEReferenceValues(EObject affectedEObject,
		EReference affectedReference, EList<Integer> newIndexForElementAt)

	def TransformationChangeResult insertNonContaimentEReference(EObject affectedEObject, EReference affectedReference,
		EObject newValue, int index)

	def TransformationChangeResult updateSingleValuedNonContainmentEReference(EObject affectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue)

	def TransformationChangeResult permuteNonContainmentEReferenceValues(EObject affectedEObject,
		EReference affectedReference, EList<Integer> newIndexForElementAt)

	def TransformationChangeResult replaceNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue, int index)

	def TransformationChangeResult removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, int index)

	def TransformationChangeResult updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, Object newValue)

	def TransformationChangeResult removeEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, int index)

	def TransformationChangeResult insertEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object newValue, int index)

	def TransformationChangeResult unsetEAttribute(EObject affectedEObject, EStructuralFeature affectedFeature,
		Object oldValue)

	def TransformationChangeResult unsetContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject[] oldCorrespondingEObjectsToDelete)

	def TransformationChangeResult unsetNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue)

	def TransformationChangeResult replaceEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute,
		Object oldValue, Object newValue, int index)

	def TransformationChangeResult permuteEAttributeValues(EObject affectedEObject, EAttribute affectedAttribute,
		EList<Integer> newIndexForElementAt)

	def void setCorrespondenceForFeatures()

	def void setCorrespondenceInstance(CorrespondenceInstance correspondenceInstance) {
		this.correspondenceInstance = correspondenceInstance
		if (null != correspondenceInstance) {
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

}
