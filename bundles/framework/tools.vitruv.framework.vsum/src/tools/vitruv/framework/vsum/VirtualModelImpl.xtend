package tools.vitruv.framework.vsum

import java.io.File
import java.util.Date
import java.util.List
import java.util.Map
import java.util.concurrent.Callable

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.lib.Functions.Function1

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagator
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagatorImpl
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl
import tools.vitruv.framework.vsum.repositories.ResourceRepositoryImpl

class VirtualModelImpl implements InternalTestVersioningVirtualModel {
	// Values.
	@Accessors(PUBLIC_GETTER)
	val File folder

	protected val ResourceRepositoryImpl resourceRepository

	val ChangePropagationSpecificationProvider changePropagationSpecificationProvider
	val ChangePropagator changePropagator
	val ModelRepositoryImpl modelRepository
	val VitruvDomainRepository metamodelRepository
	/**  Attribute for versioning */
	val Map<VURI, String> vuriToLastpropagatedChange
	/**  Attribute for versioning */
	val BiMap<VURI, VURI> vuriMap
	val BiMap<VURI, VURI> vuriSourceToTargetMap

	// Variables.
	@Accessors(PUBLIC_GETTER)
	UserInteracting userInteractor

	/**  Attribute for versioning */
	Date lastCommitDate
	/**  Attribute for versioning */
	String allLastPropagatedChangeId

	new(
		File folder,
		UserInteracting userInteracting,
		VirtualModelConfiguration modelConfiguration
	) {
		this.folder = folder
		metamodelRepository = new VitruvDomainRepositoryImpl
		userInteractor = userInteracting
		modelConfiguration.metamodels.forEach [
			metamodelRepository.addDomain(it)
			registerAtTuidManagement
		]
		resourceRepository = new ResourceRepositoryImpl(folder, metamodelRepository)
		modelRepository = new ModelRepositoryImpl
		val changePropagationSpecificationRepository = new ChangePropagationSpecificationRepository
		modelConfiguration.changePropagationSpecifications.forEach [ changePropagationSpecification |
			changePropagationSpecification.userInteracting = userInteracting
			changePropagationSpecificationRepository.putChangePropagationSpecification(changePropagationSpecification)
		]
		changePropagationSpecificationProvider = changePropagationSpecificationRepository
		changePropagator = new ChangePropagatorImpl(
			resourceRepository,
			changePropagationSpecificationProvider,
			metamodelRepository,
			resourceRepository,
			modelRepository
		)
		VirtualModelManager::instance.putVirtualModel(this)
		vuriToLastpropagatedChange = newHashMap
		allLastPropagatedChangeId = null
		vuriMap = HashBiMap::create
		vuriSourceToTargetMap = HashBiMap::create
	}

	// Overridden methods.
	override getCorrespondenceModel() {
		resourceRepository.correspondenceModel
	}

	override getModelInstance(VURI modelVuri) {
		resourceRepository.getModel(modelVuri)
	}

	override save() {
		resourceRepository.saveAllModels
	}

	override persistRootElement(VURI persistenceVuri, EObject rootElement) {
		resourceRepository.persistRootElement(persistenceVuri, rootElement)
	}

	override executeCommand(Callable<Void> command) {
		resourceRepository.createRecordingCommandAndExecuteCommandOnTransactionalDomain(command)
	}

	override addChangePropagationListener(ChangePropagationListener changePropagationListener) {
		changePropagator.addChangePropagationListener(changePropagationListener)
	}

	override propagateChange(VitruviusChange change) {
		// Save is done by the change propagator because it has to be performed before finishing sync
		return changePropagator.propagateChange(change)
	}

	override reverseChanges(List<PropagatedChange> changes) {
		val command = EMFCommandBridge::createVitruviusTransformationRecordingCommand [|
			changes.reverseView.forEach [
				applyBackward
				changePropagator.removePropagatedChange(originalChange.URI, id)
			]
			return null
		]
		resourceRepository.executeRecordingCommandOnTransactionalDomain(command)

		val changedEObjects = command.affectedObjects.filter(EObject)
		changedEObjects.map[eResource].filterNull.forEach[modified = true]
		save
	}

	override forwardChanges(List<PropagatedChange> changes) {
		val command = EMFCommandBridge::createVitruviusTransformationRecordingCommand [
			changes.forEach [
				applyForward
				changePropagator.addPropagatedChanges(originalChange.URI, id)
			]
			return null
		]
		resourceRepository.executeRecordingCommandOnTransactionalDomain(command)

		val changedEObjects = command.affectedObjects.filter(EObject)
		changedEObjects.map[eResource].filterNull.forEach[modified = true]
		save
	}

	override setUserInteractor(UserInteracting userInteract) {
		this.userInteractor = userInteract
		changePropagationSpecificationProvider.forEach[userInteracting = userInteract]
	}

	override getMappedVURI(VURI vuri) {
		if (vuriMap.containsKey(vuri))
			return vuriMap.get(vuri)
		if (vuriMap.inverse.containsKey(vuri))
			return vuriMap.inverse.get(vuri)
		return null
	}

