package tools.vitruv.framework.vsum

import java.util.Collections
import java.util.List
import java.util.Vector
import java.util.concurrent.Callable
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.helper.ChangeDomainExtractor
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagator
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagatorImpl
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl
import tools.vitruv.framework.vsum.repositories.ResourceRepositoryImpl
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.util.bridges.EcoreResourceBridge.loadOrCreateResource
import tools.vitruv.framework.vsum.repositories.TuidResolverImpl
import tools.vitruv.framework.correspondence.CorrespondenceModelFactory
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel
import java.nio.file.Path

class VirtualModelImpl implements InternalVirtualModel {
	static val Logger LOGGER = Logger.getLogger(VirtualModelImpl)
	val ResourceRepositoryImpl resourceRepository
	val ModelRepositoryImpl modelRepository
	val VitruvDomainRepository domainRepository
	val ChangePropagator changePropagator
	val VsumFileSystemLayout fileSystemLayout
	val InternalCorrespondenceModel correspondenceModel
	val extension ChangeDomainExtractor changeDomainExtractor

	/**
	 * A list of {@link PropagatedChangeListener}s that are informed of all changes made
	 */
	val List<PropagatedChangeListener> propagatedChangeListeners

	package new(VsumFileSystemLayout fileSystemLayout, InternalUserInteractor userInteractor,
		 VitruvDomainRepository domainRepository,
		 ChangePropagationSpecificationProvider changePropagationSpecificationProvider) {
		this.fileSystemLayout = fileSystemLayout
		this.domainRepository = domainRepository
		this.resourceRepository = new ResourceRepositoryImpl(fileSystemLayout, domainRepository)
		this.modelRepository = new ModelRepositoryImpl(resourceRepository.uuidGeneratorAndResolver)
		this.changePropagator = new ChangePropagatorImpl(
			resourceRepository,
			changePropagationSpecificationProvider,
			domainRepository,
			modelRepository,
			this,
			userInteractor
		)
		this.propagatedChangeListeners = new Vector<PropagatedChangeListener>()
		this.changeDomainExtractor = new ChangeDomainExtractor(domainRepository)
		this.correspondenceModel = loadCorrespondenceModel()
	}

	override getCorrespondenceModel() {
		correspondenceModel.genericView
	}

	override synchronized getModelInstance(VURI modelVuri) {
		return this.resourceRepository.getModel(modelVuri)
	}

	override synchronized save() {
		this.resourceRepository.saveAllModels()
		this.correspondenceModel.saveModel()
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
		val vitruvDomain = domainRepository.getDomain(vuri.fileExtension)
		val currentState = resourceRepository.getModel(vuri).resource
		if (currentState.isValid(newState)) {
			val strategy = vitruvDomain.stateChangePropagationStrategy
			val compositeChange = strategy.getChangeSequences(newState, currentState, uuidGeneratorAndResolver)
			return propagateChange(compositeChange)
		}
		LOGGER.error("Could not load current state for new state. No changes were propagated!")
		return emptyList
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

	override Path getFolder() {
		return fileSystemLayout.vsumProjectFolder
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
		folder.fileName.toString
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
	
	def private loadCorrespondenceModel() {
		var correspondencesVURI = fileSystemLayout.correspondencesVURI
		LOGGER.trace('''Creating or loading correspondence model from: «correspondencesVURI»''')
		val correspondencesResource = new ResourceSetImpl().withGlobalFactories()
			.loadOrCreateResource(correspondencesVURI.EMFUri)
		CorrespondenceModelFactory.instance.createCorrespondenceModel(
			new TuidResolverImpl(domainRepository, resourceRepository), uuidGeneratorAndResolver, 
			resourceRepository, domainRepository, correspondencesVURI, correspondencesResource)
	}
}
