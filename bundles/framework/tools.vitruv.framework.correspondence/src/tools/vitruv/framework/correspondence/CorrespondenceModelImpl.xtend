package tools.vitruv.framework.correspondence

import com.google.common.collect.Sets
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.VitruviusConstants
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.datatypes.ClaimableHashMap
import tools.vitruv.framework.util.datatypes.ClaimableMap
import edu.kit.ipd.sdq.commons.util.java.Pair
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
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.framework.util.bridges.JavaBridge.*
import tools.vitruv.framework.correspondence.Correspondences
import tools.vitruv.framework.correspondence.Correspondence
import tools.vitruv.framework.tuid.Tuid
import tools.vitruv.framework.correspondence.CorrespondenceFactory
import tools.vitruv.framework.tuid.TuidUpdateListener
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.domains.TuidAwareVitruvDomain
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.util.command.VitruviusRecordingCommandExecutor
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.tuid.TuidResolver

// TODO move all methods that don't need direct instance variable access to some kind of util class
class CorrespondenceModelImpl extends ModelInstance implements InternalCorrespondenceModel, TuidUpdateListener {
	static final Logger logger = Logger::getLogger(typeof(CorrespondenceModelImpl).getSimpleName())
	final VitruviusRecordingCommandExecutor modelCommandExecutor
	final VitruvDomainRepository domainRepository;
	final Correspondences correspondences
	final ClaimableMap<Tuid,Set<List<Tuid>>> tuid2tuidListsMap
	protected final ClaimableMap<List<Tuid>, Set<Correspondence>> tuid2CorrespondencesMap
	boolean changedAfterLastSave = false
	final Map<String, String> saveCorrespondenceOptions
	final TuidResolver tuidResolver;
	
	new(TuidResolver tuidResolver, VitruviusRecordingCommandExecutor modelCommandExecutor, VitruvDomainRepository domainRepository, VURI correspondencesVURI, Resource correspondencesResource) {
		super(correspondencesVURI, correspondencesResource)
		this.modelCommandExecutor = modelCommandExecutor
		// TODO MK use MutatingListFixing... when necessary (for both maps!)
		this.tuid2tuidListsMap = new ClaimableHashMap<Tuid,Set<List<Tuid>>>()
		this.tuid2CorrespondencesMap = new ClaimableHashMap<List<Tuid>, Set<Correspondence>>()
		this.saveCorrespondenceOptions = new HashMap<String, String>()
		this.saveCorrespondenceOptions.put(VitruviusConstants::getOptionProcessDanglingHref(),
			VitruviusConstants::getOptionProcessDanglingHrefDiscard())
		this.correspondences = loadAndRegisterCorrespondences(correspondencesResource)
		this.domainRepository = domainRepository;
		this.tuidResolver = tuidResolver;
		TuidManager.instance.addTuidUpdateListener(this);
	}

	override void addCorrespondence(Correspondence correspondence) {
		this.modelCommandExecutor.executeRecordingCommand(EMFCommandBridge.createVitruviusRecordingCommand(
				[ |
					addCorrespondenceToModel(correspondence)
					registerCorrespondence(correspondence)
					setChangeAfterLastSaveFlag()
					return null
				]));
	}

	def private void registerCorrespondence(Correspondence correspondence) {
		registerTuidLists(correspondence)
		registerCorrespondenceForTuids(correspondence)
	}
	
	def private registerTuidLists(Correspondence correspondence) {
		registerTuidList(correspondence.getATuids)
		registerTuidList(correspondence.getBTuids)
	}
	
	def private registerTuidList(List<Tuid> tuidList) {
		for (Tuid tuid : tuidList) {
			var tuidLists = this.tuid2tuidListsMap.get(tuid)
			if (tuidLists === null) {
				tuidLists = new HashSet<List<Tuid>>()
				this.tuid2tuidListsMap.put(tuid,tuidLists)
			}
			tuidLists.add(tuidList)
		}
	}

