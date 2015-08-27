package edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetContainmentEReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEFeature
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetNonContainmentEReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.InsertEAttributeValue
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.PermuteEAttributeValues
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.RemoveEAttributeValue
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.ReplaceEAttributeValue
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.InsertNonContainmentEReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.PermuteNonContainmentEReferenceValues
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.RemoveNonContainmentEReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReplaceNonContainmentEReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.InsertNonRootEObjectInContainmentList
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.PermuteContainmentEReferenceValues
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectInList
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import java.util.Deque
import java.util.LinkedList
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult

public class TransformationExecuter {

	val private static final Logger logger = Logger.getLogger(TransformationExecuter.simpleName)

	val protected ClaimableMap<Class<?>, EObjectMappingTransformation> mappingTransformations
	var protected Blackboard blackboard

	new() {
		mappingTransformations = new ClaimableHashMap<Class<?>, EObjectMappingTransformation>()
	}

	def void setBlackboard(Blackboard blackboard) {
		this.blackboard = blackboard
		for (mappingTransformation : mappingTransformations.values) {
			mappingTransformation.setBlackboard(blackboard)
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
		if (change instanceof EFeatureChange<?>) {
			val EFeatureChange<?> eFeatureChange = change as EFeatureChange<?>
			val EObject oldAffectedEObject = eFeatureChange.oldAffectedEObject
			val EObject newAffectedEObject = eFeatureChange.newAffectedEObject
			if (null != oldAffectedEObject && null != newAffectedEObject) {
				CorrespondenceUtils.updateCorrespondence(blackboard.correspondenceInstance, oldAffectedEObject, newAffectedEObject)
			}
		}
	}

	def private dispatch TransformationResult executeTransformation(EChange change) {
		logger.error("No executeTransformation method found for change " + change + ". Change not synchronized")
		return null
	}

	def private dispatch TransformationResult executeTransformation(CreateRootEObject<?> createRootEObject) {
		val EObject[] createdObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(createRootEObject.newValue.class).createEObject(
				createRootEObject.newValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(createRootEObject.newValue.class).
			createRootEObject(createRootEObject.newValue, createdObjects)
	}

	def private dispatch TransformationResult executeTransformation(DeleteRootEObject<?> deleteRootEObject) {
		val EObject[] removedEObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(deleteRootEObject.oldValue.class).removeEObject(
				deleteRootEObject.oldValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteRootEObject.oldValue.class).
			deleteRootEObject(deleteRootEObject.oldValue, removedEObjects)
	}

	def private dispatch TransformationResult executeTransformation(ReplaceRootEObject<?> replaceRootEObject) {
		val EObject[] createdObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(replaceRootEObject.newValue.class).createEObject(
				replaceRootEObject.newValue)
		val EObject[] removedEObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(replaceRootEObject.oldValue.class).removeEObject(
				replaceRootEObject.oldValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceRootEObject.newValue.class).
			replaceRoot(replaceRootEObject.oldValue, replaceRootEObject.newValue, removedEObjects, createdObjects)
	}

	def private dispatch TransformationResult executeTransformation(CreateNonRootEObjectInList<?> createNonRootEObjectInList) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(createNonRootEObjectInList.newValue.class)

		val EObject[] createdEObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(createNonRootEObjectInList.newValue.class).
			createEObject(createNonRootEObjectInList.newValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			createNonRootEObjectInList.newAffectedEObject.class).createNonRootEObjectInList(
			createNonRootEObjectInList.newAffectedEObject, createNonRootEObjectInList.oldAffectedEObject,
			createNonRootEObjectInList.affectedFeature, createNonRootEObjectInList.newValue,
			createNonRootEObjectInList.index, createdEObjects)
	}

	def private dispatch TransformationResult executeTransformation(DeleteNonRootEObjectInList<?> deleteNonRootEObjectInList) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteNonRootEObjectInList.oldValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			deleteNonRootEObjectInList.oldAffectedEObject.class)

