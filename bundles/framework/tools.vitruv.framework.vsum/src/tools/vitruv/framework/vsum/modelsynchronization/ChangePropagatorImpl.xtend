package tools.vitruv.framework.vsum.modelsynchronization

import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.processing.ChangePropagationObserver
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.correspondence.CorrespondenceProviding
import tools.vitruv.framework.domains.repository.ModelRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.vsum.ModelRepository
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl

class ChangePropagatorImpl implements ChangePropagator, ChangePropagationObserver {
	static extension Logger = Logger::getLogger(ChangePropagatorImpl.simpleName)
	val ChangePropagationSpecificationProvider changePropagationProvider
	val CorrespondenceProviding correspondenceProviding
	val ModelRepository resourceRepository
	val ModelRepositoryImpl modelRepository;
	val Set<ChangePropagationListener> changePropagationListeners
	val VitruvDomainRepository metamodelRepository
	
	new(ModelRepository resourceRepository, ChangePropagationSpecificationProvider changePropagationProvider,
		VitruvDomainRepository metamodelRepository, CorrespondenceProviding correspondenceProviding, ModelRepositoryImpl modelRepository) {
		this.resourceRepository = resourceRepository
		this.modelRepository = modelRepository
		this.changePropagationProvider = changePropagationProvider
		changePropagationProvider.forEach[it.registerObserver(this)]
		this.correspondenceProviding = correspondenceProviding
		this.metamodelRepository = metamodelRepository
		this.modelProviding = modelProviding
	}

	override addChangePropagationListener(ChangePropagationListener propagationListener) {
		if (propagationListener !== null)
			changePropagationListeners += propagationListener
	}

	override removeChangePropagationListener(ChangePropagationListener propagationListener) {
		changePropagationListeners.remove(propagationListener)
	}

	override synchronized List<List<VitruviusChange>> propagateChange(VitruviusChange change) {
		if (null === change || !change.containsConcreteChange) {
			info('''The change does not contain any changes to synchronize: «change»''')
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
		modelRepository.cleanupRootElementsWithoutResource
		// FIXME HK This is not clear! VirtualModel knows how to save, we bypass that, but currently this is necessary
		// because saving has to be performed before finishing propagation. Maybe we should move the observable to the VirtualModel
		resourceRepository.saveAllModels
		logger.debug(modelRepository);
		logger.debug('''
	Propagated changes:
	«FOR propagatedChange : result»
	Propagated Change:
		«propagatedChange»«ENDFOR»
		''');
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

	private def dispatch void propagateSingleChange(CompositeContainerChange change, List<PropagatedChange> propagatedChanges,
		ChangePropagationResult propagationResult, ChangedResourcesTracker changedResourcesTracker) {
		for (VitruviusChange innerChange : change.getChanges()) {
			propagateSingleChange(innerChange, propagatedChanges, propagationResult, changedResourcesTracker)
		}
	}

	private def dispatch void propagateSingleChange(TransactionalChange change, List<PropagatedChange> propagatedChanges, 
		ChangePropagationResult propagationResult, ChangedResourcesTracker changedResourcesTracker) {
		
		val changeApplicationFunction = [ResourceSet resourceSet |
				resourceRepository.getModel(change.getURI());
                change.resolveBeforeAndApplyForward(resourceSet)
                return;
        	];
		this.resourceRepository.executeOnResourceSet(changeApplicationFunction);

		val changeDomain = metamodelRepository.getDomain(change.getURI.fileExtension);
		val consequentialChanges = newArrayList();
		for (propagationSpecification : changePropagationProvider.getChangePropagationSpecifications(changeDomain)) {
			consequentialChanges += propagateChangeForChangePropagationSpecification(change, propagationSpecification, propagationResult, changedResourcesTracker);
		}
		propagatedChanges.add(new PropagatedChange(change, VitruviusChangeFactory.instance.createCompositeChange(consequentialChanges)));
	}
	
	private def List<VitruviusChange> propagateChangeForChangePropagationSpecification(TransactionalChange change, ChangePropagationSpecification propagationSpecification,
			ChangePropagationResult propagationResult, ChangedResourcesTracker changedResourcesTracker) {
		val correspondenceModel = correspondenceProviding.getCorrespondenceModel();

		val List<VitruviusChange> consequentialChanges = newArrayList();
		// TODO HK: Clone the changes for each synchronization! Should even be cloned for
		// each consistency repair routines that uses it,
		// or: make them read only, i.e. give them a read-only interface!
		val command = EMFCommandBridge.createVitruviusTransformationRecordingCommand([|
			modelRepository.startRecording;
			val propResult = propagationSpecification.propagateChange(change, correspondenceModel);
			modelRepository.cleanupRootElements();
			consequentialChanges += modelRepository.endRecording;
			consequentialChanges.forEach[logger.debug(it)];
			return propResult;
		
		])
		resourceRepository.executeRecordingCommandOnTransactionalDomain(command);
					
		// Store modification information
		val changedEObjects = command.getAffectedObjects().filter(EObject)
		changedEObjects.forEach[changedResourcesTracker.addInvolvedModelResource(it.eResource)];
		changedResourcesTracker.addSourceResourceOfChange(change);
		
		propagationResult.integrateResult(command.transformationResult);
		return consequentialChanges;
	}
	
	def private void executePropagationResult(ChangePropagationResult changePropagationResult) {
		if (null === changePropagationResult) {
			logger.info("Current propagation result is null. Can not save new root EObjects.")
			return;
		}
		val elementsToPersist = changePropagationResult.getElementToPersistenceMap();
		for (element : elementsToPersist.keySet) {
			resourceRepository.persistRootElement(elementsToPersist.get(element), element);	
		}
	}
	
	override objectCreated(EObject createdObject) {
		this.modelRepository.addRootElement(createdObject);
	}
	
}
