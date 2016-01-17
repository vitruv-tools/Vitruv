package edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import org.apache.log4j.Logger
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaHelper.*

class TUIDUpdateHelper {
	private static final Logger LOGGER = Logger.getLogger(TUIDUpdateHelper)
	private Map<EObject, Map<CorrespondenceInstance, List<TUID>>> oldTUIDMap = newHashMap
	
	public def addObjectToUpdate(CorrespondenceInstance ci, EObject eObject) {
		oldTUIDMap
			.getOrPut(eObject, [newHashMap])
			.getOrPut(ci, [newArrayList])
			.add(ci.calculateTUIDFromEObject(eObject))
	}
	
	public def updateObject(CorrespondenceInstance ci, EObject eObject) {
		if (!oldTUIDMap.containsKey(eObject)) {
			LOGGER.info("EObject " + eObject.toString() + " not in old tuid map")
		} else {
			val ciToTUIDs = oldTUIDMap.get(eObject)
			for (tuid : (ciToTUIDs.get(ci) ?: #[])) {
				ci.updateTUID(tuid, eObject)
			}
			oldTUIDMap.get(eObject)?.remove(eObject)
		}
	}
	
	public def updateAll() {
		for (entry : oldTUIDMap.entrySet) {
			val eObject = entry.key
			val ciToTUIDs = entry.value
			for (ciAndTUIDs : ciToTUIDs.entrySet) {
				for (tuid : ciAndTUIDs.value) {
					ciAndTUIDs.key.updateTUID(tuid, eObject)
				}
			}			
		}
	}

}