package tools.vitruv.dsls.mapping.helpers

import tools.vitruv.framework.tuid.Tuid
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.apache.log4j.Logger
import static extension tools.vitruv.framework.util.bridges.JavaHelper.*
import tools.vitruv.framework.correspondence.CorrespondenceModel

class TuidUpdateHelper {
	private static final Logger LOGGER = Logger.getLogger(TuidUpdateHelper)
	private Map<EObject, Map<CorrespondenceModel, List<Tuid>>> oldTuidMap = newHashMap
	
	public def addObjectToUpdate(CorrespondenceModel ci, EObject eObject) {
		oldTuidMap
			.getOrPut(eObject, [newHashMap])
			.getOrPut(ci, [newArrayList])
			.add(ci.calculateTuidFromEObject(eObject))
	}
	
	public def updateObject(CorrespondenceModel ci, EObject eObject) {
		if (!oldTuidMap.containsKey(eObject)) {
			LOGGER.info("EObject " + eObject.toString() + " not in old tuid map")
		} else {
			val ciToTuids = oldTuidMap.get(eObject)
			for (tuid : (ciToTuids.get(ci) ?: #[])) {
				tuid.updateTuid(eObject)
			}
			oldTuidMap.get(eObject)?.remove(eObject)
		}
	}
	
	public def updateAll() {
		for (entry : oldTuidMap.entrySet) {
			val eObject = entry.key
			val ciToTuids = entry.value
			for (ciAndTuids : ciToTuids.entrySet) {
				for (tuid : ciAndTuids.value) {
					tuid.updateTuid(eObject)
				}
			}			
		}
	}

}