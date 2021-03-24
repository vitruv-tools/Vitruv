package tools.vitruv.framework.correspondence.impl

import java.util.HashSet
import java.util.List
import java.util.Set
import java.util.function.Supplier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.correspondence.Correspondence
import tools.vitruv.framework.correspondence.CorrespondenceFactory
import tools.vitruv.framework.correspondence.Correspondences

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceModelView
import tools.vitruv.framework.correspondence.CorrespondenceModelViewFactory
import java.util.function.Predicate
import java.util.LinkedHashSet
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource
import static com.google.common.base.Preconditions.checkState
import org.eclipse.emf.ecore.resource.ResourceSet
import java.util.Map

class InternalCorrespondenceModelImpl implements InternalCorrespondenceModel {
	static val saveOptions = Map.of("PROCESS_DANGLING_HREF", "DISCARD")
	val Correspondences correspondences
	val Resource correspondencesResource

	new(URI resourceUri) {
		this.correspondences = CorrespondenceFactory::eINSTANCE.createCorrespondences()
		this.correspondencesResource = if (resourceUri !== null)
			new ResourceSetImpl().withGlobalFactories.createResource(resourceUri) => [
				contents += correspondences
			]
	}

	override loadSerializedCorrespondences(ResourceSet resolveIn) {
		checkState(correspondencesResource !== null,
			"Correspondences resource must be specified to load existing correspondences")
		val loadedResource = new ResourceSetImpl().withGlobalFactories.loadOrCreateResource(correspondencesResource.URI)
		if (!loadedResource.contents.empty) {
			val loadedCorrespondences = loadedResource.contents.get(0) as Correspondences
			for (correspondence : loadedCorrespondences.correspondences) {
				val resolvedLeftObjects = correspondence.leftEObjects.mapFixed[EcoreUtil.resolve(it, resolveIn)]
				val resolvedRightObjects = correspondence.rightEObjects.mapFixed[EcoreUtil.resolve(it, resolveIn)]
				for (resolvedObject : resolvedLeftObjects + resolvedRightObjects) {
					if (resolvedObject.eIsProxy) {
						throw new IllegalStateException("Object " + resolvedObject +
							" is referenced in correspondence but could not be resolved")
					}
				}
				correspondence.leftEObjects.clear
				correspondence.leftEObjects += resolvedLeftObjects
				correspondence.rightEObjects.clear
				correspondence.rightEObjects += resolvedRightObjects
			}
			this.correspondences.correspondences += loadedCorrespondences.correspondences
		}
	}

	override save() {
		this.correspondencesResource?.save(saveOptions)
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
			leftEObjects += firstObjects
			rightEObjects += secondObjects
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
		getCorrespondences(correspondenceType, correspondencesFilter, aEObjects, tag).filter [
			EcoreUtil.equals(bEObjects, getCorrespondingEObjects(aEObjects))
		].flatMapFixedTo(new LinkedHashSet)[removeCorrespondencesAndDependendCorrespondences()]
	}

	override <C extends Correspondence> Set<Correspondence> removeCorrespondencesFor(Class<C> correspondenceType,
		Predicate<C> correspondencesFilter, List<EObject> eObjects, String tag) {
		getCorrespondences(correspondenceType, correspondencesFilter, eObjects, tag).flatMapFixedTo(new LinkedHashSet) [
			removeCorrespondencesAndDependendCorrespondences()
		]
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
		return eObjects.getCorrespondences().filter(correspondenceType).filter(correspondencesFilter).filter [
			tag === null || it.tag == tag
		].toSet
	}

	private def Set<Correspondence> getCorrespondences(List<EObject> eObjects) {
		return this.correspondences.correspondences.filter [ correspondence |
			eObjects == correspondence.leftEObjects || eObjects == correspondence.rightEObjects
		].toSet
	}

	override <C extends Correspondence> Set<List<EObject>> getCorrespondingEObjects(Class<C> correspondenceType,
		Predicate<C> correspondencesFilter, List<EObject> eObjects, String tag) {
		return getCorrespondences(correspondenceType, correspondencesFilter, eObjects, tag).mapFixedTo(
			new LinkedHashSet)[getCorrespondingEObjects(eObjects)]
	}

	override List<EObject> getCorrespondingEObjects(Correspondence correspondence, List<EObject> eObjects) {
		return if (correspondence.leftEObjects == eObjects) {
			correspondence.rightEObjects
		} else {
			correspondence.leftEObjects
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
		allCorrespondences.filter(correspondenceType).filter(correspondencesFilter).flatMap [
			(it.leftEObjects + it.rightEObjects)
		].filter(type).toSet
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
