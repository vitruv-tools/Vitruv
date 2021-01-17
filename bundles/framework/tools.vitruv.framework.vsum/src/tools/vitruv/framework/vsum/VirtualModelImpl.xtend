package tools.vitruv.framework.vsum

import java.io.File
import java.util.Collections
import java.util.List
import java.util.Vector
import java.util.concurrent.Callable
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationRepository
import tools.vitruv.framework.domains.TuidAwareVitruvDomain
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.helper.ChangeDomainExtractor
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagator
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagatorImpl
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl
import tools.vitruv.framework.vsum.repositories.ResourceRepositoryImpl
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI

class VirtualModelImpl implements InternalVirtualModel {
	static val Logger LOGGER = Logger.getLogger(VirtualModelImpl.name)
	val ResourceRepositoryImpl resourceRepository
	val ModelRepositoryImpl modelRepository
	val VitruvDomainRepository metamodelRepository
	val ChangePropagator changePropagator
	val ChangePropagationSpecificationProvider changePropagationSpecificationProvider
	val File folder
	val extension ChangeDomainExtractor changeDomainExtractor

	/**
	 * A list of {@link PropagatedChangeListener}s that are informed of all changes made
	 */
	val List<PropagatedChangeListener> propagatedChangeListeners

	new(File folder, InternalUserInteractor userInteractor, VirtualModelConfiguration modelConfiguration) {
		this.folder = folder
		this.metamodelRepository = new VitruvDomainRepositoryImpl()
		for (metamodel : modelConfiguration.metamodels) {
			this.metamodelRepository.addDomain(metamodel)
			if (metamodel instanceof TuidAwareVitruvDomain) {
				metamodel.registerAtTuidManagement()
			}
		}
		this.resourceRepository = new ResourceRepositoryImpl(folder, metamodelRepository)
		this.modelRepository = new ModelRepositoryImpl(resourceRepository.uuidGeneratorAndResolver)
		val changePropagationSpecificationRepository = new ChangePropagationSpecificationRepository()
		for (changePropagationSpecification : modelConfiguration.changePropagationSpecifications) {
			changePropagationSpecification.userInteractor = userInteractor
			changePropagationSpecificationRepository.putChangePropagationSpecification(changePropagationSpecification)
		}
		this.changePropagationSpecificationProvider = changePropagationSpecificationRepository
		this.changePropagator = new ChangePropagatorImpl(
			resourceRepository,
			changePropagationSpecificationProvider,
			metamodelRepository,
			resourceRepository,
			modelRepository,
			userInteractor
		)
		VirtualModelManager.instance.putVirtualModel(this)

		this.propagatedChangeListeners = new Vector<PropagatedChangeListener>()
		this.changeDomainExtractor = new ChangeDomainExtractor(metamodelRepository)
	}

	override getCorrespondenceModel() {
		this.resourceRepository.getCorrespondenceModel()
	}

	override synchronized getModelInstance(VURI modelVuri) {
		return this.resourceRepository.getModel(modelVuri)
	}

	override synchronized save() {
		this.resourceRepository.saveAllModels()
	}

	override synchronized persistRootElement(VURI persistenceVuri, EObject rootElement) {
		this.resourceRepository.persistAsRoot(rootElement, persistenceVuri)
	}

	override synchronized executeCommand(Callable<Void> command) {
		this.resourceRepository.executeAsCommand(command);
	}

	override addChangePropagationListener(ChangePropagationListener changePropagationListener) {
		changePropagator.addChangePropagationListener(changePropagationListener)
	}
	
	override removeChangePropagationListener(ChangePropagationListener changePropagationListener) {
		changePropagator.removeChangePropagationListener(changePropagationListener)
	}

	override synchronized propagateChange(VitruviusChange change) {
		LOGGER.info('''Start change propagation''')
		change.unresolveIfApplicable
		// Save is done by the change propagator because it has to be performed before finishing sync
		val result = changePropagator.propagateChange(change)
		informPropagatedChangeListeners(result)
		LOGGER.info('''Finished change propagation''')
		return result
	}

