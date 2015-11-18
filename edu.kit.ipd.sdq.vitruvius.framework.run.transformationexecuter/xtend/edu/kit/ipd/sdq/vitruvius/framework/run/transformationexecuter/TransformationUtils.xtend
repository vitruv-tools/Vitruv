package edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence

class TransformationUtils {

	//val private static Logger logger = Logger.getLogger(TransformationUtils)

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
			removeCorrespondencesOfEObjectAndChildrenOnBothSides(object)
		for (correspondence : correspondencens) {
				resolveAndRemoveEObject(correspondence.elementATUID, blackboard)
				resolveAndRemoveEObject(correspondence.elementBTUID, blackboard)
		}
	}

	def private static resolveAndRemoveEObject(TUID tuid, Blackboard blackboard) {
		try {
			val eObject = blackboard.correspondenceInstance.resolveEObjectFromTUID(tuid)
			if (null != eObject) {
				EcoreUtil.delete(eObject)
			}
		} catch (RuntimeException e ) {
			// ignore runtime exception during object deletion
		}
	}
}
