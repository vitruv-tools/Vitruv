package tools.vitruv.framework.vsum.modelsynchronization

import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.correspondence.CorrespondenceProviding
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.change.processing.ChangePropagationObserver
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl
import tools.vitruv.framework.vsum.ModelRepository
import tools.vitruv.framework.change.uuid.UuidResolver

class ChangePropagatorImpl implements ChangePropagator, ChangePropagationObserver {
	static Logger logger = Logger.getLogger(ChangePropagatorImpl.getSimpleName())
	final VitruvDomainRepository metamodelRepository;
	final ModelRepository resourceRepository
	final ChangePropagationSpecificationProvider changePropagationProvider
	final CorrespondenceProviding correspondenceProviding
	Set<ChangePropagationListener> changePropagationListeners
	final ModelRepositoryImpl modelRepository;
	final List<EObject> objectsCreatedDuringPropagation;

	new(ModelRepository resourceRepository, ChangePropagationSpecificationProvider changePropagationProvider,
		VitruvDomainRepository metamodelRepository, CorrespondenceProviding correspondenceProviding,
		ModelRepositoryImpl modelRepository) {
		this.resourceRepository = resourceRepository
		this.modelRepository = modelRepository;
		this.changePropagationProvider = changePropagationProvider
		changePropagationProvider.forEach[it.registerObserver(this)]
		this.correspondenceProviding = correspondenceProviding
		this.changePropagationListeners = new HashSet<ChangePropagationListener>()
		this.metamodelRepository = metamodelRepository;
		this.objectsCreatedDuringPropagation = newArrayList();
	}

	override void addChangePropagationListener(ChangePropagationListener propagationListener) {
		if (propagationListener !== null) {
			this.changePropagationListeners.add(propagationListener)
		}
	}

	override void removeChangePropagationListener(ChangePropagationListener propagationListener) {
		this.changePropagationListeners.remove(propagationListener)
	}

