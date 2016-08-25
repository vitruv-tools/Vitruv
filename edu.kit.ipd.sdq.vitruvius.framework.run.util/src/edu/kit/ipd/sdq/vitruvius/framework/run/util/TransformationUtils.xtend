package edu.kit.ipd.sdq.vitruvius.framework.run.util

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel

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

	def public static deleteNonRootEObjectInList(EObject affectedEObject, EObject oldEObject, CorrespondenceModel correspondenceModel, Set<Class<?>> rootObjects) {
		val transformationResult = new TransformationResult
		removeCorrespondenceAndAllObjects(oldEObject, affectedEObject, correspondenceModel, rootObjects)
		return transformationResult
	}

	def static TransformationResult removeCorrespondenceAndAllObjects(EObject object, EObject exRootObject,
		CorrespondenceModel correspondenceModel, Set<Class<?>> rootObjects) {
		var Set<Correspondence> correspondences = null
		if (null != exRootObject) {
			val rootTUID = correspondenceModel.calculateTUIDFromEObject(exRootObject)
			val String prefix = rootTUID.toString
			EcoreUtil.remove(object)
			val tuid = correspondenceModel.calculateTUIDFromEObject(object, exRootObject, prefix)
			correspondences = correspondenceModel.
				removeCorrespondencesThatInvolveAtLeastAndDependendForTUIDs(tuid.toSet)
		} else {
			correspondences = correspondenceModel.
				removeCorrespondencesThatInvolveAtLeastAndDependend(object.toSet)
		}
		if (correspondences.nullOrEmpty) {
			logger.debug("Could not")
		}
		val transformationResult = new TransformationResult
		for (correspondence : correspondences) {
			resolveAndRemoveEObject(correspondence.ATUIDs, correspondenceModel, transformationResult, rootObjects)
			resolveAndRemoveEObject(correspondence.BTUIDs, correspondenceModel, transformationResult, rootObjects)
		}
		return transformationResult
	}

	def private static resolveAndRemoveEObject(Iterable<TUID> tuids, CorrespondenceModel correspondenceModel,
		TransformationResult transformationResult, Set<Class<?>> rootObjectClasses) {
		for (tuid : tuids) {
			try {
				val eObject = correspondenceModel.resolveEObjectFromTUID(tuid)
				if (null != eObject) {
					EcoreUtil.delete(eObject)
				}
				if(eObject.isInstanceOfARootClass(rootObjectClasses)){
					val vuri = tuid.getVURIFromTUID()
					transformationResult.addVURIToDeleteIfNotNull(vuri)
				}
			} catch (RuntimeException e) {
				// ignore runtime exception during object deletion
			}
		}
	}
	
	def private static boolean isInstanceOfARootClass(EObject eObject, Set<Class<?>> rootObjectClasses){
		for(rootClass : rootObjectClasses){
			if(rootClass.isInstance(eObject)){
				return true
			}
		}
		return false
	}
	
	/**
	 * returns the VURI of the second part of the TUID (which should be the VURI String) 
	 */
	def private static VURI getVURIFromTUID(TUID tuid) {
       val segments = tuid.toString.split(VitruviusConstants.TUIDSegmentSeperator)
       if( 2 <= segments.length){
			val key = segments.get(1)
       		return VURI.getInstance(key)       	
       }
       return null
    }
   
}
