package tools.vitruv.extensions.dslsruntime.reactions

import org.eclipse.emf.ecore.EObject
import java.io.IOException
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.framework.userinteraction.UserInteracting
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.extensions.dslsruntime.reactions.helper.PersistenceHelper
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.extensions.dslsruntime.reactions.effects.ReactionElementStatesHandlerImpl
import java.util.function.Function
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.ChangePropagationResult

abstract class AbstractRepairRoutineRealization extends CallHierarchyHaving implements RepairRoutine, ReactionElementStatesHandler {
	private extension val ReactionExecutionState executionState;
	
	@Delegate
	private val ReactionElementStatesHandler _reactionElementStatesHandler;
	
	public new(ReactionExecutionState executionState, CallHierarchyHaving calledBy) {
		super(calledBy);
		this.executionState = executionState;
		this._reactionElementStatesHandler = new ReactionElementStatesHandlerImpl(correspondenceModel, transformationResult);
	}
	
	protected def ReactionExecutionState getExecutionState() {
		return executionState;
	}
	
	protected def UserInteracting getUserInteracting() {
		return executionState.userInteracting;
	}
	
	protected def CorrespondenceModel getCorrespondenceModel() {
		return executionState.correspondenceModel;
	}
	
	protected def <T extends EObject> getCorrespondingElement(EObject correspondenceSource, Class<T> elementClass, 
		Function<T, Boolean> correspondencePreconditionMethod, String tag
	) {
		val retrievedElements = ReactionsCorrespondenceHelper.getCorrespondingModelElements(correspondenceSource, elementClass, tag, correspondencePreconditionMethod, correspondenceModel);
		if (retrievedElements.size > 1) {
			CorrespondenceFailHandlerFactory.createExceptionHandler().handle(retrievedElements, correspondenceSource, elementClass, executionState.userInteracting);
		}
		val retrievedElement = if (!retrievedElements.empty) retrievedElements.get(0) else null;
		return retrievedElement;
	}
	
	public override void applyRoutine() {
		// Exception catching should be disabled, as least as long as we do not use the framework productively
		//try {
			executeRoutine();
		//} catch (Exception exception) {
			// If an error occured during execution, avoid an application shutdown and print the error.
			//getLogger().error('''«exception.class.simpleName» during execution of effect «calledByString»: «exception.message»''');
		//}
	}
	
	protected abstract def void executeRoutine() throws IOException;
	
	public static class UserExecution {
		protected final UserInteracting userInteracting;
		protected final ChangePropagationResult transformationResult;
		protected final CorrespondenceModel correspondenceModel;
	
		new(ReactionExecutionState executionState) {
			this.userInteracting = executionState.userInteracting;
			this.transformationResult = executionState.transformationResult;
			this.correspondenceModel = executionState.correspondenceModel;
		}
		
		protected def persistProjectRelative(EObject alreadyPersistedObject, EObject element, String persistencePath) {
			if (alreadyPersistedObject == null || element == null || persistencePath == null) {
				throw new IllegalArgumentException("correspondenceSource, element and persistancePath must be specified");
			}
			val oldVURI = if (element.eResource() != null) {
				VURI.getInstance(element.eResource());
			}
			val _resourceURI = PersistenceHelper.getURIFromSourceProjectFolder(alreadyPersistedObject, persistencePath, correspondenceModel);
			EcoreUtil.remove(element);
			transformationResult.addRootEObjectToSave(element, VURI.getInstance(_resourceURI));
			transformationResult.addVuriToDeleteIfNotNull(oldVURI);
		}
		
	}
	
}