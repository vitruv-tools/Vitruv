package tools.vitruv.framework.correspondence

import com.google.common.collect.Sets
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.VitruviusConstants
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.datatypes.ClaimableHashMap
import tools.vitruv.framework.util.datatypes.ClaimableMap
import tools.vitruv.framework.util.datatypes.Pair
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import java.util.function.Supplier
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.Resource.IOWrappedException
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension tools.vitruv.framework.util.bridges.CollectionBridge.*
import static extension tools.vitruv.framework.util.bridges.JavaBridge.*
import tools.vitruv.framework.correspondence.Correspondences
import tools.vitruv.framework.correspondence.Correspondence
import tools.vitruv.framework.tuid.TUID
import tools.vitruv.framework.correspondence.CorrespondenceFactory
import tools.vitruv.framework.tuid.TuidUpdateListener
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.metamodel.MetamodelPair
import tools.vitruv.framework.metamodel.Metamodel
import tools.vitruv.framework.metamodel.ModelRepository
import tools.vitruv.framework.util.datatypes.ModelInstance

// TODO move all methods that don't need direct instance variable access to some kind of util class
class CorrespondenceModelImpl extends ModelInstance implements InternalCorrespondenceModel, TuidUpdateListener {
	static final Logger logger = Logger::getLogger(typeof(CorrespondenceModelImpl).getSimpleName())
	final MetamodelPair mapping
	final ModelRepository modelProviding
	final Correspondences correspondences
	final ClaimableMap<TUID,Set<List<TUID>>> tuid2tuidListsMap
	protected final ClaimableMap<List<TUID>, Set<Correspondence>> tuid2CorrespondencesMap
	boolean changedAfterLastSave = false
	final Map<String, String> saveCorrespondenceOptions

	new(MetamodelPair mapping, ModelRepository modelProviding, VURI correspondencesVURI, Resource correspondencesResource) {
		super(correspondencesVURI, correspondencesResource)
		this.mapping = mapping
		this.modelProviding = modelProviding
		// TODO MK use MutatingListFixing... when necessary (for both maps!)
		this.tuid2tuidListsMap = new ClaimableHashMap<TUID,Set<List<TUID>>>()
		this.tuid2CorrespondencesMap = new ClaimableHashMap<List<TUID>, Set<Correspondence>>()
		this.saveCorrespondenceOptions = new HashMap<String, String>()
		this.saveCorrespondenceOptions.put(VitruviusConstants::getOptionProcessDanglingHref(),
			VitruviusConstants::getOptionProcessDanglingHrefDiscard())
		this.correspondences = loadAndRegisterCorrespondences(correspondencesResource)
		TuidManager.instance.addTuidUpdateListener(this);
	}

	override void addCorrespondence(Correspondence correspondence) {
		this.modelProviding.createRecordingCommandAndExecuteCommandOnTransactionalDomain(
				[ |
					addCorrespondenceToModel(correspondence)
					registerCorrespondence(correspondence)
					setChangeAfterLastSaveFlag()
					return null
				]);
	}

	def private void registerCorrespondence(Correspondence correspondence) {
		registerTUIDLists(correspondence)
		registerCorrespondenceForTUIDs(correspondence)
	}
	
	def private registerTUIDLists(Correspondence correspondence) {
		registerTUIDList(correspondence.getATUIDs)
		registerTUIDList(correspondence.getBTUIDs)
	}
	
	def private registerTUIDList(List<TUID> tuidList) {
		for (TUID tuid : tuidList) {
			var tuidLists = this.tuid2tuidListsMap.get(tuid)
			if (tuidLists == null) {
				tuidLists = new HashSet<List<TUID>>()
				this.tuid2tuidListsMap.put(tuid,tuidLists)
			}
			tuidLists.add(tuidList)
		}
	}

	def private void addCorrespondenceToModel(Correspondence correspondence) {
		var EList<Correspondence> correspondenceListForAddition = this.correspondences.getCorrespondences()
		correspondenceListForAddition.add(correspondence)
	}
	
	override calculateTUIDFromEObject(EObject eObject) {
		val Metamodel metamodel = eObject.getMetamodelForEObject()
		 if (null == metamodel){
		 	return null 
		 }
         return TUID::getInstance(metamodel.calculateTUIDFromEObject(eObject))
	}
	
