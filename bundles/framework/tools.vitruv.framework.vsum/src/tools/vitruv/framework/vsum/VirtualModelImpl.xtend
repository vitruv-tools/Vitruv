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
		
	private val ResourceRepositoryImpl resourceRepository;
	private val ModelRepositoryImpl modelRepository;
	private val VitruvDomainRepository metamodelRepository;
	private val ChangePropagator changePropagator;
	private val ChangePropagationSpecificationProvider changePropagationSpecificationProvider;
	private val File folder;
	private val EChangeIdManager eChangeIdManager;
	
	/**
	 * A list of {@link PropagatedChangeListener}s that are informed of all changes made
	 */
	private val List<PropagatedChangeListener> propagatedChangeListeners;
	
	
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
		
		this.propagatedChangeListeners=new Vector<PropagatedChangeListener>();
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
		
		//Inform registered PropagatedChangeListeners
		informPropagatedChangeListeners(result);
		
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
	
	/**
	 * Registers a given {@link PropagatedChangeListener}.
	 * 
	 * @param propagatedChangeListener The listener to register
	 */
	override void addPropagatedChangeListener(PropagatedChangeListener propagatedChangeListener) {
		this.propagatedChangeListeners.add(propagatedChangeListener);				
	}
	
	/**
	 * Removes a given {@link PropagatedChangeListener}. 
	 * Does nothing if the listener was not registered before.
	 * 
	 * @param propagatedChangeListener The listener to remove
	 */
	override void removePropagatedChangeListener(PropagatedChangeListener propagatedChangeListener) {
		this.propagatedChangeListeners.remove(propagatedChangeListener);				
	}
	
	/**
	 * This method informs the registered {@link PropagatedChangeListener}s of the propagation result.
	 * 
	 * @param propagationResult The propagation result
	 */
	def private void informPropagatedChangeListeners(List<PropagatedChange> propagationResult) {
		if(this.propagatedChangeListeners.isEmpty()){
			return;
		}
		val sourceDomain=getSourceDomain(propagationResult);
		val targetDomain=getTargetDomain(propagationResult);
		val virtualModelName=getName();
		for(PropagatedChangeListener propagatedChangeListener:this.propagatedChangeListeners) {					
			propagatedChangeListener.postChanges(virtualModelName,sourceDomain,targetDomain,propagationResult);
		}
	}
	
	/**
	 * Returns the name of the virtual model
	 * 
	 * @return The name of the virtual model
	 */
	def getName() {
		val name=this.getFolder.getName;
		if(name.indexOf("_vsum")!=-1){
			//JUnit tests use a folder named Name_vsum_...
			return name.substring(0,name.indexOf("_vsum"))
		}else{
			//For live models, just return the folder's name
			return name
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
			val domain=getDomain(change.getConsequentialChanges())
			if(domain!==null){
				return domain;							
			}			
		}
		
		return null;
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
			val domain=getDomain(change.getOriginalChange())
			if(domain!==null){
				return domain;							
			}	
		}
		
		return null;
	}
	
	/**
	 * Determines the domain of a given {@link VitruviusChange}
	 * 
	 * @param change The change used to determine the domain
	 * @return The domain, null if none could be determined
	 */
	def private getDomain(VitruviusChange change) {
		if(change===null){
			return null
		}		
		
		val affectedEObjects=change.getAffectedEObjects();		
		if(affectedEObjects!==null){
			for(EObject eObject:affectedEObjects){
				try{
					val domain=this.metamodelRepository.getDomain(eObject)
					return domain													
				}catch(Exception ex1){
					//Nothing to do here. The implementation of getDomain() throws an Exception if nothing was found
					//rather then returning null in this case
				}
			}					
		}
		
		//Either no eObjects exist or none of them were known at the metamodelRepository
		return null;
	}	
	
}