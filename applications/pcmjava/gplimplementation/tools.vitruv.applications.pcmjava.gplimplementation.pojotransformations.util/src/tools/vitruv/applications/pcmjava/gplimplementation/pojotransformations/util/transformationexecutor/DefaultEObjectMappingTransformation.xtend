package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor

import org.apache.log4j.Logger
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.util.command.ChangePropagationResult

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
		new ChangePropagationResult
	}

	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult
	}

	override deleteNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] eObjectsToDelete) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult	
	}

	override insertEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object newValue, int index) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult	
	}

	override removeEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, int index) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}

	override insertNonContaimentEReference(EObject affectedEObject, EReference affectedReference, EObject newValue,
		int index) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult
	}

	override permuteContainmentEReferenceValues(EObject affectedEObject, EReference affectedReference,
		EList<Integer> newIndexForElementAt) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}

	override permuteEAttributeValues(EObject affectedEObject, EAttribute affectedAttribute,
		EList<Integer> newIndexForElementAt) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}

	override permuteNonContainmentEReferenceValues(EObject affectedEObject, EReference affectedReference,
		EList<Integer> newIndexForElementAt) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}

	override removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		int index) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult
	}

	override replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}

	override replaceEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue, int index) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}

	override replaceNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue, int index) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}

	override replaceNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue, int index, EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}

	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}

	override unsetNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult
	}

	override unsetContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] oldCorrespondingEObjectsToDelete) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}

	override unsetEAttribute(EObject affectedEObject, EStructuralFeature affectedFeature, Object oldValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}

	override replaceRoot(EObject oldRootEObject, EObject newRootEObject, EObject[] oldCorrespondingEObjectsToDelete,
		EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
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
		new ChangePropagationResult		
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}

	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}

	override insertNonRootEObjectInContainmentList(EObject object, EObject object2, EReference reference,
		EObject newValue) {
		logger.warn(
			"method " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " should not be called for " + this.class.simpleName +
				"transformation")
		new ChangePropagationResult		
	}
	
}