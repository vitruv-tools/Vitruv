package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.packagemapping.javaimplementation.util.transformationexecutor

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import java.util.Deque
import java.util.LinkedList
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.EChange
import edu.kit.ipd.sdq.vitruvius.domains.java.change.feature.JavaFeatureEChange
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.root.InsertRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.root.RemoveRootEObject
import edu.kit.ipd.sdq.vitruvius.domains.java.change.feature.reference.JavaInsertEReference
import edu.kit.ipd.sdq.vitruvius.domains.java.change.feature.reference.JavaRemoveEReference
import edu.kit.ipd.sdq.vitruvius.domains.java.change.feature.reference.JavaReplaceSingleValuedEReference
import edu.kit.ipd.sdq.vitruvius.domains.java.change.feature.attribute.JavaReplaceSingleValuedEAttribute
import edu.kit.ipd.sdq.vitruvius.domains.java.change.feature.attribute.JavaInsertEAttributeValue
import edu.kit.ipd.sdq.vitruvius.domains.java.change.feature.attribute.JavaRemoveEAttributeValue
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.reference.InsertEReference
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.reference.RemoveEReference
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.reference.ReplaceSingleValuedEReference
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.attribute.ReplaceSingleValuedEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.attribute.InsertEAttributeValue
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.attribute.RemoveEAttributeValue
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel

package class TransformationExecutor {

	val private static final Logger logger = Logger.getLogger(TransformationExecutor.simpleName)

	val protected ClaimableMap<Class<?>, EObjectMappingTransformation> mappingTransformations
	var protected CorrespondenceModel correspondenceModel

	new() {
		mappingTransformations = new ClaimableHashMap<Class<?>, EObjectMappingTransformation>()
	}

	def void setCorrespondenceModel(CorrespondenceModel correspondenceModel) {
		this.correspondenceModel = correspondenceModel
		for (mappingTransformation : mappingTransformations.values) {
			mappingTransformation.setCorrespondenceModel(correspondenceModel)
		}
	}

	def setUserInteracting(UserInteracting userInteracting) {
		for (mappingTransformations : mappingTransformations.values) {
			mappingTransformations.setUserInteracting(userInteracting)
		}
	}

	def public TransformationResult executeTransformationForChange(EChange change) {
		val TransformationResult transformationResult = executeTransformation(change)
		updateTUIDOfAffectedEObjectInEChange(change)
		return transformationResult
	}

	def protected updateTUIDOfAffectedEObjectInEChange(EChange change) {
		if (change instanceof JavaFeatureEChange<?,?>) {
			val JavaFeatureEChange<?,?> eFeatureChange = change as JavaFeatureEChange<?,?>
			val EObject oldAffectedEObject = eFeatureChange.oldAffectedEObject
			val EObject newAffectedEObject = eFeatureChange.affectedEObject
			if (null != oldAffectedEObject && null != newAffectedEObject) {
				correspondenceModel.updateTUID(oldAffectedEObject, newAffectedEObject)
			}
		}
	}

	def private dispatch TransformationResult executeTransformation(EChange change) {
		logger.error("No executeTransformation method found for change " + change + ". Change not synchronized")
		return null
	}

	def private dispatch TransformationResult executeTransformation(InsertRootEObject<?> createRootEObject) {
		val EObject[] createdObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(createRootEObject.newValue.class).createEObject(
				createRootEObject.newValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(createRootEObject.newValue.class).
			createRootEObject(createRootEObject.newValue, createdObjects)
	}

	def private dispatch TransformationResult executeTransformation(RemoveRootEObject<?> deleteRootEObject) {
		val EObject[] removedEObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(deleteRootEObject.oldValue.class).removeEObject(
				deleteRootEObject.oldValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteRootEObject.oldValue.class).
			deleteRootEObject(deleteRootEObject.oldValue, removedEObjects)
	}

//	def private dispatch TransformationResult executeTransformation(ReplaceRootEObject<?> replaceRootEObject) {
//		val EObject[] createdObjects = mappingTransformations.
//			claimForMappedClassOrImplementingInterface(replaceRootEObject.newValue.class).createEObject(
//				replaceRootEObject.newValue)
//		val EObject[] removedEObjects = mappingTransformations.
//			claimForMappedClassOrImplementingInterface(replaceRootEObject.oldValue.class).removeEObject(
//				replaceRootEObject.oldValue)
//		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceRootEObject.newValue.class).
//			replaceRoot(replaceRootEObject.oldValue, replaceRootEObject.newValue, removedEObjects, createdObjects)
//	}

	def private dispatch TransformationResult executeTransformation(InsertEReference<?,?> insertEReference) {
		val oldAffectedEObject = if (insertEReference instanceof JavaInsertEReference<?,?>) {
			insertEReference.oldAffectedEObject
		} else {
			insertEReference.affectedEObject;
		}
		if (insertEReference.isContainment) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(insertEReference.newValue.class)

		val EObject[] createdEObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(insertEReference.newValue.class).
			createEObject(insertEReference.newValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			insertEReference.affectedEObject.class).createNonRootEObjectInList(
			insertEReference.affectedEObject, oldAffectedEObject,
			insertEReference.affectedFeature, insertEReference.newValue,
			insertEReference.index, createdEObjects)
		} else {
			mappingTransformations.claimForMappedClassOrImplementingInterface(
			insertEReference.class).
			insertNonContaimentEReference(oldAffectedEObject,
				insertEReference.affectedFeature, insertEReference.newValue,
				insertEReference.index)
		}
	}

	def private dispatch TransformationResult executeTransformation(RemoveEReference<?,?> removeEReference) {
		val oldAffectedEObject = if (removeEReference instanceof JavaRemoveEReference<?,?>) {
			removeEReference.oldAffectedEObject
		} else {
			removeEReference.affectedEObject;
		}
		if (removeEReference.isContainment) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(removeEReference.oldValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class)

		val EObject[] eObjectsToDelete = mappingTransformations.
			claimForMappedClassOrImplementingInterface(removeEReference.oldValue.class).
			removeEObject(removeEReference.oldValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class).deleteNonRootEObjectInList(
			removeEReference.affectedEObject, oldAffectedEObject,
			removeEReference.affectedFeature, removeEReference.oldValue,
			removeEReference.index, eObjectsToDelete)
		} else {
			mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class).
			removeNonContainmentEReference(oldAffectedEObject,
				removeEReference.affectedFeature, removeEReference.oldValue,
				removeEReference.index)
		}
	}