		val EObject[] eObjectsToDelete = mappingTransformations.
			claimForMappedClassOrImplementingInterface(deleteNonRootEObjectInList.oldValue.class).
			removeEObject(deleteNonRootEObjectInList.oldValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			deleteNonRootEObjectInList.oldAffectedEObject.class).deleteNonRootEObjectInList(
			deleteNonRootEObjectInList.newAffectedEObject, deleteNonRootEObjectInList.oldAffectedEObject,
			deleteNonRootEObjectInList.affectedFeature, deleteNonRootEObjectInList.oldValue,
			deleteNonRootEObjectInList.index, eObjectsToDelete)
	}

	def private dispatch TransformationResult executeTransformation(
		ReplaceNonRootEObjectInList<?> replaceNonRootEObjectInList) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.newValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.oldValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			replaceNonRootEObjectInList.oldAffectedEObject.class)

		val EObject[] eObjectsToDelete = mappingTransformations.
			claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.oldValue.class).
			removeEObject(replaceNonRootEObjectInList.oldAffectedEObject)
		val EObject[] createdEObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.newValue.class).
			createEObject(replaceNonRootEObjectInList.oldAffectedEObject)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			replaceNonRootEObjectInList.oldAffectedEObject.class).replaceNonRootEObjectInList(
			replaceNonRootEObjectInList.oldAffectedEObject, replaceNonRootEObjectInList.affectedFeature,
			replaceNonRootEObjectInList.oldValue, replaceNonRootEObjectInList.newValue,
			replaceNonRootEObjectInList.index, eObjectsToDelete, createdEObjects)
	}

	def private dispatch TransformationResult executeTransformation(CreateNonRootEObjectSingle<?> createNonRootEObjectSingle) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(createNonRootEObjectSingle.newValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			createNonRootEObjectSingle.oldAffectedEObject.class)

		val EObject[] createdEObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(createNonRootEObjectSingle.newValue.class).
			createEObject(createNonRootEObjectSingle.newValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			createNonRootEObjectSingle.newAffectedEObject.class).createNonRootEObjectSingle(
			createNonRootEObjectSingle.oldAffectedEObject, createNonRootEObjectSingle.affectedFeature,
			createNonRootEObjectSingle.newValue, createdEObjects)
	}

	def private dispatch TransformationResult executeTransformation(DeleteNonRootEObjectSingle<?> deleteNonRootEObjectSingle) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteNonRootEObjectSingle.oldValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			deleteNonRootEObjectSingle.oldAffectedEObject.class)

		val EObject[] eObjectsToDelete = mappingTransformations.
			claimForMappedClassOrImplementingInterface(deleteNonRootEObjectSingle.oldValue.class).
			removeEObject(deleteNonRootEObjectSingle.oldValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			deleteNonRootEObjectSingle.oldAffectedEObject.class).deleteNonRootEObjectSingle(
			deleteNonRootEObjectSingle.oldAffectedEObject, deleteNonRootEObjectSingle.affectedFeature,
			deleteNonRootEObjectSingle.oldValue, eObjectsToDelete)
	}

	def private dispatch TransformationResult executeTransformation(
		ReplaceNonRootEObjectSingle<?> replaceNonRootEObjectSingle) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			replaceNonRootEObjectSingle.oldAffectedEObject.class)

		mappingTransformations.claimForMappedClassOrImplementingInterface(
			replaceNonRootEObjectSingle.oldAffectedEObject.class).replaceNonRootEObjectSingle(
			replaceNonRootEObjectSingle.newAffectedEObject, replaceNonRootEObjectSingle.oldAffectedEObject,
			replaceNonRootEObjectSingle.affectedFeature, replaceNonRootEObjectSingle.oldValue,
			replaceNonRootEObjectSingle.newValue)
	}

	def private dispatch TransformationResult executeTransformation(
		PermuteContainmentEReferenceValues<?> permuteContainmentEReferenceValues) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			permuteContainmentEReferenceValues.oldAffectedEObject.class).
			permuteContainmentEReferenceValues(permuteContainmentEReferenceValues.oldAffectedEObject,
				permuteContainmentEReferenceValues.affectedFeature,
				permuteContainmentEReferenceValues.newIndexForElementAt)
	}

	def private dispatch TransformationResult executeTransformation(
		InsertNonContainmentEReference<?> insertNonContaimentEReference) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			insertNonContaimentEReference.oldAffectedEObject.class).
			insertNonContaimentEReference(insertNonContaimentEReference.oldAffectedEObject,
				insertNonContaimentEReference.affectedFeature, insertNonContaimentEReference.newValue,
				insertNonContaimentEReference.index)
	}

	def private dispatch TransformationResult executeTransformation(
		RemoveNonContainmentEReference<?> removeNonContainmentEReference) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			removeNonContainmentEReference.oldAffectedEObject.class).
			removeNonContainmentEReference(removeNonContainmentEReference.oldAffectedEObject,
				removeNonContainmentEReference.affectedFeature, removeNonContainmentEReference.oldValue,
				removeNonContainmentEReference.index)
	}

	def private dispatch TransformationResult executeTransformation(
		ReplaceNonContainmentEReference<?> replaceNonContainmentEReference) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			replaceNonContainmentEReference.oldAffectedEObject.class).
			replaceNonContainmentEReference(replaceNonContainmentEReference.oldAffectedEObject,
				replaceNonContainmentEReference.affectedFeature, replaceNonContainmentEReference.oldValue,
				replaceNonContainmentEReference.newValue, replaceNonContainmentEReference.index)
	}

	def private dispatch TransformationResult executeTransformation(
		PermuteNonContainmentEReferenceValues<?> permuteNonContainmentEReferenceValues) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			permuteNonContainmentEReferenceValues.oldAffectedEObject.class).
			permuteNonContainmentEReferenceValues(permuteNonContainmentEReferenceValues.oldAffectedEObject,
				permuteNonContainmentEReferenceValues.affectedFeature,
				permuteNonContainmentEReferenceValues.newIndexForElementAt)
	}

	def private dispatch TransformationResult executeTransformation(
		UpdateSingleValuedNonContainmentEReference<?> updateSingleValuedNonContainmentEReference) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			updateSingleValuedNonContainmentEReference.oldAffectedEObject.class).
			updateSingleValuedNonContainmentEReference(updateSingleValuedNonContainmentEReference.oldAffectedEObject,
				updateSingleValuedNonContainmentEReference.affectedFeature,
				updateSingleValuedNonContainmentEReference.oldValue, updateSingleValuedNonContainmentEReference.newValue)
	}

	def private dispatch TransformationResult executeTransformation(
		UpdateSingleValuedEAttribute<?> updateSingleValuedEAttribute) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			updateSingleValuedEAttribute.oldAffectedEObject.class).updateSingleValuedEAttribute(
			updateSingleValuedEAttribute.oldAffectedEObject, updateSingleValuedEAttribute.affectedFeature,
			updateSingleValuedEAttribute.oldValue, updateSingleValuedEAttribute.newValue)
	}

	def private dispatch TransformationResult executeTransformation(InsertEAttributeValue<?> insertEAttributeValue) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(insertEAttributeValue.oldAffectedEObject.class).
			insertEAttributeValue(insertEAttributeValue.oldAffectedEObject, insertEAttributeValue.affectedFeature,
				insertEAttributeValue.newValue, insertEAttributeValue.index)
	}

	def private dispatch TransformationResult executeTransformation(RemoveEAttributeValue<?> removeEAttributeValue) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(removeEAttributeValue.oldAffectedEObject.class).
			removeEAttributeValue(removeEAttributeValue.oldAffectedEObject, removeEAttributeValue.affectedFeature,
				removeEAttributeValue.oldValue, removeEAttributeValue.index)
	}

	def private dispatch TransformationResult executeTransformation(ReplaceEAttributeValue<?> replaceEAttributeValue) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			replaceEAttributeValue.oldAffectedEObject.class).replaceEAttributeValue(
			replaceEAttributeValue.oldAffectedEObject, replaceEAttributeValue.affectedFeature,
			replaceEAttributeValue.oldValue, replaceEAttributeValue.newValue, replaceEAttributeValue.index)
	}

	def private dispatch TransformationResult executeTransformation(PermuteEAttributeValues<?> permuteEAttributeValues) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			permuteEAttributeValues.oldAffectedEObject.class).permuteEAttributeValues(
			permuteEAttributeValues.oldAffectedEObject, permuteEAttributeValues.affectedFeature,
			permuteEAttributeValues.newIndexForElementAt)
	}

	def private dispatch TransformationResult executeTransformation(UnsetEAttribute<?> unsetEAttribute) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(unsetEAttribute.oldAffectedEObject.class).
			unsetEAttribute(unsetEAttribute.oldAffectedEObject, unsetEAttribute.affectedFeature,
				unsetEAttribute.oldValue)
	}

	def private dispatch TransformationResult executeTransformation(
		UnsetNonContainmentEReference<?> unsetNonContainmentEReference) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			unsetNonContainmentEReference.oldAffectedEObject.class).
			unsetNonContainmentEReference(unsetNonContainmentEReference.oldAffectedEObject,
				unsetNonContainmentEReference.affectedFeature, unsetNonContainmentEReference.oldValue)
	}

	def private dispatch TransformationResult executeTransformation(UnsetContainmentEReference<?> unsetContainmentEReference) {
		var EObject[] eObjectsToDelete = null
		if (null != unsetContainmentEReference.oldValue) {
			eObjectsToDelete = mappingTransformations.
				claimForMappedClassOrImplementingInterface(unsetContainmentEReference.oldValue.class).
				removeEObject(unsetContainmentEReference.oldValue)
		}
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			unsetContainmentEReference.oldAffectedEObject.class).unsetContainmentEReference(
			unsetContainmentEReference.oldAffectedEObject, unsetContainmentEReference.affectedFeature,
			unsetContainmentEReference.oldValue, eObjectsToDelete)
	}

	def private dispatch TransformationResult executeTransformation(
		InsertNonRootEObjectInContainmentList<?> insertNonRootEObjectInContainmentList) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(
			insertNonRootEObjectInContainmentList.newAffectedEObject.class).insertNonRootEObjectInContainmentList(
			insertNonRootEObjectInContainmentList.newAffectedEObject,
			insertNonRootEObjectInContainmentList.oldAffectedEObject,
			insertNonRootEObjectInContainmentList.affectedFeature,
			insertNonRootEObjectInContainmentList.newValue
		)
	}

	def private dispatch TransformationResult executeTransformation(UnsetEFeature<?> unsetEFeature) {
		logger.error("executeTransformation for UnsetEFeature<?> is not implemented yet...")
		return null
	}

	def public addMapping(EObjectMappingTransformation transformation) {
		if (null != blackboard) {
			transformation.setBlackboard(blackboard)
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
