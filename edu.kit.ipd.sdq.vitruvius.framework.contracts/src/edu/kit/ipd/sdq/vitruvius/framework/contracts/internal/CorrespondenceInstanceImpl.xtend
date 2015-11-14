package edu.kit.ipd.sdq.vitruvius.framework.contracts.internal

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FeatureInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableHashMap
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Triple
import java.io.IOException
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*

// TODO move all methods that don't need direct instance variable access to some kind of util class
class CorrespondenceInstanceImpl extends ModelInstance implements CorrespondenceInstanceDecorator {
	static final Logger logger = Logger::getLogger(typeof(CorrespondenceInstanceImpl).getSimpleName())
	final Mapping mapping
	final ModelProviding modelProviding
	final Correspondences correspondences
	protected final ClaimableMap<List<TUID>, Set<Correspondence>> tuid2CorrespondencesMap
	ClaimableMap<FeatureInstance, Set<FeatureInstance>> featureInstance2CorrespondingFIMap
	boolean changedAfterLastSave = false
	final Map<String, String> saveCorrespondenceOptions

	new(Mapping mapping, ModelProviding modelProviding, VURI correspondencesVURI, Resource correspondencesResource) {
		super(correspondencesVURI, correspondencesResource)
		this.mapping = mapping
		this.modelProviding = modelProviding
		this.tuid2CorrespondencesMap = new ClaimableHashMap<List<TUID>, Set<Correspondence>>()
		this.featureInstance2CorrespondingFIMap = new ClaimableHashMap<FeatureInstance, Set<FeatureInstance>>()
		this.saveCorrespondenceOptions = new HashMap<String, String>()
		this.saveCorrespondenceOptions.put(VitruviusConstants::getOptionProcessDanglingHref(),
			VitruviusConstants::getOptionProcessDanglingHrefDiscard())
		this.correspondences = loadAndRegisterCorrespondences(correspondencesResource)
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CorrespondenceInstance#
	 * addSameTypeCorrespondence
	 * (edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence)
	 */
	override void addCorrespondence(Correspondence correspondence) {
		addCorrespondenceToModel(correspondence)
		registerCorrespondence(correspondence)
		setChangeAfterLastSaveFlag()
	}

	def private void addCorrespondenceToModel(Correspondence correspondence) {
		var EList<Correspondence> correspondenceListForAddition = this.correspondences.getCorrespondences()
		correspondenceListForAddition.add(correspondence)
	}
	
	override calculateTUIDFromEObject(EObject eObject) {
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
		} else {
			return TUID::getInstance(metamodel.calculateTUIDFromEObject(eObject))
		}
	}

