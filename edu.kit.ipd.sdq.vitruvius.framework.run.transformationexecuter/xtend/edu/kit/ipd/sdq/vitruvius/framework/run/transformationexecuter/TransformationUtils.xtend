package edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import org.eclipse.emf.ecore.EReference

class TransformationUtils {
	private new(){
	}
	
	def public static TransformationChangeResult createTransformationChangeResult(EObject[] newRootObjectsToSave, 
			VURI[] objectsToDelete, EObject[] existingObjectsToSave){
		var transformationChangeResult = new TransformationChangeResult()
		if(null != newRootObjectsToSave){
			transformationChangeResult.newRootObjectsToSave.addAll(newRootObjectsToSave)
		}
		if(null != objectsToDelete){
			transformationChangeResult.existingObjectsToDelete.addAll(objectsToDelete)
		}
		if(null != existingObjectsToSave){
			transformationChangeResult.existingObjectsToSave.addAll(existingObjectsToSave)
		}
		transformationChangeResult
	}
	
	def public static TransformationChangeResult createTransformationChangeResultForNewRootEObjects(EObject[] newRootEObjects){
		createTransformationChangeResult(newRootEObjects, null, null)
	}
	
	def public static TransformationChangeResult createTransformationChangeResultForEObjectsToSave(EObject[] eObjectsToSave){
		createTransformationChangeResult(null, null, eObjectsToSave)
	}
	
	def public static TransformationChangeResult createTransformationChangeResultForEObjectsToDelete(VURI[] eObjectsToDelete){
		createTransformationChangeResult(null, eObjectsToDelete, null)
	}
	
	def public static TransformationChangeResult createEmptyTransformationChangeResult(){
		return createTransformationChangeResult(null, null, null)
	}
	
	def public static EAttribute getAttributeByNameFromEObject(String attributeName, EObject eObject) {
		return eObject.eClass.getEAllAttributes.filter[attribute|attribute.name.equals(attributeName)].iterator.next
	}
	
	def public static EReference getReferenceByNameFromEObject(String referenceName, EObject eObject) {
		return eObject.eClass.EAllReferences.filter[reference|reference.name.equals(referenceName)].iterator.next
	}
}