	@Deprecated
	override calculateTUIDFromEObject(EObject eObject, EObject virtualRootObject, String prefix) {
		 val Metamodel metamodel = eObject.getMetamodelForEObject()
		 if(null == metamodel){
		 	return null 
		 }
		 if(null == virtualRootObject || null == prefix){
		 	logger.info("virtualRootObject or prefix is null. Using standard calculation method for EObject " + eObject)
         	return TUID::getInstance(metamodel.calculateTUIDFromEObject(eObject))
     	}
     	return TUID::getInstance(metamodel.calculateTUIDFromEObject(eObject, virtualRootObject, prefix))
	}
	
	def private getMetamodelForEObject(EObject eObject){
		var Metamodel metamodel = null
		if (this.mapping.getMetamodelA().hasMetaclassInstances(eObject.toList)) {
			metamodel = this.mapping.getMetamodelA()
		}
		if (this.mapping.getMetamodelB().hasMetaclassInstances(eObject.toList)) {
			metamodel = this.mapping.getMetamodelB()
		}
		if (metamodel === null) {
			logger.warn('''EObject: '«»«eObject»' is neither an instance of MM1 nor an instance of MM2. '''.toString)
			return null
		}
		return metamodel
	}

	override List<TUID> calculateTUIDsFromEObjects(List<EObject> eObjects) {
		return eObjects.mapFixed[calculateTUIDFromEObject(it)].toList
	}

	override def boolean changedAfterLastSave() {
		return this.changedAfterLastSave
	}

	
	override Correspondence claimUniqueCorrespondence(List<EObject> aEObjects, List<EObject> bEObjects) {
		 val correspondences = getCorrespondences(aEObjects)
		 for (Correspondence correspondence : correspondences) {
		 	val correspondingBs = if (correspondence.^as.containsAll(aEObjects)) correspondence.bs else correspondence.^as
		 	if (correspondingBs != null && correspondingBs.equals(bEObjects)) {
		 		return correspondence;
		 	}
		 }
		 throw new RuntimeException("No correspondence for '" + aEObjects + "' and '" + bEObjects + "' was found!");
	}
	
