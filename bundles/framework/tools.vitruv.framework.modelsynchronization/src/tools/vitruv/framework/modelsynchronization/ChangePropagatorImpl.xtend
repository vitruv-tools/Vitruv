package tools.vitruv.framework.modelsynchronization

import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.correspondence.CorrespondenceProviding
import tools.vitruv.framework.domains.repository.ModelRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.util.command.ChangePropagationResult

class ChangePropagatorImpl implements ChangePropagator {
	static Logger logger = Logger::getLogger(ChangePropagatorImpl.simpleName)
	final VitruvDomainRepository metamodelRepository
	final ModelRepository modelProviding
	final ChangePropagationSpecificationProvider changePropagationProvider
	final CorrespondenceProviding correspondenceProviding
	Set<ChangePropagationListener> changePropagationListeners

	new(ModelRepository modelProviding, ChangePropagationSpecificationProvider changePropagationProvider,
		VitruvDomainRepository metamodelRepository, CorrespondenceProviding correspondenceProviding) {
		this.modelProviding = modelProviding
		this.changePropagationProvider = changePropagationProvider
		this.correspondenceProviding = correspondenceProviding
		changePropagationListeners = new HashSet<ChangePropagationListener>
		this.metamodelRepository = metamodelRepository
	}

	override void addChangePropagationListener(ChangePropagationListener propagationListener) {
		if (propagationListener !== null)
			changePropagationListeners.add(propagationListener)

	}

	override void removeChangePropagationListener(ChangePropagationListener propagationListener) {
		changePropagationListeners.remove(propagationListener)
	}

	override synchronized List<List<VitruviusChange>> propagateChange(VitruviusChange change) {
		if (change === null || !change.containsConcreteChange) {
			logger.info('''The change does not contain any changes to synchronize: «change»''')
			Collections::emptyList
		} else if (!change.validate) {
			throw new IllegalArgumentException('''Change contains changes from different models: «change»''')
		} else {
			startChangePropagation(change)
			val List<List<VitruviusChange>> result = new ArrayList<List<VitruviusChange>>
			val changedResourcesTracker = new ChangedResourcesTracker
			val propagationResult = new ChangePropagationResult

			propagateSingleChange(change, result, propagationResult, changedResourcesTracker)

			changedResourcesTracker.markNonSourceResourceAsChanged
			executePropagationResult(propagationResult)
			// FIXME HK This is not clear! VirtualModel knows how to save, we bypass that, but currently this is necessary
			// because saving has to be performed before finishing propagation. Maybe we should move the observable to the VirtualModel
			modelProviding.saveAllModels
			finishChangePropagation(change)
			return result
		}

		startChangePropagation(change)
		change.applyBackwardIfLegacy
		var List<List<VitruviusChange>> result = new ArrayList<List<VitruviusChange>>
		val changedResourcesTracker = new ChangedResourcesTracker
		val propagationResult = new ChangePropagationResult
		propagateSingleChange(change, result, propagationResult, changedResourcesTracker)
		changedResourcesTracker.markNonSourceResourceAsChanged
		executePropagationResult(propagationResult)
		// FIXME HK This is not clear! VirtualModel knows how to save, we bypass that, but currently this is necessary
		// because saving has to be performed before finishing propagation. Maybe we should move the observable to the VirtualModel
		modelProviding.saveAllModels
		finishChangePropagation(change)
		result
	}

	private def void startChangePropagation(VitruviusChange change) {
		logger.info('''Started synchronizing change: «change»''')
		changePropagationListeners.forEach[startedChangePropagation]
	}

	private def void finishChangePropagation(VitruviusChange change) {
		changePropagationListeners.forEach[finishedChangePropagation]
		logger.info('''Finished synchronizing change: «change»''')
	}

	private def dispatch void propagateSingleChange(CompositeContainerChange change,
		List<List<VitruviusChange>> commandExecutionChanges, ChangePropagationResult propagationResult,
		ChangedResourcesTracker changedResourcesTracker) {
		change.changes.forEach [
			propagateSingleChange(it, commandExecutionChanges, propagationResult, changedResourcesTracker)
		]
	}

	private def dispatch void propagateSingleChange(TransactionalChange change,
		List<List<VitruviusChange>> commandExecutionChanges, ChangePropagationResult propagationResult,
		ChangedResourcesTracker changedResourcesTracker) {
		val changeApplicationFunction = [ ResourceSet resourceSet |
			modelProviding.getModel(change.URI)
			change.resolveBeforeAndApplyForward(resourceSet)
			return
		]
		modelProviding.executeOnResourceSet(changeApplicationFunction)
		val changeDomain = metamodelRepository.getDomain(change.URI.fileExtension)
		for (propagationSpecification : changePropagationProvider.getChangePropagationSpecifications(changeDomain)) {
			propagateChangeForChangePropagationSpecification(change, propagationSpecification, commandExecutionChanges,
				propagationResult, changedResourcesTracker)
		}
	}

	private def void propagateChangeForChangePropagationSpecification(TransactionalChange change,
		ChangePropagationSpecification propagationSpecification, List<List<VitruviusChange>> commandExecutionChanges,
		ChangePropagationResult propagationResult, ChangedResourcesTracker changedResourcesTracker) {

		val changeApplicationFunction = [ ResourceSet resourceSet |
			modelProviding.getModel(change.URI)
			change.resolveBeforeAndApplyForward(resourceSet)
			return
		]
		modelProviding.executeOnResourceSet(changeApplicationFunction)

		val changeDomain = metamodelRepository.getDomain(change.URI.fileExtension)
		changePropagationProvider.getChangePropagationSpecifications(changeDomain).forEach [
			propagateChangeForChangePropagationSpecification(change, it, commandExecutionChanges, propagationResult,
				changedResourcesTracker)
		]
	}

	def private void executePropagationResult(ChangePropagationResult changePropagationResult) {
		if (null === changePropagationResult)
			logger.info("Current propagation result is null. Can not save new root EObjects::")
		else {
			val elementsToPersist = changePropagationResult.elementToPersistenceMap
			elementsToPersist.keySet.forEach[modelProviding.persistRootElement(elementsToPersist.get(it), it)]
		}
	}
}