//	def private dispatch TransformationResult executeTransformation(
//		ReplaceNonRootEObjectInList<?> replaceNonRootEObjectInList) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.newValue.class)
//		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.oldValue.class)
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			replaceNonRootEObjectInList.oldAffectedEObject.class)
//
//		val EObject[] eObjectsToDelete = mappingTransformations.
//			claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.oldValue.class).
//			removeEObject(replaceNonRootEObjectInList.oldAffectedEObject)
//		val EObject[] createdEObjects = mappingTransformations.
//			claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.newValue.class).
//			createEObject(replaceNonRootEObjectInList.oldAffectedEObject)
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			replaceNonRootEObjectInList.oldAffectedEObject.class).replaceNonRootEObjectInList(
//			replaceNonRootEObjectInList.oldAffectedEObject, replaceNonRootEObjectInList.affectedFeature,
//			replaceNonRootEObjectInList.oldValue, replaceNonRootEObjectInList.newValue,
//			replaceNonRootEObjectInList.index, eObjectsToDelete, createdEObjects)
//	}

	def private dispatch TransformationResult executeTransformation(ReplaceSingleValuedEReference<?,?> replaceSingleValuedEReference) {
		val oldAffectedEObject = if (replaceSingleValuedEReference instanceof JavaReplaceSingleValuedEReference<?,?>) {
			replaceSingleValuedEReference.oldAffectedEObject
		} else {
			replaceSingleValuedEReference.affectedEObject;
		}
		if (replaceSingleValuedEReference.isContainment) {
		if (replaceSingleValuedEReference.oldValue == null && replaceSingleValuedEReference.newValue != null) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceSingleValuedEReference.newValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class)

		val EObject[] createdEObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(replaceSingleValuedEReference.newValue.class).
			createEObject(replaceSingleValuedEReference.newValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			replaceSingleValuedEReference.affectedEObject.class).createNonRootEObjectSingle(
			oldAffectedEObject, replaceSingleValuedEReference.affectedFeature,
			replaceSingleValuedEReference.newValue, createdEObjects)
		} else if (replaceSingleValuedEReference.oldValue != null && replaceSingleValuedEReference.newValue == null) {
			mappingTransformations.claimForMappedClassOrImplementingInterface(replaceSingleValuedEReference.oldValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class)

		val EObject[] eObjectsToDelete = mappingTransformations.
			claimForMappedClassOrImplementingInterface(replaceSingleValuedEReference.oldValue.class).
			removeEObject(replaceSingleValuedEReference.oldValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class).deleteNonRootEObjectSingle(
			oldAffectedEObject, replaceSingleValuedEReference.affectedFeature,
			replaceSingleValuedEReference.oldValue, eObjectsToDelete)
		} else {
			mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class)

		mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class).replaceNonRootEObjectSingle(
			replaceSingleValuedEReference.affectedEObject, oldAffectedEObject,
			replaceSingleValuedEReference.affectedFeature, replaceSingleValuedEReference.oldValue,
			replaceSingleValuedEReference.newValue)
		}
		} else {
			mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class).
			updateSingleValuedNonContainmentEReference(oldAffectedEObject,
				replaceSingleValuedEReference.affectedFeature,
				replaceSingleValuedEReference.oldValue, replaceSingleValuedEReference.newValue)
		}
	}

