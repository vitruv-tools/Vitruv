package tools.vitruv.framework.vsum.modelsynchronization

import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.correspondence.CorrespondenceProviding
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.change.processing.ChangePropagationObserver
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl
import tools.vitruv.framework.vsum.ModelRepository
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.change.description.CompositeChange

import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.correspondence.CorrespondencePackage
import tools.vitruv.framework.change.description.ConcreteChange
import java.util.LinkedList
import java.util.Collection
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import tools.vitruv.framework.userinteraction.UserInteractionListener
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.userinteraction.UserInteractionFactory

class ChangePropagatorImpl implements ChangePropagator, ChangePropagationObserver, UserInteractionListener {
	static Logger logger = Logger.getLogger(ChangePropagatorImpl.getSimpleName())
	final VitruvDomainRepository metamodelRepository;
	final ModelRepository resourceRepository
	final ChangePropagationSpecificationProvider changePropagationProvider
	final CorrespondenceProviding correspondenceProviding
	Set<ChangePropagationListener> changePropagationListeners
	final ModelRepositoryImpl modelRepository;
	final List<EObject> objectsCreatedDuringPropagation;
	final InternalUserInteractor userInteractor;
	final Collection<UserInteractionBase> userInteractions = new LinkedList();

	new(ModelRepository resourceRepository, ChangePropagationSpecificationProvider changePropagationProvider,
		VitruvDomainRepository metamodelRepository, CorrespondenceProviding correspondenceProviding,
		ModelRepositoryImpl modelRepository, InternalUserInteractor userInteractor) {
		this.resourceRepository = resourceRepository
		this.modelRepository = modelRepository;
		this.changePropagationProvider = changePropagationProvider
		changePropagationProvider.forEach[it.registerObserver(this)]
		this.correspondenceProviding = correspondenceProviding
		this.changePropagationListeners = new HashSet<ChangePropagationListener>()
		this.metamodelRepository = metamodelRepository;
		this.objectsCreatedDuringPropagation = newArrayList();
		this.userInteractor = userInteractor;
		userInteractor.registerUserInputListener(this)
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

		val List<PropagatedChange> changePropagationResult = new ArrayList
		val changedResourcesTracker = new ChangedResourcesTracker();
		for (transactionalChange : change.transactionalChangeSequence) {
			changePropagationResult += applyAndPropagateSingleChange(transactionalChange, changedResourcesTracker);
		}
		handleObjectsWithoutResource();
		changedResourcesTracker.markNonSourceResourceAsChanged();
		// FIXME HK This is not clear! VirtualModel knows how to save, we bypass that, but currently this is necessary
		// because saving has to be performed before finishing propagation. Maybe we should move the observable to the VirtualModel
		resourceRepository.saveAllModels
		logger.debug(modelRepository);
		logger.debug('''
			Propagated changes:
			«FOR propagatedChange : changePropagationResult»
				Propagated Change:
				«propagatedChange»«ENDFOR»
			''');
		finishChangePropagation(change)

		return changePropagationResult
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

	private def List<PropagatedChange> applyAndPropagateSingleChange(
		TransactionalChange change,
		ChangedResourcesTracker changedResourcesTracker
	) {
		val changeApplicationFunction = [UuidResolver uuidResolver |
			change.resolveBeforeAndApplyForward(uuidResolver)
            // If change has a URI, add the model to the repository
            if (change.URI !== null) resourceRepository.getModel(change.getURI());
            change.affectedEObjects.forEach[modelRepository.addRootElement(it)];
            return;
    	];
		this.resourceRepository.executeOnUuidResolver(changeApplicationFunction);
		modelRepository.cleanupRootElements;

		val changedObjects = change.affectedEObjects;
		if (changedObjects.nullOrEmpty) {
			throw new IllegalStateException("There are no objects affected by the given changes");
		}

		return propagateSingleChange(change, changedResourcesTracker)
	}

	private def List<PropagatedChange> propagateSingleChange(
		TransactionalChange change,
		ChangedResourcesTracker changedResourcesTracker
	) {
		val consequentialChanges = newArrayList();
		
		// retrieve user inputs from past changes, construct a UserInteractor which tries to reuse them:
		val pastUserInputsFromChange = change.getUserInteractions()
		if (!pastUserInputsFromChange.nullOrEmpty) {
			userInteractor.decorateUserInteractionResultProvider([provider | UserInteractionFactory.instance.createPredefinedInteractionResultProvider(provider, pastUserInputsFromChange)]);
		}
		//modelRepository.startRecording;
		resourceRepository.startRecording;
		for (propagationSpecification : changePropagationProvider.
			getChangePropagationSpecifications(change.changeDomain)) {
			propagateChangeForChangePropagationSpecification(change, propagationSpecification, changedResourcesTracker);
		}
		//consequentialChanges += modelRepository.endRecording();
		consequentialChanges += resourceRepository.endRecording();
		consequentialChanges.forEach[logger.debug(it)];

		userInteractor.removeDecoratingUserInteractionResultProvider();
        change.userInteractions = userInteractions
		val propagatedChange = new PropagatedChange(change,
				VitruviusChangeFactory.instance.createCompositeChange(consequentialChanges))
		val resultingChanges = new ArrayList()
		resultingChanges += propagatedChange

		val consequentialChangesToRePropagate = propagatedChange.consequentialChanges.transactionalChangeSequence
			.map[rewrapWithoutCorrespondenceChanges].filterNull.filter[containsConcreteChange]
			.filter [changeDomain.shouldTransitivelyPropagateChanges]

		for (changeToPropagate : consequentialChangesToRePropagate) {
			resultingChanges += propagateSingleChange(changeToPropagate, changedResourcesTracker)
		}

		return resultingChanges
	}
	
	private def dispatch TransactionalChange rewrapWithoutCorrespondenceChanges(CompositeTransactionalChange change) {
		val newChange = VitruviusChangeFactory.instance.createCompositeTransactionalChange()
		change.changes.map[rewrapWithoutCorrespondenceChanges].filterNull.forEach[newChange.addChange(it)];
		return newChange;
	}
	
	private def dispatch TransactionalChange rewrapWithoutCorrespondenceChanges(ConcreteChange change) {
		return if (!change.affectedEObjects.exists[isInCorrespondenceModel]) change else null;
	}
	
	private def dispatch TransactionalChange rewrapWithoutCorrespondenceChanges(TransactionalChange change) {
		return change;
	}
	
	private def boolean isInCorrespondenceModel(EObject object) {
		val typeAndSuperTypes = Collections.singletonList(object.eClass) + object.eClass.EAllSuperTypes
		return typeAndSuperTypes.exists[EPackage === CorrespondencePackage.eINSTANCE]
	}

	def private dispatch Iterable<TransactionalChange> getTransactionalChangeSequence(CompositeTransactionalChange composite) {
		if (composite.containsConcreteChange) {
			return Collections.singleton(composite)
		} else {
			return Collections.emptyList
		}
	}

	def private dispatch Iterable<TransactionalChange> getTransactionalChangeSequence(CompositeChange<?> composite) {
		composite.changes.flatMap [transactionalChangeSequence]
	}

	def private dispatch Iterable<TransactionalChange> getTransactionalChangeSequence(TransactionalChange transactionalChange) {
		if (transactionalChange.containsConcreteChange) {
			return Collections.singleton(transactionalChange)
		} else {
			return Collections.emptyList
		} 
	}

	def private getChangeDomain(VitruviusChange change) {
		val resolvedObjects = change.affectedEObjects.filter[!eIsProxy];
		metamodelRepository.getDomain(resolvedObjects.head)
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
		val command = resourceRepository.createCommand() [
			propagationSpecification.propagateChange(change, correspondenceModel, resourceRepository);
			modelRepository.cleanupRootElements();
			null
		]
		command.executeAndRethrowException();

		// Store modification information
		val changedEObjects = command.getAffectedObjects().filter(EObject)
		changedEObjects.forEach[changedResourcesTracker.addInvolvedModelResource(it.eResource)];
		changedResourcesTracker.addSourceResourceOfChange(change);
	}

	override objectCreated(EObject createdObject) {
		this.objectsCreatedDuringPropagation += createdObject;
		this.modelRepository.addRootElement(createdObject);
	}

    override onUserInteractionReceived(UserInteractionBase interaction) {
        userInteractions.add(interaction)
    }

}
