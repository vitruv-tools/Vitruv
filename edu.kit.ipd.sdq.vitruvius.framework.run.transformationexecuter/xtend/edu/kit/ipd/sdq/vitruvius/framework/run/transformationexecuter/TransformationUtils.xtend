package edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil

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
	
	def static removeCorrespondenceAndAllObjects(EObject[] eObjects, Blackboard blackboard){
		eObjects.forEach[eObject|removeCorrespondenceAndAllObjects(eObject, blackboard)]
	}
	
	def static removeCorrespondenceAndAllObjects(EObject object, Blackboard blackboard) {
	    val correspondencens = blackboard.correspondenceInstance.removeDirectAndChildrenCorrespondencesOnBothSides(object)
	    for(correspondence : correspondencens){
	    	if(correspondence instanceof EObjectCorrespondence){
	    		val eoc = correspondence as EObjectCorrespondence 
	    		resolveAndRemoveEObject(eoc.elementATUID, blackboard)
	    		resolveAndRemoveEObject(eoc.elementBTUID, blackboard)	    		
	    	}
	    }
	}
	
	def private static resolveAndRemoveEObject(TUID tuid, Blackboard blackboard){
		val eObject = blackboard.correspondenceInstance.resolveEObjectFromTUID(tuid)
		if(null != eObject){
			EcoreUtil.remove(eObject)
		}
	}
	
	def static deleteFile(VURI vuri) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}