//	def private dispatch TransformationResult executeTransformation(
//		PermuteContainmentEReferenceValues<?> permuteContainmentEReferenceValues) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			permuteContainmentEReferenceValues.oldAffectedEObject.class).
//			permuteContainmentEReferenceValues(permuteContainmentEReferenceValues.oldAffectedEObject,
//				permuteContainmentEReferenceValues.affectedFeature,
//				permuteContainmentEReferenceValues.newIndexForElementAt)
//	}
//
//	def private dispatch TransformationResult executeTransformation(
//		ReplaceNonContainmentEReference<?> replaceNonContainmentEReference) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			replaceNonContainmentEReference.oldAffectedEObject.class).
//			replaceNonContainmentEReference(replaceNonContainmentEReference.oldAffectedEObject,
//				replaceNonContainmentEReference.affectedFeature, replaceNonContainmentEReference.oldValue,
//				replaceNonContainmentEReference.newValue, replaceNonContainmentEReference.index)
//	}
//
//	def private dispatch TransformationResult executeTransformation(
//		PermuteNonContainmentEReferenceValues<?> permuteNonContainmentEReferenceValues) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			permuteNonContainmentEReferenceValues.oldAffectedEObject.class).
//			permuteNonContainmentEReferenceValues(permuteNonContainmentEReferenceValues.oldAffectedEObject,
//				permuteNonContainmentEReferenceValues.affectedFeature,
//				permuteNonContainmentEReferenceValues.newIndexForElementAt)
//	}

	def private dispatch TransformationResult executeTransformation(
		ReplaceSingleValuedEAttribute<?,?> replaceSingleValuedEAttribute) {
		val oldAffectedEObject = if (replaceSingleValuedEAttribute instanceof JavaReplaceSingleValuedEAttribute<?,?>) {
			replaceSingleValuedEAttribute.oldAffectedEObject
		} else {
			replaceSingleValuedEAttribute.affectedEObject;
		}
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			oldAffectedEObject.class).updateSingleValuedEAttribute(
			oldAffectedEObject, replaceSingleValuedEAttribute.affectedFeature,
			replaceSingleValuedEAttribute.oldValue, replaceSingleValuedEAttribute.newValue)
	}

	def private dispatch TransformationResult executeTransformation(InsertEAttributeValue<?,?> insertEAttributeValue) {
		val oldAffectedEObject = if (insertEAttributeValue instanceof JavaInsertEAttributeValue<?,?>) {
			insertEAttributeValue.oldAffectedEObject
		} else {
			insertEAttributeValue.affectedEObject;
		}
		mappingTransformations.claimForMappedClassOrImplementingInterface(oldAffectedEObject.class).
			insertEAttributeValue(oldAffectedEObject, insertEAttributeValue.affectedFeature,
				insertEAttributeValue.newValue, insertEAttributeValue.index)
	}

	def private dispatch TransformationResult executeTransformation(RemoveEAttributeValue<?,?> removeEAttributeValue) {
		val oldAffectedEObject = if (removeEAttributeValue instanceof JavaRemoveEAttributeValue<?,?>) {
			removeEAttributeValue.oldAffectedEObject
		} else {
			removeEAttributeValue.affectedEObject;
		}
		mappingTransformations.claimForMappedClassOrImplementingInterface(oldAffectedEObject.class).
			removeEAttributeValue(oldAffectedEObject, removeEAttributeValue.affectedFeature,
				removeEAttributeValue.oldValue, removeEAttributeValue.index)
	}

