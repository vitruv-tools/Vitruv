package tools.vitruv.framework.modelsynchronization

import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
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
import tools.vitruv.framework.util.command.EMFCommandBridge

class ChangePropagatorImpl implements ChangePropagator {
	static val Logger logger = Logger::getLogger(ChangePropagatorImpl.simpleName)
	val VitruvDomainRepository metamodelRepository
	val ModelRepository modelProviding
	val ChangePropagationSpecificationProvider changePropagationProvider
	val CorrespondenceProviding correspondenceProviding
	Set<ChangePropagationListener> changePropagationListeners

	new(ModelRepository modelProviding, ChangePropagationSpecificationProvider changePropagationProvider,
		VitruvDomainRepository metamodelRepository, CorrespondenceProviding correspondenceProviding) {
		changePropagationListeners = new HashSet<ChangePropagationListener>
		this.changePropagationProvider = changePropagationProvider
		this.correspondenceProviding = correspondenceProviding
		this.metamodelRepository = metamodelRepository
		this.modelProviding = modelProviding
	}

	override addChangePropagationListener(ChangePropagationListener propagationListener) {
		if (propagationListener !== null)
			changePropagationListeners.add(propagationListener)
	}

	override removeChangePropagationListener(ChangePropagationListener propagationListener) {
		changePropagationListeners.remove(propagationListener)
	}

	override synchronized List<List<VitruviusChange>> propagateChange(VitruviusChange change) {
		if (change === null || !change.containsConcreteChange) {
			logger.info('''The change does not contain any changes to synchronize: «change»''')
			return Collections::emptyList
		}
		if (!change.validate)
			throw new IllegalArgumentException('''Change contains changes from different models: «change»''')

		startChangePropagation(change)
		change.applyBackwardIfLegacy
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
		for (VitruviusChange innerChange : change.changes) {
			propagateSingleChange(innerChange, commandExecutionChanges, propagationResult, changedResourcesTracker)
		}
	}

	private def dispatch void propagateSingleChange(TransactionalChange change,
		List<List<VitruviusChange>> commandExecutionChanges, ChangePropagationResult propagationResult,
		ChangedResourcesTracker changedResourcesTracker) {

		val changeApplicationFunction = [ ResourceSet resourceSet |
			modelProviding.getModel(change.URI)
			change.resolveBeforeAndApplyForward(resourceSet)
			return
		]
		this.modelProviding.executeOnResourceSet(changeApplicationFunction)

		val changeDomain = metamodelRepository.getDomain(change.URI.fileExtension)
		for (propagationSpecification : changePropagationProvider.getChangePropagationSpecifications(changeDomain)) {
			propagateChangeForChangePropagationSpecification(change, propagationSpecification, commandExecutionChanges,
				propagationResult, changedResourcesTracker)
		}
	}

	private def void propagateChangeForChangePropagationSpecification(TransactionalChange change,
		ChangePropagationSpecification propagationSpecification, List<List<VitruviusChange>> commandExecutionChanges,
		ChangePropagationResult propagationResult, ChangedResourcesTracker changedResourcesTracker) {
		val correspondenceModel = correspondenceProviding.correspondenceModel
		// TODO HK: Clone the changes for each synchronization! Should even be cloned for
		// each consistency repair routines that uses it,
		// or: make them read only, i.e. give them a read-only interface!
		val command = EMFCommandBridge::createVitruviusTransformationRecordingCommand([|
			return propagationSpecification.propagateChange(change, correspondenceModel)
		])
		modelProviding.executeRecordingCommandOnTransactionalDomain(command)

// Store modification information
		val changedEObjects = command.affectedObjects.filter(EObject)
		changedEObjects.forEach[changedResourcesTracker.addInvolvedModelResource(it.eResource)]
		changedResourcesTracker.addSourceResourceOfChange(change)

		propagationResult.integrateResult(command.transformationResult)
	}

	private def void executePropagationResult(ChangePropagationResult changePropagationResult) {
		if (null === changePropagationResult) {
			logger.info("Current propagation result is null. Can not save new root EObjects::")
			return
		}
		val elementsToPersist = changePropagationResult.elementToPersistenceMap
		for (element : elementsToPersist.keySet) {
			modelProviding.persistRootElement(elementsToPersist.get(element), element)
		}
	}
}
