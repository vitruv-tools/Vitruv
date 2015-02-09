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
import de.uka.ipd.sdq.pcm.usagemodel.UsagemodelFactory

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

}