	override getResolvedPropagatedChanges(VURI vuri) {
		getPropagatedChanges(vuri, [VURI newVURI|changePropagator.getResolvedPropagatedChanges(newVURI)])
	}

	override getUnresolvedPropagatedChanges(VURI vuri) {
		getPropagatedChanges(vuri, [VURI newVURI|changePropagator.getUnresolvedPropagatedChanges(newVURI)])
	}

	override getUnresolvedPropagatedChangesSinceLastCommit(VURI vuri) {
		val changes = getUnresolvedPropagatedChanges(vuri)
		if (vuriToLastpropagatedChange.containsKey(vuri)) {
			val lastPropagatedId = vuriToLastpropagatedChange.get(vuri)
			val filteredChanges = dropAllPreviousChanges(changes, lastPropagatedId)
			if (filteredChanges.empty) {
				// FIXME PS This is a dirty hack! 
				// Please handle correct!
				val otherChanges = getUnresolvedChanges(
					vuriSourceToTargetMap,
					vuri,
					[VURI newVURI|changePropagator.getUnresolvedPropagatedChanges(newVURI)]
				)

				return otherChanges.reverseView.take(changes.length).toList.reverseView
			} else {
				return filteredChanges
			}
		} else {
			return changes.toList
		}
	}

	override getAllUnresolvedPropagatedChangesSinceLastCommit() {
		val changes = changePropagator.allUnresolvedPropagatedChanges
		return if (null === allLastPropagatedChangeId)
			changes.toList
		else
			dropAllPreviousChanges(changes, allLastPropagatedChangeId)
	}

	override setLastPropagatedChangeId(VURI vuri, String id) {
		vuriToLastpropagatedChange.put(vuri, id)
		if (vuriMap.containsKey(vuri)) {
			val vuri2 = vuriMap.get(vuri)
			vuriToLastpropagatedChange.put(vuri2, id)
		}
		if (vuriMap.inverse.containsKey(vuri)) {
			val vuri2 = vuriMap.inverse.get(vuri)
			vuriToLastpropagatedChange.put(vuri2, id)
		}
	}

	override getAllResolvedPropagatedChanges() {
		changePropagator.allResolvedPropagatedChanges
	}

	override getAllUnresolvedPropagatedChanges() {
		changePropagator.allUnresolvedPropagatedChanges
	}

	override getUserInteractionsSinceLastCommit() {
		if (null === lastCommitDate)
			userInteractor.allUserInteractions
		else
			userInteractor.getAllUserInteractionsSince(lastCommitDate)
	}

	override setAllLastPropagatedChangeId(String id) {
		allLastPropagatedChangeId = id
		lastCommitDate = new Date
	}

	/**
	 * {@inheritDoc}
	 */
	override propagateChange(PropagatedChange propagatedChange) {
		propagateChange(propagatedChange, null)
	}

	override propagateChange(PropagatedChange propagatedChange, VURI vuri) {
		changePropagator.propagateChange(propagatedChange, vuri)
	}

	override propagateChange(VURI vuri, VitruviusChange change, String changeId) {
		changePropagator.propagateChange(vuri, change, changeId)
	}

	override getResolvedChange(String id) {
		changePropagator.getResolvedChange(id)
	}

	/**
	 * {@inheritDoc}
	 */
	override addMappedVURIs(VURI vuri1, VURI vuri2) {
		vuriMap.put(vuri1, vuri2)
	}

	/**
	 * {@inheritDoc}
	 */
	override addTriggeredRelation(VURI source, VURI target) {
		vuriSourceToTargetMap.put(source, target)
	}

	// Private methods.
	private def dropAllPreviousChanges(
		List<PropagatedChange> propagatedChanges,
		String lastCommitedChange
	) {
		if (propagatedChanges.exists[id == lastCommitedChange])
			propagatedChanges.dropWhile[id != lastCommitedChange].drop(1).toList
		else
			propagatedChanges
	}

	private def getUnresolvedChanges(
		BiMap<VURI, VURI> bimap,
		VURI vuri,
		Function1<VURI, List<PropagatedChange>> changeFunction
	) {
		if (bimap.containsKey(vuri)) {
			val vuri2 = bimap.get(vuri)
			return changeFunction.apply(vuri2)
		}
		if (bimap.inverse.containsKey(vuri)) {
			val vuri3 = bimap.inverse.get(vuri)
			return changeFunction.apply(vuri3)
		}
		return #[]
	}

	private def getPropagatedChanges(VURI vuri, Function1<VURI, List<PropagatedChange>> changeFunction) {
		val changes = newArrayList
		val unresolvedChanges = changeFunction.apply(vuri)
		if (unresolvedChanges.empty) {
			changes += getUnresolvedChanges(vuriMap, vuri, changeFunction)
			if (changes.empty) {
				changes += getUnresolvedChanges(vuriSourceToTargetMap, vuri, changeFunction)
			}
		} else {
			changes += unresolvedChanges
		}
		return changes
	}

}
