package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.Accessors

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaHelper.*
import org.apache.log4j.Logger

@Accessors(PUBLIC_GETTER)
class MappingExecutionState extends TransformationResult {
	private static final Logger LOGGER = Logger.getLogger(MappingExecutionState)
	private Map<EObject, Map<CorrespondenceInstance, List<TUID>>> oldTUIDMap = newHashMap
	
	private final List<EObject> createdEObjects = newArrayList
	private final List<EObject> changedEObjects = newArrayList
	private final List<EObject> deletedEObjects = newArrayList
	private final List<Correspondence> createdCorrespondences = newArrayList
	
	private final MappedCorrespondenceInstance mci;
	private final Blackboard bb
	
	private final List<Resource> resourcesToSave = newArrayList
	
	new(Blackboard bb) {
		super()
		this.mci = bb.correspondenceInstance as MappedCorrespondenceInstance;
		this.bb = bb;
	}
	
	public def addObjectForTuidUpdate(EObject eObject) {
		oldTUIDMap
			.getOrPut(eObject, [newHashMap])
			.getOrPut(mci, [newArrayList])
			.add(mci.calculateTUIDFromEObject(eObject))
	}
	
	public def updateTuidOfCachedObject(EObject eObject) {
		if (!oldTUIDMap.containsKey(eObject)) {
//			LOGGER.info("EObject " + eObject.toString() + " not in old tuid map")
		} else {
			val ciToTUIDs = oldTUIDMap.get(eObject)
			for (tuid : (ciToTUIDs.get(mci) ?: #[])) {
				mci.updateTUID(tuid, eObject)
			}
			oldTUIDMap.get(eObject)?.remove(eObject)
		}
	}
	
	public def updateAllTuidsOfCachedObjects() {
		for (entry : oldTUIDMap.entrySet) {
			val eObject = entry.key
			if (eObject != null) { // TODO: do this correctly
				val ciToTUIDs = entry.value
				for (ciAndTUIDs : ciToTUIDs.entrySet) {
					for (tuid : ciAndTUIDs.value) {
						ciAndTUIDs.key.updateTUID(tuid, eObject)
					}
				}
			}
		}
		oldTUIDMap.clear
	}
	
	public def addCreatedEObject(EObject eObject) {
		this.createdEObjects.add(eObject)
	}
	
	public def addChangedEObject(EObject eObject) {
		this.changedEObjects.add(eObject);
	}
	
	public def addResourceToSave(EObject eObject) {
		if (eObject.eResource != null) {
			this.resourcesToSave.add(eObject.eResource)
		}
	}
	
	public def addCreatedCorrespondence(Correspondence correspondence) {
		this.createdCorrespondences.add(correspondence);
	}
	
	public def record(EObject... eObjects) {
		eObjects.forEach[addObjectForTuidUpdate]
		eObjects.forEach[addChangedEObject]
	}
	
	public def getAllAffectedEObjects() {
		return (createdEObjects + changedEObjects).toSet
	}
	
	public def persistAll() {
		saveAffectedEObjects(changedEObjects, bb.modelProviding)
		
		for (res : resourcesToSave) {
			bb.modelProviding.saveExistingModelInstanceOriginal(VURI::getInstance(res))
			bb.modelProviding.forceReloadModelInstanceOriginalIfExisting(VURI::getInstance(res))
			println("Saved: " + VURI::getInstance(res).toString)
		}
		
		resourcesToSave.clear
		changedEObjects.clear // TODO: correct?
	}
	
	// temporarily from CommandExecutingImpl
	def private void saveAffectedEObjects(List<? extends Object> affectedObjects, ModelProviding modelProviding) {
		val Set<VURI> vurisToSave = new HashSet<VURI>()
		for (Object object : affectedObjects) {
			if (object instanceof EObject) {
				val EObject eObject = object as EObject
				if ((null !== eObject) && (null !== eObject.eResource())) {
					vurisToSave.add(VURI::getInstance(eObject.eResource()))
				}

			}

		}
		for (VURI vuri : vurisToSave) {
			println("Saving: " + vuri.toString)
			modelProviding.saveExistingModelInstanceOriginal(vuri)
		}

	}
}