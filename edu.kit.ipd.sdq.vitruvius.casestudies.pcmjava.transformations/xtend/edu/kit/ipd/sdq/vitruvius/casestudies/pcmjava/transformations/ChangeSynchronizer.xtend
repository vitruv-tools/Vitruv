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
import org.apache.log4j.Logger

public class ChangeSynchronizer {

	val private static final Logger logger = Logger.getLogger(ChangeSynchronizer.simpleName)

	val private ClaimableMap<Class<?>, EObjectMappingTransformation> mappingTransformations
	var private CorrespondenceInstance correspondenceInstance

	new() {
		mappingTransformations = new ClaimableHashMap<Class<?>, EObjectMappingTransformation>()
		fillTransformationMap()
	}

	def setCorrespondenceInstance(CorrespondenceInstance correspondenceInstance) {
		this.correspondenceInstance = correspondenceInstance
		for (mappingTransformation : mappingTransformations.values) {
			mappingTransformation.setCorrespondenceInstance(correspondenceInstance)
		}
	}

	def public synchronizeChange(EChange change) {
		syncChange(change)
	}

	def private dispatch syncChange(EChange change) {
		logger.error("No syncChang method found for change " + change + ". Change not synchronized")
	}

	def private dispatch syncChange(CreateRootEObject createRootEObject) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(createRootEObject.changedEObject.class).
			addEObject(createRootEObject.changedEObject)
	}

	def private dispatch syncChange(DeleteRootEObject deleteRootEObject) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteRootEObject.changedEObject.class).
			removeEObject(deleteRootEObject.changedEObject)
	}

	def private dispatch syncChange(CreateNonRootEObject<?> createNonRootEObject) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(createNonRootEObject.changedEObject.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(createNonRootEObject.affectedEObject.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(createNonRootEObject.changedEObject.class).
			addEObject(createNonRootEObject.changedEObject)
		mappingTransformations.claimForMappedClassOrImplementingInterface(createNonRootEObject.affectedEObject.class).
			updateEReference(createNonRootEObject.affectedEObject, createNonRootEObject.affectedFeature,
				createNonRootEObject.newValue)
	}

	def private dispatch syncChange(DeleteNonRootEObject<?> deleteNonRootEObject) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteNonRootEObject.changedEObject.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteNonRootEObject.affectedEObject.class)
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteNonRootEObject.changedEObject.class).
			addEObject(deleteNonRootEObject.changedEObject)
		mappingTransformations.claimForMappedClassOrImplementingInterface(deleteNonRootEObject.affectedEObject.class).
			updateEReference(deleteNonRootEObject.affectedEObject, deleteNonRootEObject.affectedFeature,
				deleteNonRootEObject.newValue)
	}

	def private dispatch syncChange(UpdateEAttribute<?> updateEAttribute) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(updateEAttribute.affectedEObject.class).
			updateEAttribute(updateEAttribute.affectedEObject, updateEAttribute.affectedFeature,
				updateEAttribute.newValue)
	}

	def private dispatch syncChange(UpdateEReference<?> updateEReference) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(updateEReference.affectedEObject.class).
			updateEReference(updateEReference.affectedEObject, updateEReference.affectedFeature,
				updateEReference.newValue)
	}

	def private dispatch syncChange(UpdateEContainmentReference<?> updateEContainmentReference) {
		mappingTransformations.claimForMappedClassOrImplementingInterface(updateEContainmentReference.affectedEObject.class).
			updateEContainmentReference(updateEContainmentReference.affectedEObject,
				updateEContainmentReference.affectedFeature, updateEContainmentReference.newValue)
	}

	def private dispatch syncChange(UnsetEFeature<?> unsetEFeature) {
		logger.error("syncChange for UnsetEFeature<?> is not implemented yet...")
	}

	def private fillTransformationMap() {
		addMapping(new RepositoryMappingTransformation)
		addMapping(new OperationInterfaceMappingTransformation)

	//FIXME: use reflections instead of direct instantiation. The code below should do the trick, 
	//however the dependencies of Reflections have to be added to the classpath/plugin.
	/*val Reflections reflections = new Reflections(); 
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
		}*/
	}

	def private addMapping(EObjectMappingTransformation transformation) {
		transformation.setCorrespondenceInstance(correspondenceInstance)
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
