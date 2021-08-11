package tools.vitruv.framework.correspondence.impl

import java.util.HashSet
import java.util.LinkedHashSet
import java.util.List
import java.util.Set
import java.util.function.Predicate
import java.util.function.Supplier
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.correspondence.Correspondence
import tools.vitruv.framework.correspondence.CorrespondenceFactory
import tools.vitruv.framework.correspondence.CorrespondenceModelView
import tools.vitruv.framework.correspondence.CorrespondenceModelViewFactory
import tools.vitruv.framework.correspondence.Correspondences
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel

import static com.google.common.base.Preconditions.checkState

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

class InternalCorrespondenceModelImpl implements InternalCorrespondenceModel {
	static val logger = Logger.getLogger(InternalCorrespondenceModelImpl)
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
				correspondence.leftEObjects => [
					val resolvedObjects = it.resolve(resolveIn)
					it.clear()
					it += resolvedObjects	
				]
				correspondence.rightEObjects => [
					val resolvedObjects = it.resolve(resolveIn)
					it.clear()
					it += resolvedObjects	
				]
			}
			this.correspondences.correspondences += loadedCorrespondences.correspondences
		}
	}
	
	private static def resolve(Iterable<EObject> eObjects, ResourceSet resolveIn) {
		val resolvedEObjects = eObjects.mapFixed[EcoreUtil.resolve(it, resolveIn)]
		for (resolvedEObject : resolvedEObjects) {
			checkState(!resolvedEObject.eIsProxy, "object %s is referenced in correspondence but could not be resolved", resolvedEObject)
		}
		return resolvedEObjects
	}

	override save() {
		removeCorrespondencesForRemovedElements()
		this.correspondencesResource?.save(null)
	}
	
	private def void removeCorrespondencesForRemovedElements() {
		val iterator = correspondences.correspondences.iterator
		while (iterator.hasNext()) {
			val element = iterator.next()
			if (element.leftEObjects.exists[!isInManagedResource] || element.rightEObjects.exists[!isInManagedResource]) {
				checkState(element.leftEObjects.forall[!isInManagedResource] || element.leftEObjects.forall[!isInManagedResource],
					"Correspondence between %s and %s contains elements %s that are not contained in a resource anymore.",
					element.leftEObjects, element.rightEObjects, (element.leftEObjects + element.rightEObjects).filter[!isInManagedResource])
				iterator.remove()
				if (logger.traceEnabled) {
					logger.trace('''Correspondence between «element.leftEObjects» and «element.rightEObjects» has been removed as all its elements have been removed from resources.''')
				}
			}
		}
	}
	
	private def static isInManagedResource(EObject object) {
		object instanceof EClass || object.eResource?.resourceSet !== null
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

	private def List<EObject> getCorrespondingEObjects(Correspondence correspondence, List<EObject> eObjects) {
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
