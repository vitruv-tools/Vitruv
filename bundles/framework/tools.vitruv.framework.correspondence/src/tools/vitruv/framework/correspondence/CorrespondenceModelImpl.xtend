package tools.vitruv.framework.correspondence

import java.io.IOException
import java.util.List
import java.util.Map
import java.util.Set

import java.util.function.Supplier
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.domains.TuidAwareVitruvDomain
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.tuid.Tuid
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.tuid.TuidResolver
import tools.vitruv.framework.tuid.TuidUpdateListener
import tools.vitruv.framework.util.VitruviusConstants
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.util.command.VitruviusRecordingCommandExecutor
import tools.vitruv.framework.util.datatypes.ClaimableHashMap
import tools.vitruv.framework.util.datatypes.ClaimableMap
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.impl.ModelInstanceImpl

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.framework.util.bridges.JavaBridge.*

// TODO move all methods that don't need direct instance variable access to some kind of util class
class CorrespondenceModelImpl extends ModelInstanceImpl implements InternalCorrespondenceModel, TuidUpdateListener {
	static extension CorrespondenceFactory = CorrespondenceFactory::eINSTANCE
	static extension Logger = Logger::getLogger(CorrespondenceModelImpl)
	static extension TuidManager = TuidManager::instance

	boolean changedAfterLastSave = false
	protected val ClaimableMap<List<Tuid>, Set<Correspondence>> tuid2CorrespondencesMap
	Iterable<Pair<List<Tuid>, Set<Correspondence>>> tuidUpdateData
	val ClaimableMap<Tuid, Set<List<Tuid>>> tuid2tuidListsMap
	val Correspondences correspondences
	val Map<String, String> saveCorrespondenceOptions
	val TuidResolver tuidResolver
	val VitruvDomainRepository domainRepository
	val VitruviusRecordingCommandExecutor modelCommandExecutor
	boolean isRegistered = false

	new(TuidResolver tuidResolver, VitruviusRecordingCommandExecutor modelCommandExecutor,
		VitruvDomainRepository domainRepository, VURI correspondencesVURI, Resource correspondencesResource) {
		super(correspondencesVURI, correspondencesResource)
		this.modelCommandExecutor = modelCommandExecutor
		// TODO MK use MutatingListFixing::.. when necessary (for both maps!)
		this.tuid2tuidListsMap = new ClaimableHashMap<Tuid, Set<List<Tuid>>>
		this.tuid2CorrespondencesMap = new ClaimableHashMap<List<Tuid>, Set<Correspondence>>
		this.saveCorrespondenceOptions = newHashMap
		this.saveCorrespondenceOptions.put(VitruviusConstants::getOptionProcessDanglingHref,
			VitruviusConstants::getOptionProcessDanglingHrefDiscard)
		this.correspondences = loadAndRegisterCorrespondences(correspondencesResource)
		this.domainRepository = domainRepository
		this.tuidResolver = tuidResolver
		addTuidUpdateListener(this)
		isRegistered = true
	}

	override registerToTUIDManager() {
		if (isRegistered)
			throw new IllegalStateException('''
				Trying to register «this» to TUIDManager but was already registered.
			''')
		addTuidUpdateListener(this)
		isRegistered = true
	}

	override deregisterFromTUIDManager() {
		if (!isRegistered)
			throw new IllegalStateException('''
				Trying to unregister «this» from TUIDManager but was not registered yet.
			''')
		removeTuidUpdateListener(this)
		isRegistered = false
	}

	override addCorrespondence(Correspondence correspondence) {
		modelCommandExecutor.executeRecordingCommand(EMFCommandBridge.createVitruviusRecordingCommand(
				[
			addCorrespondenceToModel(correspondence)
			registerCorrespondence(correspondence)
			setChangeAfterLastSaveFlag
			return null
		]))
	}

	override changedAfterLastSave() {
		changedAfterLastSave
	}

