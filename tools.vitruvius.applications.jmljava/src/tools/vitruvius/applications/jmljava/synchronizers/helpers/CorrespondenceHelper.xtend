package tools.vitruvius.applications.jmljava.synchronizers.helpers

import tools.vitruvius.framework.correspondence.CorrespondenceModel
import tools.vitruvius.framework.correspondence.Correspondence
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import static extension tools.vitruvius.framework.util.bridges.CollectionBridge.*

class CorrespondenceHelper {
	
	private static val LOGGER = Logger.getLogger(CorrespondenceHelper)
	
	public static def <T extends EObject> T getSingleCorrespondingEObjectOfType(CorrespondenceModel ci, EObject subject, Class<T> type) {
		val correspondingEObjects = ci.getCorrespondingEObjects(subject.toList).filter(type)
		if (correspondingEObjects.empty) {
			return null
		}
		if (correspondingEObjects.size > 1) {
			LOGGER.warn("More than one corresponding EObject of type " + type.simpleName + " found for " + subject + ". Using only one of there objects.")
		}
		return correspondingEObjects.get(0)
	}
	
	public static def getSingleCorrespondence(CorrespondenceModel ci, EObject srcElement, EObject dstElement) {
		val corrs = ci.getCorrespondences(srcElement.toList)
		val sameTypeCorrs = corrs.filter(Correspondence)
		val dstTUID = ci.calculateTUIDsFromEObjects(dstElement.toList).claimOne
		val result =  sameTypeCorrs.findFirst[BTUIDs.exists[it.equals(dstTUID)] || ATUIDs.exists[it.equals(dstTUID)]]
		return result
		
		//return ci.getAllCorrespondences(srcElement).filter(Correspondence).findFirst[elementBTUID == dstElement.TUID || elementATUID == dstElement.TUID]
	}
	
	public static def <T extends EObject> T resolveEObjectByItsTUID(CorrespondenceModel ci, T objectToFind) {
		val root = EcoreUtil.getRootContainer(objectToFind)
		// TODO SS does CorrespondenceHelper.resolveEObjectByItsTUID return its input objectToFind?
		val tuid = ci.calculateTUIDsFromEObjects(objectToFind.toList).claimOne;
		return ci.resolveEObjectFromTUID(tuid) as T;
	}
}