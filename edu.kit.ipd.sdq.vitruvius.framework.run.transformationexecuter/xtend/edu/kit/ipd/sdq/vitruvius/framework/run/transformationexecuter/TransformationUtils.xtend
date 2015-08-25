package edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import java.util.HashSet

class TransformationUtils {
	
	val private static Logger logger = Logger.getLogger(TransformationUtils)
	
	private new() {
	}

	def public static EAttribute getAttributeByNameFromEObject(String attributeName, EObject eObject) {
		return eObject.eClass.getEAllAttributes.filter[attribute|attribute.name.equals(attributeName)].iterator.next
	}

	def public static EReference getReferenceByNameFromEObject(String referenceName, EObject eObject) {
		return eObject.eClass.EAllReferences.filter[reference|reference.name.equals(referenceName)].iterator.next
	}

	def static removeCorrespondenceAndAllObjects(EObject[] eObjects, Blackboard blackboard) {
		eObjects.forEach[eObject|removeCorrespondenceAndAllObjects(eObject, blackboard)]
	}

	def static removeCorrespondenceAndAllObjects(EObject object, Blackboard blackboard) {
		val correspondencens = blackboard.correspondenceInstance.
			removeDirectAndChildrenCorrespondencesOnBothSides(object)
		for (correspondence : correspondencens) {
			if (correspondence instanceof EObjectCorrespondence) {
				val eoc = correspondence as EObjectCorrespondence
				resolveAndRemoveEObject(eoc.elementATUID, blackboard)
				resolveAndRemoveEObject(eoc.elementBTUID, blackboard)
			}
		}
	}

	def private static resolveAndRemoveEObject(TUID tuid, Blackboard blackboard) {
		val eObject = blackboard.correspondenceInstance.resolveEObjectFromTUID(tuid)
		if (null != eObject) {
			EcoreUtil.remove(eObject)
		}
	}

	def static deleteFile(VURI vuri) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	def static saveNonRootEObject(EObject... eObjects) {
		val resources = new HashSet
		eObjects.forEach [ eObject |
			if (null == eObject || null == eObject.eResource) {
				logger.warn("Can not save eObject " + eObject + " cause the resource of the eObject is null")
			}
			resources.add(eObject.eResource)
		]
		resources.forEach[resource|EcoreResourceBridge.saveResource(resource)]
	}

}
