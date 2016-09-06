package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.Utilities
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.helpers.CorrespondenceHelper
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel
import edu.kit.ipd.sdq.vitruvius.framework.util.command.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.tuid.TUID
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.util.transformationexecutor.EObjectMappingTransformation
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

/**
 * Base class for all EObject mapping transformations, which provides default
 * implementations for every change type. The default implementation issues a
 * log message on a call. Additionally some utility methods are provided,
 * which are useful for almost all transformations. 
 */
abstract class EObjectMappingTransformationBase extends EObjectMappingTransformation {

	protected static def <T extends EObject> clone(T obj) {
		return Utilities.clone(obj)
	}

	protected static def <T extends EObject> getParentOfType(EObject eobject, Class<T> type) {
		return Utilities.getParentOfType(eobject, type)
	}

	protected static def <T extends EObject> getChildrenOfType(EObject eobject, Class<T> type) {
		return Utilities.getChildrenOfType(eobject, type)
	}

	protected def <T extends EObject> T getSingleCorrespondingEObjectOfType(EObject subject, Class<T> type) {
		return CorrespondenceHelper.getSingleCorrespondingEObjectOfType(correspondenceModel, subject, type)
	}

	protected def getSingleCorrespondence(EObject srcElement, EObject dstElement) {
		return CorrespondenceHelper.getSingleCorrespondence(correspondenceModel, srcElement, dstElement)
	}

	protected def <T extends EObject> T getModelInstanceElement(T obj) {
		return obj.getModelInstanceElement(correspondenceModel) 
	}

	protected static def <T extends EObject> T getModelInstanceElement(T obj, CorrespondenceModel ci) {
		val match = Utilities.getModelInstanceElement(obj, ci)
		if (match != null) {
			return match
		}
		return Utilities.getModelInstanceElementByTUID(obj, ci)
	}

	protected abstract def Logger getLogger()

	private def createDefaultImplementationLoggingString(String methodName, String[] parameterNames,
		Object[] parameterValues) {
		val str = new StringBuilder()
		str.append("Default implementation of " + methodName + " is called in " + this.class.simpleName + ":")
		for (var i = 0; i < parameterNames.size; i++) {
			val paramName = parameterNames.get(i)
			val paramValue = parameterValues.get(i)
			str.append("\n\t" + paramName + ": " + paramValue)
		}
		return str.toString
	}

