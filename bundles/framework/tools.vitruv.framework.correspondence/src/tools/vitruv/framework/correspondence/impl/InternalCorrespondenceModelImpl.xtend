package tools.vitruv.framework.correspondence.impl

import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import java.util.function.Supplier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.correspondence.Correspondence
import tools.vitruv.framework.correspondence.CorrespondenceFactory
import tools.vitruv.framework.correspondence.Correspondences
import tools.vitruv.framework.util.VitruviusConstants
import tools.vitruv.framework.uuid.UuidResolver

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.framework.util.bridges.JavaBridge.*
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceModelView
import tools.vitruv.framework.correspondence.CorrespondenceModelViewFactory
import java.util.function.Predicate
import java.util.LinkedHashSet
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.util.bridges.EcoreResourceBridge.loadOrCreateResource
import static extension tools.vitruv.framework.util.bridges.EcoreResourceBridge.getResourceContentRootIfUnique
import static com.google.common.base.Preconditions.checkState

class InternalCorrespondenceModelImpl implements InternalCorrespondenceModel {
	static val saveAndLoadOptions = Map.of(VitruviusConstants.optionProcessDanglingHref,
		VitruviusConstants.optionProcessDanglingHrefDiscard)
	val Correspondences correspondences
	val UuidResolver uuidResolver
	val Resource correspondencesResource

	new(UuidResolver uuidResolver, URI resourceUri) {
		this.uuidResolver = uuidResolver
		this.correspondences = CorrespondenceFactory::eINSTANCE.createCorrespondences()
		this.correspondencesResource = if (resourceUri !== null)
			new ResourceSetImpl().withGlobalFactories.createResource(resourceUri) => [
				contents += correspondences
			]
	}

	override loadSerializedCorrespondences() {
		checkState(correspondencesResource !== null,
			"Correspondences resource must be specified to load existing correspondences")
		val loadedResource = (new ResourceSetImpl().withGlobalFactories => [loadOptions += saveAndLoadOptions]).
			loadOrCreateResource(correspondencesResource.URI)
		val loadedCorrespondences = loadedResource.resourceContentRootIfUnique?.dynamicCast(Correspondences,
			"Correspondences model")
		if (loadedCorrespondences !== null) {
			this.correspondences.correspondences += loadedCorrespondences.correspondences
		}
	}
	
