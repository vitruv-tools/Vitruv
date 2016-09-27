package tools.vitruv.framework.vsum

import tools.vitruv.framework.metamodel.MetamodelRepository
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EObject
import java.util.concurrent.Callable
import tools.vitruv.framework.modelsynchronization.ChangeSynchronizerImpl
import tools.vitruv.framework.change.processing.impl.AbstractChange2CommandTransformingProviding
import tools.vitruv.framework.modelsynchronization.ChangeSynchronizing
import tools.vitruv.framework.modelsynchronization.SynchronisationListener
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.change.processing.Change2CommandTransformingProviding
import tools.vitruv.framework.metamodel.MetamodelRepositoryImpl

class VirtualModelImpl implements InternalVirtualModel {
	private val ModelRepositoryImpl modelRepository;
	private val MetamodelRepository metamodelRepository;
	private val ChangeSynchronizing changeSynchronizer;
	private val Change2CommandTransformingProviding transformingProviding;
	private val String name;
	
	public new(String name, VirtualModelConfiguration modelConfiguration) {
		this(name, modelConfiguration, null);
	}
	
	public new(String name, VirtualModelConfiguration modelConfiguration, ClassLoader classLoader) {
		this.name = name;
		metamodelRepository = new MetamodelRepositoryImpl();
		for (metamodel : modelConfiguration.metamodels) {
			metamodelRepository.addMetamodel(metamodel);
		}
		this.modelRepository = new ModelRepositoryImpl(metamodelRepository, classLoader);
//		for (transformer : modelConfiguration.change2CommandTransformings) {
//			val transformableMetamodels = transformer.transformableMetamodels;
//			// TODO HK This is ugly: get the correspondence model to initialize it
//			modelRepository.getCorrespondenceModel(transformableMetamodels.first, transformableMetamodels.second)
//		}
		transformingProviding = AbstractChange2CommandTransformingProviding.createChange2CommandTransformingProviding(modelConfiguration.change2CommandTransformings.toList);
		this.changeSynchronizer = new ChangeSynchronizerImpl(modelRepository, transformingProviding, metamodelRepository, modelRepository);
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
	
	override createModel(VURI vuri, EObject rootEObject) {
		this.modelRepository.createModel(vuri, rootEObject);
	}
	
	override executeCommand(Callable<Void> command) {
		this.modelRepository.createRecordingCommandAndExecuteCommandOnTransactionalDomain(command);
	}
	
	override addChangeSynchronizationListener(SynchronisationListener synchronizationListener) {
		changeSynchronizer.addSynchronizationListener(synchronizationListener);
	}
	
	override propagateChange(VitruviusChange change) {
		changeSynchronizer.synchronizeChange(change);
		save();
	}
	
	override setUserInteractor(UserInteracting userInteractor) {
		for (transformer : this.transformingProviding) {
			transformer.userInteracting = userInteractor;
		}
	}
	
	override String getName() {
		return name;
	}
}