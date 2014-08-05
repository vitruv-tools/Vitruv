package edu.kit.ipd.sdq.vitruvius.framework.transformationexecuter

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
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
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.PermuteContainmentEReferenceValues
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectInList
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject

public class ChangeSynchronizer { 

	val private static final Logger logger = Logger.getLogger(ChangeSynchronizer.simpleName)

	val private ClaimableMap<Class<?>, EObjectMappingTransformation> mappingTransformations
	var private CorrespondenceInstance correspondenceInstance

	new() {
		mappingTransformations = new ClaimableHashMap<Class<?>, EObjectMappingTransformation>()
	}
	
	def setCorrespondenceInstance(CorrespondenceInstance correspondenceInstance) {
		this.correspondenceInstance = correspondenceInstance
		for (mappingTransformation : mappingTransformations.values) {
			mappingTransformation.setCorrespondenceInstance(correspondenceInstance)
		}
	}

	def public TransformationChangeResult synchronizeChange(EChange change) {
		syncChange(change)
	}

	def private dispatch TransformationChangeResult syncChange(EChange change) {
		logger.error("No syncChang method found for change " + change + ". Change not synchronized")
		return null
	}

	def private dispatch TransformationChangeResult syncChange(CreateRootEObject<?> createRootEObject) {
		val EObject[] createdObjects = mappingTransformations.claimForMappedClassOrImplementingInterface(createRootEObject.newValue.class).
			createEObject(createRootEObject.newValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(createRootEObject.newValue.class).
			createRootEObject(createRootEObject.newValue, createdObjects)
	}

	def private dispatch TransformationChangeResult syncChange(DeleteRootEObject<?> deleteRootEObject) {
		val EObject[] removedEObjects = mappingTransformations.claimForMappedClassOrImplementingInterface(deleteRootEObject.oldValue.class).
			removeEObject(deleteRootEObject.oldValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteRootEObject.oldValue.class).
			deleteRootEObject(deleteRootEObject.oldValue, removedEObjects) 
	}
	
	def private dispatch TransformationChangeResult syncChange(ReplaceRootEObject<?> replaceRootEObject){
		val EObject[] createdObjects = mappingTransformations.claimForMappedClassOrImplementingInterface(replaceRootEObject.newValue.class).
			createEObject(replaceRootEObject.newValue)
		val EObject[] removedEObjects = mappingTransformations.claimForMappedClassOrImplementingInterface(replaceRootEObject.oldValue.class).
			removeEObject(replaceRootEObject.oldValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceRootEObject.newValue.class).
			replaceRoot(replaceRootEObject.oldValue, replaceRootEObject.newValue, removedEObjects, createdObjects)
	}

	def private dispatch TransformationChangeResult syncChange(CreateNonRootEObjectInList<?> createNonRootEObjectInList) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(createNonRootEObjectInList.newValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(createNonRootEObjectInList.affectedEObject.class)
		
		val EObject[] createdEObjects = mappingTransformations.
			claimForMappedClassOrImplementingInterface(createNonRootEObjectInList.newValue.class).createEObject(
				createNonRootEObjectInList.newValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(createNonRootEObjectInList.affectedEObject.class).
			createNonRootEObjectInList(createNonRootEObjectInList.affectedEObject, createNonRootEObjectInList.affectedFeature,
				createNonRootEObjectInList.newValue, createNonRootEObjectInList.index, createdEObjects)
	}

	def private dispatch TransformationChangeResult syncChange(DeleteNonRootEObjectInList<?> deleteNonRootEObjectInList)  {
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteNonRootEObjectInList.oldValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteNonRootEObjectInList.affectedEObject.class)
		
		val EObject[] eObjectsToDelete = mappingTransformations.claimForMappedClassOrImplementingInterface(deleteNonRootEObjectInList.oldValue.class).
			removeEObject(deleteNonRootEObjectInList.oldValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteNonRootEObjectInList.affectedEObject.class).
			deleteNonRootEObjectInList(deleteNonRootEObjectInList.affectedEObject, deleteNonRootEObjectInList.affectedFeature,
				deleteNonRootEObjectInList.oldValue, deleteNonRootEObjectInList.index, eObjectsToDelete)
	}

	def private dispatch TransformationChangeResult syncChange(ReplaceNonRootEObjectInList<?> replaceNonRootEObjectInList){
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.newValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.oldValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.affectedEObject.class)
		
		val EObject[] eObjectsToDelete = mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.oldValue.class).
			removeEObject(replaceNonRootEObjectInList.affectedEObject)
		val EObject[] createdEObjects = mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.newValue.class).
			createEObject(replaceNonRootEObjectInList.affectedEObject)
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectInList.newValue.class).
			replaceNonRootEObjectInList(replaceNonRootEObjectInList.affectedEObject, replaceNonRootEObjectInList.affectedFeature, 
				replaceNonRootEObjectInList.oldValue, replaceNonRootEObjectInList.newValue, replaceNonRootEObjectInList.index,
				eObjectsToDelete, createdEObjects)
	}

	def private dispatch TransformationChangeResult syncChange(CreateNonRootEObjectSingle<?> createNonRootEObjectSingle){
		mappingTransformations.claimForMappedClassOrImplementingInterface(createNonRootEObjectSingle.newValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(createNonRootEObjectSingle.affectedEObject.class)
		
		val EObject[] createdEObjects = mappingTransformations.claimForMappedClassOrImplementingInterface(createNonRootEObjectSingle.newValue.class).
			createEObject(createNonRootEObjectSingle.newValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(createNonRootEObjectSingle.affectedEObject.class).
			createNonRootEObjectSingle(createNonRootEObjectSingle.affectedEObject, createNonRootEObjectSingle.affectedFeature, 
				createNonRootEObjectSingle.newValue, createdEObjects)
	}

	def private dispatch TransformationChangeResult syncChange(DeleteNonRootEObjectSingle<?> deleteNonRootEObjectSingle) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteNonRootEObjectSingle.oldValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteNonRootEObjectSingle.affectedEObject.class)
		
		val EObject[] eObjectsToDelete = mappingTransformations.claimForMappedClassOrImplementingInterface(deleteNonRootEObjectSingle.oldValue.class).
			removeEObject(deleteNonRootEObjectSingle.oldValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteNonRootEObjectSingle.affectedEObject.class).
			deleteNonRootEObjectSingle(deleteNonRootEObjectSingle.affectedEObject, deleteNonRootEObjectSingle.affectedFeature,
				deleteNonRootEObjectSingle.oldValue, eObjectsToDelete)
	}

	
	def private dispatch TransformationChangeResult syncChange(ReplaceNonRootEObjectSingle<?> replaceNonRootEObjectSingle){
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectSingle.newValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectSingle.oldValue.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectSingle.affectedEObject.class)
		
		val EObject[] createdEObjects = mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectSingle.oldValue.class).
			removeEObject(replaceNonRootEObjectSingle.affectedEObject)
		val EObject[] eObjectsToDelete =mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectSingle.newValue.class).
			createEObject(replaceNonRootEObjectSingle.affectedEObject)
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonRootEObjectSingle.newValue.class).
			replaceNonRootEObjectSingle(replaceNonRootEObjectSingle.affectedEObject, replaceNonRootEObjectSingle.affectedFeature,
				replaceNonRootEObjectSingle.oldValue, replaceNonRootEObjectSingle.newValue, eObjectsToDelete, createdEObjects)
	}
	
	def private dispatch TransformationChangeResult syncChange(PermuteContainmentEReferenceValues<?> permuteContainmentEReferenceValues){
		mappingTransformations.claimForMappedClassOrImplementingInterface(permuteContainmentEReferenceValues.affectedEObject.class).
			permuteContainmentEReferenceValues(permuteContainmentEReferenceValues.affectedEObject, permuteContainmentEReferenceValues.affectedFeature,
				permuteContainmentEReferenceValues.newIndexForElementAt)
	}

	def private dispatch TransformationChangeResult syncChange(InsertNonContainmentEReference<?> insertNonContaimentEReference){
		mappingTransformations.claimForMappedClassOrImplementingInterface(insertNonContaimentEReference.affectedEObject.class).
			insertNonContaimentEReference(insertNonContaimentEReference.affectedEObject, insertNonContaimentEReference.affectedFeature,
				insertNonContaimentEReference.newValue, insertNonContaimentEReference.index)
	}
	
	def private dispatch TransformationChangeResult syncChange(RemoveNonContainmentEReference<?> removeNonContainmentEReference){
		mappingTransformations.claimForMappedClassOrImplementingInterface(removeNonContainmentEReference.affectedEObject.class).
			removeNonContainmentEReference(removeNonContainmentEReference.affectedEObject, removeNonContainmentEReference.affectedFeature,
				removeNonContainmentEReference.oldValue, removeNonContainmentEReference.index)
	}
	
	def private dispatch TransformationChangeResult syncChange(ReplaceNonContainmentEReference<?> replaceNonContainmentEReference){
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceNonContainmentEReference.affectedEObject.class).
			replaceNonContainmentEReference(replaceNonContainmentEReference.affectedEObject, replaceNonContainmentEReference.affectedFeature,
				replaceNonContainmentEReference.oldValue, replaceNonContainmentEReference.newValue, replaceNonContainmentEReference.index)
	}
	
	def private dispatch TransformationChangeResult syncChange(PermuteNonContainmentEReferenceValues<?> permuteNonContainmentEReferenceValues){
		mappingTransformations.claimForMappedClassOrImplementingInterface(permuteNonContainmentEReferenceValues.affectedEObject.class).
			permuteNonContainmentEReferenceValues(permuteNonContainmentEReferenceValues.affectedEObject, permuteNonContainmentEReferenceValues.affectedFeature,
				permuteNonContainmentEReferenceValues.newIndexForElementAt)
	}
	
	def private dispatch TransformationChangeResult syncChange(UpdateSingleValuedNonContainmentEReference<?> updateSingleValuedNonContainmentEReference){
		mappingTransformations.claimForMappedClassOrImplementingInterface(updateSingleValuedNonContainmentEReference.affectedEObject.class).
			updateSingleValuedNonContainmentEReference(updateSingleValuedNonContainmentEReference.affectedEObject, updateSingleValuedNonContainmentEReference.affectedFeature,
				updateSingleValuedNonContainmentEReference.oldValue, updateSingleValuedNonContainmentEReference.newValue)
	}
	
	def private dispatch TransformationChangeResult syncChange(UpdateSingleValuedEAttribute<?> updateSingleValuedEAttribute){
		mappingTransformations.claimForMappedClassOrImplementingInterface(updateSingleValuedEAttribute.affectedEObject.class).
			updateSingleValuedEAttribute(updateSingleValuedEAttribute.affectedEObject, updateSingleValuedEAttribute.affectedFeature, 
				updateSingleValuedEAttribute.oldValue, updateSingleValuedEAttribute.newValue)
	} 
		
	def private dispatch TransformationChangeResult syncChange(InsertEAttributeValue<?> insertEAttributeValue){
		mappingTransformations.claimForMappedClassOrImplementingInterface(insertEAttributeValue.affectedEObject.class).
			insertEAttributeValue(insertEAttributeValue.affectedEObject, insertEAttributeValue.affectedFeature, 
				insertEAttributeValue.newValue, insertEAttributeValue.index)
	} 	
	
	def private dispatch TransformationChangeResult syncChange(RemoveEAttributeValue<?> removeEAttributeValue){
		mappingTransformations.claimForMappedClassOrImplementingInterface(removeEAttributeValue.affectedEObject.class).
			removeEAttributeValue(removeEAttributeValue.affectedEObject, removeEAttributeValue.affectedFeature, 
				removeEAttributeValue.oldValue, removeEAttributeValue.index)
	}
	
	def private dispatch TransformationChangeResult syncChange(ReplaceEAttributeValue<?> replaceEAttributeValue){
		mappingTransformations.claimForMappedClassOrImplementingInterface(replaceEAttributeValue.affectedEObject.class).
			replaceEAttributeValue(replaceEAttributeValue.affectedEObject, replaceEAttributeValue.affectedFeature, 
				replaceEAttributeValue.oldValue, replaceEAttributeValue.newValue, replaceEAttributeValue.index)
	}
	
	def private dispatch TransformationChangeResult syncChange(PermuteEAttributeValues<?> permuteEAttributeValues){
		mappingTransformations.claimForMappedClassOrImplementingInterface(permuteEAttributeValues.affectedEObject.class).
			permuteEAttributeValues(permuteEAttributeValues.affectedEObject, permuteEAttributeValues.affectedFeature, 
				permuteEAttributeValues.newIndexForElementAt)
	}
		
	def private dispatch TransformationChangeResult syncChange(UnsetEAttribute<?> unsetEAttribute){
		mappingTransformations.claimForMappedClassOrImplementingInterface(unsetEAttribute.affectedEObject.class).
			unsetEAttribute(unsetEAttribute.affectedEObject, unsetEAttribute.affectedFeature, unsetEAttribute.oldValue)
	}
	
	def private dispatch TransformationChangeResult syncChange(UnsetNonContainmentEReference<?> unsetNonContainmentEReference){
		mappingTransformations.claimForMappedClassOrImplementingInterface(unsetNonContainmentEReference.affectedEObject.class).
			unsetNonContainmentEReference(unsetNonContainmentEReference.affectedEObject, unsetNonContainmentEReference.affectedFeature,
				unsetNonContainmentEReference.oldValue)
	}


	def private dispatch TransformationChangeResult syncChange(UnsetContainmentEReference<?> unsetContainmentEReference){
		val EObject[] eObjectsToDelete = mappingTransformations.claimForMappedClassOrImplementingInterface(unsetContainmentEReference.oldValue.class).
			removeEObject(unsetContainmentEReference.oldValue)
		mappingTransformations.claimForMappedClassOrImplementingInterface(unsetContainmentEReference.affectedEObject.class).
			unsetContainmentEReference(unsetContainmentEReference.affectedEObject, unsetContainmentEReference.affectedFeature,
				unsetContainmentEReference.oldValue, eObjectsToDelete)
	}
	
	def private dispatch TransformationChangeResult syncChange(UnsetEFeature<?> unsetEFeature) {
		logger.error("syncChange for UnsetEFeature<?> is not implemented yet...")
		return null
	}

	def public addMapping(EObjectMappingTransformation transformation) {
		if(null != correspondenceInstance){
			transformation.setCorrespondenceInstance(correspondenceInstance)
		}
		mappingTransformations.putClaimingNullOrSameMapped(transformation.classOfMappedEObject, transformation)
	}

	def private EObjectMappingTransformation claimForMappedClassOrImplementingInterface(
		ClaimableMap<Class<?>, EObjectMappingTransformation> transformations, Class<?> concreteInstance) {
		for (concreteIf : concreteInstance.interfaces) {
			if (transformations.containsKey(concreteIf)) {
				return transformations.get(concreteIf)
			}
		}
		return transformations.claimValueForKey(concreteInstance)
	}

}
