package tools.vitruv.framework.vsum

import java.io.File
import java.util.concurrent.Callable
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import tools.vitruv.framework.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.modelsynchronization.ChangePropagator
import tools.vitruv.framework.modelsynchronization.ChangePropagatorImpl
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagator
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagatorImpl
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl
import tools.vitruv.framework.vsum.repositories.ResourceRepositoryImpl
import tools.vitruv.framework.vsum.VirtualModelConfiguration
import tools.vitruv.framework.vsum.VirtualModelManager

class VirtualModelImpl implements InternalVirtualModel {
	protected val ModelRepositoryImpl modelRepository
	val ChangePropagationSpecificationProvider changePropagationSpecificationProvider
	val ChangePropagator changePropagator
	val VitruvDomainRepository metamodelRepository
    val ResourceRepositoryImpl resourceRepository

    @Accessors(PUBLIC_GETTER)
	val File folder
    
    new(File folder, UserInteracting userInteracting, VirtualModelConfiguration modelConfiguration) {
        this.folder = folder
		metamodelRepository = new VitruvDomainRepositoryImpl
		modelConfiguration.metamodels.forEach[metamodelRepository.addDomain(it)]
        modelRepository = new ModelRepositoryImpl
        val changePropagationSpecificationRepository = new ChangePropagationSpecificationRepository
		for (changePropagationSpecification : modelConfiguration.changePropagationSpecifications) {
			changePropagationSpecification.userInteracting = userInteracting;
			changePropagationSpecificationRepository.putChangePropagationSpecification(changePropagationSpecification)
		}
		this.changePropagationSpecificationProvider = changePropagationSpecificationRepository;
		this.changePropagator = new ChangePropagatorImpl(resourceRepository, changePropagationSpecificationProvider, metamodelRepository, resourceRepository, modelRepository);
		VirtualModelManager.instance.putVirtualModel(this);
	}

	override getCorrespondenceModel() {
		modelRepository.correspondenceModel
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
		changePropagator.propagateChange(change)
	}
	
	override reverseChanges(List<PropagatedChange> changes) {
		val command = EMFCommandBridge.createVitruviusTransformationRecordingCommand([|
			changes.reverseView.forEach[applyBackward];
			return null;
		])
		resourceRepository.executeRecordingCommandOnTransactionalDomain(command);

		val changedEObjects = command.getAffectedObjects().filter(EObject)
		changedEObjects.map[eResource].filterNull.forEach[modified = true];
		save();
	}

	override setUserInteractor(UserInteracting userInteractor) {
		for (propagationSpecification : changePropagationSpecificationProvider) {
			propagationSpecification.userInteracting = userInteractor
		}
	}
}
