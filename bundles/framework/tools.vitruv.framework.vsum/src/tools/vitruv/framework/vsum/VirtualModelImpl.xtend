package tools.vitruv.framework.vsum

import java.io.File
import java.util.concurrent.Callable
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import tools.vitruv.framework.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.modelsynchronization.ChangePropagator
import tools.vitruv.framework.modelsynchronization.ChangePropagatorImpl
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.vsum.VirtualModelConfiguration
import tools.vitruv.framework.vsum.VirtualModelManager
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl

class VirtualModelImpl implements InternalVirtualModel {
	val ChangePropagationSpecificationProvider changePropagationSpecificationProvider
	val ChangePropagator changePropagator
	val ModelRepositoryImpl modelRepository
	val VitruvDomainRepository metamodelRepository

	@Accessors(PUBLIC_GETTER)
	val File folder

	new(File folder, VirtualModelConfiguration modelConfiguration) {
		this.folder = folder
		metamodelRepository = new VitruvDomainRepositoryImpl
		for (metamodel : modelConfiguration.metamodels) {
			metamodelRepository.addDomain(metamodel)
		}
		modelRepository = new ModelRepositoryImpl(folder, metamodelRepository)
		val changePropagationSpecificationRepository = new ChangePropagationSpecificationRepository
		for (changePropagationSpecification : modelConfiguration.changePropagationSpecifications) {
			changePropagationSpecificationRepository.putChangePropagationSpecification(changePropagationSpecification)
		}
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
		for (propagationSpecification : changePropagationSpecificationProvider) {
			propagationSpecification.userInteracting = userInteractor
		}
	}
}
