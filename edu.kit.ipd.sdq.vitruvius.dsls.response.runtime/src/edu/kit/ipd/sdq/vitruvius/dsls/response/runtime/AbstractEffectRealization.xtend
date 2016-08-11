package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import org.eclipse.emf.ecore.EObject
import java.io.IOException
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import org.eclipse.emf.ecore.util.EcoreUtil
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.helper.PersistenceHelper
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects.ResponseElementStatesHandlerImpl
import java.util.function.Function
import org.eclipse.xtend.lib.annotations.Delegate
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.helper.ResponseCorrespondenceHelper

abstract class AbstractEffectRealization extends CallHierarchyHaving implements RepairRoutine, ResponseElementStatesHandler {
	private extension val ResponseExecutionState executionState;
	
	@Delegate
	private val ResponseElementStatesHandler _responseElementStatesHandler;
	
	public new(ResponseExecutionState executionState, CallHierarchyHaving calledBy) {
		super(calledBy);
		this.executionState = executionState;
		this._responseElementStatesHandler = new ResponseElementStatesHandlerImpl(correspondenceInstance);
	}
	
	protected def ResponseExecutionState getExecutionState() {
		return executionState;
	}
	
	protected def <T extends EObject> getCorrespondingElement(EObject correspondenceSource, Class<T> elementClass, 
		Function<T, Boolean> correspondencePreconditionMethod, String tag
	) {
		val retrievedElements = ResponseCorrespondenceHelper.getCorrespondingModelElements(correspondenceSource, elementClass, tag, correspondencePreconditionMethod, correspondenceInstance);
		if (retrievedElements.size > 1) {
			CorrespondenceFailHandlerFactory.createExceptionHandler().handle(retrievedElements, correspondenceSource, elementClass, executionState.userInteracting);
		}
		val retrievedElement = if (!retrievedElements.empty) retrievedElements.get(0) else null;
		return retrievedElement;
	}
	
	public override void applyRoutine() {
		try {
			executeRoutine();
		} catch (Exception exception) {
			// If an error occured during execution, avoid an application shutdown and print the error.
			getLogger().error('''«exception.class.simpleName» during execution of effect «calledByString»: «exception.message»''');
		}
	}
	
	protected abstract def void executeRoutine() throws IOException;
	
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