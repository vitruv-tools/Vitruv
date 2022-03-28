package tools.vitruv.framework.propagation.impl

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
import tools.vitruv.framework.propagation.ChangePropagationObserver
import tools.vitruv.framework.propagation.ChangePropagationSpecification
import tools.vitruv.framework.propagation.ChangePropagationSpecificationProvider
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.userinteraction.UserInteractionListener

import static com.google.common.base.Preconditions.checkState
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import java.util.HashSet
import java.util.Set
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.propagation.ChangeRecordingModelRepository

class ChangePropagator {
	static val logger = Logger.getLogger(ChangePropagator)
	val ChangeRecordingModelRepository modelRepository
	val ChangePropagationSpecificationProvider changePropagationProvider
	val InternalUserInteractor userInteractor

	new(ChangeRecordingModelRepository modelRepository,
		ChangePropagationSpecificationProvider changePropagationProvider, InternalUserInteractor userInteractor) {
		this.modelRepository = modelRepository
		this.changePropagationProvider = changePropagationProvider
		this.userInteractor = userInteractor
	}

	def List<PropagatedChange> propagateChange(VitruviusChange change) {
		val resolvedChange = modelRepository.applyChange(change)
		resolvedChange.affectedEObjects.map[eResource].filterNull.forEach[modified = true]

		if (logger.isTraceEnabled) {
			logger.trace('''
				Will now propagate this input change:
					«resolvedChange»
			''')
		}
		return new ChangePropagation(this, resolvedChange, null).propagateChanges()
	}

	@FinalFieldsConstructor
	private static class ChangePropagation implements ChangePropagationObserver, UserInteractionListener {
		extension val ChangePropagator outer
		val VitruviusChange sourceChange
		val ChangePropagation previous
		val Set<Resource> changedResources = new HashSet
		val List<EObject> createdObjects = new ArrayList
		val List<UserInteractionBase> userInteractions = new ArrayList

		def private propagateChanges() {
			val result = sourceChange.transactionalChangeSequence.flatMapFixed[propagateSingleChange(it)]
			handleObjectsWithoutResource()
			changedResources.forEach[modified = true]
			return result
		}

		def private List<PropagatedChange> propagateSingleChange(TransactionalChange change) {
			checkState(!change.affectedEObjects.isNullOrEmpty, "There are no objects affected by this change:%s%s",
				System.lineSeparator, change)

			val userInteractorChange = installUserInteractorForChange(change)
			changePropagationProvider.forEach[registerObserver(this)]
			userInteractor.registerUserInputListener(this)

			val propagationResultChanges = try {
					sourceChange.affectedEObjectsMetamodelDescriptors.flatMap [
						changePropagationProvider.getChangePropagationSpecifications(it)
					].toSet.flatMapFixed [
						propagateChangeForChangePropagationSpecification(change, it)
					]
				} finally {
					userInteractor.deregisterUserInputListener(this)
					changePropagationProvider.forEach[deregisterObserver(this)]
					userInteractorChange.close()
				}

			if (logger.isDebugEnabled) {
				logger.debug(
					'''Propagated «FOR p : propagationPath SEPARATOR ' -> '»«p»«ENDFOR» -> {«FOR changeInPropagation : propagationResultChanges SEPARATOR ", "»«
						changeInPropagation.change.affectedEObjectsMetamodelDescriptors»«ENDFOR»}'''
				)
			}
			if (logger.isTraceEnabled) {
				logger.trace('''
					Result changes:
						«FOR result : propagationResultChanges»
							«result.change.affectedEObjectsMetamodelDescriptors»: «result.change»
						«ENDFOR»
				''')
			}

			change.userInteractions = userInteractions
			val propagatedChange = new PropagatedChange(change,
				VitruviusChangeFactory.instance.createCompositeChange(propagationResultChanges.mapFixed[it.change]))
			val resultingChanges = new ArrayList()
			resultingChanges += propagatedChange

			val nextPropagations = propagationResultChanges.filter [
				shouldBeFurtherPropagated && it.change.containsConcreteChange
			].mapFixed [
				new ChangePropagation(outer, it.change, this)
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
			val changesInPropagation = modelRepository.recordChanges [
				for (eChange : change.EChanges) {
					propagationSpecification.propagateChange(eChange, modelRepository.correspondenceModel,
						modelRepository)
				}
			]

			// Store modification information
			changedResources += changesInPropagation.flatMap[it.change.affectedEObjects].map[eResource].filterNull

			return changesInPropagation
		}

		def private AutoCloseable installUserInteractorForChange(VitruviusChange change) {
			// retrieve user inputs from past changes, construct a UserInteractor which tries to reuse them:
			val pastUserInputsFromChange = change.userInteractions

			if (!pastUserInputsFromChange.nullOrEmpty) {
				userInteractor.replaceUserInteractionResultProvider [ currentProvider |
					UserInteractionFactory.instance.createPredefinedInteractionResultProvider(currentProvider,
						pastUserInputsFromChange)
				]
			} else
				[]
		}

		def private void handleObjectsWithoutResource() {
			// Find created objects without resource
			for (createdObjectWithoutResource : createdObjects.filter[eResource === null]) {
				checkState(
					!modelRepository.correspondenceModel.hasCorrespondences(List.of(createdObjectWithoutResource)),
					"The object %s is part of a correspondence to %s but not in any resource",
					createdObjectWithoutResource,
					modelRepository.correspondenceModel.getCorrespondingEObjects(#[createdObjectWithoutResource]))
				logger.warn("Object was created but has no correspondence and is thus lost: " +
					createdObjectWithoutResource)
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
			if (previous === null)
				List.of("<input change> in " + sourceChange.affectedEObjectsMetamodelDescriptors.toString)
			else
				previous.propagationPath + List.of(sourceChange.affectedEObjectsMetamodelDescriptors.toString)
		}
	}

	def private Iterable<TransactionalChange> getTransactionalChangeSequence(VitruviusChange change) {
		switch (change) {
			case !change.containsConcreteChange: emptyList()
			TransactionalChange: List.of(change)
			CompositeChange<?>: change.changes.flatMap[transactionalChangeSequence]
			default: throw new IllegalStateException("Unexpected change type: " + change.class.simpleName)
		}
	}

}
