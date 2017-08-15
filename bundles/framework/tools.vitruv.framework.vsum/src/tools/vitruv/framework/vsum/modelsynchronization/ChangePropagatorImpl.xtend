package tools.vitruv.framework.vsum.modelsynchronization

import java.util.Collections
import java.util.List
import java.util.Map
import java.util.Set
import java.util.UUID

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.ListMultimap

import tools.vitruv.framework.change.description.ChangeCloner
import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.description.impl.ChangeClonerImpl
import tools.vitruv.framework.change.description.impl.PropagatedChangeWithCorrespondentImpl
import tools.vitruv.framework.change.processing.ChangePropagationObserver
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.correspondence.CorrespondenceProviding
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.ModelRepository
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl

class ChangePropagatorImpl implements ChangePropagator, ChangePropagationObserver {
	// Extensions
	static extension ChangeCloner = new ChangeClonerImpl
	static extension Logger = Logger::getLogger(ChangePropagatorImpl.simpleName)
	static extension VitruviusChangeFactory = VitruviusChangeFactory::instance

	// Values 
	val ChangePropagationSpecificationProvider changePropagationProvider
	val CorrespondenceProviding correspondenceProviding
	val List<EObject> objectsCreatedDuringPropagation
	val ListMultimap<VURI, String> vuriToIds
	val Map<String, PropagatedChange> idToResolvedChanges
	val Map<String, PropagatedChange> idToUnresolvedChanges
	val ModelRepository resourceRepository
	val ModelRepositoryImpl modelRepository
	val Set<ChangePropagationListener> changePropagationListeners
	val VitruvDomainRepository metamodelRepository

	String currentChangeId

	new(
		ModelRepository resourceRepository,
		ChangePropagationSpecificationProvider changePropagationProvider,
		VitruvDomainRepository metamodelRepository,
		CorrespondenceProviding correspondenceProviding,
		ModelRepositoryImpl modelRepository
	) {
		this.resourceRepository = resourceRepository
		this.modelRepository = modelRepository
		this.changePropagationProvider = changePropagationProvider
		this.changePropagationProvider.forEach[registerObserver(this)]
		this.correspondenceProviding = correspondenceProviding
		this.metamodelRepository = metamodelRepository
		changePropagationListeners = newHashSet
		vuriToIds = ArrayListMultimap::create
		idToUnresolvedChanges = newHashMap
		idToResolvedChanges = newHashMap
		objectsCreatedDuringPropagation = newArrayList
	}

	override addChangePropagationListener(ChangePropagationListener propagationListener) {
		if (propagationListener !== null)
			changePropagationListeners += propagationListener
	}

	override removeChangePropagationListener(ChangePropagationListener propagationListener) {
		if (propagationListener !== null)
			changePropagationListeners -= propagationListener
	}

	/**
	 * {@inheritDoc} 
	 */
	override propagateChange(PropagatedChange propagatedChange, VURI vuri) {
		val changeId = propagatedChange.id
		if (null === vuri && vuriToIds.values.parallelStream.anyMatch[it == changeId])
			throw new IllegalStateException(''' «changeId»''')
		else if (vuriToIds.get(vuri).parallelStream.anyMatch[it == changeId])
			throw new IllegalStateException(''' «changeId»''')
		currentChangeId = changeId
		val originalChange = propagatedChange.originalChange
		val clonedChange = cloneVitruviusChange(originalChange)
		val consequentialChanges = propagatedChange.consequentialChanges
		val changeApplicationFunction = createChangeApplyFunction(originalChange as TransactionalChange)

		resourceRepository.executeOnResourceSet(changeApplicationFunction)

		originalChange.affectedEObjects.forEach[modelRepository.addRootElement(it)]
		modelRepository.cleanupRootElements

		val changedObjects = originalChange.affectedEObjects
		if (changedObjects.nullOrEmpty)
			throw new IllegalStateException('''There are no objects affected by the given change«originalChange»''')

		val consequentialChangesApplicationFunction = createChangeApplyFunction(
			consequentialChanges as TransactionalChange)

		resourceRepository.executeOnResourceSet(consequentialChangesApplicationFunction)
		addPropagatedChanges(clonedChange, originalChange, newArrayList)
	}

