package tools.vitruv.framework.vsum.modelsynchronization

import java.util.ArrayList
import java.util.Collections
import java.util.List
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.description.CompositeChange
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.change.processing.ChangePropagationObserver
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.correspondence.CorrespondencePackage
import tools.vitruv.framework.correspondence.CorrespondenceProviding
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.userinteraction.UserInteractionListener
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.vsum.ModelRepository
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl

import static com.google.common.base.Preconditions.checkNotNull
import static com.google.common.base.Preconditions.checkState
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class ChangePropagatorImpl implements ChangePropagator, UserInteractionListener {
	static Logger logger = Logger.getLogger(ChangePropagatorImpl)
	val VitruvDomainRepository domainRepository
	val ModelRepository resourceRepository
	val ChangePropagationSpecificationProvider changePropagationProvider
	val CorrespondenceProviding correspondenceProviding
	val Set<ChangePropagationListener> changePropagationListeners
	val ModelRepositoryImpl modelRepository
	val InternalUserInteractor userInteractor
	val List<UserInteractionBase> userInteractions

	new(ModelRepository resourceRepository, ChangePropagationSpecificationProvider changePropagationProvider,
		VitruvDomainRepository metamodelRepository, CorrespondenceProviding correspondenceProviding,
		ModelRepositoryImpl modelRepository, InternalUserInteractor userInteractor) {
		this.resourceRepository = resourceRepository
		this.modelRepository = modelRepository
		this.changePropagationProvider = changePropagationProvider
		this.correspondenceProviding = correspondenceProviding
		this.changePropagationListeners = Collections.synchronizedSet(newHashSet)
		this.userInteractions = Collections.synchronizedList(newArrayList)
		this.domainRepository = metamodelRepository
		this.userInteractor = userInteractor
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
		checkNotNull("The change to propagate was null!")
		if (!change.containsConcreteChange()) {
			logger.info('''The change does not contain any changes to synchronize: «change»''')
			return emptyList
		}

		startChangePropagation(change)

		change.transactionalChangeSequence.forEach [applySingleChange(it)]
		val changePropagationResult = new ChangePropagation(this, change, change.changedDomain).propagateChanges()

		// FIXME HK This is not clear! VirtualModel knows how to save, we bypass that, but currently this is necessary
		// because saving has to be performed before finishing propagation. Maybe we should move the observable to the VirtualModel
		resourceRepository.saveAllModels
		logger.trace(modelRepository)
		logger.trace('''
			Propagated changes:
			«FOR propagatedChange : changePropagationResult»
				Propagated Change:
				«propagatedChange»
			«ENDFOR»
		''')
		finishChangePropagation(change)

		return changePropagationResult
	}
	
	private def void startChangePropagation(VitruviusChange change) {
		logger.debug('''Started synchronizing change: «change»''')
		this.changePropagationListeners.forEach [startedChangePropagation]
	}

	private def void finishChangePropagation(VitruviusChange change) {
		this.changePropagationListeners.forEach [finishedChangePropagation]
		logger.debug('''Finished synchronizing change: «change»''')
	}
	
	private def void applySingleChange(TransactionalChange change) {
		resourceRepository.executeOnUuidResolver [ UuidResolver uuidResolver |
			change.resolveBeforeAndApplyForward(uuidResolver)
			// If change has a URI, add the model to the repository
			change.changedVURIs.forEach [resourceRepository.getModel(it)]
			change.affectedEObjects.forEach [modelRepository.addRootElement(it)]
		]
		modelRepository.cleanupRootElements()
	}
	
	@FinalFieldsConstructor
	private static class ChangePropagation implements ChangePropagationObserver {
		extension val ChangePropagatorImpl outer
		val VitruviusChange sourceChange
		val VitruvDomain sourceDomain
		val ChangedResourcesTracker changedResourcesTracker = new ChangedResourcesTracker
		val List<EObject> objectsCreatedDuringPropagation = new ArrayList
		
		
		private def propagateChanges() {
			val result = sourceChange.transactionalChangeSequence.flatMapFixed [propagateSingleChange(it)]
			handleObjectsWithoutResource()
			changedResourcesTracker.markNonSourceResourceAsChanged()
			return result
		}
		
		private def List<PropagatedChange> propagateSingleChange(TransactionalChange change) {
			val changedObjects = change.affectedEObjects
			checkState(!changedObjects.nullOrEmpty, "There are no objects affected by this change:%s%s", System.lineSeparator, change)
			// retrieve user inputs from past changes, construct a UserInteractor which tries to reuse them:
			val pastUserInputsFromChange = change.userInteractions
	
			val AutoCloseable userInteractorChange = if (!pastUserInputsFromChange.nullOrEmpty) {
				userInteractor.replaceUserInteractionResultProvider[ currentProvider |
					UserInteractionFactory.instance.
						createPredefinedInteractionResultProvider(currentProvider, pastUserInputsFromChange)
				]
			} else []
			changePropagationProvider.forEach [registerObserver(this)]

			// target domain to changes in that domain
			val propagationResults = try {
				changePropagationProvider.getChangePropagationSpecifications(sourceDomain).mapFixed [
					targetDomain -> propagateChangeForChangePropagationSpecification(change, it)
				]
			} finally {
				changePropagationProvider.forEach [deregisterObserver(this)]
				userInteractorChange.close()
			}
			
			if (logger.traceEnabled) {
				logger.trace('''
					«FOR result : propagationResults»
					Changes generated in «result.key» by change propagation:
						«result.value»
					«ENDFOR»
				''')
			}
			change.userInteractions = newArrayList(userInteractions)
			userInteractions.clear()
	
			val propagatedChange = new PropagatedChange(change,
				VitruviusChangeFactory.instance.createCompositeChange(propagationResults.flatMapFixed [value]))
			val resultingChanges = new ArrayList()
			resultingChanges += propagatedChange
	
			val nextPropagations = propagationResults
				.filter [key.shouldTransitivelyPropagateChanges]
				.map [key -> VitruviusChangeFactory.instance.createCompositeChange(value.dropCorrespondenceChanges)]
				.filter [value.containsConcreteChange]
				.mapFixed [new ChangePropagation(outer, value, key)]
	
			for (nextPropagation : nextPropagations) {
				resultingChanges += nextPropagation.propagateChanges()
			}
	
			return resultingChanges
		}
		
		private def Iterable<? extends TransactionalChange> propagateChangeForChangePropagationSpecification(
			TransactionalChange change,
			ChangePropagationSpecification propagationSpecification
		) {
			// modelRepository.startRecording
			resourceRepository.startRecording()

			val correspondenceModel = correspondenceProviding.getCorrespondenceModel()
	
			// TODO HK: Clone the changes for each synchronization! Should even be cloned for
			// each consistency repair routines that uses it,
			// or: make them read only, i.e. give them a read-only interface!
			val changedEObjects = resourceRepository.executeAsCommand [
				propagationSpecification.propagateChange(change, correspondenceModel, resourceRepository)
				modelRepository.cleanupRootElements()
			].affectedObjects.filter(EObject)
	
			// Store modification information
			changedEObjects.forEach[changedResourcesTracker.addInvolvedModelResource(eResource)]
			changedResourcesTracker.addSourceResourceOfChange(change)
			
			resourceRepository.endRecording() /* + modelRepository.endRecording() */
		}
		
		private def void handleObjectsWithoutResource() {
			modelRepository.cleanupRootElementsWithoutResource
			// Find created objects without resource
			for (createdObjectWithoutResource : objectsCreatedDuringPropagation.filter[eResource === null]) {
				val hasCorrespondence = correspondenceProviding.correspondenceModel.hasCorrespondences(
					List.of(createdObjectWithoutResource))
				checkState(
					!hasCorrespondence, '''Every object must be contained within a resource: «createdObjectWithoutResource»''')
				logger.warn("Object was created but has no correspondence and is thus lost: " +
					createdObjectWithoutResource)
			}
		} 
		
		override synchronized objectCreated(EObject createdObject) {
			objectsCreatedDuringPropagation += createdObject
			modelRepository.addRootElement(createdObject)
		}
	}
	
	def private static List<? extends TransactionalChange> dropCorrespondenceChanges(Iterable<? extends TransactionalChange> changes) {
		changes.map [rewrapWithoutCorrespondenceChanges].filterNull.toList
	}

	def private static dispatch TransactionalChange rewrapWithoutCorrespondenceChanges(CompositeTransactionalChange change) {
		val newChanges = change.changes.dropCorrespondenceChanges()
		if (!newChanges.isEmpty) {
			VitruviusChangeFactory.instance.createCompositeTransactionalChange(newChanges)
		} else null
	}

	def private static dispatch TransactionalChange rewrapWithoutCorrespondenceChanges(ConcreteChange change) {
		return if (!change.affectedEObjects.exists [isFromCorrespondencePackage]) change else null
	}

	def private static boolean isFromCorrespondencePackage(EObject object) {
		val typeAndSuperTypes = Collections.singletonList(object.eClass) + object.eClass.EAllSuperTypes
		return typeAndSuperTypes.exists [EPackage === CorrespondencePackage.eINSTANCE]
	}
	
	def private Iterable<TransactionalChange> getTransactionalChangeSequence(VitruviusChange change) {
		switch (change) {
			case !change.containsConcreteChange: emptyList()
			TransactionalChange: List.of(change)
			CompositeChange<?>: change.changes.flatMap [transactionalChangeSequence]
			default: throw new IllegalStateException("Unexpected change type: " + change.class.simpleName)
		}
	}

	override onUserInteractionReceived(UserInteractionBase interaction) {
		userInteractions.add(interaction)
	}
	
	private def VitruvDomain getChangedDomain(VitruviusChange change) {
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
