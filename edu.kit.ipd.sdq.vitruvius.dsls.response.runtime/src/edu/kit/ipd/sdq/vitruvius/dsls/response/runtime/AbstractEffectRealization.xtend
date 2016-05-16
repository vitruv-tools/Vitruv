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
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.helper.PersistenceHelper
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects.EffectElementCreate
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects.EffectElement
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence

abstract class AbstractEffectRealization extends CallHierarchyHaving {
	private extension val ResponseExecutionState executionState;
	protected final Map<EObject, EffectElement> elementStates;
	private boolean aborted = false;
	
	public new(ResponseExecutionState executionState, CallHierarchyHaving calledBy) {
		super(calledBy);
		this.executionState = executionState;
		this.elementStates = new HashMap<EObject, EffectElement>();
	}
	
	protected def ResponseExecutionState getExecutionState() {
		return executionState;
	}
	
	protected def void addCorrespondenceBetween(EObject firstElement, EObject secondElement, String tag) {
		if (!elementStates.containsKey(firstElement)) {
			elementStates.put(firstElement, new EffectElementRetrieve(firstElement, executionState.correspondenceInstance));
		}
		this.elementStates.get(firstElement).addCorrespondingElement(secondElement, tag);
	}
	
	protected def isAborted() {
		return aborted;
	}
	
	protected def void markObjectDelete(EObject element) {
		this.elementStates.get(element).setDelete();
	}
	
	protected def void removeCorrespondenceBetween(EObject firstElement, EObject secondElement
	) {
		if (!elementStates.containsKey(firstElement)) {
			elementStates.put(firstElement, new EffectElementRetrieve(firstElement, executionState.correspondenceInstance));
		}
		this.elementStates.get(firstElement).removeCorrespondingElement(secondElement);
	}
	
	private def <T extends EObject> getCorrespondingElement(EObject correspondenceSource, Function1<T, Boolean> correspondencePreconditionMethod, 
		Function0<String> tagSupplier, Class<T> elementClass
	) {
		val tag = tagSupplier.apply();
		val retrievedElements = CorrespondenceHelper.getCorrespondingModelElements(correspondenceSource, elementClass, tag, correspondencePreconditionMethod, correspondenceInstance);
		if (retrievedElements.size > 1) {
			CorrespondenceFailHandlerFactory.createExceptionHandler().handle(retrievedElements, correspondenceSource, elementClass, executionState.userInteracting);
		}
		val retrievedElement = if (!retrievedElements.empty) retrievedElements.get(0) else null;
		return retrievedElement;
	}
	
	protected def initializeCreateElementState(EObject element) {
		this.elementStates.put(element, new EffectElementCreate(element, executionState.correspondenceInstance));
	}
	
	protected def <T extends EObject> T initializeRetrieveElementState(Function0<EObject> correspondenceSourceSupplier, 
		Function1<T, Boolean> correspondencePreconditionMethod, Function0<String> tagSupplier, Class<T> elementClass, 
		boolean optional, boolean required, boolean absence) {
		val correspondenceSource = correspondenceSourceSupplier.apply();
		val correspondingElement = correspondenceSource.getCorrespondingElement(correspondencePreconditionMethod, tagSupplier,
			elementClass
		);
		if (correspondingElement == null && required) {
			aborted = true;
		}
		if (correspondingElement != null && absence) {
			aborted = true;
		}
		
		if (correspondingElement != null) {
			this.elementStates.put(correspondingElement, 
				new EffectElementRetrieve(correspondingElement, executionState.correspondenceInstance)
			);
		}
		return correspondingElement;
	}
	
	protected def preProcessElements() {
		for (state : elementStates.values) {
			state.preProcess();
		}
	}
	
	protected def postProcessElements() {
//		for (state : correspondenceStates) {
//			state.postProcess();
//		}
		for (element : elementStates.keySet) {
			elementStates.get(element).postProcess();
			elementStates.get(element).updateTUID();
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
		protected final CorrespondenceInstance<Correspondence> correspondenceInstance;
	
		new(ResponseExecutionState executionState) {
			this.userInteracting = executionState.userInteracting;
			this.transformationResult = executionState.transformationResult;
			this.correspondenceInstance = executionState.correspondenceInstance;
		}
		
		protected def persistProjectRelative(EObject alreadyPersistedObject, EObject element, String persistencePath) {
			if (alreadyPersistedObject == null || element == null || persistencePath == null) {
				throw new IllegalArgumentException("correspondenceSource, element and persistancePath must be specified");
			}
			val oldVURI = if (element.eResource() != null) {
				VURI.getInstance(element.eResource());
			}
			val _resourceURI = PersistenceHelper.getURIFromSourceProjectFolder(alreadyPersistedObject, persistencePath, correspondenceInstance);
			EcoreUtil.remove(element);
			transformationResult.addRootEObjectToSave(element, VURI.getInstance(_resourceURI));
			transformationResult.addVURIToDeleteIfNotNull(oldVURI);
		}
		
	}
	
}