	override synchronized List<PropagatedChange> propagateChange(VitruviusChange change) {
		if (change === null || !change.containsConcreteChange) {
			info('''The change does not contain any changes to synchronize: «change»''')
			return Collections::emptyList
		}
		if (!change.validate)
			throw new IllegalArgumentException('''Change contains changes from different models: «change»''')

		startChangePropagation(change)
		change.applyBackwardIfLegacy
		var List<PropagatedChange> result = newArrayList
		val changedResourcesTracker = new ChangedResourcesTracker
		propagateSingleChange(change, result, changedResourcesTracker)
		changedResourcesTracker.markNonSourceResourceAsChanged
		// FIXME HK This is not clear! VirtualModel knows how to save, we bypass that, but currently this is necessary
		// because saving has to be performed before finishing propagation. Maybe we should move the observable to the VirtualModel
		resourceRepository.saveAllModels
		debug(modelRepository)
		debug('''
			Propagated changes:
			«FOR propagatedChange : result»
				Propagated Change:
				«propagatedChange»
			«ENDFOR»
		''')
		finishChangePropagation(change)
		return result
	}

	override getResolvedPropagatedChanges(VURI vuri) {
		return if (vuriToIds.containsKey(vuri)) {
			vuriToIds.get(vuri).map[idToResolvedChanges.get(it)].toList
		} else
			#[]
	}

	override getUnresolvedPropagatedChanges(VURI vuri) {
		return if (vuriToIds.containsKey(vuri)) {
			vuriToIds.get(vuri).map[idToUnresolvedChanges.get(it)].toList
		} else
			#[]
	}

	override removePropagatedChange(VURI vuri, String id) {
		vuriToIds.remove(vuri, id)
	}

	override addPropagatedChanges(VURI vuri, String id) {
		vuriToIds.put(vuri, id)
	}

	override objectCreated(EObject createdObject) {
		objectsCreatedDuringPropagation += createdObject
		modelRepository.addRootElement(createdObject)
	}

	override getAllResolvedPropagatedChanges() {
		val returnValue = vuriToIds.keySet.map[resolvedPropagatedChanges].flatten.toList
		return returnValue
	}

	override getAllUnresolvedPropagatedChanges() {
		val returnValue = vuriToIds.keySet.map[unresolvedPropagatedChanges].flatten.toList
		return returnValue
	}

	override getResolvedChange(String id) {
		return idToResolvedChanges.get(id)
	}

	private def void startChangePropagation(VitruviusChange change) {
		info('''Started synchronizing change: «change»''')
		changePropagationListeners.forEach[startedChangePropagation]
		resourceRepository.currentVURI = change.URI
	}

	private def void finishChangePropagation(VitruviusChange change) {
		changePropagationListeners.forEach[finishedChangePropagation]
		info('''Finished synchronizing change: «change»''')
	}

	private def dispatch void propagateSingleChange(
		CompositeContainerChange change,
		List<PropagatedChange> propagatedChanges,
		ChangedResourcesTracker changedResourcesTracker
	) {
		change.changes.forEach[propagateSingleChange(it, propagatedChanges, changedResourcesTracker)]
	}

	static val functionApplyChangeOnResourceSet = [ ModelRepository repo, TransactionalChange change, ResourceSet resourceSet |
		// If change has a URI, load the model
		if (change.URI !== null) repo.getModel(change.URI)
		change.resolveBeforeAndApplyForward(resourceSet)
		return
	]

	private def createChangeApplyFunction(TransactionalChange transactionalChange) {
		functionApplyChangeOnResourceSet.curry(resourceRepository).curry(transactionalChange)
	}

	private def dispatch void propagateSingleChange(
		TransactionalChange change,
		List<PropagatedChange> propagatedChanges,
		ChangedResourcesTracker changedResourcesTracker
	) {
		val clonedChange = cloneVitruviusChange(change)
		val changeApplicationFunction = createChangeApplyFunction(change)

		resourceRepository.executeOnResourceSet(changeApplicationFunction)

		change.affectedEObjects.forEach[modelRepository.addRootElement(it)]
		modelRepository.cleanupRootElements

		val changedObjects = change.affectedEObjects
		if (changedObjects.nullOrEmpty)
			throw new IllegalStateException('''There are no objects affected by the given change«change»''')

		val changeDomain = metamodelRepository.getDomain(changedObjects.get(0))
		val propagationResult = new ChangePropagationResult
		resourceRepository.startRecording
		changePropagationProvider.getChangePropagationSpecifications(changeDomain).forEach [
			propagateChangeForChangePropagationSpecification(change, it, propagationResult, changedResourcesTracker)
		]
		handleObjectsWithoutResource
		resourceRepository.endRecording
		addPropagatedChanges(clonedChange, change, propagatedChanges)
	}