//	def private dispatch TransformationResult executeTransformation(ReplaceEAttributeValue<?> replaceEAttributeValue) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			replaceEAttributeValue.oldAffectedEObject.class).replaceEAttributeValue(
//			replaceEAttributeValue.oldAffectedEObject, replaceEAttributeValue.affectedFeature,
//			replaceEAttributeValue.oldValue, replaceEAttributeValue.newValue, replaceEAttributeValue.index)
//	}

//	def private dispatch TransformationResult executeTransformation(PermuteEAttributeValues<?> permuteEAttributeValues) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			permuteEAttributeValues.oldAffectedEObject.class).permuteEAttributeValues(
//			permuteEAttributeValues.oldAffectedEObject, permuteEAttributeValues.affectedFeature,
//			permuteEAttributeValues.newIndexForElementAt)
//	}
//
//	def private dispatch TransformationResult executeTransformation(UnsetEAttribute<?> unsetEAttribute) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(unsetEAttribute.oldAffectedEObject.class).
//			unsetEAttribute(unsetEAttribute.oldAffectedEObject, unsetEAttribute.affectedFeature,
//				unsetEAttribute.oldValue)
//	}

//	def private dispatch TransformationResult executeTransformation(
//		UnsetNonContainmentEReference<?> unsetNonContainmentEReference) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			unsetNonContainmentEReference.oldAffectedEObject.class).
//			unsetNonContainmentEReference(unsetNonContainmentEReference.oldAffectedEObject,
//				unsetNonContainmentEReference.affectedFeature, unsetNonContainmentEReference.oldValue)
//	}

//	def private dispatch TransformationResult executeTransformation(UnsetContainmentEReference<?> unsetContainmentEReference) {
//		var EObject[] eObjectsToDelete = null
//		if (null != unsetContainmentEReference.oldValue) {
//			eObjectsToDelete = mappingTransformations.
//				claimForMappedClassOrImplementingInterface(unsetContainmentEReference.oldValue.class).
//				removeEObject(unsetContainmentEReference.oldValue)
//		}
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			unsetContainmentEReference.oldAffectedEObject.class).unsetContainmentEReference(
//			unsetContainmentEReference.oldAffectedEObject, unsetContainmentEReference.affectedFeature,
//			unsetContainmentEReference.oldValue, eObjectsToDelete)
//	}

//	def private dispatch TransformationResult executeTransformation(
//		InsertNonRootEObjectInContainmentList<?> insertNonRootEObjectInContainmentList) {
//		mappingTransformations.claimForMappedClassOrImplementingInterface(
//			insertNonRootEObjectInContainmentList.newAffectedEObject.class).insertNonRootEObjectInContainmentList(
//			insertNonRootEObjectInContainmentList.newAffectedEObject,
//			insertNonRootEObjectInContainmentList.oldAffectedEObject,
//			insertNonRootEObjectInContainmentList.affectedFeature,
//			insertNonRootEObjectInContainmentList.newValue
//		)
//	}
//
//	def private dispatch TransformationResult executeTransformation(UnsetEFeature<?> unsetEFeature) {
//		logger.error("executeTransformation for UnsetEFeature<?> is not implemented yet...")
//		return null
//	}

	def public addMapping(EObjectMappingTransformation transformation) {
		if (null != correspondenceModel) {
			transformation.setCorrespondenceModel(correspondenceModel)
		}
		mappingTransformations.putClaimingNullOrSameMapped(transformation.classOfMappedEObject, transformation)
	}

	def private EObjectMappingTransformation claimForMappedClassOrImplementingInterface(
		ClaimableMap<Class<?>, EObjectMappingTransformation> transformations, Class<?> concreteInstance) {
		val Deque<Class<?>> bfsQueue = new LinkedList()
		bfsQueue.addAll(concreteInstance)
		while (!bfsQueue.empty) {
			val concreteIf = bfsQueue.poll
			if (transformations.containsKey(concreteIf)) {
				return transformations.get(concreteIf)
			} else {
				bfsQueue.addAll(concreteIf.interfaces)
			}
		}
		return transformations.claimValueForKey(concreteInstance)
	}

}