	def private void addCorrespondenceToModel(Correspondence correspondence) {
		var EList<Correspondence> correspondenceListForAddition = this.correspondences.getCorrespondences()
		correspondenceListForAddition.add(correspondence)
	}
	
	override calculateTuidFromEObject(EObject eObject) {
		val TuidAwareVitruvDomain metamodel = eObject.getMetamodelForEObject()
		 if (null === metamodel){
		 	return null 
		 }
         return metamodel.calculateTuid(eObject)
	}
	
	@Deprecated
	override calculateTuidFromEObject(EObject eObject, EObject virtualRootObject, String prefix) {
		 val TuidAwareVitruvDomain metamodel = eObject.getMetamodelForEObject()
		 if(null === metamodel){
		 	return null 
		 }
		 if(null === virtualRootObject || null === prefix){
		 	logger.info("virtualRootObject or prefix is null. Using standard calculation method for EObject " + eObject)
         	return metamodel.calculateTuid(eObject)
     	}
     	return Tuid::getInstance(metamodel.calculateTuidFromEObject(eObject, virtualRootObject, prefix))
	}
	
	def private getMetamodelForEObject(EObject eObject){
		return this.domainRepository.getDomain(eObject);
	}

	override List<Tuid> calculateTuidsFromEObjects(List<EObject> eObjects) {
		return eObjects.mapFixed[calculateTuidFromEObject(it)].toList
	}

