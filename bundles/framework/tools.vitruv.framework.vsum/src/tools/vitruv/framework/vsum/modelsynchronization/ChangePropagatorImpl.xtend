package tools.vitruv.framework.vsum.modelsynchronization

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.ListMultimap
import java.util.Collections
import java.util.List
import java.util.Map
import java.util.Set
import java.util.UUID
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.ChangeCloner
import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.description.impl.PropagatedChangeImpl
import tools.vitruv.framework.change.processing.ChangePropagationObserver
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.correspondence.CorrespondenceProviding
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.ModelRepository
import tools.vitruv.framework.vsum.repositories.ModelRepositoryInterface

class ChangePropagatorImpl implements ChangePropagator, ChangePropagationObserver {
	static extension ChangeCloner = new ChangeCloner
	static extension Logger = Logger::getLogger(ChangePropagatorImpl.simpleName)
	val ChangePropagationSpecificationProvider changePropagationProvider
	val CorrespondenceProviding correspondenceProviding
	val ModelRepository resourceRepository
	val ModelRepositoryInterface modelRepository
	val Set<ChangePropagationListener> changePropagationListeners
	val VitruvDomainRepository metamodelRepository
	val ListMultimap<VURI, String> vuriToIds
	val Map<String, PropagatedChange> idToUnresolvedChanges
	val Map<String, PropagatedChange> idToResolvedChanges

	new(
		ModelRepository resourceRepository,
		ChangePropagationSpecificationProvider changePropagationProvider,
		VitruvDomainRepository metamodelRepository,
		CorrespondenceProviding correspondenceProviding,
		ModelRepositoryInterface modelRepository
	) {
		this.resourceRepository = resourceRepository
		this.modelRepository = modelRepository
		this.changePropagationProvider = changePropagationProvider
		this.changePropagationProvider.forEach[registerObserver(this)]
		this.correspondenceProviding = correspondenceProviding
		this.metamodelRepository = metamodelRepository
		changePropagationListeners = newHashSet
		vuriToIds = ArrayListMultimap.create
		idToUnresolvedChanges = newHashMap
		idToResolvedChanges = newHashMap
	}

	override objectCreated(EObject createdObject) {
		modelRepository.addRootElement(createdObject)
	}

	override addChangePropagationListener(ChangePropagationListener propagationListener) {
		if (propagationListener !== null)
			changePropagationListeners += propagationListener
	}

	override removeChangePropagationListener(ChangePropagationListener propagationListener) {
		if (propagationListener !== null)
			changePropagationListeners -= propagationListener
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
		val propagationResult = new ChangePropagationResult
		propagateSingleChange(change, result, propagationResult, changedResourcesTracker)
		changedResourcesTracker.markNonSourceResourceAsChanged
		executePropagationResult(propagationResult)
		modelRepository.cleanupRootElementsWithoutResource
		// FIXME HK This is not clear! VirtualModel knows how to save, we bypass that, but currently this is necessary
		// because saving has to be performed before finishing propagation. Maybe we should move the observable to the VirtualModel
		resourceRepository.saveAllModels
		debug(modelRepository)
		debug('''
			Propagated changes:
			«FOR propagatedChange : result»
				Propagated Change:
			«propagatedChange»«ENDFOR»
		''')
		finishChangePropagation(change)
		return result
	}

	private def void startChangePropagation(VitruviusChange change) {
		info('''Started synchronizing change: «change»''')
		changePropagationListeners.forEach[startedChangePropagation]
	}

	private def void finishChangePropagation(VitruviusChange change) {
		changePropagationListeners.forEach[finishedChangePropagation]
		info('''Finished synchronizing change: «change»''')
	}

	private def dispatch void propagateSingleChange(
		CompositeContainerChange change,
		List<PropagatedChange> propagatedChanges,
		ChangePropagationResult propagationResult,
		ChangedResourcesTracker changedResourcesTracker
	) {
		change.changes.forEach [
			propagateSingleChange(it, propagatedChanges, propagationResult, changedResourcesTracker)
		]
	}

