package tools.vitruv.framework.vsum

import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EObject
import java.util.concurrent.Callable
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationRepository
import tools.vitruv.framework.modelsynchronization.ChangePropagator
import tools.vitruv.framework.modelsynchronization.ChangePropagatorImpl
import tools.vitruv.framework.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl

class VirtualModelImpl implements InternalVirtualModel {
	val ModelRepositoryImpl modelRepository
	val VitruvDomainRepository metamodelRepository
	val ChangePropagator changePropagator
	val ChangePropagationSpecificationProvider changePropagationSpecificationProvider
	val String name

	new(String name, VirtualModelConfiguration modelConfiguration) {
		this.name = name
		metamodelRepository = new VitruvDomainRepositoryImpl
		modelConfiguration.metamodels.forEach[metamodelRepository.addDomain(it)]
		modelRepository = new ModelRepositoryImpl(name, metamodelRepository)
		val changePropagationSpecificationRepository = new ChangePropagationSpecificationRepository
		modelConfiguration.changePropagationSpecifications.forEach [
			changePropagationSpecificationRepository.putChangePropagationSpecification(it)
		]
		changePropagationSpecificationProvider = changePropagationSpecificationRepository
		changePropagator = new ChangePropagatorImpl(modelRepository, changePropagationSpecificationProvider,
			metamodelRepository, modelRepository)
		VirtualModelManager::instance.putVirtualModel(this)
	}

	override getCorrespondenceModel() {
		modelRepository.correspondenceModel
	}

	override getModelInstance(VURI modelVuri) {
		modelRepository.getModel(modelVuri)
	}

	override save() {
		modelRepository.saveAllModels
	}

	override persistRootElement(VURI persistenceVuri, EObject rootElement) {
		modelRepository.persistRootElement(persistenceVuri, rootElement)
	}

	override executeCommand(Callable<Void> command) {
		modelRepository.createRecordingCommandAndExecuteCommandOnTransactionalDomain(command)
	}

	override addChangePropagationListener(ChangePropagationListener changePropagationListener) {
		changePropagator.addChangePropagationListener(changePropagationListener)
	}

	override propagateChange(VitruviusChange change) {
		// Save is done by the change propagator because it has to be performed before finishing sync
		changePropagator.propagateChange(change)
	}

	override setUserInteractor(UserInteracting userInteractor) {
		changePropagationSpecificationProvider.forEach[userInteracting = userInteractor]
	}

	override String getName() {
		name
	}
}