	/**
	 * @see tools.vitruv.framework.vsum.VirtualModel#propagateChangedState(Resource)
	 */
	override synchronized propagateChangedState(Resource newState) {
		return propagateChangedState(newState, newState?.URI)
	}

	/**
	 * @see tools.vitruv.framework.vsum.VirtualModel#propagateChangedState(Resource, URI)
	 */
	override synchronized propagateChangedState(Resource newState, URI oldLocation) {
		if (newState === null || oldLocation === null) {
			throw new IllegalArgumentException("New state and old location cannot be null!")
		}
		val vuri = VURI.getInstance(oldLocation) // using the URI of a resource allows using the model resource, the model root, or any model element as input.
		val vitruvDomain = metamodelRepository.getDomain(vuri.fileExtension)
		val currentState = resourceRepository.getModel(vuri).resource
		if (currentState.isValid(newState)) {
			val strategy = vitruvDomain.stateChangePropagationStrategy
			val compositeChange = strategy.getChangeSequences(newState, currentState, uuidGeneratorAndResolver)
			return propagateChange(compositeChange)
		}
		LOGGER.error("Could not load current state for new state. No changes were propagated!")
		return #[] // empty list
	}

	override synchronized reverseChanges(List<PropagatedChange> changes) {
		resourceRepository.executeAsCommand([|
			changes.reverseView.forEach[it.applyBackward(uuidGeneratorAndResolver)]
			return null
		])
		
		// TODO HK Instead of this make the changes set the modified flag of the resource when applied
		val changedEObjects = changes.map[originalChange.affectedEObjects + consequentialChanges.affectedEObjects].flatten
		changedEObjects.map[eResource].filterNull.forEach[modified = true]
		save()
	}

	override setUserInteractor(UserInteractor userInteractor) {
		for (propagationSpecification : this.changePropagationSpecificationProvider) {
			propagationSpecification.userInteractor = userInteractor
		}
	}

	override File getFolder() {
		return folder
	}

	override getUuidGeneratorAndResolver() {
		return resourceRepository.uuidGeneratorAndResolver
	}

	/**
	 * Registers a given {@link PropagatedChangeListener}.
	 * 
	 * @param propagatedChangeListener The listener to register
	 */
	override void addPropagatedChangeListener(PropagatedChangeListener propagatedChangeListener) {
		this.propagatedChangeListeners.add(propagatedChangeListener)
	}

	/**
	 * Removes a given {@link PropagatedChangeListener}. 
	 * Does nothing if the listener was not registered before.
	 * 
	 * @param propagatedChangeListener The listener to remove
	 */
	override void removePropagatedChangeListener(PropagatedChangeListener propagatedChangeListener) {
		this.propagatedChangeListeners.remove(propagatedChangeListener)
	}

	/**
	 * Returns the name of the virtual model.
	 * 
	 * @return The name of the virtual model
	 */
	def getName() {
		this.folder.name
	}

	/**
	 * This method informs the registered {@link PropagatedChangeListener}s of the propagation result.
	 * 
	 * @param propagationResult The propagation result
	 */
	def private void informPropagatedChangeListeners(List<PropagatedChange> propagationResult) {
		if (this.propagatedChangeListeners.isEmpty()) {
			return
		}
		val sourceDomain = getSourceDomain(propagationResult)
		val targetDomain = getTargetDomain(propagationResult)
		for (PropagatedChangeListener propagatedChangeListener : this.propagatedChangeListeners) {
			propagatedChangeListener.postChanges(name, sourceDomain, targetDomain, propagationResult)
		}
	}

	/**
	 * Confirms whether the current state is retrievable via its URI from the resource set of the new state.
	 */
	private def boolean isValid(Resource currentState, Resource newState) {
		newState.resourceSet.URIConverter.exists(currentState.URI, Collections.emptyMap)
	}
	
	override void dispose() {
		resourceRepository.dispose	
	}

}