	private def void handleObjectsWithoutResource() {
		modelRepository.cleanupRootElementsWithoutResource
		// Find created objects without resource
		for (createdObjectWithoutResource : objectsCreatedDuringPropagation.filter[eResource === null]) {
			if (correspondenceProviding.correspondenceModel.hasCorrespondences(#[createdObjectWithoutResource])) {
				throw new IllegalStateException("Every object must be contained within a resource: " +
					createdObjectWithoutResource)
			} else {
				warn("Object was created but has no correspondence and is thus lost: " + createdObjectWithoutResource)
			}
		}
		objectsCreatedDuringPropagation.clear
	}

	private def propagateChangeForChangePropagationSpecification(
		TransactionalChange change,
		ChangePropagationSpecification propagationSpecification,
		ChangePropagationResult propagationResult,
		ChangedResourcesTracker changedResourcesTracker
	) {
		val correspondenceModel = correspondenceProviding.correspondenceModel

		// TODO HK: Clone the changes for each synchronization! Should even be cloned for
		// each consistency repair routines that uses it,
		// or: make them read only, i.e. give them a read-only interface!
		val command = EMFCommandBridge::createVitruviusTransformationRecordingCommand [
			val propResult = propagationSpecification.propagateChange(change, correspondenceModel)
			modelRepository.cleanupRootElements
			return propResult
		]
		resourceRepository.executeRecordingCommandOnTransactionalDomain(command)

		// Store modification information
		val changedEObjects = command.affectedObjects.filter(EObject)
		changedEObjects.forEach[changedResourcesTracker.addInvolvedModelResource(eResource)]
		changedResourcesTracker.addSourceResourceOfChange(change)

		executePropagationResult(command.transformationResult)
		propagationResult.integrateResult(command.transformationResult)
	}

	private def void executePropagationResult(ChangePropagationResult changePropagationResult) {
		if (null === changePropagationResult) {
			info("Current propagation result is null. Can not save new root EObjects.")
			return
		}
		if (changePropagationResult.elementToPersistenceMap.empty) {
			info("Current propagation result is empty. Can not save new root EObjects.")
			return
		}
		changePropagationResult.elementToPersistenceMap.entrySet.forEach [
			resourceRepository.persistRootElement(value, key)
		]
	}

	private def addPropagatedChanges(
		VitruviusChange unresolvedChange,
		VitruviusChange resolvedChange,
		List<PropagatedChange> propagatedChanges
	) {
		val isUnresolved = modelRepository.unresolveChanges
		val vuri = resolvedChange.URI
		val uuid = if (null !== currentChangeId) currentChangeId else UUID::randomUUID.toString
		currentChangeId = null
		val unresolvedTriggeredChanges = resourceRepository.lastUnresolvedChanges
		val resolvedTriggeredChanges = resourceRepository.lastResolvedChanges
		if (unresolvedTriggeredChanges.length !== resolvedTriggeredChanges.length)
			throw new IllegalStateException('''
				The length of changes should be equal but there are «unresolvedTriggeredChanges.length»
				respectively «resolvedTriggeredChanges.length»
			''')
		val unresolvedPropagatedChange = new PropagatedChangeWithCorrespondentImpl(uuid, unresolvedChange,
			createCompositeChange(unresolvedTriggeredChanges))
		val resolvedPropagatedChange = new PropagatedChangeWithCorrespondentImpl(uuid, resolvedChange,
			createCompositeChange(resolvedTriggeredChanges))

		unresolvedPropagatedChange.correspondent = resolvedPropagatedChange
		resolvedPropagatedChange.correspondent = unresolvedPropagatedChange

		propagatedChanges += if (isUnresolved)
			unresolvedPropagatedChange
		else
			resolvedPropagatedChange
		if (!resolvedPropagatedChange.resolved)
			error('''«resolvedPropagatedChange» should be resolved, but was not''')
		if (unresolvedPropagatedChange.resolved)
			error('''«unresolvedPropagatedChange» should be unresolved, but was not''')
		if (null !== vuri) {
			vuriToIds.put(vuri, uuid)
			idToResolvedChanges.put(uuid, resolvedPropagatedChange)
			idToUnresolvedChanges.put(uuid, unresolvedPropagatedChange)
		} else
			warn('''resolvedChange.URI was null''')
	}

}
