package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.CreateNonRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.CreateRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.DeleteNonRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.DeleteRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UnsetEFeature
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEContainmentReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.UpdateEReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.impl.EObjectChangeImpl
import java.util.Set
import org.apache.log4j.Logger
import org.reflections.Reflections

class ChangeSynchronizer  {
	
	val private static final Logger logger = Logger.getLogger(ChangeSynchronizer.simpleName)
	val private ClaimableMap<Class<?>, EObjectMappingTransformation> mappingTransformations
	
	new (){
		mappingTransformations = new ClaimableHashMap<Class<?>, EObjectMappingTransformation>();
		fillTransformationMap()
	}
	
	def public synchronizeChange(EChange change){
		syncChange(change)
	}
	
	def dispatch syncChange(EChange change){
		logger.error("No syncChang method found for change " + change + ". Change not synchronized")
	}
	
	def dispatch syncChange(CreateRootEObject createRootEObject){
		mappingTransformations.claimValueForKey(createRootEObject.class).addEObject(createRootEObject.changedEObject)
	}
	
	def dispatch syncChange(DeleteRootEObject deleteRootEObject){
		mappingTransformations.claimValueForKey(deleteRootEObject.class).removeEObject(deleteRootEObject.changedEObject)
	}
	
	def dispatch syncChange(CreateNonRootEObject<?> createNonRootEObject){
		mappingTransformations.claimKeyIsMapped(createNonRootEObject.changedEObject.class)
		mappingTransformations.claimKeyIsMapped(createNonRootEObject.affectedEObject.class)
		mappingTransformations.get(createNonRootEObject.changedEObject.class).addEObject(createNonRootEObject.changedEObject)
		mappingTransformations.get(createNonRootEObject.affectedEObject.class).updateEReference(
			createNonRootEObject.affectedEObject, createNonRootEObject.affectedFeature, createNonRootEObject.newValue)
	}
	
	def dispatch syncChange(DeleteNonRootEObject<?> deleteNonRootEObject){
		mappingTransformations.claimKeyIsMapped(deleteNonRootEObject.changedEObject.class)
		mappingTransformations.claimKeyIsMapped(deleteNonRootEObject.affectedEObject.class)
		mappingTransformations.get(deleteNonRootEObject.changedEObject.class).addEObject(deleteNonRootEObject.changedEObject)
		mappingTransformations.get(deleteNonRootEObject.affectedEObject.class).updateEReference(
			deleteNonRootEObject.affectedEObject, deleteNonRootEObject.affectedFeature, deleteNonRootEObject.newValue
		)
	}	
	
	def dispatch syncChange(UpdateEAttribute<?> updateEAttribute){
		mappingTransformations.claimValueForKey(updateEAttribute.affectedEObject.class).updateEAttribute(
			updateEAttribute.affectedEObject, updateEAttribute.affectedFeature, updateEAttribute.newValue)
	}
	
	def dispatch syncChange(UpdateEReference<?> updateEReference){
		mappingTransformations.claimValueForKey(updateEReference.affectedEObject.class).updateEReference(
			updateEReference.affectedEObject, updateEReference.affectedFeature, updateEReference.newValue)
	}
	
	def dispatch syncChange(UpdateEContainmentReference<?> updateEContainmentReference){
		mappingTransformations.claimValueForKey(updateEContainmentReference.affectedEObject.class).updateEContainmentReference(
			updateEContainmentReference.affectedEObject, updateEContainmentReference.affectedFeature, updateEContainmentReference.newValue)
	}
	
	def dispatch syncChange(UnsetEFeature<?> unsetEFeature){
		logger.error("syncChange for UnsetEFeature<?> is not implemented yet...")
	}
	
	def private fillTransformationMap() {
		val Reflections reflections = new Reflections();
		val Set<String> transformationClasses = reflections.store.getSubTypesOf(typeof(EObjectMappingTransformation).name)
		for(String transClassName : transformationClasses){
			val Class<?> currentClass = Class.forName(transClassName) 
			val currentInstance = currentClass.newInstance
			if((currentInstance instanceof EObjectMappingTransformation)){
				val EObjectMappingTransformation currentMappingTransformation = currentInstance as EObjectMappingTransformation
				mappingTransformations.putClaimingNullOrSameMapped(currentMappingTransformation.getClassOfMappedEObject(), currentMappingTransformation)
			}else{
				logger.warn("current instance " + currentInstance + " is not an instance of " +
					 typeof(EObjectChangeImpl).name + " This should not happen...")
			}
		}
	}
	
}