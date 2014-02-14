package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableHashMap
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UnsetEFeature
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEContainmentReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.EObjectChangeImpl
import java.util.Set
import org.apache.log4j.Logger
import org.reflections.Reflections

public class ChangeSynchronizer  {
	
	val private static final Logger logger = Logger.getLogger(ChangeSynchronizer.simpleName)
	
	val private ClaimableMap<Class<?>, EObjectMappingTransformation> mappingTransformations
	var private CorrespondenceInstance correspondenceInstance
	
	new (){
		mappingTransformations = new ClaimableHashMap<Class<?>, EObjectMappingTransformation>()
		fillTransformationMap()
	}
	
	def setCorrespondenceInstance(CorrespondenceInstance correspondenceInstance){
		this.correspondenceInstance = correspondenceInstance
	}
	
	def public synchronizeChange(EChange change){
		syncChange(change)
	}
	
	def private dispatch syncChange(EChange change){
		logger.error("No syncChang method found for change " + change + ". Change not synchronized")
	}
	
	def private dispatch syncChange(CreateRootEObject createRootEObject){
		mappingTransformations.claimValueForKey(createRootEObject.class).addEObject(createRootEObject.changedEObject)
	}
	
	def private dispatch syncChange(DeleteRootEObject deleteRootEObject){
		mappingTransformations.claimValueForKey(deleteRootEObject.class).removeEObject(deleteRootEObject.changedEObject)
	}
	
	def private dispatch syncChange(CreateNonRootEObject<?> createNonRootEObject){
		mappingTransformations.claimKeyIsMapped(createNonRootEObject.changedEObject.class)
		mappingTransformations.claimKeyIsMapped(createNonRootEObject.affectedEObject.class)
		mappingTransformations.get(createNonRootEObject.changedEObject.class).addEObject(createNonRootEObject.changedEObject)
		mappingTransformations.get(createNonRootEObject.affectedEObject.class).updateEReference(
			createNonRootEObject.affectedEObject, createNonRootEObject.affectedFeature, createNonRootEObject.newValue)
	}
	
	def private dispatch syncChange(DeleteNonRootEObject<?> deleteNonRootEObject){
		mappingTransformations.claimKeyIsMapped(deleteNonRootEObject.changedEObject.class)
		mappingTransformations.claimKeyIsMapped(deleteNonRootEObject.affectedEObject.class)
		mappingTransformations.get(deleteNonRootEObject.changedEObject.class).addEObject(deleteNonRootEObject.changedEObject)
		mappingTransformations.get(deleteNonRootEObject.affectedEObject.class).updateEReference(
			deleteNonRootEObject.affectedEObject, deleteNonRootEObject.affectedFeature, deleteNonRootEObject.newValue
		)
	}	
	
	def private dispatch syncChange(UpdateEAttribute<?> updateEAttribute){
		mappingTransformations.claimValueForKey(updateEAttribute.affectedEObject.class).updateEAttribute(
			updateEAttribute.affectedEObject, updateEAttribute.affectedFeature, updateEAttribute.newValue)
	}
	
	def private dispatch syncChange(UpdateEReference<?> updateEReference){
		mappingTransformations.claimValueForKey(updateEReference.affectedEObject.class).updateEReference(
			updateEReference.affectedEObject, updateEReference.affectedFeature, updateEReference.newValue)
	}
	
	def private dispatch syncChange(UpdateEContainmentReference<?> updateEContainmentReference){
		mappingTransformations.claimValueForKey(updateEContainmentReference.affectedEObject.class).updateEContainmentReference(
			updateEContainmentReference.affectedEObject, updateEContainmentReference.affectedFeature, updateEContainmentReference.newValue)
	}
	
	def private dispatch syncChange(UnsetEFeature<?> unsetEFeature){
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
				currentMappingTransformation.setCorrespondenceInstance(correspondenceInstance)
				mappingTransformations.putClaimingNullOrSameMapped(currentMappingTransformation.getClassOfMappedEObject(), currentMappingTransformation)
			}else{
				logger.warn("current instance " + currentInstance + " is not an instance of " +
					 typeof(EObjectChangeImpl).name + " This should not happen...")
			}
		}
	}
	
}