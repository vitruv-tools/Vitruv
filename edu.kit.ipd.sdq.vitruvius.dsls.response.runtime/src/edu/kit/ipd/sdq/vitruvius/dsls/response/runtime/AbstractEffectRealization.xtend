package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import org.eclipse.emf.ecore.EObject
import java.io.IOException
import java.util.Map
import java.util.HashMap
import org.eclipse.xtext.xbase.lib.Functions.Function0
import org.eclipse.xtext.xbase.lib.Functions.Function1
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.helper.CorrespondenceHelper
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects.EffectElementRetrieve
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import org.eclipse.emf.ecore.util.EcoreUtil
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import java.util.Collections
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.helper.PersistenceHelper
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects.EffectCorrespondenceDeletion
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects.EffectCorrespondenceCreation
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects.EffectCorrespondenceModification
import java.util.List
import java.util.ArrayList

abstract class AbstractEffectRealization extends CallHierarchyHaving {
	private extension val ResponseExecutionState executionState;
	protected final List<EffectCorrespondenceModification> correspondenceStates;
	protected final Map<EObject, EffectElementRetrieve> retrieveElementStates;
	private boolean aborted = false;
	
	public new(ResponseExecutionState executionState, CallHierarchyHaving calledBy) {
		super(calledBy);
		this.executionState = executionState;
		this.correspondenceStates = new ArrayList<EffectCorrespondenceModification>();
		this.retrieveElementStates = new HashMap<EObject, EffectElementRetrieve>();
	}
	
	protected def ResponseExecutionState getExecutionState() {
		return executionState;
	}
	
	protected def void initializeCreateCorrespondenceState(EObject firstElement, EObject secondElement, String tag) {
		this.correspondenceStates.add(new EffectCorrespondenceCreation(firstElement, secondElement, executionState, tag));
	}
	
	protected def isAborted() {
		return aborted;
	}
	
	protected def void initializeDeleteCorrespondenceState(EObject firstElement, boolean deleteFirst, 
		EObject secondElement, boolean deleteSecond
	) {
		if (deleteFirst) {
			retrieveElementStates.get(firstElement).disableTUIDUpdate();
		}
		if (deleteSecond) {
			retrieveElementStates.get(secondElement).disableTUIDUpdate();
		}
		this.correspondenceStates.add(new EffectCorrespondenceDeletion(firstElement, deleteFirst, secondElement, deleteSecond, executionState));
	}
	
	private def <T extends EObject> getCorrespondingElement(EObject correspondenceSource, Function1<T, Boolean> correspondencePreconditionMethod, 
		Function0<String> tagSupplier, Class<T> elementClass, boolean optional, boolean required
	) {
		val tag = tagSupplier.apply();
		val retrievedElements = CorrespondenceHelper.getCorrespondingModelElements(correspondenceSource, elementClass, tag, correspondencePreconditionMethod, blackboard);
		if (retrievedElements.size != 1) {
			if (retrievedElements.size == 0 && optional) {
				return null;
			}
			if (retrievedElements.size == 0 && required) {
				aborted = true;
				return null;
			}
			CorrespondenceFailHandlerFactory.createExceptionHandler().handle(retrievedElements, correspondenceSource, elementClass, executionState.userInteracting);
		}
		val retrievedElement = if (!retrievedElements.empty) retrievedElements.get(0) else null;
		return retrievedElement;
	}
	
	protected def <T extends EObject> T initializeRetrieveElementState(Function0<EObject> correspondenceSourceSupplier, 
		Function1<T, Boolean> correspondencePreconditionMethod, Function0<String> tagSupplier, Class<T> elementClass, 
		boolean optional, boolean required) {
		val correspondenceSource = correspondenceSourceSupplier.apply();
		val correspondingElement = correspondenceSource.getCorrespondingElement(correspondencePreconditionMethod, tagSupplier,
			elementClass, optional, required
		);
		this.retrieveElementStates.put(correspondingElement, 
			new EffectElementRetrieve(correspondingElement, correspondenceSource, executionState)
		);
		return correspondingElement;
	}
	
	protected def preProcessElements() {
		for (state : correspondenceStates) {
			state.preProcess();
		}
	}
	
	protected def postProcessElements() {
		for (state : correspondenceStates) {
			state.postProcess();
		}
		for (element : retrieveElementStates.keySet) {
			retrieveElementStates.get(element).updateTUIDs();
		}
	}
	
	public def void applyEffect() {
		// If not all parameters are set this is not a lightweight error caused by erroneous responses but an implementation
		// error of the response mechanism, so throw an exception
		if (!allParametersSet()) {
			getLogger().error('''Not all parameters were set for executing effect («this.class.simpleName»)''');
			throw new IllegalStateException('''Not all parameters were set for executing effect («this.class.simpleName»)''');
		}
		try {
			executeEffect();
		} catch (Exception exception) {
			// If an error occured during execution, avoid an application shutdown and print the error.
			getLogger().error('''«exception.class.simpleName» during execution of effect «calledByString»: «exception.message»''');
		}
	}
	
	protected abstract def boolean allParametersSet();
	protected abstract def void executeEffect() throws IOException;
	
	public static class UserExecution {
		protected final UserInteracting userInteracting;
		protected final TransformationResult transformationResult;
		protected final Blackboard blackboard;
	
		new(ResponseExecutionState executionState) {
			this.userInteracting = executionState.userInteracting;
			this.transformationResult = executionState.transformationResult;
			this.blackboard = executionState.blackboard;
		}
		
		protected def persistProjectRelative(EObject alreadyPersistedObject, EObject element, String persistencePath) {
			if (alreadyPersistedObject == null || element == null || persistencePath == null) {
				throw new IllegalArgumentException("correspondenceSource, element and persistancePath must be specified");
			}
			val oldVURI = if (element.eResource() != null) {
				VURI.getInstance(element.eResource());
			}
			val _resourceURI = PersistenceHelper.getURIFromSourceProjectFolder(alreadyPersistedObject, persistencePath, blackboard);
			EcoreUtil.remove(element);
			transformationResult.addRootEObjectToSave(element, VURI.getInstance(_resourceURI));
			transformationResult.addVURIToDeleteIfNotNull(oldVURI);
		}
		
	}
	
}