	override calculateTuidFromEObject(EObject eObject) {
		val TuidAwareVitruvDomain metamodel = eObject.metamodelForEObject
		if (null === metamodel)
			return null
		return metamodel.calculateTuid(eObject)
	}

	@Deprecated
	override calculateTuidFromEObject(EObject eObject, EObject virtualRootObject, String prefix) {
		val TuidAwareVitruvDomain metamodel = eObject.metamodelForEObject
		if (null === metamodel)
			return null
		if (null === virtualRootObject || null === prefix) {
			info("virtualRootObject or prefix is null. Using standard calculation method for EObject " + eObject)
			return metamodel.calculateTuid(eObject)
		}
		return Tuid::getInstance(metamodel.calculateTuidFromEObject(eObject, virtualRootObject, prefix))
	}

	override calculateTuidsFromEObjects(List<EObject> eObjects) {
		eObjects.mapFixed[calculateTuidFromEObject].toList
	}

	override claimUniqueCorrespondence(List<EObject> aEObjects, List<EObject> bEObjects) {
		val correspondences = getCorrespondences(aEObjects)
		for (Correspondence correspondence : correspondences) {
			val correspondingBs = if (correspondence.^as.containsAll(aEObjects))
					correspondence.bs
				else
					correspondence.^as
			if (correspondingBs !== null && correspondingBs.equals(bEObjects))
				return correspondence
		}
		throw new RuntimeException("No correspondence for '" + aEObjects + "' and '" + bEObjects + "' was found!")
	}

