package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EcoreResourceBridge
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import java.util.HashSet
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

class TransformationUtils {

	val private static Logger logger = Logger.getLogger(TransformationUtils.name)

	val private static ResourceSet resourceSet = new ResourceSetImpl

	private new() {
	}

	def static saveRootEObject(EObject rootEObject, VURI path) {
		val Resource resource = resourceSet.createResource(path.EMFUri)
		EcoreResourceBridge.saveEObjectAsOnlyContent(rootEObject, resource)
	}

	def static findCorrespondingEObject(CorrespondenceInstance correspondenceInstance, EObject eObject) {
		val Set<Correspondence> correspondences = correspondenceInstance.getAllCorrespondences(eObject)
		val Set<EObject> correspondingEObjects = new HashSet<EObject>()
		for (correspondece : correspondences) {
			correspondece.allInvolvedEObjects.filter[involvedEObj|involvedEObj != eObject].forEach[involvedEObj|
				correspondingEObjects.add(involvedEObj)]
		}
		return correspondingEObjects
	}

	def static findCorrespondingEObjectIfUnique(CorrespondenceInstance correspondenceInstance, EObject eObject) {
		val Set<EObject> correspondingObjects = findCorrespondingEObject(correspondenceInstance, eObject)
		if (1 != correspondingObjects.size) {
			throw new RuntimeException(
				"findCorrespondingEObjectIfUnique failed: " + correspondingObjects.size +
					" corresponding objects found (expected 1)" + correspondingObjects)
		}
		return correspondingObjects.iterator.next
	}

	def static <T> findCorrespondingEObjectByTypeIfUnique(CorrespondenceInstance correspondenceInstance, EObject eObject,
		Object corresponsingType) {
		val EObject correspondingObject = findCorrespondingEObjectIfUnique(correspondenceInstance, eObject)
		return correspondingObject.class.cast(corresponsingType)

	//		if (!correspondingObject.class.(corresponsingType)) {
	//			throw new RuntimeException(
	//				"findCorrespondingEObjectByTypeIfUnique failed: correspondingObject (" + correspondingObject +
	//					") is not from expected type" + corresponsingType)
	//		}
	//		return correspondingObject.class.cast(corresponsingType)
	}

	def static findParentCorrespondenceForEObject(CorrespondenceInstance correspondenceInstance, EObject eObject) {
		val Set<Correspondence> possibleParentCorrespondences = correspondenceInstance.getAllCorrespondences(eObject)
		if (possibleParentCorrespondences.empty) {
			logger.info("No parent correspondence found for eObject" + eObject + " returning null")
			return null
		} else if (1 > possibleParentCorrespondences.size) {
			logger.info(
				"Found " + possibleParentCorrespondences.size + " parent correspondences for eObject " + eObject +
					". Returning the first")
		}
		return possibleParentCorrespondences.iterator.next
	}

}