	@Deprecated
	override Correspondence createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2) {
		return createAndAddManualCorrespondence(eObjects1, eObjects2)
	}

	override  createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2, 
		Supplier<Correspondence> correspondenceCreator) {
		val Correspondence correspondence = correspondenceCreator.get
		createAndAddCorrespondence(eObjects1, eObjects2, correspondence)
	}

	override Correspondence createAndAddManualCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2) {
		val correspondence = CorrespondenceFactory.eINSTANCE.createManualCorrespondence
		createAndAddCorrespondence(eObjects1, eObjects2, correspondence)
	} 

	def private createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2, Correspondence correspondence){
		setCorrespondenceFeatures(correspondence, eObjects1, eObjects2)
		addCorrespondence(correspondence)
		return correspondence
	}
	
	override Set<Correspondence> getCorrespondences(List<EObject> eObjects) {
		var List<TUID> tuids = calculateTUIDsFromEObjects(eObjects)
		return getCorrespondencesForTUIDs(tuids)
	}

	
	override Set<Correspondence> getCorrespondencesForTUIDs(List<TUID> tuids) {
		var Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(tuids)
		if (correspondences === null) {
			correspondences = new HashSet<Correspondence>()
			this.tuid2CorrespondencesMap.put(tuids, correspondences)
			registerTUIDList(tuids)
		}
		return correspondences
	}

	override Set<List<EObject>> getCorrespondingEObjects(List<EObject> eObjects) {
		this.getCorrespondingEObjects(Correspondence, eObjects);
	}

	override Set<List<EObject>> getCorrespondingEObjects(Class<? extends Correspondence> correspondenceType, List<EObject> eObjects) {
		var List<TUID> tuids = calculateTUIDsFromEObjects(eObjects)
		var Set<List<TUID>> correspondingTUIDLists = getCorrespondingTUIDs(correspondenceType, tuids)
		return resolveEObjectsSetsFromTUIDsSets(correspondingTUIDLists)
	}
	
	def private Set<List<TUID>> getCorrespondingTUIDs(Class<? extends Correspondence> correspondenceType, List<TUID> tuids) {
		var Set<Correspondence> allCorrespondences = getCorrespondencesForTUIDs(tuids)
		var Set<List<TUID>> correspondingTUIDLists = new HashSet<List<TUID>>(allCorrespondences.size())
		for (Correspondence correspondence : allCorrespondences.filter(correspondenceType)) {
			var List<TUID> aTUIDs = correspondence.getATUIDs()
			var List<TUID> bTUIDs = correspondence.getBTUIDs()
			if (aTUIDs === null || bTUIDs === null || aTUIDs.size == 0 || bTUIDs.size == 0) {
				throw new IllegalStateException(
					'''The correspondence '«»«correspondence»' links to an empty TUID '«»«aTUIDs»' or '«»«bTUIDs»'!'''.
						toString)
			}
			if (aTUIDs.equals(tuids)) {
				correspondingTUIDLists.add(bTUIDs)
			} else {
				correspondingTUIDLists.add(aTUIDs)
			}
		}
		return correspondingTUIDLists
	}
	
	override void saveModel() {
		try {
			EcoreResourceBridge::saveResource(getResource(), this.saveCorrespondenceOptions)
		} catch (IOException e) {
			throw new RuntimeException(
				'''Could not save correspondence instance '«»«this»' using the resource '«»«getResource()»' and the options '«»«this.saveCorrespondenceOptions»': «e»'''.
					toString)
		}
	}

	override MetamodelPair getMapping() {
		return this.mapping
	}

	def private Metamodel getMetamodelHavingTUID(String tuidString) {
		var Metamodel metamodel = null
		var Metamodel metamodelA = this.mapping.getMetamodelA()
		if (metamodelA.hasTUID(tuidString)) {
			metamodel = metamodelA
		}
		var Metamodel metamodelB = this.mapping.getMetamodelB()
		if (metamodelB.hasTUID(tuidString)) {
			metamodel = metamodelB
		}
		if (metamodel === null) {
			throw new IllegalArgumentException(
				'''The TUID '«»«tuidString»' is neither valid for «metamodelA» nor «metamodelB»'''.
					toString)
		}
		return metamodel
	}

	override boolean hasCorrespondences() {
		for (Set<Correspondence> correspondences : this.tuid2CorrespondencesMap.values()) {
			if (!correspondences.isEmpty()) {
				return true
			}
		};
		return false
	}

	override boolean hasCorrespondences(List<EObject> eObjects) {
		var List<TUID> tuids = calculateTUIDsFromEObjects(eObjects)
		var Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(tuids)
		return correspondences != null && correspondences.size() > 0
	}

	def private Correspondences loadAndRegisterCorrespondences(Resource correspondencesResource) {
		try {
			correspondencesResource.load(this.saveCorrespondenceOptions)
		} catch (IOWrappedException e) {
			if (e.cause instanceof Exception) {
				logger.trace(
					"Could not load correspondence resource - creating new correspondence instance resource."
				)
			}
		}
		// TODO implement lazy loading for correspondences because they may get really big
		var Correspondences correspondences = EcoreResourceBridge::getResourceContentRootIfUnique(getResource())?.dynamicCast(Correspondences, "correspondence model")
		if (correspondences === null) {
			correspondences = CorrespondenceFactory::eINSTANCE.createCorrespondences()
			correspondencesResource.getContents().add(correspondences)
		} else if (correspondences instanceof Correspondences) {
			registerLoadedCorrespondences(correspondences as Correspondences)
		} else {
			throw new RuntimeException(
				'''The unique root object '«»«correspondences»' of the correspondence model '«»«getURI()»' is not correctly typed!'''.
					toString)
		}
		correspondences.setCorrespondenceModel(this)
		return correspondences as Correspondences
	}

	def private void registerCorrespondenceForTUIDs(Correspondence correspondence) {
		val correspondencesForAs = getCorrespondencesForTUIDs(correspondence.getATUIDs)
		correspondencesForAs.add(correspondence)
		val correspondencesForBs = getCorrespondencesForTUIDs(correspondence.getBTUIDs)
		correspondencesForBs.add(correspondence)
	}

	def private void registerLoadedCorrespondences(Correspondences correspondences) {
		for (Correspondence correspondence : correspondences.getCorrespondences()) {
			registerCorrespondence(correspondence)
		}
	}

	def private void removeCorrespondenceFromMaps(Correspondence markedCorrespondence) {
		var List<TUID> aTUIDs = markedCorrespondence.getATUIDs
		var List<TUID> bTUIDs = markedCorrespondence.getBTUIDs
		removeTUID2TUIDListsEntries(aTUIDs)
		removeTUID2TUIDListsEntries(bTUIDs)
		this.tuid2CorrespondencesMap.get(aTUIDs).remove(markedCorrespondence);
		if (tuid2CorrespondencesMap.get(aTUIDs).empty) {
			tuid2CorrespondencesMap.remove(aTUIDs);
		}
		this.tuid2CorrespondencesMap.get(bTUIDs).remove(markedCorrespondence);
		if (tuid2CorrespondencesMap.get(bTUIDs).empty) {
			tuid2CorrespondencesMap.remove(bTUIDs);
		}
	}
	
	def private void removeTUID2TUIDListsEntries(List<TUID> tuids) {
		for (TUID tuid : tuids) {
			val tuidLists = this.tuid2tuidListsMap.get(tuid)
			tuidLists.remove(tuids)
		}
	}

	override Set<Correspondence> removeCorrespondencesAndDependendCorrespondences(Correspondence correspondence) {
		val markedCorrespondences = markCorrespondenceAndDependingCorrespondences(correspondence)
		removeMarkedCorrespondences(markedCorrespondences)
		return markedCorrespondences
	}
	
	private def Set<Correspondence> markCorrespondenceAndDependingCorrespondences(Correspondence correspondence) {
		var Set<Correspondence> markedCorrespondences = new HashSet<Correspondence>()
		markCorrespondenceAndDependingCorrespondencesRecursively(markedCorrespondences, correspondence)
		return markedCorrespondences
	}
	
	private def void markCorrespondenceAndDependingCorrespondencesRecursively(Set<Correspondence> markedCorrespondences, Correspondence correspondence) {
		markedCorrespondences.add(correspondence);
		// FIXME MK detect dependency cycles in correspondences already when the reference is updated
		for(dependingCorrespondence : correspondence.dependedOnBy) {
			markCorrespondenceAndDependingCorrespondencesRecursively(markedCorrespondences,dependingCorrespondence)
		}
	}
	
	private def removeMarkedCorrespondences(Iterable<Correspondence> markedCorrespondences) {
		for (Correspondence markedCorrespondence : markedCorrespondences) {
			removeCorrespondenceFromMaps(markedCorrespondence)
			EcoreUtil::remove(markedCorrespondence)
			setChangeAfterLastSaveFlag()
		}
	}
	
	override Set<Correspondence> removeCorrespondencesThatInvolveAtLeastAndDependend(Set<EObject> eObjects) {
		return removeCorrespondencesThatInvolveAtLeastAndDependendForTUIDs(eObjects.mapFixed[calculateTUIDFromEObject(it)].toSet)
	}

	override Set<Correspondence> removeCorrespondencesThatInvolveAtLeastAndDependendForTUIDs(Set<TUID> tuids) {
		val correspondences = getCorrespondencesThatInvolveAtLeastTUIDs(tuids)
		val markedCorrespondences = correspondences.mapFixed[markCorrespondenceAndDependingCorrespondences(it)].flatten
		removeMarkedCorrespondences(markedCorrespondences)
		return markedCorrespondences.toSet
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CorrespondenceModel#
	 * resetChangedAfterLastSave()
	 */
	override void resetChangedAfterLastSave() {
		this.changedAfterLastSave = false
	}

	override EObject resolveEObjectFromRootAndFullTUID(EObject root,
		String tuidString) {
		return getMetamodelHavingTUID(tuidString).
			resolveEObjectFromRootAndFullTUID(root, tuidString)
	}
	
	
	override List<EObject> resolveEObjectsFromTUIDs(List<TUID> tuids) {
		return tuids.mapFixed[resolveEObjectFromTUID(it)].toList
	}
	
	override Set<List<EObject>> resolveEObjectsSetsFromTUIDsSets(Set<List<TUID>> tuidLists) {
		return tuidLists.mapFixed[resolveEObjectsFromTUIDs(it)].toSet
	}
	
	override EObject resolveEObjectFromTUID(TUID tuid) {
		var String tuidString = tuid.toString()
		var Metamodel metamodel = getMetamodelHavingTUID(tuidString)
		var VURI vuri = metamodel.getModelVURIContainingIdentifiedEObject(tuidString)
		var EObject rootEObject = null
		var ModelInstance modelInstance = null
		if (vuri !== null) {
			modelInstance = this.modelProviding.getModel(vuri)
			rootEObject = modelInstance.getFirstRootEObject()
		}
		var EObject resolvedEobject = null
		try {
			// if the tuid is cached because it has no resource the rootEObject is null
			resolvedEobject = metamodel.
				resolveEObjectFromRootAndFullTUID(rootEObject, tuidString)
		} catch (IllegalArgumentException iae) {
			// do nothing - just try the solving again
		}
		if (null === resolvedEobject && modelInstance !== null) {
			// reload the model and try to solve it again
			modelInstance.load(null, true)
			rootEObject = modelInstance.getUniqueRootEObject()
			resolvedEobject = metamodel.
				resolveEObjectFromRootAndFullTUID(rootEObject, tuidString)
			if (null === resolvedEobject) {
				// if resolved EObject is still null throw an exception
				// TODO think about something more lightweight than throwing an exception
				throw new RuntimeException(
					'''Could not resolve TUID «tuidString» in eObject «rootEObject»'''.
						toString)
			}

		}
		if (null !== resolvedEobject && resolvedEobject.eIsProxy()) {
			EcoreUtil::resolve(resolvedEobject, getResource())
		}
		return resolvedEobject
	}

	def public void setChangeAfterLastSaveFlag() {
		this.changedAfterLastSave = true
	}

	def private void setCorrespondenceFeatures(
		Correspondence correspondence, List<EObject> eObjects1,
		List<EObject> eObjects2) {
		var aEObjects = eObjects1
		var bEObjects = eObjects2
		if (this.mapping.getMetamodelA().hasMetaclassInstances(bEObjects)) {
			// swap
			var tmp = aEObjects
			aEObjects = bEObjects
			bEObjects = tmp
		}
		var List<TUID> aTUIDs = calculateTUIDsFromEObjects(aEObjects)
		correspondence.getATUIDs().addAll(aTUIDs)
		var List<TUID> bTUIDs = calculateTUIDsFromEObjects(bEObjects)
		correspondence.getBTUIDs().addAll(bTUIDs)
	}

//	override void updateTUID(EObject oldEObject, EObject newEObject) {
//		// If the object has no TUID, do nothing
//		val metamodel = oldEObject?.metamodelForEObject;
//		if (metamodel == null || !metamodel.hasTUID(oldEObject)) {
//			return;
//		}
//		var TUID oldTUID = calculateTUIDsFromEObjects(oldEObject.toList).claimOne
//		this.updateTUID(oldTUID, newEObject)
//	}
//
//	override void updateTUID(TUID oldTUID, EObject newEObject) {
//		var TUID newTUID = calculateTUIDsFromEObjects(newEObject.toList).claimOne
//		updateTUID(oldTUID, newTUID)
//	}
//
//	 //FIXME note to MK: this currently only works if all key-lists in tuid2CorrespondencesMap only contain one element. 
//	 //If you implement an update function for list of TUIDs be careful since there could be the case that one TUID is contained 
//	 //in more than only one key-lists. My current guess is that we have to update all key-lists in which the TUID occurs
//	override void updateTUID(TUID oldTUID, TUID newTUID) {
//		var boolean sameTUID = if(oldTUID !== null) oldTUID.equals(newTUID) else newTUID === null
//		if (sameTUID || oldTUID === null) {
//			return;
//		}
//		var String oldTUIDString = oldTUID.toString()
//		
//		oldTUID.renameOrMoveLastSegment(newTUID)
//		var Metamodel metamodel = getMetamodelHavingTUID(oldTUIDString)
//		metamodel.removeIfRootAndCached(oldTUIDString)
//	}
	
	override getAllCorrespondencesWithoutDependencies() {
		this.correspondences.correspondences.filter[it.dependsOn == null || it.dependsOn.size == 0].toSet
	}
	
	override getCorrespondencesThatInvolveAtLeast(Set<EObject> eObjects) {
		return getCorrespondencesThatInvolveAtLeastTUIDs(eObjects.mapFixed[calculateTUIDFromEObject(it)].toSet)
	}
	
	override getCorrespondencesThatInvolveAtLeastTUIDs(Set<TUID> tuids) {
		val supTUIDLists = tuids?.mapFixed[this.tuid2tuidListsMap.get(it)].filterNull.flatten.filter[it.containsAll(tuids)]
		val corrit = supTUIDLists?.mapFixed[getCorrespondencesForTUIDs(it)]
		val flatcorr = corrit.flatten
		if(flatcorr.nullOrEmpty){
			logger.debug("could not find correspondences for tuids: " + tuids)
			return Sets.newHashSet
		}
		val corrset = flatcorr.toSet
		return corrset
	}
	
	override getAllCorrespondences() {
		return correspondences.correspondences
	}
	
	/**
	 * Gets the side of a correspondence for the given metamodel nsURI.
	 * @param correspondence the correspondence for which the correct side should be chosen
	 * @param mmNsUri the namespace URI for the requested side
	 * 
	 * @throws IllegalArgumentException if the <code>mmNsUri</code> is not the namespace URI of one of the mapped
	 * meta models.
	 * 
	 * @author Dominik Werle 
	 */
	override getTUIDsForMetamodel(Correspondence correspondence, String metamodelNamespaceUri) {
	 	if (mapping.getMetamodelA.nsURIs.contains(metamodelNamespaceUri)) {
	 		return correspondence.getATUIDs
	 	} else if (mapping.getMetamodelB.nsURIs.contains(metamodelNamespaceUri)) {
	 		return correspondence.getBTUIDs
	 	} else {
	 		throw new IllegalArgumentException('''Metamodel namespace URI "«metamodelNamespaceUri»" is not a namespace URI of one of the metamodels for the associated mapping''')
	 	}
	 }
	 
	override <U extends Correspondence> getView(Class<U> correspondenceType) {
		return new CorrespondenceModelView(correspondenceType, this);
	}
	
	override <U extends Correspondence> getEditableView(Class<U> correspondenceType, Supplier<U> correspondenceCreator) {
		return new CorrespondenceModelView(correspondenceType, this, correspondenceCreator);
	}
	
	private Iterable<Pair<List<TUID>,Set<Correspondence>>> tuidUpdateData;
		
	/**
	 * Removes the current entries in the
	 * {@link CorrespondenceModelImpl#tuid2CorrespondencesMap} map for the given oldTUID
	 * before the hash code of it is updated and returns a pair containing the oldTUID and
	 * the removed correspondence model elements of the map.
	 *
	 * @param oldCurrentTUID
	 * @return oldCurrentTUIDAndStringAndMapEntriesTriple
	 */
	override performPreAction(TUID oldCurrentTUID) {
		if (tuidUpdateData != null) {
			throw new IllegalStateException("Two update calls were running at the same time");
		}
		// The TUID is used as key in this map. Therefore the entry has to be removed before
		// the hashCode of the TUID changes.
		// remove the old map entries for the tuid before its hashcode changes
		val oldTUIDLists = tuid2tuidListsMap.remove(oldCurrentTUID) ?: new HashSet<List<TUID>>()
		val oldTUIDList2Correspondences = new ArrayList<Pair<List<TUID>,Set<Correspondence>>>(oldTUIDLists.size);
		for (oldTUIDList : oldTUIDLists) {
			val correspondencesForOldTUIDList = tuid2CorrespondencesMap.remove(oldTUIDList) ?: new HashSet<Correspondence>()
			oldTUIDList2Correspondences.add(new Pair<List<TUID>,Set<Correspondence>>(oldTUIDList,correspondencesForOldTUIDList))
		}
		tuidUpdateData = oldTUIDList2Correspondences
	}
		
	 /**
	 * Re-adds all map entries after the hash code of tuids was updated.
	 *
	 * @param removedMapEntries
	 */
	override void performPostAction(TUID tuid) {
		// The correspondence model is an EMF-based model, so modifications have to be
		// performed within a transaction.
		this.modelProviding.createRecordingCommandAndExecuteCommandOnTransactionalDomain([ |
			if (tuidUpdateData == null) {
				throw new IllegalStateException("Update was not started before performing post action");
			}
			val oldTUIDList2Correspondences = tuidUpdateData;
			val newSetOfoldTUIDLists = new HashSet<List<TUID>>()
			for (oldTUIDList2CorrespondencesEntry : oldTUIDList2Correspondences) {
				val oldTUIDList = new ArrayList<TUID>(oldTUIDList2CorrespondencesEntry.first);
				val correspondences = oldTUIDList2CorrespondencesEntry.second
				// re-add the tuid list with the new hashcode to the set for the  for the tuid2tuidListsMap entry
				newSetOfoldTUIDLists.add(oldTUIDList)
				// re-add the correspondences entry for the current list of tuids with the new hashcode 
				tuid2CorrespondencesMap.put(oldTUIDList, correspondences)
			}
			// re-add the entry that maps the tuid to the set if tuid lists that contain it
			tuid2tuidListsMap.put(tuid, newSetOfoldTUIDLists)
			tuidUpdateData = null;
			setChangeAfterLastSaveFlag();
			return null;
		]);
	}

}		