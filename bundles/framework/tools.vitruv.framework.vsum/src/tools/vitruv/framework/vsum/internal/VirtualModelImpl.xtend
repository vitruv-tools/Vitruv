package tools.vitruv.framework.vsum.internal

import java.util.List
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.propagation.ChangePropagationSpecificationProvider
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import tools.vitruv.framework.vsum.helper.ChangeDomainExtractor
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout
import java.nio.file.Path
import static com.google.common.base.Preconditions.checkNotNull
import java.util.LinkedList
import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import tools.vitruv.framework.vsum.internal.ChangePropagator
import tools.vitruv.framework.vsum.ChangePropagationListener

class VirtualModelImpl implements InternalVirtualModel {
	static val Logger LOGGER = Logger.getLogger(VirtualModelImpl)
	val ModelRepository resourceRepository
	val VitruvDomainRepository domainRepository
	val ChangePropagator changePropagator
	val VsumFileSystemLayout fileSystemLayout
	val List<ChangePropagationListener> changePropagationListeners = new LinkedList()
	val List<PropagatedChangeListener> propagatedChangeListeners = new LinkedList()
	val extension ChangeDomainExtractor changeDomainExtractor
	
	new(VsumFileSystemLayout fileSystemLayout, InternalUserInteractor userInteractor,
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
	
	def loadExistingModels() {
		this.resourceRepository.loadExistingModels()
	}

	override synchronized getCorrespondenceModel() {
		this.resourceRepository.correspondenceModel
	}

	override synchronized getModelInstance(URI modelUri) {
		this.resourceRepository.getModel(modelUri)
	}

	private def synchronized save() {
		this.resourceRepository.saveOrDeleteModels()
	}

	override synchronized propagateChange(VitruviusChange change) {
		checkNotNull(change, "change to propagate")
		checkArgument(change.containsConcreteChange, 
			"This change contains no concrete changes:%s%s", System.lineSeparator, change)
		val unresolvedChange = change.unresolve()
		
		LOGGER.info("Start change propagation")
		startChangePropagation(unresolvedChange)
		
		val result = changePropagator.propagateChange(unresolvedChange)
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
		
		finishChangePropagation(unresolvedChange)
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
		checkArgument(oldLocation !== null || newState !== null, "either new state or old location must not be null")
		val currentState = oldLocation.retrieveCurrentModelState()
		if (currentState === null) {
			checkState(newState !== null, "new state must not be null if no resource exists for old location " + oldLocation)
		}
		val vitruvDomain = domainRepository.getDomain(if (oldLocation !== null) oldLocation.fileExtension else newState.URI.fileExtension)
		val strategy = vitruvDomain.stateChangePropagationStrategy
		val compositeChange = if (currentState === null) {
				strategy.getChangeSequenceForCreated(newState)
			} else if (newState === null) {
				strategy.getChangeSequenceForDeleted(currentState)
			} else {
				strategy.getChangeSequenceBetween(newState, currentState)
			}
		if (!compositeChange.containsConcreteChange) {
			LOGGER.warn("State-based change for " + oldLocation + " was empty")
			return emptyList
		}
		return propagateChange(compositeChange)
	}
	
	private def retrieveCurrentModelState(URI location) {
		if (location !== null) {
			resourceRepository.getModel(location)?.resource
		}
	}

	override Path getFolder() {
		return fileSystemLayout.vsumProjectFolder
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

	override void dispose() {
		resourceRepository.close()
	}

}
