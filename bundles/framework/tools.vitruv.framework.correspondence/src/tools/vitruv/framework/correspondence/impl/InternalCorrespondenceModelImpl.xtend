package tools.vitruv.framework.correspondence.impl

import java.io.IOException
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
import tools.vitruv.framework.correspondence.Correspondence
import tools.vitruv.framework.correspondence.CorrespondenceFactory
import tools.vitruv.framework.correspondence.Correspondences
import tools.vitruv.framework.util.VitruviusConstants
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.uuid.UuidResolver

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.framework.util.bridges.JavaBridge.*
import static extension tools.vitruv.framework.correspondence.CorrespondenceUtil.*
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceModelView
import tools.vitruv.framework.correspondence.CorrespondenceModelViewFactory
import java.util.function.Predicate
import tools.vitruv.framework.util.command.CommandCreatorAndExecutor

// TODO move all methods that don't need direct instance variable access to some kind of util class
class InternalCorrespondenceModelImpl extends ModelInstance implements InternalCorrespondenceModel {
	static val logger = Logger.getLogger(InternalCorrespondenceModelImpl)
	final CommandCreatorAndExecutor modelCommandExecutor
	final Correspondences correspondences
	boolean changedAfterLastSave = false
	final UuidResolver uuidResolver;

	new(UuidResolver uuidResolver, CommandCreatorAndExecutor modelCommandExecutor,
		VURI correspondencesVURI, Resource correspondencesResource) {
		super(correspondencesVURI, correspondencesResource)
		this.modelCommandExecutor = modelCommandExecutor
		this.correspondences = loadAndRegisterCorrespondences(correspondencesResource)
		this.uuidResolver = uuidResolver;
	}
	
	def private getSaveAndLoadOptions() {
		Map.of(
			VitruviusConstants.optionProcessDanglingHref, VitruviusConstants.optionProcessDanglingHrefDiscard
		)
	}

	private def void addCorrespondence(Correspondence correspondence) {
		this.modelCommandExecutor.executeAsCommand[
			addCorrespondenceToModel(correspondence)
			setChangedAfterLastSaveFlag()
		]
	}

	def private void addCorrespondenceToModel(Correspondence correspondence) {
		var EList<Correspondence> correspondenceListForAddition = this.correspondences.getCorrespondences()
		correspondenceListForAddition.add(correspondence)
	}

