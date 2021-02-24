package tools.vitruv.framework.vsum.modelsynchronization

import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.description.CompositeChange
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.change.processing.ChangePropagationObserver
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.userinteraction.UserInteractionListener
import tools.vitruv.framework.vsum.ModelRepository

import static com.google.common.base.Preconditions.checkNotNull
import static com.google.common.base.Preconditions.checkState
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import java.util.HashSet
import java.util.Set
import org.eclipse.emf.ecore.resource.Resource

class ChangePropagator {
	static val logger = Logger.getLogger(ChangePropagator)
	val VitruvDomainRepository domainRepository
	val ModelRepository resourceRepository
	val ChangePropagationSpecificationProvider changePropagationProvider
	val InternalUserInteractor userInteractor

	new(ModelRepository resourceRepository, ChangePropagationSpecificationProvider changePropagationProvider,
		VitruvDomainRepository domainRepository, InternalUserInteractor userInteractor) {
		this.resourceRepository = resourceRepository
		this.changePropagationProvider = changePropagationProvider
		this.domainRepository = domainRepository
		this.userInteractor = userInteractor
	}

	def List<PropagatedChange> propagateChange(VitruviusChange change) {
		change.resolveBeforeAndApplyForward(resourceRepository.uuidResolver)
		
		val changedDomain = change.changedDomain
		if (logger.isTraceEnabled) {
			logger.trace('''
				Will now propagate this input change:
					«change»
			''')
		}
		return new ChangePropagation(this, change, changedDomain, null).propagateChanges()
	}

	@FinalFieldsConstructor
	private static class ChangePropagation implements ChangePropagationObserver, UserInteractionListener {
		extension val ChangePropagator outer
		val VitruviusChange sourceChange
		val VitruvDomain sourceDomain
		val ChangePropagation previous
		val Set<Resource> changedResources = new HashSet
		val List<EObject> createdObjects = new ArrayList
		val List<UserInteractionBase> userInteractions = new ArrayList
		
		def private propagateChanges() {
			val result = sourceChange.transactionalChangeSequence.flatMapFixed [propagateSingleChange(it)]
			handleObjectsWithoutResource()
			changedResources.forEach [modified = true]
			return result
		}
		
		def private List<PropagatedChange> propagateSingleChange(TransactionalChange change) {
			checkState(!change.affectedEObjects.isNullOrEmpty, 
				"There are no objects affected by this change:%s%s", System.lineSeparator, change)

			val userInteractorChange = installUserInteractorForChange(change)
			changePropagationProvider.forEach [registerObserver(this)]
			userInteractor.registerUserInputListener(this)

			val propagationResultChanges = try {
				changePropagationProvider.getChangePropagationSpecifications(sourceDomain).mapFixed [
					targetDomain -> propagateChangeForChangePropagationSpecification(change, it)
				]
			} finally {
				userInteractor.deregisterUserInputListener(this)
				changePropagationProvider.forEach [deregisterObserver(this)]
				userInteractorChange.close()
			}
			
			if (logger.isDebugEnabled) {
				logger.debug('''Propagated «FOR p : propagationPath SEPARATOR ' -> '»«p»«ENDFOR» -> {«FOR result: propagationResultChanges SEPARATOR ", "»«result.key»«ENDFOR»}''')
			}
			if (logger.isTraceEnabled) {
				logger.trace('''
					Result changes:
						«FOR result : propagationResultChanges»
							«result.key»: «result.value»
						«ENDFOR»
				''')
			}

			change.userInteractions = userInteractions
			val propagatedChange = new PropagatedChange(change,
				VitruviusChangeFactory.instance.createCompositeChange(propagationResultChanges.flatMapFixed [value]))
			val resultingChanges = new ArrayList()
			resultingChanges += propagatedChange
	
			val nextPropagations = propagationResultChanges
				.filter [key.shouldTransitivelyPropagateChanges && value.exists[containsConcreteChange]]
				.mapFixed [
					new ChangePropagation(outer, VitruviusChangeFactory.instance.createCompositeChange(value), key, this)
				]

			for (nextPropagation : nextPropagations) {
				resultingChanges += nextPropagation.propagateChanges()
			}
	
			return resultingChanges
		}
		
		def private propagateChangeForChangePropagationSpecification(
			TransactionalChange change,
			ChangePropagationSpecification propagationSpecification
		) {
			resourceRepository.startRecording()
			
			// TODO HK: Clone the changes for each synchronization! Should even be cloned for
			// each consistency repair routines that uses it,
			// or: make them read only, i.e. give them a read-only interface!
			propagationSpecification.propagateChange(change, resourceRepository.correspondenceModel, resourceRepository)
			val changes = resourceRepository.endRecording()
	
			// Store modification information
			changedResources += changes.flatMap [affectedEObjects].map [eResource].filterNull
			
			return changes
		}
		
		def private AutoCloseable installUserInteractorForChange(VitruviusChange change) {
			// retrieve user inputs from past changes, construct a UserInteractor which tries to reuse them:
			val pastUserInputsFromChange = change.userInteractions
	
			if (!pastUserInputsFromChange.nullOrEmpty) {
				userInteractor.replaceUserInteractionResultProvider[ currentProvider |
					UserInteractionFactory.instance.
						createPredefinedInteractionResultProvider(currentProvider, pastUserInputsFromChange)
				]
			} else []
		}
		
		
		def private void handleObjectsWithoutResource() {
			// Find created objects without resource
			for (createdObjectWithoutResource : createdObjects.filter[eResource === null]) {
				checkState(!resourceRepository.correspondenceModel.hasCorrespondences(List.of(createdObjectWithoutResource)),
					"This object is part of a correspondence but not in any resource: %s", createdObjectWithoutResource)
				logger.warn("Object was created but has no correspondence and is thus lost: " 
					+ createdObjectWithoutResource)
			}
		}
		
		override objectCreated(EObject createdObject) {
			createdObjects += createdObject
		}
		
		override onUserInteractionReceived(UserInteractionBase interaction) {
			userInteractions += interaction
		}
		
		override toString() '''propagate «FOR p : propagationPath SEPARATOR ' -> '»«p»«ENDFOR»: «sourceChange»'''
		
		def private Iterable<String> getPropagationPath() {
			if (previous === null) List.of("<input change> in " + sourceDomain.toString)
			else previous.propagationPath + List.of(sourceDomain.toString)
		}
	}
	
	def private Iterable<TransactionalChange> getTransactionalChangeSequence(VitruviusChange change) {
		switch (change) {
			case !change.containsConcreteChange: emptyList()
			TransactionalChange: List.of(change)
			CompositeChange<?>: change.changes.flatMap [transactionalChangeSequence]
			default: throw new IllegalStateException("Unexpected change type: " + change.class.simpleName)
		}
	}

	def private VitruvDomain getChangedDomain(VitruviusChange change) {
		val changeDomain = change.changedVURIs.fold(null as VitruvDomain) [changeDomain, changedVuri |
			val resourceDomain = domainRepository.getDomain(changedVuri.fileExtension)
			if (changeDomain === null) {
				resourceDomain
			} else if (resourceDomain === null || resourceDomain == changeDomain) {
				changeDomain
			} else {
				throw new IllegalStateException('''
					This change affects multiple domains («changeDomain» and «resourceDomain»):
						«change»
				''')
			}
		]
		checkState(changeDomain !== null, "Cannot determine the domain of this change:%s%s", System.lineSeparator, change)
		changeDomain
	}
}
