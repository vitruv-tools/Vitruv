package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.helpers

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.metamodels.JMLTUIDCalculatorAndResolver
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.metamodels.JaMoPPTUIDCalculatorAndResolver
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TUIDCalculatorAndResolver
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil

class CorrespondenceHelper {
	
	private static val LOGGER = Logger.getLogger(CorrespondenceHelper)
	
	public static def <T extends EObject> T getSingleCorrespondingEObjectOfType(CorrespondenceInstance ci, EObject subject, Class<T> type) {
		val correspondingEObjects = ci.getAllCorrespondingEObjects(subject).filter(type)
		if (correspondingEObjects.empty) {
			return null
		}
		if (correspondingEObjects.size > 1) {
			LOGGER.warn("More than one corresponding EObject of type " + type.simpleName + " found for " + subject + ". Using only one of there objects.")
		}
		return correspondingEObjects.get(0)
	}
	
	public static def getSingleCorrespondence(CorrespondenceInstance ci, EObject srcElement, EObject dstElement) {
		val corrs = ci.getAllCorrespondences(srcElement)
		val sameTypeCorrs = corrs.filter(SameTypeCorrespondence)
		val result =  sameTypeCorrs.findFirst[elementBTUID.equals(dstElement.TUID) || elementATUID.equals(dstElement.TUID)]
		return result
		
		//return ci.getAllCorrespondences(srcElement).filter(SameTypeCorrespondence).findFirst[elementBTUID == dstElement.TUID || elementATUID == dstElement.TUID]
	}
	
	public static def getTUID(EObject obj) {
		var TUIDCalculatorAndResolver tuidGenerator = null
		if (obj.eClass.getEPackage.nsURI.toLowerCase.contains("jml")) {
			tuidGenerator = new JMLTUIDCalculatorAndResolver()
		} else {
			tuidGenerator = new JaMoPPTUIDCalculatorAndResolver()
		}
		return TUID.getInstance(tuidGenerator.calculateTUIDFromEObject(obj))
	} 
	
	public static def <T extends EObject> T resolveEObjectByItsTUID(CorrespondenceInstance ci, T objectToFind) {
		val root = EcoreUtil.getRootContainer(objectToFind)
		return ci.resolveEObjectFromTUID(objectToFind.TUID) as T;
	}
}