package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.helpers

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.util.transformationexecutor.EObjectMappingTransformation
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

class DummyTransformations extends EObjectMappingTransformation {

	private val Class<?> mappedEObject

	new(Class<?> mappedEObject) {
		this.mappedEObject = mappedEObject
	}

	override getClassOfMappedEObject() {
		return mappedEObject
	}

	override createEObject(EObject eObject) {
		return #[]
	}

	override removeEObject(EObject eObject) {
		return #[]
	}

	override setCorrespondenceForFeatures() {
	}

	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override replaceRoot(EObject oldRootEObject, EObject newRootEObject, EObject[] oldCorrespondingEObjectsToDelete,
		EObject[] newCorrespondingEObjects) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override deleteNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] eObjectsToDelete) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override replaceNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue, int index, EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override permuteContainmentEReferenceValues(EObject affectedEObject, EReference affectedReference,
		EList<Integer> newIndexForElementAt) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override insertNonContaimentEReference(EObject affectedEObject, EReference affectedReference, EObject newValue,
		int index) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override permuteNonContainmentEReferenceValues(EObject affectedEObject, EReference affectedReference,
		EList<Integer> newIndexForElementAt) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override replaceNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue, int index) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		int index) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override removeEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, int index) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override insertEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object newValue, int index) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override unsetEAttribute(EObject affectedEObject, EStructuralFeature affectedFeature, Object oldValue) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override unsetContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] oldCorrespondingEObjectsToDelete) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override unsetNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override replaceEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue, int index) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override permuteEAttributeValues(EObject affectedEObject, EAttribute affectedAttribute,
		EList<Integer> newIndexForElementAt) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

	override insertNonRootEObjectInContainmentList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference reference, EObject newValue) {
		throw new UnsupportedOperationException(
			"This is a dummy method only, which should never be called! (" + mappedEObject + ")")
	}

}
