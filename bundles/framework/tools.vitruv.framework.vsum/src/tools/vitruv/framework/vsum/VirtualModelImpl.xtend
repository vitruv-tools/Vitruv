package tools.vitruv.framework.vsum

import java.io.File
import java.util.concurrent.Callable
import org.eclipse.emf.ecore.EObject
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
	val ModelRepositoryImpl modelRepository
	val VitruvDomainRepository metamodelRepository
	val ChangePropagator changePropagator
	val ChangePropagationSpecificationProvider changePropagationSpecificationProvider
	val File folder

	new(File folder, VirtualModelConfiguration modelConfiguration) {
		this.folder = folder
		metamodelRepository = new VitruvDomainRepositoryImpl
		for (metamodel : modelConfiguration.metamodels) {
			metamodelRepository.addDomain(metamodel)
		}
		this.modelRepository = new ModelRepositoryImpl(folder, metamodelRepository)
		val changePropagationSpecificationRepository = new ChangePropagationSpecificationRepository
		for (changePropagationSpecification : modelConfiguration.changePropagationSpecifications) {
			changePropagationSpecificationRepository.putChangePropagationSpecification(changePropagationSpecification)
		}
		this.changePropagationSpecificationProvider = changePropagationSpecificationRepository
		this.changePropagator = new ChangePropagatorImpl(modelRepository, changePropagationSpecificationProvider,
			metamodelRepository, modelRepository)
		VirtualModelManager.instance.putVirtualModel(this)
	}

	override getCorrespondenceModel() {
		this.modelRepository.correspondenceModel
	}

	override getModelInstance(VURI modelVuri) {
		return this.modelRepository.getModel(modelVuri)
	}

	override save() {
		this.modelRepository.saveAllModels
	}

	override persistRootElement(VURI persistenceVuri, EObject rootElement) {
		this.modelRepository.persistRootElement(persistenceVuri, rootElement)
	}

	override executeCommand(Callable<Void> command) {
		this.modelRepository.createRecordingCommandAndExecuteCommandOnTransactionalDomain(command)
	}

	override addChangePropagationListener(ChangePropagationListener changePropagationListener) {
		changePropagator.addChangePropagationListener(changePropagationListener)
	}

	override propagateChange(VitruviusChange change) {
// Save is done by the change propagator because it has to be performed before finishing sync
		changePropagator.propagateChange(change)
	}

	override setUserInteractor(UserInteracting userInteractor) {
		for (propagationSpecification : this.changePropagationSpecificationProvider) {
			propagationSpecification.userInteracting = userInteractor
		}
	}

	override File getFolder() {
		return folder
	}
}
