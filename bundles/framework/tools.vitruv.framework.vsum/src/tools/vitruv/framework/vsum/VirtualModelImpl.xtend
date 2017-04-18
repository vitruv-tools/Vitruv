package tools.vitruv.framework.vsum

import tools.vitruv.framework.metamodel.MetamodelRepository
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EObject
import java.util.concurrent.Callable
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.metamodel.MetamodelRepositoryImpl
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationRepository
import tools.vitruv.framework.modelsynchronization.ChangePropagator
import tools.vitruv.framework.modelsynchronization.ChangePropagatorImpl
import tools.vitruv.framework.modelsynchronization.ChangePropagationListener

class VirtualModelImpl implements InternalVirtualModel {
	private val ModelRepositoryImpl modelRepository;
	private val MetamodelRepository metamodelRepository;
	private val ChangePropagator changePropagator;
	private val ChangePropagationSpecificationProvider changePropagationSpecificationProvider;
	private val String name;
	
	public new(String name, VirtualModelConfiguration modelConfiguration) {
		this.name = name;
		metamodelRepository = new MetamodelRepositoryImpl();
		for (metamodel : modelConfiguration.metamodels) {
			metamodelRepository.addMetamodel(metamodel);
		}
		this.modelRepository = new ModelRepositoryImpl(name, metamodelRepository);
		val changePropagationSpecificationRepository = new ChangePropagationSpecificationRepository();
		for (changePropagationSpecification : modelConfiguration.changePropagationSpecifications) {
			changePropagationSpecificationRepository.putChangePropagationSpecification(changePropagationSpecification)
		}
		this.changePropagationSpecificationProvider = changePropagationSpecificationRepository;
		this.changePropagator = new ChangePropagatorImpl(modelRepository, changePropagationSpecificationProvider, metamodelRepository, modelRepository);
		VirtualModelManager.instance.putVirtualModel(this);
	}
	
	override getCorrespondenceModel(VURI metamodel1, VURI metamodel2) {
		this.modelRepository.getCorrespondenceModel(metamodel1, metamodel2);
	}
	
	override getModelInstance(VURI modelVuri) {
		return this.modelRepository.getModel(modelVuri);
	}
	
	override save() {
		this.modelRepository.saveAllModels();
	}
	
	override persistRootElement(VURI persistenceVuri, EObject rootElement) {
		this.modelRepository.persistRootElement(persistenceVuri, rootElement);
	}
	
	override executeCommand(Callable<Void> command) {
		this.modelRepository.createRecordingCommandAndExecuteCommandOnTransactionalDomain(command);
	}
	
	override addChangePropagationListener(ChangePropagationListener changePropagationListener) {
		changePropagator.addChangePropagationListener(changePropagationListener);
	}
	
	override propagateChange(VitruviusChange change) {
		// Save is done by the change propagator because it has to be performed before finishing sync
		changePropagator.propagateChange(change);
	}
	
	override setUserInteractor(UserInteracting userInteractor) {
		for (propagationSpecification : this.changePropagationSpecificationProvider) {
			propagationSpecification.userInteracting = userInteractor;
		}
	}
	
	override String getName() {
		return name;
	}
}