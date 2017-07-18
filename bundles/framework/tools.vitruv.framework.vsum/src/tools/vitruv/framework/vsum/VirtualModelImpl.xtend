package tools.vitruv.framework.vsum

import java.io.File
import java.util.List
import java.util.concurrent.Callable
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagator
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagatorImpl
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl
import tools.vitruv.framework.vsum.repositories.ResourceRepositoryImpl

class VirtualModelImpl implements InternalVirtualModel {
	protected val ResourceRepositoryImpl resourceRepository
	val ChangePropagationSpecificationProvider changePropagationSpecificationProvider
	val ChangePropagator changePropagator
	val ModelRepositoryImpl modelRepository
	val VitruvDomainRepository metamodelRepository
	@Accessors(PUBLIC_GETTER)
	val File folder

	new(File folder, UserInteracting userInteracting, VirtualModelConfiguration modelConfiguration) {
		this.folder = folder
		this.metamodelRepository = new VitruvDomainRepositoryImpl
		for (metamodel : modelConfiguration.metamodels) {
			this.metamodelRepository.addDomain(metamodel)
		}
		this.resourceRepository = new ResourceRepositoryImpl(folder, metamodelRepository)
		this.modelRepository = new ModelRepositoryImpl
		val changePropagationSpecificationRepository = new ChangePropagationSpecificationRepository
		for (changePropagationSpecification : modelConfiguration.changePropagationSpecifications) {
			changePropagationSpecification.userInteracting = userInteracting
			changePropagationSpecificationRepository.putChangePropagationSpecification(changePropagationSpecification)
		}
		this.changePropagationSpecificationProvider = changePropagationSpecificationRepository
		this.changePropagator = new ChangePropagatorImpl(resourceRepository, changePropagationSpecificationProvider,
			metamodelRepository, resourceRepository, modelRepository)
		VirtualModelManager::instance.putVirtualModel(this)
	}

	override getCorrespondenceModel() {
		resourceRepository.correspondenceModel
	}

	override getModelInstance(VURI modelVuri) {
		resourceRepository.getModel(modelVuri)
	}

	override save() {
		resourceRepository.saveAllModels
	}

	override persistRootElement(VURI persistenceVuri, EObject rootElement) {
		resourceRepository.persistRootElement(persistenceVuri, rootElement)
	}

	override executeCommand(Callable<Void> command) {
		resourceRepository.createRecordingCommandAndExecuteCommandOnTransactionalDomain(command)
	}

	override addChangePropagationListener(ChangePropagationListener changePropagationListener) {
		changePropagator.addChangePropagationListener(changePropagationListener)
	}

	override propagateChange(VitruviusChange change) {
		// Save is done by the change propagator because it has to be performed before finishing sync
		return changePropagator.propagateChange(change)
	}

	override reverseChanges(List<PropagatedChange> changes) {

		val command = EMFCommandBridge::createVitruviusTransformationRecordingCommand([|
			changes.reverseView.forEach [
				applyBackward
				changePropagator.removePropagatedChange(originalChange.URI, id)
			]
			return null
		])
		resourceRepository.executeRecordingCommandOnTransactionalDomain(command)

		val changedEObjects = command.affectedObjects.filter(EObject)
		changedEObjects.map[eResource].filterNull.forEach[modified = true]
		save
	}

	override forwardChanges(List<PropagatedChange> changes) {
		val command = EMFCommandBridge::createVitruviusTransformationRecordingCommand([|
			changes.forEach [
				applyForward
				changePropagator.addPropagatedChanges(originalChange.URI, id)
			]
			return null
		])
		resourceRepository.executeRecordingCommandOnTransactionalDomain(command)

		val changedEObjects = command.affectedObjects.filter(EObject)
		changedEObjects.map[eResource].filterNull.forEach[modified = true]
		save
	}

	override setUserInteractor(UserInteracting userInteractor) {
		for (propagationSpecification : changePropagationSpecificationProvider) {
			propagationSpecification.userInteracting = userInteractor
		}
	}

	override getResolvedPropagatedChanges(VURI vuri) {
		changePropagator.getResolvedPropagatedChanges(vuri)
	}

	override getUnresolvedPropagatedChanges(VURI vuri) {
		changePropagator.getUnresolvedPropagatedChanges(vuri)
	}
}
