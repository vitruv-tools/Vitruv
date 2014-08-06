package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EObjectMappingTransformation
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

/**
 * Base class for all JaMoPP2PCMMappingTransformations
 * includes empty implementation for all methods from EObjectMappingTransformation which are not
 *  needed in the JaMoPP2PCMMappingTransformations.
 * The only methods that are needed are:
 * createEObject,
 * removeEObject,
 * updateSingleVauledEAttribute,
 * updateSingleVauedNonContainmentEReference,
 * createNonRootEObjectInList,
 * deleteNonRootEObjectInList,
 */
abstract class JaMoPPPCMMappingTransformationBase extends EObjectMappingTransformation { 
	
	private static val Logger logger = Logger.getLogger(JaMoPPPCMMappingTransformationBase.simpleName)
	
	override deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation")
		return null
	}
	
	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation")
		return null
	}
	
	override deleteNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject oldValue, EObject[] eObjectsToDelete) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation")
		return null
	}
	
	override insertEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object newValue, int index) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation")
		return null
	}
	
	override removeEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, int index) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation")
		return null
	}
	
	override insertNonContaimentEReference(EObject affectedEObject, EReference affectedReference, EObject newValue, int index) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation")
		return null
	}
	
	override permuteContainmentEReferenceValues(EObject affectedEObject, EReference affectedReference, EList<Integer> newIndexForElementAt) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation")
		return null
	}
	
	override permuteEAttributeValues(EObject affectedEObject, EAttribute affectedAttribute, EList<Integer> newIndexForElementAt) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation")
		return null
	}
	
	override permuteNonContainmentEReferenceValues(EObject affectedEObject, EReference affectedReference, EList<Integer> newIndexForElementAt) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation -"
				+ "all EObjects within the class should be contained in the class")
		return null
	}
	
	override removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue, int index) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation -"
				+ "all EObjects within the class should be contained in the class")
		return null
	}

	override replaceNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject oldValue, EObject newValue, EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation")
		return null
	}
	
	override replaceEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, Object newValue, int index) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation")
		return null
	}
	
	override replaceNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue, EObject newValue, int index) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation")
		return null
	}
	
	override replaceNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue, EObject newValue, int index, EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation")
		return null
	}
	
	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue, EObject newValue) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation")
		return null
	}
	
	override unsetNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation (unset should not be possible in the java editor")
		return null
	}
	
	override unsetContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue, EObject[] oldCorrespondingEObjectsToDelete) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation (unset should not be possible in the java editor")
		return null
	}
	
	override unsetEAttribute(EObject affectedEObject, EStructuralFeature affectedFeature, Object oldValue) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation (unset should not be possible in the java editor")
		return null
	}
	
	override replaceRoot(EObject oldRootEObject, EObject newRootEObject, EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects) {
		logger.warn("method should not be called for JaMoPP2PCMMappingTransformationBase transformation")
		return null
	}
}