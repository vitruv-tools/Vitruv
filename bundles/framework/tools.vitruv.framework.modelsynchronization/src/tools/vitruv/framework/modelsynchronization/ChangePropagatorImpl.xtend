package tools.vitruv.framework.modelsynchronization

import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.Set
import org.apache.log4j.Logger
import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.correspondence.CorrespondenceProviding
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.metamodel.MetamodelRepository
import tools.vitruv.framework.metamodel.ModelRepository
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.util.command.ChangePropagationResult

class ChangePropagatorImpl implements ChangePropagator {
	static Logger logger = Logger.getLogger(ChangePropagatorImpl.getSimpleName())
	final MetamodelRepository metamodelRepository;
	final ModelRepository modelProviding
	final ChangePropagationSpecificationProvider changePropagationProvider
	final CorrespondenceProviding correspondenceProviding
	Set<ChangePropagationListener> changePropagationListeners
	
	new(ModelRepository modelProviding, ChangePropagationSpecificationProvider changePropagationProvider,
		MetamodelRepository metamodelRepository, CorrespondenceProviding correspondenceProviding) {
		this.modelProviding = modelProviding
		this.changePropagationProvider = changePropagationProvider
		this.correspondenceProviding = correspondenceProviding
		this.changePropagationListeners = new HashSet<ChangePropagationListener>()
		this.metamodelRepository = metamodelRepository;
	}

	override void addChangePropagationListener(ChangePropagationListener propagationListener) {
		if (propagationListener !== null) {
			this.changePropagationListeners.add(propagationListener)
		}
	}

	override void removeChangePropagationListener(ChangePropagationListener propagationListener) {
		this.changePropagationListeners.remove(propagationListener)
	}

	override synchronized List<List<VitruviusChange>> propagateChange(VitruviusChange change) {
		if (change == null || !change.containsConcreteChange()) {
			logger.info('''The change does not contain any changes to synchronize: «change»''')
			return Collections.emptyList()
		}
		if (!change.validate()) {
			throw new IllegalArgumentException('''Change contains changes from different models: «change»''')
		}
		
		startChangePropagation(change);
		change.applyBackward()
		var List<List<VitruviusChange>> result = new ArrayList<List<VitruviusChange>>()
		val changedResourcesTracker = new ChangedResourcesTracker();
		val propagationResult = new ChangePropagationResult();
		propagateSingleChange(change, result, propagationResult, changedResourcesTracker);
		changedResourcesTracker.markNonSourceResourceAsChanged();
		executePropagationResult(propagationResult);
		// FIXME HK This is not clear! VirtualModel knows how to save, we bypass that, but currently this is necessary
		// because saving has to be performed before finishing propagation. Maybe we should move the observable to the VirtualModel
		modelProviding.saveAllModels
		finishChangePropagation(change)
		return result
	}

	private def void startChangePropagation(VitruviusChange change) {
		logger.info('''Started synchronizing change: «change»''')
		for (ChangePropagationListener syncListener : this.changePropagationListeners) {
			syncListener.startedChangePropagation()
		}	
	}

	private def void finishChangePropagation(VitruviusChange change) {
		for (ChangePropagationListener syncListener : this.changePropagationListeners) {
			syncListener.finishedChangePropagation()
		}
		logger.info('''Finished synchronizing change: «change»''')
	}

	private def dispatch void propagateSingleChange(CompositeContainerChange change, List<List<VitruviusChange>> commandExecutionChanges,
		ChangePropagationResult propagationResult, ChangedResourcesTracker changedResourcesTracker) {
		for (VitruviusChange innerChange : ((change as CompositeContainerChange)).getChanges()) {
			propagateSingleChange(innerChange, commandExecutionChanges, propagationResult, changedResourcesTracker)
		}
	}

	private def dispatch void propagateSingleChange(TransactionalChange change, 
		List<List<VitruviusChange>> commandExecutionChanges, ChangePropagationResult propagationResult,
		ChangedResourcesTracker changedResourcesTracker) {
		change.applyForward();
		val changeMetamodel = metamodelRepository.getMetamodel(change.URI.fileExtension);
		for (propagationSpecification : changePropagationProvider.getChangePropagationSpecifications(changeMetamodel.URI)) {
			propagateChangeForChangePropagationSpecification(change, propagationSpecification, commandExecutionChanges, propagationResult, changedResourcesTracker);
		}
	}
	
	private def void propagateChangeForChangePropagationSpecification(TransactionalChange change, ChangePropagationSpecification propagationSpecification,
			List<List<VitruviusChange>> commandExecutionChanges, ChangePropagationResult propagationResult, ChangedResourcesTracker changedResourcesTracker) {
		val correspondenceModel = correspondenceProviding.getCorrespondenceModel(propagationSpecification.metamodelPair.first, propagationSpecification.metamodelPair.second);
		// TODO HK: Clone the changes for each synchronization! Should even be cloned for
		// each consistency repair routines that uses it,
		// or: make them read only, i.e. give them a read-only interface!
		val command = EMFCommandBridge.createVitruviusTransformationRecordingCommand([|
			return propagationSpecification.propagateChange(change, correspondenceModel);
		])
		modelProviding.executeRecordingCommandOnTransactionalDomain(command);
					
		// Store modification information
		val changedEObjects = command.getAffectedObjects().filter(EObject)
		changedEObjects.forEach[changedResourcesTracker.addInvolvedModelResource(it.eResource)];
		changedResourcesTracker.addSourceResourceOfChange(change);
		
		propagationResult.integrateResult(command.transformationResult);
	}
	
	def private void executePropagationResult(ChangePropagationResult changePropagationResult) {
		if (null === changePropagationResult) {
			logger.info("Current propagation result is null. Can not save new root EObjects.")
			return;
		}
		val elementsToPersist = changePropagationResult.getElementToPersistenceMap();
		for (element : elementsToPersist.keySet) {
			modelProviding.persistRootElement(elementsToPersist.get(element), element);	
		}
	}
}
