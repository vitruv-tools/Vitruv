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
import java.util.Vector

class VirtualModelImpl implements InternalVirtualModel {
	
	//############################ ChangeVisualization		
	/**
	 * A list of {@link PropagatedChangeListener} that are informed of all changes made
	 */
	private val List<PropagatedChangeListener> propagatedChangeListeners=new Vector<PropagatedChangeListener>();
		
		
	/**
	 * This method informs registered the {@link PropagatedChangeListener}s of the propagation result.
	 * 
	 * @param propagationResult The propagation result
	 */
	def private informPropagatedChangeListeners(List<PropagatedChange> propagationResult) {
		if(this.propagatedChangeListeners.isEmpty()){
			return;
		}
		val sourceDomain=getSourceDomain(propagationResult);
		val targetDomain=getTargetDomain(propagationResult);
		for(PropagatedChangeListener propagatedChangeListener:this.propagatedChangeListeners) {					
			propagatedChangeListener.postChanges(sourceDomain,targetDomain,propagationResult);
		}
	}
	
	/**
	 * Determines the target domain of a given propagation result
	 * 
	 * @param changes The propagation result
	 * @return The target domain, null if none could be determined
	 */	
	def private getTargetDomain(List<PropagatedChange> changes) {
		if(changes===null) return null;
		
		for(PropagatedChange change:changes){				
			val consequentialChanges=change.getConsequentialChanges();
			if(consequentialChanges!==null){
				val affectedEObjects=consequentialChanges.getAffectedEObjects();
				val domain=getDomain(affectedEObjects);
				if(domain!==null){
					return domain;
				}				
			}			
		}
		
		return null;//nothing found
	}
	
	/**
	 * Determines the source domain of a given propagation result
	 * 
	 * @param changes The propagation result
	 * @return The source domain, null if none could be determined
	 */	
	def private getSourceDomain(List<PropagatedChange> changes) {
		if(changes===null) return null;
		
		for(PropagatedChange change:changes){				
			val originalChange=change.getOriginalChange();
			if(originalChange!==null){
				val affectedEObjects=originalChange.getAffectedEObjects();
				val domain=getDomain(affectedEObjects);
				if(domain!==null){
					return domain;
				}
			}	
		}
		
		return null;//nothing found
	}
	
	/**
	 * Determines the domain of given eObjects
	 * 
	 * @param eObjects The eObjects used to determine the domain
	 */
	def private getDomain(Iterable<EObject> eObjects) {
		if(eObjects!==null){
			for(EObject eObject:eObjects){
				try{
					val domain=this.metamodelRepository.getDomain(eObject)
					return domain													
				}catch(Exception ex1){
					//Nothing to do here. The implementation of getDomain() throws an Exception if nothing was found
					//rather then returning null in this case
				}
			}					
		}else{
			return null;
		}
	}
		
	/**
	 * Registers a given {@link PropagatedChangeListener}.
	 * 
	 * @param propagatedChangeListener The listener to register
	 */
	def addPropagatedChangeListener(PropagatedChangeListener propagatedChangeListener) {
		this.propagatedChangeListeners.add(propagatedChangeListener);				
	}
	
	/**
	 * Removes a given {@link PropagatedChangeListener}. 
	 * Does nothing if the listener was not registered before.
	 * 
	 * @param propagatedChangeListener The listener to register
	 */
	def removePropagatedChangeListener(PropagatedChangeListener propagatedChangeListener) {
		this.propagatedChangeListeners.remove(propagatedChangeListener);				
	}		
	//############################ ChangeVisualization
	
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
		this.modelRepository = new ModelRepositoryImpl(resourceRepository.uuidGeneratorAndResolver);
		val changePropagationSpecificationRepository = new ChangePropagationSpecificationRepository();
		for (changePropagationSpecification : modelConfiguration.changePropagationSpecifications) {
			changePropagationSpecification.userInteracting = userInteracting;
			changePropagationSpecificationRepository.putChangePropagationSpecification(changePropagationSpecification)
		}
		this.changePropagationSpecificationProvider = changePropagationSpecificationRepository;
		this.changePropagator = new ChangePropagatorImpl(resourceRepository, changePropagationSpecificationProvider, metamodelRepository, resourceRepository, modelRepository);
		this.eChangeIdManager = new EChangeIdManager(this.uuidGeneratorAndResolver);
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
		
		//###############  ChangeVisualization
		informPropagatedChangeListeners(result);
		//###############  ChangeVisualization
		
		return result;
	}
	
	override reverseChanges(List<PropagatedChange> changes) {
		val command = EMFCommandBridge.createVitruviusRecordingCommand([|
			changes.reverseView.forEach[it.applyBackward(uuidGeneratorAndResolver)];
			return null;
		])
		resourceRepository.executeRecordingCommandOnTransactionalDomain(command);

		// TODO HK Instead of this make the changes set the modified flag of the resource when applied
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