	override <C extends Correspondence> C createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2,
		String tag, Supplier<C> correspondenceCreator) {
		val correspondence = correspondenceCreator.get
		createAndAddCorrespondence(eObjects1, eObjects2, tag, correspondence)
	}

	override Correspondence createAndAddManualCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2,
		String tag) {
		val correspondence = CorrespondenceFactory.eINSTANCE.createManualCorrespondence
		createAndAddCorrespondence(eObjects1, eObjects2, tag, correspondence)
	}

	def private <C extends Correspondence> C createAndAddCorrespondence(List<EObject> eObjects1,
		List<EObject> eObjects2, String tag, C correspondence) {
		setCorrespondenceFeatures(correspondence, eObjects1, eObjects2, tag)
		addCorrespondence(correspondence)
		return correspondence
	}

	override <C extends Correspondence> Set<C> getCorrespondences(Class<C> correspondenceType,
		Predicate<C> correspondencesFilter, List<EObject> eObjects, String tag) {
		val Set<Correspondence> correspondences = new HashSet<Correspondence>()
		if (eObjects.haveUuids) {
			val uuids = eObjects.map[uuidResolver.getPotentiallyCachedUuid(it)];
			correspondences += getCorrespondencesForUuids(uuids);
		}

		return correspondences.filter(correspondenceType).filter(correspondencesFilter).filter [
			tag === null || it.tag == tag
		].toSet;
	}

	private def Set<Correspondence> getCorrespondencesForUuids(List<String> uuids) {
		return this.correspondences.correspondences.filter[AUuids.containsAll(uuids) || BUuids.containsAll(uuids)].toSet
	}

	private def haveUuids(List<EObject> eObjects) {
		if (eObjects.forall[uuidResolver.hasPotentiallyCachedUuid(it)]) {
			return true;
		} else {
			logger.warn("UUID resolver has no UUID for one of the elements: " + eObjects);
		}
		return false
	}

	override <C extends Correspondence> Set<List<EObject>> getCorrespondingEObjects(Class<C> correspondenceType,
		Predicate<C> correspondencesFilter, List<EObject> eObjects, String tag) {
		val Set<List<EObject>> result = newHashSet;
		val correspondences = getCorrespondences(correspondenceType, correspondencesFilter, eObjects, tag);
		for (correspondence : correspondences) {
			result += resolveCorrespondingObjects(correspondence, eObjects);
		}
		return result.toSet;
	}

	override List<EObject> getCorrespondingEObjectsInCorrespondence(Correspondence correspondence,
		List<EObject> eObjects) {
		resolveCorrespondingObjects(correspondence, eObjects)
	}

	private def List<EObject> resolveCorrespondingObjects(Correspondence correspondence, List<EObject> eObjects) {
		val uuids = eObjects.map[uuidResolver.getPotentiallyCachedUuid(it)];
		val correspondingUuids = correspondence.getCorrespondingUuids(uuids);
		return correspondingUuids.map[resolveEObjectFromUuid];
	}

	override void saveModel() {
		if (!changedAfterLastSave) {
			return;
		}
		try {
			EcoreResourceBridge::saveResource(getResource(), saveAndLoadOptions)
			this.resetChangedAfterLastSaveFlag;
		} catch (IOException e) {
			throw new RuntimeException(
				'''Could not save correspondence instance ‹«this»› using the resource ‹«resource»› and the options ‹«
				saveAndLoadOptions»›''', e)
		}
	}

	override boolean hasCorrespondences() {
		return !this.correspondences.correspondences.empty
	}

	override hasCorrespondences(List<EObject> eObjects) {
		val correspondences = this.getCorrespondences(Correspondence, [true], eObjects, null);
		return correspondences !== null && correspondences.size() > 0
	}
	
	def private Correspondences loadAndRegisterCorrespondences(Resource correspondencesResource) {
		try {
			correspondencesResource.load(saveAndLoadOptions)
		} catch (IOException e) {
			if (e.cause instanceof Exception) {
				logger.trace(
					"Could not load correspondence resource - creating new correspondence instance resource."
				)
			}
		}
		// TODO implement lazy loading for correspondences because they may get really big
		var Correspondences correspondences = EcoreResourceBridge::getResourceContentRootIfUnique(getResource())?.
			dynamicCast(Correspondences, "correspondence model")
		if (correspondences === null) {
			correspondences = CorrespondenceFactory::eINSTANCE.createCorrespondences()
			correspondencesResource.getContents().add(correspondences)
		}
		return correspondences
	}

	private def void setChangedAfterLastSaveFlag() {
		this.changedAfterLastSave = true
	}

	private def void resetChangedAfterLastSaveFlag() {
		this.changedAfterLastSave = false
	}

	def private void setCorrespondenceFeatures(Correspondence correspondence, List<EObject> eObjects1,
		List<EObject> eObjects2, String tag) {
		var aEObjects = eObjects1
		var bEObjects = eObjects2
		correspondence.getAUuids().addAll(aEObjects.map [
			if (uuidResolver.hasPotentiallyCachedUuid(it)) {
				uuidResolver.getPotentiallyCachedUuid(it)
			} else {
				uuidResolver.registerCachedEObject(it);
			}
		])
		correspondence.getBUuids().addAll(bEObjects.map [
			if (uuidResolver.hasPotentiallyCachedUuid(it)) {
				uuidResolver.getPotentiallyCachedUuid(it)
			} else {
				uuidResolver.registerCachedEObject(it);
			}
		])
		correspondence.tag = tag;
	}

	override getAllCorrespondences() {
		return correspondences.correspondences
	}

	private def resolveEObjectFromUuid(String uuid) {
		return uuidResolver.getPotentiallyCachedEObject(uuid)
	}

	override <C extends Correspondence> Set<Correspondence> removeCorrespondencesBetween(Class<C> correspondenceType,
		Predicate<C> correspondencesFilter, List<EObject> aEObjects, List<EObject> bEObjects, String tag) {
		val removedCorrespondences = newArrayList;
		val correspondences = getCorrespondences(correspondenceType, correspondencesFilter, aEObjects, tag);
		for (correspondence : correspondences) {
			val correspondingEObjects = resolveCorrespondingObjects(correspondence, aEObjects);
			if (EcoreUtil.equals(bEObjects, correspondingEObjects)) {
				removedCorrespondences += removeCorrespondencesAndDependendCorrespondences(correspondence);
			}
		}
		return removedCorrespondences.toSet;
	}

	override <C extends Correspondence> Set<Correspondence> removeCorrespondencesFor(Class<C> correspondenceType,
		Predicate<C> correspondencesFilter, List<EObject> eObjects, String tag) {
		val correspondences = getCorrespondences(correspondenceType, correspondencesFilter, eObjects, tag);
		return correspondences.toList.mapFixed[removeCorrespondencesAndDependendCorrespondences].flatten.toSet
	}

	private def Set<Correspondence> removeCorrespondencesAndDependendCorrespondences(Correspondence correspondence) {
		var Set<Correspondence> markedCorrespondences = new HashSet<Correspondence>()
		markCorrespondenceAndDependingCorrespondencesRecursively(markedCorrespondences, correspondence)
		removeMarkedCorrespondences(markedCorrespondences)
		return markedCorrespondences
	}

	private def void markCorrespondenceAndDependingCorrespondencesRecursively(Set<Correspondence> markedCorrespondences,
		Correspondence correspondence) {
		markedCorrespondences.add(correspondence);
		// FIXME MK detect dependency cycles in correspondences already when the reference is updated
		for (dependingCorrespondence : correspondence.dependedOnBy) {
			markCorrespondenceAndDependingCorrespondencesRecursively(markedCorrespondences, dependingCorrespondence)
		}
	}

	private def removeMarkedCorrespondences(Iterable<Correspondence> markedCorrespondences) {
		for (Correspondence markedCorrespondence : markedCorrespondences) {
			EcoreUtil::remove(markedCorrespondence)
			setChangedAfterLastSaveFlag()
		}
	}

	override <E, C extends Correspondence> getAllEObjectsOfTypeInCorrespondences(Class<C> correspondenceType,
		Predicate<C> correspondencesFilter, Class<E> type) {
		allCorrespondences.filter(correspondenceType).filter(correspondencesFilter).map [
			(it.AUuids + it.BUuids).toList.map[resolveEObjectFromUuid]
		].flatten.filter(type).toSet
	}

	override <V extends CorrespondenceModelView<?>> getView(
		CorrespondenceModelViewFactory<V> correspondenceModelViewFactory) {
		return correspondenceModelViewFactory.createCorrespondenceModelView(this);
	}

	override <V extends CorrespondenceModelView<?>> getEditableView(
		CorrespondenceModelViewFactory<V> correspondenceModelViewFactory) {
		return correspondenceModelViewFactory.createEditableCorrespondenceModelView(this);
	}

	override getGenericView() {
		return new GenericCorrespondenceModelViewImpl(this);
	}

}