	@Deprecated
	override createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2) {
		return createAndAddManualCorrespondence(eObjects1, eObjects2)
	}

	override createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2,
		Supplier<Correspondence> correspondenceCreator) {
		val Correspondence correspondence = correspondenceCreator.get
		createAndAddCorrespondence(eObjects1, eObjects2, correspondence)
	}

	override createAndAddManualCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2) {
		val correspondence = createManualCorrespondence
		createAndAddCorrespondence(eObjects1, eObjects2, correspondence)
	}

	override getCorrespondences(List<EObject> eObjects) {
		val tuids = calculateTuidsFromEObjects(eObjects)
		return getCorrespondencesForTuids(tuids)
	}

	override getCorrespondencesForTuids(List<Tuid> tuids) {
		var correspondences = tuid2CorrespondencesMap.get(tuids)
		if (correspondences === null) {
			tuid2CorrespondencesMap.entrySet.forEach [
				debug(it)
			]
			correspondences = newHashSet
			tuid2CorrespondencesMap.put(tuids, correspondences)
			registerTuidList(tuids)
		}
		return correspondences
	}

	override getCorrespondingEObjects(List<EObject> eObjects) {
		getCorrespondingEObjects(Correspondence, eObjects)
	}

	override getCorrespondingEObjects(Class<? extends Correspondence> correspondenceType, List<EObject> eObjects) {
		val tuids = calculateTuidsFromEObjects(eObjects)
		val correspondingTuidLists = getCorrespondingTuids(correspondenceType, tuids)
		return correspondingTuidLists.mapFixed[resolveEObjectsFromTuids].toSet
	}

	private def Set<List<Tuid>> getCorrespondingTuids(
		Class<? extends Correspondence> correspondenceType,
		List<Tuid> tuids
	) {
		val allCorrespondences = getCorrespondencesForTuids(tuids)
		val Set<List<Tuid>> correspondingTuidLists = newHashSet
		allCorrespondences.filter(correspondenceType).forEach [
			if (ATuids === null || BTuids === null || ATuids.size == 0 || BTuids.size == 0)
				throw new IllegalStateException('''
					The correspondence '«it»' links to an empty Tuid '«ATuids»' or '«BTuids»'!
				''')
			correspondingTuidLists += if(ATuids == tuids) BTuids else ATuids
		]
		return correspondingTuidLists
	}

	override saveModel() {
		try {
			EcoreResourceBridge::saveResource(getResource(),
				saveCorrespondenceOptions)
		} catch (IOException e) {
			throw new RuntimeException(
				'''
				Could not save correspondence instance '«»«this»' using the resource '«»«getResource()»' and the options '«»«saveCorrespondenceOptions»': «e»
			''')
		}
	}

	override hasCorrespondences() {
		tuid2CorrespondencesMap.values.exists[!empty]
	}

	override hasCorrespondences(List<EObject> eObjects) {
		val List<Tuid> tuids = calculateTuidsFromEObjects(eObjects)
		val Set<Correspondence> correspondences = tuid2CorrespondencesMap.get(tuids)
		return correspondences !== null && correspondences.size > 0
	}

	override removeCorrespondencesThatInvolveAtLeastAndDependend(Set<EObject> eObjects) {
		return removeCorrespondencesThatInvolveAtLeastAndDependendForTuids(eObjects.mapFixed [
			calculateTuidFromEObject
		].toSet)
	}

	override removeCorrespondencesThatInvolveAtLeastAndDependendForTuids(Set<Tuid> tuids) {
		val correspondences = getCorrespondencesThatInvolveAtLeastTuids(tuids)
		val markedCorrespondences = correspondences.mapFixed [
			markCorrespondenceAndDependingCorrespondences
		].flatten
		removeMarkedCorrespondences(markedCorrespondences)
		return markedCorrespondences.toSet
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CorrespondenceModel#
	 * resetChangedAfterLastSave()
	 */
	override resetChangedAfterLastSave() {
		changedAfterLastSave = false
	}

	override resolveEObjectsFromTuids(List<Tuid> tuids) {
		tuids.mapFixed[tuidResolver.resolveEObjectFromTuid(it)]
	}

	override resolveEObjectFromTuid(Tuid tuid) {
		tuidResolver.resolveEObjectFromTuid(tuid)
	}

	override getAllCorrespondencesWithoutDependencies() {
		correspondences.correspondences.filter[dependsOn === null || dependsOn.size == 0].toSet
	}

	override getCorrespondencesThatInvolveAtLeast(Set<EObject> eObjects) {
		getCorrespondencesThatInvolveAtLeastTuids(eObjects.mapFixed[calculateTuidFromEObject].toSet)
	}

	override getCorrespondencesThatInvolveAtLeastTuids(Set<Tuid> tuids) {
		val supTuidLists = tuids?.mapFixed[tuid2tuidListsMap.get(it)].filterNull.flatten.filter [
			containsAll(tuids)
		]
		val corrit = supTuidLists?.mapFixed[correspondencesForTuids]
		val flatcorr = corrit.flatten
		if (flatcorr.nullOrEmpty) {
			debug("could not find correspondences for tuids: " + tuids)
			return newHashSet
		}
		val corrset = flatcorr.toSet
		return corrset
	}

	override getAllCorrespondences() {
		correspondences.correspondences
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
		val domainA = domainRepository.getDomain(correspondence.ATuids.get(0))
		val domainB = domainRepository.getDomain(correspondence.BTuids.get(0))
		if (domainA.nsUris.contains(metamodelNamespaceUri)) {
			return correspondence.getATuids
		} else if (domainB.nsUris.contains(metamodelNamespaceUri)) {
			return correspondence.
				getBTuids
		} else {
			throw new IllegalArgumentException('''Metamodel namespace URI "«metamodelNamespaceUri»" is not a namespace URI of one of the metamodels for the associated mapping''')
		}
	}

	override <U extends Correspondence> getView(Class<U> correspondenceType) {
		new CorrespondenceModelView(correspondenceType, this)
	}

	override <U extends Correspondence> getEditableView(
		Class<U> correspondenceType,
		Supplier<U> correspondenceCreator
	) {
		new CorrespondenceModelView(correspondenceType, this, correspondenceCreator)
	}

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
		if (tuidUpdateData !== null)
			throw new IllegalStateException("Two update calls were running at the same time")
		// The Tuid is used as key in this map. Therefore the entry has to be removed before
		// the hashCode of the Tuid changes.
		// remove the old map entries for the tuid before its hashcode changes
		val oldTuidLists = tuid2tuidListsMap.remove(oldCurrentTuid) ?: newHashSet
		val oldTuidList2Correspondences = newArrayList
		oldTuidLists.forEach [
			val correspondencesForOldTuidList = tuid2CorrespondencesMap.remove(it) ?: newHashSet
			oldTuidList2Correspondences += it -> correspondencesForOldTuidList
		]
		tuidUpdateData = oldTuidList2Correspondences
	}

	/**
	 * Re-adds all map entries after the hash code of tuids was updated.
	 * 
	 * @param removedMapEntries
	 */
	override performPostAction(Tuid tuid) {
		// The correspondence model is an EMF-based model, so modifications have to be
		// performed within a transaction.
		modelCommandExecutor.executeRecordingCommand(EMFCommandBridge.createVitruviusRecordingCommand([
			if (tuidUpdateData === null)
				throw new IllegalStateException("Update was not started before performing post action")
			val oldTuidList2Correspondences = tuidUpdateData
			val Set<List<Tuid>> newSetOfoldTuidLists = newHashSet
			for (oldTuidList2CorrespondencesEntry : oldTuidList2Correspondences) {
				val List<Tuid> oldTuidList = newArrayList(oldTuidList2CorrespondencesEntry.key)
				val correspondences = oldTuidList2CorrespondencesEntry.value
				// re-add the tuid list with the new hashcode to the set for the  for the tuid2tuidListsMap entry
				newSetOfoldTuidLists += oldTuidList
				// re-add the correspondences entry for the current list of tuids with the new hashcode
				tuid2CorrespondencesMap.put(oldTuidList.immutableCopy, correspondences.immutableCopy)
			}
			// re-add the entry that maps the tuid to the set if tuid lists that contain it
			tuid2tuidListsMap.put(tuid, newSetOfoldTuidLists)
			tuidUpdateData = null
			setChangeAfterLastSaveFlag
			return null
		]))
	}

	override removeCorrespondencesAndDependendCorrespondences(Correspondence correspondence) {
		val markedCorrespondences = markCorrespondenceAndDependingCorrespondences(correspondence)
		removeMarkedCorrespondences(markedCorrespondences)
		return markedCorrespondences
	}

	def void setChangeAfterLastSaveFlag() {
		changedAfterLastSave = true
	}

	private def void registerCorrespondence(Correspondence correspondence) {
		registerTuidLists(correspondence)
		registerCorrespondenceForTuids(correspondence)
	}

	private def registerTuidLists(Correspondence correspondence) {
		registerTuidList(correspondence.getATuids)
		registerTuidList(correspondence.getBTuids)
	}

	private def registerTuidList(List<Tuid> tuidList) {
		tuidList.forEach [
			var tuidLists = tuid2tuidListsMap.get(it)
			if (tuidLists === null) {
				tuidLists = newHashSet
				tuid2tuidListsMap.put(it, tuidLists)
			}
			tuidLists += tuidList.<Tuid>immutableCopy
		]
	}

	private def Correspondences loadAndRegisterCorrespondences(Resource correspondencesResource) {
		try {
			correspondencesResource.load(saveCorrespondenceOptions)
		} catch (IOException e) {
			if (e.cause instanceof Exception) {
				trace(
					"Could not load correspondence resource - creating new correspondence instance resource."
				)
			}
		}
		// TODO implement lazy loading for correspondences because they may get really big
		var Correspondences correspondences = EcoreResourceBridge::getResourceContentRootIfUnique(getResource())?.
			dynamicCast(Correspondences, "correspondence model")
		if (correspondences === null) {
			correspondences = createCorrespondences
			correspondencesResource.contents += correspondences
		} else {
			registerLoadedCorrespondences(correspondences)
		}
		correspondences.correspondenceModel = this
		return correspondences
	}

	private def void registerCorrespondenceForTuids(Correspondence correspondence) {
		val correspondencesForAs = getCorrespondencesForTuids(correspondence.getATuids)
		correspondencesForAs += correspondence
		val correspondencesForBs = getCorrespondencesForTuids(correspondence.getBTuids)
		correspondencesForBs += correspondence
	}

	private def void registerLoadedCorrespondences(Correspondences correspondences) {
		for (Correspondence correspondence : correspondences.correspondences) {
			registerCorrespondence(correspondence)
		}
	}

	private def void removeCorrespondenceFromMaps(Correspondence markedCorrespondence) {
		val List<Tuid> aTuids = markedCorrespondence.getATuids
		val List<Tuid> bTuids = markedCorrespondence.getBTuids
		removeTuid2TuidListsEntries(aTuids)
		removeTuid2TuidListsEntries(bTuids)
		tuid2CorrespondencesMap.get(aTuids).remove(markedCorrespondence)
		if (tuid2CorrespondencesMap.get(aTuids).empty)
			tuid2CorrespondencesMap.remove(aTuids)
		tuid2CorrespondencesMap.get(bTuids).remove(markedCorrespondence)
		if (tuid2CorrespondencesMap.get(bTuids).empty)
			tuid2CorrespondencesMap.remove(bTuids)
	}

	private def void removeTuid2TuidListsEntries(List<Tuid> tuids) {
		tuids.forEach [
			val tuidLists = tuid2tuidListsMap.get(it)
			tuidLists.remove(tuids)
		]
	}

	private def Set<Correspondence> markCorrespondenceAndDependingCorrespondences(Correspondence correspondence) {
		val Set<Correspondence> markedCorrespondences = newHashSet
		markCorrespondenceAndDependingCorrespondencesRecursively(markedCorrespondences, correspondence)
		return markedCorrespondences
	}

	private def void markCorrespondenceAndDependingCorrespondencesRecursively(Set<Correspondence> markedCorrespondences,
		Correspondence correspondence) {
		markedCorrespondences += correspondence
		// FIXME MK detect dependency cycles in correspondences already when the reference is updated
		for (dependingCorrespondence : correspondence.dependedOnBy) {
			markCorrespondenceAndDependingCorrespondencesRecursively(markedCorrespondences, dependingCorrespondence)
		}
	}

	private def removeMarkedCorrespondences(Iterable<Correspondence> markedCorrespondences) {
		markedCorrespondences.forEach [
			removeCorrespondenceFromMaps
			EcoreUtil::remove(it)
			setChangeAfterLastSaveFlag
		]
	}

	private def createAndAddCorrespondence(
		List<EObject> eObjects1,
		List<EObject> eObjects2,
		Correspondence correspondence
	) {
		setCorrespondenceFeatures(correspondence, eObjects1, eObjects2)
		addCorrespondence(correspondence)
		return correspondence
	}

	private def void setCorrespondenceFeatures(
		Correspondence correspondence,
		List<EObject> eObjects1,
		List<EObject> eObjects2
	) {
		val aEObjects = eObjects1
		val bEObjects = eObjects2
		val List<Tuid> aTuids = calculateTuidsFromEObjects(aEObjects)
		correspondence.ATuids += aTuids
		val List<Tuid> bTuids = calculateTuidsFromEObjects(bEObjects)
		correspondence.BTuids += bTuids
	}

	private def getMetamodelForEObject(EObject eObject) {
		domainRepository.getDomain(eObject)
	}

	private def void addCorrespondenceToModel(Correspondence correspondence) {
		val EList<Correspondence> correspondenceListForAddition = correspondences.correspondences
		correspondenceListForAddition += correspondence
	}
}