	override synchronized List<PropagatedChange> propagateChange(VitruviusChange change) {
		if (change === null || !change.containsConcreteChange()) {
			logger.info('''The change does not contain any changes to synchronize: «change»''')
			return Collections.emptyList()
		}
		if (!change.validate()) {
			throw new IllegalArgumentException('''Change contains changes from different models: «change»''')
		}

		startChangePropagation(change);

		val List<PropagatedChange> thisChangePropagationResult = new ArrayList
		val changedResourcesTracker = new ChangedResourcesTracker();
		propagateSingleChange(change, thisChangePropagationResult, changedResourcesTracker);
		handleObjectsWithoutResource();
		changedResourcesTracker.markNonSourceResourceAsChanged();
		// FIXME HK This is not clear! VirtualModel knows how to save, we bypass that, but currently this is necessary
		// because saving has to be performed before finishing propagation. Maybe we should move the observable to the VirtualModel
		resourceRepository.saveAllModels
		logger.debug(modelRepository);
		logger.debug('''
			Propagated changes:
			«FOR propagatedChange : thisChangePropagationResult»
				Propagated Change:
				«propagatedChange»«ENDFOR»
			''');
		finishChangePropagation(change)

		val transitivelyPropagatedChanges = new ArrayList
		for (resultingChange : thisChangePropagationResult) {
			if (resultingChange.consequentialChanges.containsConcreteChange &&
				resultingChange.consequentialChanges.changeDomain.shouldTransitivelyPropagateChanges) {
				resourceRepository.createRecordingCommandAndExecuteCommandOnTransactionalDomain [
					transitivelyPropagatedChanges += propagateChange(resultingChange.consequentialChanges)
					null
				]
			}
		}
		thisChangePropagationResult += transitivelyPropagatedChanges

		return thisChangePropagationResult
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

	private def dispatch void propagateSingleChange(CompositeContainerChange change,
		List<PropagatedChange> propagatedChanges, ChangedResourcesTracker changedResourcesTracker) {
		for (VitruviusChange innerChange : change.getChanges()) {
			propagateSingleChange(innerChange, propagatedChanges, changedResourcesTracker)
		}
	}

	private def dispatch void propagateSingleChange(TransactionalChange change, List<PropagatedChange> propagatedChanges, 
		ChangedResourcesTracker changedResourcesTracker) {
		
		val changeApplicationFunction = [UuidResolver uuidResolver |
				change.resolveBeforeAndApplyForward(uuidResolver)
                // If change has a URI, add the model to the repository
                if (change.URI !== null) resourceRepository.getModel(change.getURI());
                return;
        	];
		this.resourceRepository.executeOnUuidResolver(changeApplicationFunction);
		
		change.affectedEObjects.forEach[modelRepository.addRootElement(it)];
		modelRepository.cleanupRootElements;

		val changedObjects = change.affectedEObjects;
		if (changedObjects.nullOrEmpty) {
			throw new IllegalStateException("There are no objects affected by the given changes");
		}
		val changeDomain = change.changeDomain
		val consequentialChanges = newArrayList();
		resourceRepository.startRecording;
		for (propagationSpecification : changePropagationProvider.
			getChangePropagationSpecifications(changeDomain)) {
			propagateChangeForChangePropagationSpecification(change, propagationSpecification, changedResourcesTracker);
		}
		consequentialChanges += resourceRepository.endRecording();
		consequentialChanges.forEach[logger.debug(it)];
		propagatedChanges.add(
			new PropagatedChange(change,
				VitruviusChangeFactory.instance.createCompositeChange(consequentialChanges)));
	}

	def private getChangeDomain(VitruviusChange change) {
		val resolvedObjects = <EObject>newArrayList();
		// Add affected objects if change is resolved
		resolvedObjects += change.affectedEObjects;
		// Resolve IDs to get actual objects
		change.affectedEObjectIds.forEach[id | resourceRepository.executeOnUuidResolver[resolvedObjects += it.getEObject(id)]]
		metamodelRepository.getDomain(resolvedObjects.filterNull.head)
	}

	private def void handleObjectsWithoutResource() {
		modelRepository.cleanupRootElementsWithoutResource
		// Find created objects without resource
		for (createdObjectWithoutResource : objectsCreatedDuringPropagation.filter[eResource === null]) {
			if (correspondenceProviding.correspondenceModel.hasCorrespondences(
				#[createdObjectWithoutResource])) {
				throw new IllegalStateException("Every object must be contained within a resource: " + createdObjectWithoutResource);
			} else {
				logger.warn("Object was created but has no correspondence and is thus lost: " +
					createdObjectWithoutResource);
			}
		}
		objectsCreatedDuringPropagation.clear();
	}

	private def void propagateChangeForChangePropagationSpecification(TransactionalChange change,
		ChangePropagationSpecification propagationSpecification,
		ChangedResourcesTracker changedResourcesTracker) {
		val correspondenceModel = correspondenceProviding.getCorrespondenceModel();

		// TODO HK: Clone the changes for each synchronization! Should even be cloned for
		// each consistency repair routines that uses it,
		// or: make them read only, i.e. give them a read-only interface!
		val command = EMFCommandBridge.createVitruviusRecordingCommand [
			propagationSpecification.propagateChange(change, correspondenceModel, resourceRepository);
			modelRepository.cleanupRootElements();
			null
		]
		resourceRepository.executeRecordingCommandOnTransactionalDomain(command);

		// Store modification information
		val changedEObjects = command.getAffectedObjects().filter(EObject)
		changedEObjects.forEach[changedResourcesTracker.addInvolvedModelResource(it.eResource)];
		changedResourcesTracker.addSourceResourceOfChange(change);
	}

	override objectCreated(EObject createdObject) {
		this.objectsCreatedDuringPropagation += createdObject;
		this.modelRepository.addRootElement(createdObject);
	}

}