	override save() {
		this.correspondencesResource?.save(saveAndLoadOptions)
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

	def private <C extends Correspondence> C createAndAddCorrespondence(List<EObject> firstObjects,
		List<EObject> secondObjects, String tag, C correspondence) {
		correspondence => [
			AUuids += firstObjects.map [
				if (uuidResolver.hasPotentiallyCachedUuid(it)) {
					uuidResolver.getPotentiallyCachedUuid(it)
				} else {
					uuidResolver.registerCachedEObject(it)
				}
			]
			BUuids += secondObjects.map [
				if (uuidResolver.hasPotentiallyCachedUuid(it)) {
					uuidResolver.getPotentiallyCachedUuid(it)
				} else {
					uuidResolver.registerCachedEObject(it)
				}
			]
			it.tag = tag
		]
		this.correspondences.correspondences += correspondence
		return correspondence
	}

	def private removeCorrespondence(Correspondence correspondence) {
		EcoreUtil.remove(correspondence)
	}

	override <C extends Correspondence> Set<Correspondence> removeCorrespondencesBetween(Class<C> correspondenceType,
		Predicate<C> correspondencesFilter, List<EObject> aEObjects, List<EObject> bEObjects, String tag) {
		getCorrespondences(correspondenceType, correspondencesFilter, aEObjects, tag)
			.filter[EcoreUtil.equals(bEObjects, resolveCorrespondingObjects(aEObjects))]
			.flatMapFixedTo(new LinkedHashSet) [removeCorrespondencesAndDependendCorrespondences()]
	}

	override <C extends Correspondence> Set<Correspondence> removeCorrespondencesFor(Class<C> correspondenceType,
		Predicate<C> correspondencesFilter, List<EObject> eObjects, String tag) {
		getCorrespondences(correspondenceType, correspondencesFilter, eObjects, tag)
			.flatMapFixedTo(new LinkedHashSet) [removeCorrespondencesAndDependendCorrespondences()]
	}

	private def Set<Correspondence> removeCorrespondencesAndDependendCorrespondences(Correspondence correspondence) {
		var markedCorrespondences = new HashSet<Correspondence>()
		markCorrespondenceAndDependingCorrespondencesRecursively(markedCorrespondences, correspondence)
		markedCorrespondences.forEach[removeCorrespondence]
		return markedCorrespondences
	}

	private def void markCorrespondenceAndDependingCorrespondencesRecursively(Set<Correspondence> markedCorrespondences,
		Correspondence correspondence) {
		markedCorrespondences.add(correspondence)
		// FIXME MK detect dependency cycles in correspondences already when the reference is updated
		for (dependingCorrespondence : correspondence.dependedOnBy) {
			markCorrespondenceAndDependingCorrespondencesRecursively(markedCorrespondences, dependingCorrespondence)
		}
	}

	override <C extends Correspondence> Set<C> getCorrespondences(Class<C> correspondenceType,
		Predicate<C> correspondencesFilter, List<EObject> eObjects, String tag) {
		val correspondences = new HashSet<Correspondence>()
		val uuids = eObjects.mapFixed [uuidResolver.getPotentiallyCachedUuid(it)]
		correspondences += getCorrespondencesForUuids(uuids)

		return correspondences
			.filter(correspondenceType).filter(correspondencesFilter)
			.filter[tag === null || it.tag == tag].toSet
	}

	private def Set<Correspondence> getCorrespondencesForUuids(List<String> uuids) {
		return this.correspondences.correspondences.filter[AUuids.containsAll(uuids) || BUuids.containsAll(uuids)].toSet
	}

	override <C extends Correspondence> Set<List<EObject>> getCorrespondingEObjects(Class<C> correspondenceType,
		Predicate<C> correspondencesFilter, List<EObject> eObjects, String tag) {
		return getCorrespondences(correspondenceType, correspondencesFilter, eObjects, tag)
			.mapFixedTo(new LinkedHashSet) [resolveCorrespondingObjects(eObjects)]
	}

	override List<EObject> getCorrespondingEObjectsInCorrespondence(Correspondence correspondence,
		List<EObject> eObjects) {
		resolveCorrespondingObjects(correspondence, eObjects)
	}

	private def List<EObject> resolveCorrespondingObjects(Correspondence correspondence, List<EObject> eObjects) {
		val uuids = eObjects.map[uuidResolver.getPotentiallyCachedUuid(it)]
		val correspondingUuids = correspondence.getCorrespondingUuids(uuids)
		return correspondingUuids.mapFixed[resolveEObjectFromUuid]
	}

	private static def getCorrespondingUuids(Correspondence correspondence, List<String> uuids) {
		var List<String> aUuids = correspondence.AUuids
		var List<String> bUuids = correspondence.BUuids
		if (aUuids === null || bUuids === null || aUuids.size == 0 || bUuids.size == 0) {
			throw new IllegalStateException(
				'''The correspondence '«»«correspondence»' links to an empty Uuid '«»«aUuids»' or '«»«bUuids»'!'''.
					toString)
		}
		if (aUuids.equals(uuids)) {
			return bUuids
		} else {
			return aUuids
		}
	}

	override boolean hasCorrespondences() {
		return !this.correspondences.correspondences.empty
	}

	override hasCorrespondences(List<EObject> eObjects) {
		val correspondences = this.getCorrespondences(Correspondence, [true], eObjects, null)
		return correspondences !== null && correspondences.size() > 0
	}

	override getAllCorrespondences() {
		return correspondences.correspondences
	}

	override <E, C extends Correspondence> getAllEObjectsOfTypeInCorrespondences(Class<C> correspondenceType,
		Predicate<C> correspondencesFilter, Class<E> type) {
		allCorrespondences
			.filter(correspondenceType).filter(correspondencesFilter)
			.flatMap[(it.AUuids + it.BUuids).map[resolveEObjectFromUuid]]
			.filter(type).toSet
	}

	private def resolveEObjectFromUuid(String uuid) {
		return uuidResolver.getPotentiallyCachedEObject(uuid)
	}
	
	override <V extends CorrespondenceModelView<?>> getView(
		CorrespondenceModelViewFactory<V> correspondenceModelViewFactory) {
		return correspondenceModelViewFactory.createCorrespondenceModelView(this)
	}

	override <V extends CorrespondenceModelView<?>> getEditableView(
		CorrespondenceModelViewFactory<V> correspondenceModelViewFactory) {
		return correspondenceModelViewFactory.createEditableCorrespondenceModelView(this)
	}

	override getGenericView() {
		return new GenericCorrespondenceModelViewImpl(this)
	}

}
