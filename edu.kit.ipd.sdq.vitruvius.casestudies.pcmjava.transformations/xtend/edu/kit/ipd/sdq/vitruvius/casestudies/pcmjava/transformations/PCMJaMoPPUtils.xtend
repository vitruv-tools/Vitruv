package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import java.util.Map
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.JaMoPP2PCMUtils
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.members.Method

class PCMJaMoPPUtils {
	private static val Logger logger = Logger.getLogger(PCMJaMoPPUtils.simpleName)

	protected new() {
	}

	/**
	 * Checks whether the affectedAttribute is in the featureCorrespondenceMap and returns all corresponding objects, 
	 * if any. otherwise null is returned.
	 */
	def static checkKeyAndCorrespondingObjects(EObject eObject, EStructuralFeature affectedEFeature,
		Map<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		CorrespondenceInstance correspondenceInstance) {
		if (!featureCorrespondenceMap.containsKey(affectedEFeature)) {
			logger.debug("no feature correspondence found for affectedEeature: " + affectedEFeature)
			return null
		}
		var correspondingEObjects = correspondenceInstance.getAllCorrespondingEObjects(eObject)
		if (correspondingEObjects.nullOrEmpty) {
			logger.info("No corresponding objects found for " + eObject)
		}
		return correspondingEObjects
	}

	def static updateNameAttribute(
		Set<EObject> correspondingEObjects,
		Object newValue,
		EStructuralFeature affectedFeature,
		ClaimableMap<EStructuralFeature, EStructuralFeature> featureCorrespondenceMap,
		CorrespondenceInstance correspondenceInstance,
		boolean markFilesOfChangedEObjectsAsFilesToSave,
		Set<Class<? extends EObject>> classesOfRootObjects
	) {
		val EStructuralFeature eStructuralFeature = featureCorrespondenceMap.claimValueForKey(affectedFeature)
		var transformationChangeResult = new TransformationChangeResult
		
		val boolean rootAffected = correspondingEObjects.exists[eObject|
			eObjectInstanceOfRootEObject(eObject, classesOfRootObjects)]
		if (rootAffected) {
			logger.error("The method updateNameattribut is not able to rename root objects")
			return transformationChangeResult
		}
		for (EObject correspondingObject : correspondingEObjects) {
			if (null == correspondingObject || null == correspondingObject.eResource) {
				logger.error(
					"corresponding object is null or correspondingObject is not contained in a resource!: " +
						correspondingObject + " (object.isProxy=" + correspondingObject.eIsProxy + ")")
			} else {
				val TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(correspondingObject)
				correspondingObject.eSet(eStructuralFeature, newValue)
				transformationChangeResult.addCorrespondenceToUpdate(correspondenceInstance, oldTUID,
					correspondingObject, null)
				if (markFilesOfChangedEObjectsAsFilesToSave) {
					transformationChangeResult.existingObjectsToSave.add(correspondingObject)
				}
			}
		}
		transformationChangeResult
	}

	def private static boolean eObjectInstanceOfRootEObject(EObject object, Set<Class<? extends EObject>> classes) {
		for (c : classes) {
			if (c.isInstance(object)) {
				return true
			}
		}
		return false
	}

	def static removeEObjectAndAddCorrespondencesToDelete(EObject[] objectsToDelete,
		CorrespondenceInstance correspondenceInstance, TransformationChangeResult tcr) {
		for (eObjectToDelete : objectsToDelete) {
			val tuidToRemove = correspondenceInstance.calculateTUIDFromEObject(eObjectToDelete)
			tcr.addCorrespondenceToDelete(correspondenceInstance, tuidToRemove)
			if(null != eObjectToDelete.eContainer){
				tcr.existingObjectsToSave.add(eObjectToDelete.eContainer)
			}
			EcoreUtil.delete(eObjectToDelete)
		}
	}

	/**
	 * Signatures are considered equal if methods have the same name, the same parameter types and the same return type
	 * We do not consider modifiers (e.g. public or private here)
	 */
	public static def boolean hasSameSignature(Method method1, Method method2) {
		if (method1 == method2) {
			return true
		}
		if (!method1.name.equals(method1.name)) {
			return false
		}
		if (!method1.typeReference.hasSameTargetReference(method2.typeReference)) {
			return false
		}
		if (method1.parameters.size != method2.parameters.size) {
			return false
		}
		var int i = 0
		for (param1 : method1.parameters) {
			if (!hasSameTargetReference(param1.typeReference, method2.parameters.get(i).typeReference)) {
				return false
			}
			i++
		}
		return true
	}

	private static def boolean hasSameTargetReference(TypeReference reference1, TypeReference reference2) {
		if (reference1 == reference2 || reference1.equals(reference2)) {
			return true
		}
		val target1 = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(reference1)
		val target2 = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(reference2)
		return target1 == target2 || target1.equals(target2)
	}

}