	override def boolean changedAfterLastSave() {
		return this.changedAfterLastSave
	}

	
	override Correspondence claimUniqueCorrespondence(List<EObject> aEObjects, List<EObject> bEObjects) {
		 val correspondences = getCorrespondences(aEObjects)
		 for (Correspondence correspondence : correspondences) {
		 	val correspondingBs = if (correspondence.^as.containsAll(aEObjects)) correspondence.bs else correspondence.^as
		 	if (correspondingBs !== null && correspondingBs.equals(bEObjects)) {
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
		var List<Tuid> tuids = calculateTuidsFromEObjects(eObjects)
		return getCorrespondencesForTuids(tuids)
	}

	
	override Set<Correspondence> getCorrespondencesForTuids(List<Tuid> tuids) {
		var Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(tuids)
		if (correspondences === null) {
			correspondences = new HashSet<Correspondence>()
			this.tuid2CorrespondencesMap.put(tuids, correspondences)
			registerTuidList(tuids)
		}
		return correspondences
	}

	override Set<List<EObject>> getCorrespondingEObjects(List<EObject> eObjects) {
		this.getCorrespondingEObjects(Correspondence, eObjects);
	}

	override Set<List<EObject>> getCorrespondingEObjects(Class<? extends Correspondence> correspondenceType, List<EObject> eObjects) {
		var List<Tuid> tuids = calculateTuidsFromEObjects(eObjects)
		var Set<List<Tuid>> correspondingTuidLists = getCorrespondingTuids(correspondenceType, tuids)
		return correspondingTuidLists.mapFixed[resolveEObjectsFromTuids(it)].toSet;
	}
	
	def private Set<List<Tuid>> getCorrespondingTuids(Class<? extends Correspondence> correspondenceType, List<Tuid> tuids) {
		var Set<Correspondence> allCorrespondences = getCorrespondencesForTuids(tuids)
		var Set<List<Tuid>> correspondingTuidLists = new HashSet<List<Tuid>>(allCorrespondences.size())
		for (Correspondence correspondence : allCorrespondences.filter(correspondenceType)) {
			var List<Tuid> aTuids = correspondence.getATuids()
			var List<Tuid> bTuids = correspondence.getBTuids()
			if (aTuids === null || bTuids === null || aTuids.size == 0 || bTuids.size == 0) {
				throw new IllegalStateException(
					'''The correspondence '«»«correspondence»' links to an empty Tuid '«»«aTuids»' or '«»«bTuids»'!'''.
						toString)
			}
			if (aTuids.equals(tuids)) {
				correspondingTuidLists.add(bTuids)
			} else {
				correspondingTuidLists.add(aTuids)
			}
		}
		return correspondingTuidLists
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

	override boolean hasCorrespondences() {
		for (Set<Correspondence> correspondences : this.tuid2CorrespondencesMap.values()) {
			if (!correspondences.isEmpty()) {
				return true
			}
		};
		return false
	}

	override boolean hasCorrespondences(List<EObject> eObjects) {
		var List<Tuid> tuids = calculateTuidsFromEObjects(eObjects)
		var Set<Correspondence> correspondences = this.tuid2CorrespondencesMap.get(tuids)
		return correspondences !== null && correspondences.size() > 0
	}

	def private Correspondences loadAndRegisterCorrespondences(Resource correspondencesResource) {
		try {
			correspondencesResource.load(this.saveCorrespondenceOptions)
		} catch (IOException e) {
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
		} else {
			registerLoadedCorrespondences(correspondences)
		}
		correspondences.setCorrespondenceModel(this)
		return correspondences
	}

	def private void registerCorrespondenceForTuids(Correspondence correspondence) {
		val correspondencesForAs = getCorrespondencesForTuids(correspondence.getATuids)
		correspondencesForAs.add(correspondence)
		val correspondencesForBs = getCorrespondencesForTuids(correspondence.getBTuids)
		correspondencesForBs.add(correspondence)
	}

	def private void registerLoadedCorrespondences(Correspondences correspondences) {
		for (Correspondence correspondence : correspondences.getCorrespondences()) {
			registerCorrespondence(correspondence)
		}
	}

	def private void removeCorrespondenceFromMaps(Correspondence markedCorrespondence) {
		var List<Tuid> aTuids = markedCorrespondence.getATuids
		var List<Tuid> bTuids = markedCorrespondence.getBTuids
		removeTuid2TuidListsEntries(aTuids)
		removeTuid2TuidListsEntries(bTuids)
		this.tuid2CorrespondencesMap.get(aTuids).remove(markedCorrespondence);
		if (tuid2CorrespondencesMap.get(aTuids).empty) {
			tuid2CorrespondencesMap.remove(aTuids);
		}
		this.tuid2CorrespondencesMap.get(bTuids).remove(markedCorrespondence);
		if (tuid2CorrespondencesMap.get(bTuids).empty) {
			tuid2CorrespondencesMap.remove(bTuids);
		}
	}
	
	def private void removeTuid2TuidListsEntries(List<Tuid> tuids) {
		for (Tuid tuid : tuids) {
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
		return removeCorrespondencesThatInvolveAtLeastAndDependendForTuids(eObjects.mapFixed[calculateTuidFromEObject(it)].toSet)
	}

	override Set<Correspondence> removeCorrespondencesThatInvolveAtLeastAndDependendForTuids(Set<Tuid> tuids) {
		val correspondences = getCorrespondencesThatInvolveAtLeastTuids(tuids)
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

	override List<EObject> resolveEObjectsFromTuids(List<Tuid> tuids) {
		tuids.mapFixed[tuidResolver.resolveEObjectFromTuid(it)];
	}

	override EObject resolveEObjectFromTuid(Tuid tuid) {
		tuidResolver.resolveEObjectFromTuid(tuid);
	}
	
	def public void setChangeAfterLastSaveFlag() {
		this.changedAfterLastSave = true
	}

	def private void setCorrespondenceFeatures(
		Correspondence correspondence, List<EObject> eObjects1,
		List<EObject> eObjects2) {
		var aEObjects = eObjects1
		var bEObjects = eObjects2
		var List<Tuid> aTuids = calculateTuidsFromEObjects(aEObjects)
		correspondence.getATuids().addAll(aTuids)
		var List<Tuid> bTuids = calculateTuidsFromEObjects(bEObjects)
		correspondence.getBTuids().addAll(bTuids)
	}

	override getAllCorrespondencesWithoutDependencies() {
		this.correspondences.correspondences.filter[it.dependsOn === null || it.dependsOn.size == 0].toSet
	}
	
	override getCorrespondencesThatInvolveAtLeast(Set<EObject> eObjects) {
		return getCorrespondencesThatInvolveAtLeastTuids(eObjects.mapFixed[calculateTuidFromEObject(it)].toSet)
	}
	
	override getCorrespondencesThatInvolveAtLeastTuids(Set<Tuid> tuids) {
		val supTuidLists = tuids?.mapFixed[this.tuid2tuidListsMap.get(it)].filterNull.flatten.filter[it.containsAll(tuids)]
		val corrit = supTuidLists?.mapFixed[getCorrespondencesForTuids(it)]
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
	override getTuidsForMetamodel(Correspondence correspondence, String metamodelNamespaceUri) {
		// FIXME This is really ugly, fix it!
	 	val domainA = domainRepository.getDomain(correspondence.ATuids.get(0));
	 	val domainB = domainRepository.getDomain(correspondence.BTuids.get(0));
	 	if (domainA.nsUris.contains(metamodelNamespaceUri)) {
	 		return correspondence.getATuids
	 	} else if (domainB.nsUris.contains(metamodelNamespaceUri)) {
	 		return correspondence.getBTuids
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
	
	private Iterable<Pair<List<Tuid>,Set<Correspondence>>> tuidUpdateData;
		
	/**
	 * Removes the current entries in the
	 * {@link CorrespondenceModelImpl#tuid2CorrespondencesMap} map for the given oldTuid
	 * before the hash code of it is updated and returns a pair containing the oldTuid and
	 * the removed correspondence model elements of the map.
	 *
	 * @param oldCurrentTuid
	 * @return oldCurrentTuidAndStringAndMapEntriesTriple
	 */
	override performPreAction(Tuid oldCurrentTuid) {
		if (tuidUpdateData !== null) {
			throw new IllegalStateException("Two update calls were running at the same time");
		}
		// The Tuid is used as key in this map. Therefore the entry has to be removed before
		// the hashCode of the Tuid changes.
		// remove the old map entries for the tuid before its hashcode changes
		val oldTuidLists = tuid2tuidListsMap.remove(oldCurrentTuid) ?: new HashSet<List<Tuid>>()
		val oldTuidList2Correspondences = new ArrayList<Pair<List<Tuid>,Set<Correspondence>>>(oldTuidLists.size);
		for (oldTuidList : oldTuidLists) {
			val correspondencesForOldTuidList = tuid2CorrespondencesMap.remove(oldTuidList) ?: new HashSet<Correspondence>()
			oldTuidList2Correspondences.add(new Pair<List<Tuid>,Set<Correspondence>>(oldTuidList,correspondencesForOldTuidList))
		}
		tuidUpdateData = oldTuidList2Correspondences
	}
		
	 /**
	 * Re-adds all map entries after the hash code of tuids was updated.
	 *
	 * @param removedMapEntries
	 */
	override void performPostAction(Tuid tuid) {
		// The correspondence model is an EMF-based model, so modifications have to be
		// performed within a transaction.
		this.modelCommandExecutor.executeRecordingCommand(EMFCommandBridge.createVitruviusRecordingCommand([ |
			if (tuidUpdateData === null) {
				throw new IllegalStateException("Update was not started before performing post action");
			}
			val oldTuidList2Correspondences = tuidUpdateData;
			val newSetOfoldTuidLists = new HashSet<List<Tuid>>()
			for (oldTuidList2CorrespondencesEntry : oldTuidList2Correspondences) {
				val oldTuidList = new ArrayList<Tuid>(oldTuidList2CorrespondencesEntry.first);
				val correspondences = oldTuidList2CorrespondencesEntry.second
				// re-add the tuid list with the new hashcode to the set for the  for the tuid2tuidListsMap entry
				newSetOfoldTuidLists.add(oldTuidList)
				// re-add the correspondences entry for the current list of tuids with the new hashcode 
				tuid2CorrespondencesMap.put(oldTuidList, correspondences)
			}
			// re-add the entry that maps the tuid to the set if tuid lists that contain it
			tuid2tuidListsMap.put(tuid, newSetOfoldTuidLists)
			tuidUpdateData = null;
			setChangeAfterLastSaveFlag();
			return null;
		]));
	}

}		