package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

/**
 * The implements create and delete EObject with null and all other transformations with empty results.
 * The goal is to have for all EObjects a matching class in the map in order to avoid runtime exceptions.   
 */
class DefaultEObjectMappingTransformation extends EmptyEObjectMappingTransformation {
	
	private static val Logger logger = Logger.getLogger(DefaultEObjectMappingTransformation.simpleName)
	
	override getClassOfMappedEObject() {
		return EObject
	}
	
	override setCorrespondenceForFeatures() {
	}
	
	override deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
	}

	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override deleteNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] eObjectsToDelete) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override insertEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object newValue, int index) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override removeEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, int index) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override insertNonContaimentEReference(EObject affectedEObject, EReference affectedReference, EObject newValue,
		int index) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override permuteContainmentEReferenceValues(EObject affectedEObject, EReference affectedReference,
		EList<Integer> newIndexForElementAt) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override permuteEAttributeValues(EObject affectedEObject, EAttribute affectedAttribute,
		EList<Integer> newIndexForElementAt) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override permuteNonContainmentEReferenceValues(EObject affectedEObject, EReference affectedReference,
		EList<Integer> newIndexForElementAt) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		int index) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override replaceEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue, int index) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override replaceNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue, int index) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override replaceNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue, int index, EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override unsetNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override unsetContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] oldCorrespondingEObjectsToDelete) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override unsetEAttribute(EObject affectedEObject, EStructuralFeature affectedFeature, Object oldValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override replaceRoot(EObject oldRootEObject, EObject newRootEObject, EObject[] oldCorrespondingEObjectsToDelete,
		EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override createEObject(EObject eObject) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation. EObject: " + eObject)
		return null
	}

	override removeEObject(EObject eObject) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		return null
	}

	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}

	override insertNonRootEObjectInContainmentList(EObject object, EObject object2, EReference reference,
		EObject newValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		
	}
	
}