	override List<TUID> calculateTUIDsFromEObjects(List<EObject> eObjects) {
		return eObjects.map[calculateTUIDFromEObject(it)]
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CorrespondenceInstance#
	 * changedAfterLastSave()
	 */
	override boolean changedAfterLastSave() {
		return this.changedAfterLastSave
	}

	
	override Correspondence claimUniqueCorrespondence(List<EObject> aEObjects, List<EObject> bEObjects) {
		 val correspondences = getCorrespondences(aEObjects)
		 for (Correspondence correspondence : correspondences) {
		 	val correspondingBs = correspondence.bs
		 	if (correspondingBs != null && correspondingBs.equals(bEObjects)) {
		 		return correspondence;
		 	}
		 }
		 throw new RuntimeException("No correspondence for '" + aEObjects + "' and '" + bEObjects + "' was found!");
	}


	override Correspondence createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2) {
		var Correspondence correspondence = CorrespondenceFactory::eINSTANCE.createCorrespondence()
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
		}
		return correspondences
	}

	override Set<List<EObject>> getCorrespondingEObjects(List<EObject> eObjects) {
		var List<TUID> tuids = calculateTUIDsFromEObjects(eObjects)
		var Set<List<TUID>> correspondingTUIDLists = getCorrespondingTUIDs(tuids)
		return resolveEObjectsSetsFromTUIDsSets(correspondingTUIDLists)
	}

	def private Set<List<TUID>> getCorrespondingTUIDs(List<TUID> tuids) {
		var Set<Correspondence> allCorrespondences = getCorrespondencesForTUIDs(tuids)
		var Set<List<TUID>> correspondingTUIDLists = new HashSet<List<TUID>>(allCorrespondences.size())
		for (Correspondence correspondence : allCorrespondences) {
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

	override Map<String, Object> getFileExtPrefix2ObjectMapForSave() {
		try {
			EcoreResourceBridge::saveResource(getResource(), this.saveCorrespondenceOptions)
		} catch (IOException e) {
			throw new RuntimeException(
				'''Could not save correspondence instance '«»«this»' using the resource '«»«getResource()»' and the options '«»«this.saveCorrespondenceOptions»': «e»'''.
					toString)
		}
		// we do not need to save anything else in a correspondence instance because the
		// involved mapping is fix and everything else can be recomputed from the model
		return new HashMap<String, Object>() // do _not_ return an immutable empty map as decorators will add entries
	}

	override Set<String> getFileExtPrefixesForObjectsToLoad() {
		return new HashSet<String>() // do _not_ return an immutable empty map as decorators will add entries
	}

	override <T extends CorrespondenceInstanceDecorator> T getFirstCorrespondenceInstanceDecoratorOfTypeInChain(
		Class<T> type) {
		if (type.isInstance(this)) {
			return type.cast(this)
		} 
		return null
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#getMapping()
	 */
	override Mapping getMapping() {
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
		return correspondences !== null && correspondences.size() > 0
	}

	override void initialize(Map<String, Object> fileExtPrefix2ObjectMap) {
		// nothing to initialize, everything was done based on the correspondence model
	}

	def private Correspondences loadAndRegisterCorrespondences(
		Resource correspondencesResource) {
		try {
			correspondencesResource.load(this.saveCorrespondenceOptions)
		} catch (Exception e) {
			logger.trace(
				"Could not load correspondence resource - creating new correspondence instance resource."
			)
		}
		// TODO implement lazy loading for correspondences because they may get really big
		var EObject correspondences = EcoreResourceBridge::
			getResourceContentRootIfUnique(getResource())
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
		return correspondences as Correspondences
	}

	def private void markNeighborAndChildrenCorrespondences(
		Correspondence correspondence, Set<Correspondence> markedCorrespondences) {
		if (markedCorrespondences.add(correspondence)) {
			var TUID elementATUID = correspondence.getElementATUID()
			markNeighborAndChildrenCorrespondences(elementATUID, markedCorrespondences)
			var TUID elementBTUID = correspondence.getElementBTUID()
			markNeighborAndChildrenCorrespondences(elementBTUID, markedCorrespondences)
		}

	}

	def private void markNeighborAndChildrenCorrespondences(TUID tuid,
		Set<Correspondence> markedCorrespondences) {
		var EObject eObject = resolveEObjectFromTUID(tuid)
		markNeighborAndChildrenCorrespondencesRecursively(eObject,
			markedCorrespondences)
	}

	def private void markNeighborAndChildrenCorrespondencesRecursively(EObject eObject,
		Set<Correspondence> markedCorrespondences) {
		var Set<Correspondence> allCorrespondences = getCorrespondences(eObject.toList)
		for (Correspondence correspondence : allCorrespondences) {
			markNeighborAndChildrenCorrespondences(correspondence,
				markedCorrespondences)
		}
		var List<EObject> children = eObject.eContents()
		for (EObject child : children) {
			markNeighborAndChildrenCorrespondencesRecursively(child,
				markedCorrespondences)
		}

	}

	def private void registerCorrespondence(Correspondence correspondence) {
		var List<TUID> allInvolvedTUIDs = new ArrayList<TUID>(
			correspondence.getATUIDs())
		allInvolvedTUIDs.addAll(correspondence.getBTUIDs()) // add all involved eObjects to the sets for these objects in the map
		for (TUID involvedTUID : allInvolvedTUIDs) {
			var Set<Correspondence> correspondences = getCorrespondencesForTUIDs(involvedTUID.toList)
			if (!correspondences.contains(correspondence)) {
				correspondences.add(correspondence)
			}

		}

	}

	def private void registerLoadedCorrespondences(Correspondences correspondences) {
		for (Correspondence correspondence : correspondences.getCorrespondences()) {
			registerCorrespondence(correspondence)
		}

	}

	def private void removeCorrespondenceFromMaps(Correspondence markedCorrespondence) {
		var TUID elementATUID = markedCorrespondence.getElementATUID()
		var TUID elementBTUID = markedCorrespondence.getElementBTUID()
		this.tuid2CorrespondencesMap.remove(elementATUID.toList)
		this.tuid2CorrespondencesMap.remove(elementBTUID.toList)
	}

	override Set<Correspondence> removeCorrespondencesOfEObjectsAndChildrenOnBothSides(
		// FIXME MK completely rethink removal 
		Correspondence correspondence) {
		if (correspondence === null) {
			return Collections::emptySet()
		}
		var Set<Correspondence> markedCorrespondences = new HashSet<Correspondence>()
		markNeighborAndChildrenCorrespondences(correspondence, markedCorrespondences)
		for (Correspondence markedCorrespondence : markedCorrespondences) {
			removeCorrespondenceFromMaps(markedCorrespondence)
			EcoreUtil::remove(markedCorrespondence)
			setChangeAfterLastSaveFlag()
		}
		return markedCorrespondences
	}

	override Set<Correspondence> removeCorrespondencesOfEObjectAndChildrenOnBothSides(
		EObject eObject) {
		val TUID tuid = calculateTUIDsFromEObjects(eObject.toList).claimOne
		return removeCorrespondencesOfEObjectAndChildrenOnBothSides(tuid)
	}

	override Set<Correspondence> removeCorrespondencesOfEObjectAndChildrenOnBothSides(
		TUID tuid) {
		var Set<Correspondence> directCorrespondences = getCorrespondencesForTUIDs(tuid.toList)
		var Set<Correspondence> directAndChildrenCorrespondences = new HashSet<Correspondence>()
		for (Correspondence correspondence : directCorrespondences) {
			directAndChildrenCorrespondences.addAll(
				removeCorrespondencesOfEObjectsAndChildrenOnBothSides(correspondence))
		}
		return directAndChildrenCorrespondences
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CorrespondenceInstance#
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
		return tuids.map[resolveEObjectFromTUID(it)]
	}
	
	override Set<List<EObject>> resolveEObjectsSetsFromTUIDsSets(Set<List<TUID>> tuidLists) {
		return tuidLists.map[resolveEObjectsFromTUIDs(it)].toSet
	}
	
	override EObject resolveEObjectFromTUID(TUID tuid) {
		var String tuidString = tuid.toString()
		var Metamodel metamodel = getMetamodelHavingTUID(tuidString)
		var VURI vuri = metamodel.getModelVURIContainingIdentifiedEObject(tuidString)
		var EObject rootEObject = null
		var ModelInstance modelInstance = null
		if (vuri !== null) {
			modelInstance = this.modelProviding.getAndLoadModelInstanceOriginal(vuri)
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

	def private void setChangeAfterLastSaveFlag() {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#update(org
	 * .eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 */
	override void updateTUID(EObject oldEObject, EObject newEObject) {
		var TUID oldTUID = calculateTUIDsFromEObjects(oldEObject.toList).claimOne
		this.updateTUID(oldTUID, newEObject)
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#update(edu
	 * .kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID,
	 * org.eclipse.emf.ecore.EObject)
	 */
	override void updateTUID(TUID oldTUID, EObject newEObject) {
		var TUID newTUID = calculateTUIDsFromEObjects(newEObject.toList).claimOne
		updateTUID(oldTUID, newTUID)
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance#update(edu
	 * .kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID,
	 * edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID)
	 */
	 //FIXME note to MK: this currently only works if all key-lists in tuid2CorrespondencesMap only contain one element. 
	 //If you implement an update function for list of TUIDs be careful since there could be the case that one TUID is contained 
	 //in more than only one key-lists. My current guess is that we have to update all key-lists in which the TUID occurs
	override void updateTUID(TUID oldTUID, TUID newTUID) {
		var boolean sameTUID = if(oldTUID !== null) oldTUID.equals(
				newTUID) else newTUID === null
		if (sameTUID || oldTUID === null) {
			return;
		}
		var String oldTUIDString = oldTUID.
			toString()
		// The TUID is used as key in this map. Therefore the entry has to be removed before
		// the hashCode of the TUID changes.
		// remove the old map entries for the tuid before its hashcode changes
		var TUID.BeforeHashCodeUpdateLambda before = ([ TUID oldCurrentTUID | 
			var Set<Correspondence> correspondencesForOldTUID = CorrespondenceInstanceImpl.
				this.tuid2CorrespondencesMap.remove(oldCurrentTUID.toList)
			// because featureInstance2CorrespondingFIMap uses no TUID as key we do not need to
			// update it
			return new Triple<TUID, String, Set<Correspondence>>(oldCurrentTUID, oldCurrentTUID.toString(),correspondencesForOldTUID)
		] as TUID.BeforeHashCodeUpdateLambda)
		var TUID.AfterHashCodeUpdateLambda after = ([ Triple<TUID, String, Set<Correspondence>> removedMapEntry |
			var TUID oldCurrentTUID = removedMapEntry.getFirst()
			var String oldCurrentTUIDString = removedMapEntry.
				getSecond()
			var Set<Correspondence> correspondencesForOldSegment = removedMapEntry.
				getThird()
			// re-add the entries using the tuid with the new hashcode
			if (correspondencesForOldSegment !== null) {
				for (Correspondence correspondence : correspondencesForOldSegment) {
					if (oldCurrentTUIDString !== null &&
						oldCurrentTUIDString.equals(
							correspondence.getElementATUID().
								toString())) {
						correspondence.
							setElementATUID(oldCurrentTUID)
					} else if (oldCurrentTUIDString !== null &&
						oldCurrentTUIDString.equals(
							correspondence.getElementBTUID().
								toString())) {
						correspondence.
							setElementBTUID(oldCurrentTUID)
					} else if (oldCurrentTUID === null ||
						(!oldCurrentTUID.equals(
							correspondence.getElementATUID()) &&
							!oldCurrentTUID.equals(
								correspondence.getElementBTUID()))
						) {
							throw new RuntimeException(
								'''None of the corresponding elements in '«»«correspondence»' has the TUID '«»«oldCurrentTUID»'!'''.
									toString)
						}
					}
					CorrespondenceInstanceImpl.this.
						tuid2CorrespondencesMap.put(
							oldCurrentTUID.toList,
							correspondencesForOldSegment)
					}
				] as TUID.AfterHashCodeUpdateLambda)
				oldTUID.renameSegments(newTUID, before,
					after
				)
				var Metamodel metamodel = getMetamodelHavingTUID(
					oldTUIDString)
				metamodel.
					removeIfRootAndCached(oldTUIDString)
			}

		}
				