	private def dispatch void propagateSingleChange(
		TransactionalChange change,
		List<PropagatedChange> propagatedChanges,
		ChangePropagationResult propagationResult,
		ChangedResourcesTracker changedResourcesTracker
	) {
		val clonedChange = clone(change)
		val changeApplicationFunction = [ ResourceSet resourceSet |
			resourceRepository.getModel(change.URI)
			change.resolveBeforeAndApplyForward(resourceSet)
			return
		]

		resourceRepository.executeOnResourceSet(changeApplicationFunction)

		change.affectedEObjects.forEach[modelRepository.addRootElement(it)]
		modelRepository.cleanupRootElements

		val changeDomain = metamodelRepository.getDomain(change.getURI.fileExtension)
		val consequentialChanges = newArrayList
		changePropagationProvider.getChangePropagationSpecifications(changeDomain).forEach [
			consequentialChanges +=
				propagateChangeForChangePropagationSpecification(change, it, propagationResult, changedResourcesTracker)
		]
		addPropagatedChanges(clonedChange, change, consequentialChanges, propagatedChanges)
	}

	private def List<VitruviusChange> propagateChangeForChangePropagationSpecification(
		TransactionalChange change,
		ChangePropagationSpecification propagationSpecification,
		ChangePropagationResult propagationResult,
		ChangedResourcesTracker changedResourcesTracker
	) {
		val correspondenceModel = correspondenceProviding.correspondenceModel

		val List<VitruviusChange> consequentialChanges = newArrayList
		// TODO HK: Clone the changes for each synchronization! Should even be cloned for
		// each consistency repair routines that uses it,
		// or: make them read only, i.e. give them a read-only interface!
		val command = EMFCommandBridge::createVitruviusTransformationRecordingCommand [|
			modelRepository.startRecording
			val propResult = propagationSpecification.propagateChange(change, correspondenceModel)
			modelRepository.cleanupRootElements
			consequentialChanges += modelRepository.endRecording
			consequentialChanges.forEach[debug(it)]
			return propResult

		]
		resourceRepository.executeRecordingCommandOnTransactionalDomain(command)

		// Store modification information
		val changedEObjects = command.affectedObjects.filter(EObject)
		changedEObjects.forEach[changedResourcesTracker.addInvolvedModelResource(eResource)]
		changedResourcesTracker.addSourceResourceOfChange(change)

		propagationResult.integrateResult(command.transformationResult)
		return consequentialChanges
	}

	private def void executePropagationResult(ChangePropagationResult changePropagationResult) {
		if (null === changePropagationResult) {
			info("Current propagation result is null. Can not save new root EObjects.")
			return
		}
		changePropagationResult.elementToPersistenceMap.entrySet.forEach [
			resourceRepository.persistRootElement(value, key)
		]
	}

	private def addPropagatedChanges(
		VitruviusChange unresolvedChange,
		VitruviusChange resolvedChange,
		List<VitruviusChange> consequentialChanges,
		List<PropagatedChange> propagatedChanges
	) {
		val isUnresolved = modelRepository.unresolveChanges
		val vuri = resolvedChange.URI
		val uuid = UUID::randomUUID.toString
		val unresolvedTriggeredChanges = modelRepository.lastUnresolvedChanges
		val resolvedTriggeredChanges = modelRepository.lastResolvedChanges
		if (unresolvedTriggeredChanges.length !== resolvedTriggeredChanges.length)
			throw new IllegalStateException('''
				The length of changes should be equal but there are «unresolvedTriggeredChanges.length» 
				respectively «resolvedTriggeredChanges.length»
			''')

		val unresolvedPropagatedChange = new PropagatedChangeImpl(uuid, unresolvedChange,
			VitruviusChangeFactory::instance.createCompositeChange(unresolvedTriggeredChanges))
		val resolvedPropagatedChange = new PropagatedChangeImpl(uuid, resolvedChange,
			VitruviusChangeFactory::instance.createCompositeChange(resolvedTriggeredChanges))

		propagatedChanges += if (isUnresolved) unresolvedPropagatedChange else resolvedPropagatedChange
		if (!resolvedPropagatedChange.resolved)
			throw new IllegalStateException
		if (unresolvedPropagatedChange.resolved)
			throw new IllegalStateException

		vuriToIds.put(vuri, uuid)
		idToResolvedChanges.put(uuid, resolvedPropagatedChange)
		idToUnresolvedChanges.put(uuid, unresolvedPropagatedChange)
	}

}
