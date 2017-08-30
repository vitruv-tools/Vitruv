package tools.vitruv.framework.vsum

import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EObject
import java.util.concurrent.Callable
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationRepository
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagator
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagatorImpl
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import java.io.File
import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.vsum.repositories.ResourceRepositoryImpl
import tools.vitruv.framework.vsum.repositories.ModelRepositoryImpl
import tools.vitruv.framework.change.echange.EChangeIdManager

class VirtualModelImpl implements InternalVirtualModel {
	private val ResourceRepositoryImpl resourceRepository;
	private val ModelRepositoryImpl modelRepository;
	private val VitruvDomainRepository metamodelRepository;
	private val ChangePropagator changePropagator;
	private val ChangePropagationSpecificationProvider changePropagationSpecificationProvider;
	private val File folder;
	private val EChangeIdManager eChangeIdManager;
	
	public new(File folder, UserInteracting userInteracting, VirtualModelConfiguration modelConfiguration) {
		this.folder = folder;
		this.metamodelRepository = new VitruvDomainRepositoryImpl();
		for (metamodel : modelConfiguration.metamodels) {
			this.metamodelRepository.addDomain(metamodel);
			metamodel.registerAtTuidManagement();
		}
		this.resourceRepository = new ResourceRepositoryImpl(folder, metamodelRepository);
		this.modelRepository = new ModelRepositoryImpl();
		val changePropagationSpecificationRepository = new ChangePropagationSpecificationRepository();
		for (changePropagationSpecification : modelConfiguration.changePropagationSpecifications) {
			changePropagationSpecification.userInteracting = userInteracting;
			changePropagationSpecificationRepository.putChangePropagationSpecification(changePropagationSpecification)
		}
		this.changePropagationSpecificationProvider = changePropagationSpecificationRepository;
		this.changePropagator = new ChangePropagatorImpl(resourceRepository, changePropagationSpecificationProvider, metamodelRepository, resourceRepository, modelRepository);
		this.eChangeIdManager = new EChangeIdManager(this.uuidGeneratorAndResolver, this.uuidGeneratorAndResolver, false);
		VirtualModelManager.instance.putVirtualModel(this);
	}
	
	override getCorrespondenceModel() {
		this.resourceRepository.getCorrespondenceModel();
	}
	
	override getModelInstance(VURI modelVuri) {
		return this.resourceRepository.getModel(modelVuri);
	}
	
	override save() {
		this.resourceRepository.saveAllModels();
	}
	
	override persistRootElement(VURI persistenceVuri, EObject rootElement) {
		this.resourceRepository.persistAsRoot(rootElement, persistenceVuri);
	}
	
	override executeCommand(Callable<Void> command) {
		this.resourceRepository.createRecordingCommandAndExecuteCommandOnTransactionalDomain(command);
	}
	
	override addChangePropagationListener(ChangePropagationListener changePropagationListener) {
		changePropagator.addChangePropagationListener(changePropagationListener);
	}
	
	override propagateChange(VitruviusChange change) {
		change.unresolveIfApplicable
		// Save is done by the change propagator because it has to be performed before finishing sync
		val result = changePropagator.propagateChange(change);
		return result;
	}
	
	override reverseChanges(List<PropagatedChange> changes) {
		val command = EMFCommandBridge.createVitruviusRecordingCommand([|
			changes.reverseView.forEach[it.applyBackward(uuidGeneratorAndResolver)];
			return null;
		])
		resourceRepository.executeRecordingCommandOnTransactionalDomain(command);

		val changedEObjects = changes.map[originalChange.affectedEObjects + consequentialChanges.affectedEObjects].flatten
		changedEObjects.map[eResource].filterNull.forEach[modified = true];
		save();
	}
	
	override setUserInteractor(UserInteracting userInteractor) {
		for (propagationSpecification : this.changePropagationSpecificationProvider) {
			propagationSpecification.userInteracting = userInteractor;
		}
	}
	
	override File getFolder() {
		return folder;
	}
	
	override getUuidGeneratorAndResolver() {
		return resourceRepository.uuidGeneratorAndResolver
	}
	
}