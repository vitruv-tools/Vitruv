package tools.vitruv.framework.vsum

import java.util.Collections
import java.util.List
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
import tools.vitruv.framework.vsum.repositories.ResourceRepositoryImpl
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout
import java.nio.file.Path
import static com.google.common.base.Preconditions.checkNotNull
import java.util.LinkedList
import static com.google.common.base.Preconditions.checkArgument
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagator

class VirtualModelImpl implements InternalVirtualModel {
	static val Logger LOGGER = Logger.getLogger(VirtualModelImpl)
	val ModelRepository resourceRepository
	val VitruvDomainRepository domainRepository
	val ChangePropagator changePropagator
	val VsumFileSystemLayout fileSystemLayout
	val List<ChangePropagationListener> changePropagationListeners = new LinkedList()
	val List<PropagatedChangeListener> propagatedChangeListeners = new LinkedList()
	val extension ChangeDomainExtractor changeDomainExtractor

	package new(VsumFileSystemLayout fileSystemLayout, InternalUserInteractor userInteractor,
		VitruvDomainRepository domainRepository,
		ChangePropagationSpecificationProvider changePropagationSpecificationProvider) {
		this.fileSystemLayout = fileSystemLayout
		this.domainRepository = domainRepository
		this.resourceRepository = new ResourceRepositoryImpl(fileSystemLayout, domainRepository)
		this.changeDomainExtractor = new ChangeDomainExtractor(domainRepository)
		this.changePropagator = new ChangePropagator(
			resourceRepository,
			changePropagationSpecificationProvider,
			domainRepository,
			userInteractor
		)
	}

	override synchronized getCorrespondenceModel() {
		this.resourceRepository.correspondenceModel
	}

	override synchronized getModelInstance(VURI modelVuri) {
		this.resourceRepository.getModel(modelVuri)
	}

	override synchronized save() {
		this.resourceRepository.saveOrDeleteModels()
	}

	override synchronized persistRootElement(VURI persistenceVuri, EObject rootElement) {
		this.resourceRepository.persistAsRoot(rootElement, persistenceVuri)
	}

	override synchronized propagateChange(VitruviusChange change) {
		checkNotNull(change, "change to propagate")
		checkArgument(change.containsConcreteChange, 
			"This change contains no concrete changes:%s%s", System.lineSeparator, change)

		LOGGER.info("Start change propagation")
		startChangePropagation(change)

		change.unresolveIfApplicable
		val result = changePropagator.propagateChange(change)
		save()
		
		if (LOGGER.isTraceEnabled) {
			LOGGER.trace('''
				Propagated changes:
				«FOR propagatedChange : result»
					Propagated Change:
					«propagatedChange»
				«ENDFOR»
			''')
		}
		
		finishChangePropagation(change)
		informPropagatedChangeListeners(result)
		LOGGER.info("Finished change propagation")
		return result
	}

	private def void startChangePropagation(VitruviusChange change) {
		if (LOGGER.isDebugEnabled) LOGGER.debug('''Started synchronizing change: «change»''')
		changePropagationListeners.forEach[startedChangePropagation]
	}

	private def void finishChangePropagation(VitruviusChange change) {
		changePropagationListeners.forEach [finishedChangePropagation]
		if (LOGGER.isDebugEnabled) LOGGER.debug('''Finished synchronizing change: «change»''')
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
			val compositeChange = strategy.getChangeSequences(newState, currentState, uuidResolver)
			return propagateChange(compositeChange)
		}
		LOGGER.error("Could not load current state for new state. No changes were propagated!")
		return emptyList
	}

	override synchronized reverseChanges(List<PropagatedChange> changes) {
		changes.reverseView.forEach [applyBackward(uuidResolver)]

		// TODO HK Instead of this make the changes set the modified flag of the resource when applied
		changes.flatMap [originalChange.affectedEObjects + consequentialChanges.affectedEObjects]
			.map [eResource]
			.filterNull
			.forEach[modified = true]
		save()
	}

	override Path getFolder() {
		return fileSystemLayout.vsumProjectFolder
	}

	override getUuidResolver() {
		return resourceRepository.uuidResolver
	}

	/**
	 * Registers the given {@link ChangePropagationListener}.
	 * The listener must not be <code>null</code>.
	 */
	override synchronized void addChangePropagationListener(ChangePropagationListener propagationListener) {
		this.changePropagationListeners.add(checkNotNull(propagationListener, "propagationListener"))
	}

	/**
	 * Unregisters the given {@link ChangePropagationListener}.
	 * The listener must not be <code>null</code>.
	 */
	override synchronized void removeChangePropagationListener(ChangePropagationListener propagationListener) {
		this.changePropagationListeners.remove(checkNotNull(propagationListener, "propagationListener"))
	}

	/**
	 * Registers the given {@link PropagatedChangeListener}.
	 * The listener must not be <code>null</code>.
	 */
	override synchronized void addPropagatedChangeListener(PropagatedChangeListener propagatedChangeListener) {
		this.propagatedChangeListeners.add(checkNotNull(propagatedChangeListener, "propagatedChangeListener"))
	}

	/**
	 * Unregister the given {@link PropagatedChangeListener}. 
	 * The listener must not be <code>null</code>.
	 */
	override synchronized void removePropagatedChangeListener(PropagatedChangeListener propagatedChangeListener) {
		this.propagatedChangeListeners.remove(checkNotNull(propagatedChangeListener, "propagatedChangeListener"))
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
		resourceRepository.close()
	}

}