	override createEObject(EObject eObject) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName, #["eObject"], #[eObject])
		logger.info(logMsg)
		return #[]
	}

	override removeEObject(EObject eObject) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName, #["eObject"], #[eObject])
		logger.info(logMsg)
		return #[]
	}

	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["newRootEObject", "newCorrespondingEObjects"], #[newRootEObject, newCorrespondingEObjects])
		logger.info(logMsg)
		return new TransformationResult
	}

	override deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["oldRootEObject", "oldCorrespondingEObjectsToDelete"], #[oldRootEObject, oldCorrespondingEObjectsToDelete])
		logger.info(logMsg)
		return new TransformationResult
	}

	override replaceRoot(EObject oldRootEObject, EObject newRootEObject, EObject[] oldCorrespondingEObjectsToDelete,
		EObject[] newCorrespondingEObjects) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["oldRootEObject", "newRootEObject", "oldCorrespondingEObjectsToDelete", "newCorrespondingEObjects"],
			#[oldRootEObject, newRootEObject, oldCorrespondingEObjectsToDelete, newCorrespondingEObjects])
		logger.info(logMsg)
		return new TransformationResult
	}

	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject newValue,
		int index, EObject[] newCorrespondingEObjects) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["newAffectedEObject", "oldAffectedEObject", "affectedReference", "newValue", "index", "newCorrespondingEObjects"],
			#[newAffectedEObject, oldAffectedEObject, affectedReference, newValue, index, newCorrespondingEObjects])
		logger.info(logMsg)
		return new TransformationResult
	}

	override deleteNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] eObjectsToDelete) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue", "eObjectsToDelete"],
			#[affectedEObject, affectedReference, oldValue, eObjectsToDelete])
		logger.info(logMsg)
		return new TransformationResult
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "newValue", "newCorrespondingEObjects"],
			#[affectedEObject, affectedReference, newValue, newCorrespondingEObjects])
		logger.info(logMsg)
		return new TransformationResult
	}

	override replaceNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue, int index, EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue", "newValue", "index",
				"oldCorrespondingEObjectsToDelete", "newCorrespondingEObjects"],
			#[affectedEObject, affectedReference, oldValue, newValue, index, oldCorrespondingEObjectsToDelete,
				newCorrespondingEObjects])
		logger.info(logMsg)
		return new TransformationResult
	}

	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldCorrespondingEObjectsToDelete) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["newAffectedEObject", "oldAffectedEObject", "affectedReference", "oldValue", "index", "oldCorrespondingEObjectsToDelete"],
			#[newAffectedEObject, oldAffectedEObject, affectedReference, oldValue, index, oldCorrespondingEObjectsToDelete])
		logger.info(logMsg)
		return new TransformationResult
	}

	override replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue", "newValue"],
			#[newAffectedEObject, affectedReference, oldValue, newValue])
		logger.info(logMsg)
		return new TransformationResult
	}

	override permuteContainmentEReferenceValues(EObject affectedEObject, EReference affectedReference,
		EList<Integer> newIndexForElementAt) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "newIndexForElementAt"],
			#[affectedEObject, affectedReference, newIndexForElementAt])
		logger.info(logMsg)
		return new TransformationResult
	}

	override insertNonContaimentEReference(EObject affectedEObject, EReference affectedReference, EObject newValue,
		int index) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "newValue", "index"],
			#[affectedEObject, affectedReference, newValue, index])
		logger.info(logMsg)
		return new TransformationResult
	}

	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue", "newValue"],
			#[affectedEObject, affectedReference, oldValue, newValue])
		logger.info(logMsg)
		return new TransformationResult
	}

	override permuteNonContainmentEReferenceValues(EObject affectedEObject, EReference affectedReference,
		EList<Integer> newIndexForElementAt) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "newIndexForElementAt"],
			#[affectedEObject, affectedReference, newIndexForElementAt])
		logger.info(logMsg)
		return new TransformationResult
	}

	override replaceNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue, int index) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue", "newValue", "index"],
			#[affectedEObject, affectedReference, oldValue, newValue, index])
		logger.info(logMsg)
		return new TransformationResult
	}

	override removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		int index) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue", "index"],
			#[affectedEObject, affectedReference, oldValue, index])
		logger.info(logMsg)
		return new TransformationResult
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedAttribute", "oldValue", "newValue"],
			#[affectedEObject, affectedAttribute, oldValue, newValue])
		logger.info(logMsg)
		return new TransformationResult
	}

	override removeEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, int index) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedAttribute", "oldValue", "index"],
			#[affectedEObject, affectedAttribute, oldValue, index])
		logger.info(logMsg)
		return new TransformationResult
	}

	override insertEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object newValue, int index) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedAttribute", "newValue", "index"],
			#[affectedEObject, affectedAttribute, newValue, index])
		logger.info(logMsg)
		return new TransformationResult
	}

	override unsetEAttribute(EObject affectedEObject, EStructuralFeature affectedFeature, Object oldValue) {

		//TODO why structural feature instead of eattribute?
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedFeature", "oldValue"], #[affectedEObject, affectedFeature, oldValue])
		logger.info(logMsg)
		return new TransformationResult
	}

	override unsetContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] oldCorrespondingEObjectsToDelete) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue", "oldCorrespondingEObjectsToDelete"],
			#[affectedEObject, affectedReference, oldValue, oldCorrespondingEObjectsToDelete])
		logger.info(logMsg)
		return new TransformationResult
	}

	override unsetNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue"], #[affectedEObject, affectedReference, oldValue])
		logger.info(logMsg)
		return new TransformationResult
	}

	override replaceEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue, int index) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedAttribute", "oldValue", "newValue", "index"],
			#[affectedEObject, affectedAttribute, oldValue, newValue, index])
		logger.info(logMsg)
		return new TransformationResult
	}

	override permuteEAttributeValues(EObject affectedEObject, EAttribute affectedAttribute,
		EList<Integer> newIndexForElementAt) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedAttribute", "newIndexForElementAt"],
			#[affectedEObject, affectedAttribute, newIndexForElementAt])
		logger.info(logMsg)
		return new TransformationResult
	}
	
	override insertNonRootEObjectInContainmentList(EObject oldAffectedEObject, EObject newAffectedEObject, EReference reference, EObject newValue) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["oldAffectedEObject", "newAffectedEObject", "reference", "newValue"],
			#[oldAffectedEObject, newAffectedEObject, reference, newValue])
		logger.info(logMsg)
		return new TransformationResult
	}

}
