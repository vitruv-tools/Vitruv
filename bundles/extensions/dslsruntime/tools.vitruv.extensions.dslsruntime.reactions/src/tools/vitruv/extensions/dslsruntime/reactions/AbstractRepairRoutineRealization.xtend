package tools.vitruv.extensions.dslsruntime.reactions

import java.io.IOException
import java.util.List
import java.util.function.Function
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.extensions.dslsruntime.reactions.effects.ReactionElementsHandlerImpl
import tools.vitruv.extensions.dslsruntime.reactions.helper.PersistenceHelper
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.extensions.dslsruntime.reactions.structure.Loggable
import tools.vitruv.framework.change.processing.ChangePropagationObservable
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.util.command.ResourceAccess
import tools.vitruv.framework.util.datatypes.VURI

abstract class AbstractRepairRoutineRealization extends CallHierarchyHaving implements RepairRoutine, ReactionElementsHandler {
	private val AbstractRepairRoutinesFacade routinesFacade;
	private extension val ReactionExecutionState executionState;

	@Delegate
	private val ReactionElementsHandler _reactionElementsHandler;

	public new(AbstractRepairRoutinesFacade routinesFacade, ReactionExecutionState executionState, CallHierarchyHaving calledBy) {
		super(calledBy);
		this.routinesFacade = routinesFacade;
		this.executionState = executionState;
		this._reactionElementsHandler = new ReactionElementsHandlerImpl(correspondenceModel);
	}

	// generic return type for convenience; the requested type has to match the type of the facade provided during construction
	protected def <T extends AbstractRepairRoutinesFacade> T getRoutinesFacade() {
		return routinesFacade as T;
	}

	protected def ReactionExecutionState getExecutionState() {
		return executionState;
	}

	protected def UserInteractor getUserInteractor() {
		return executionState.userInteractor;
	}

	protected def CorrespondenceModel getCorrespondenceModel() {
		return executionState.correspondenceModel;
	}

	protected def void notifyObjectCreated(EObject createdObject) {
		executionState.changePropagationObservable.notifyObjectCreated(createdObject);
	}
	
	protected def <T extends EObject> List<T> getCorrespondingElements(
		EObject correspondenceSource,
		Class<T> elementClass,
		Function<T, Boolean> correspondencePreconditionMethod,
		String tag
	) {
		val retrievedElements = ReactionsCorrespondenceHelper.getCorrespondingModelElements(correspondenceSource,
			elementClass, tag, correspondencePreconditionMethod, correspondenceModel);
		return retrievedElements;
	}

	protected def <T extends EObject> T getCorrespondingElement(
		EObject correspondenceSource,
		Class<T> elementClass,
		Function<T, Boolean> correspondencePreconditionMethod,
		String tag,
		boolean asserted
	) {
		val retrievedElements = getCorrespondingElements(correspondenceSource,
			elementClass, correspondencePreconditionMethod, tag);
		if (retrievedElements.size > 1) {
			CorrespondenceFailHandlerFactory.createExceptionHandler().handle(retrievedElements, correspondenceSource,
				elementClass, executionState.userInteractor);
		}
		if (asserted && retrievedElements.size == 0) {
			CorrespondenceFailHandlerFactory.createExceptionHandler().handle(retrievedElements, correspondenceSource,
				elementClass, executionState.userInteractor);
		}
		val retrievedElement = if (!retrievedElements.empty) retrievedElements.get(0) else null;
		return retrievedElement;
	}

	public override boolean applyRoutine() {
		// capture the current routines facade execution state:
		val facadeExecutionState = routinesFacade._getExecutionState().capture();
		// set the reaction execution state and caller to use for all following routine calls:
		routinesFacade._getExecutionState.setExecutionState(executionState, this);

		try {
			// Exception handling could be added here when productively used
			return executeRoutine();
		} finally {
			// restore the previously captured execution state of the facade:
			routinesFacade._getExecutionState().restore(facadeExecutionState);
		}
	}

	protected abstract def boolean executeRoutine() throws IOException;

	public static class UserExecution extends Loggable {
		protected final UserInteractor userInteractor;
		protected final ResourceAccess resourceAccess;
		protected final CorrespondenceModel correspondenceModel;
		protected final ChangePropagationObservable changePropagationObservable;

		new(ReactionExecutionState executionState) {
			this.userInteractor = executionState.userInteractor;
			this.resourceAccess = executionState.resourceAccess;
			this.correspondenceModel = executionState.correspondenceModel;
			this.changePropagationObservable = executionState.changePropagationObservable;
		}

		/**
		 * Persists a given {@link EObject} as root object in the {@link Resource} at the specified path, 
		 * relative to the project root folder.
		 * 
		 * @param alreadyPersistedObject -
		 * 		An object that was already persisted within the project (necessary for retrieving the project folder)
		 * @param elementToPersist -
		 * 		The element to be persisted
		 * @param persistencePath -
		 * 		The path relative to the project root folder at which the element shall be persisted,
		 * 		using "/" as separator char and including the file name with extension
		 */
		protected def persistProjectRelative(EObject alreadyPersistedObject, EObject elementToPersist,
			String persistencePath) {
			if (alreadyPersistedObject === null || elementToPersist === null || persistencePath === null) {
				throw new IllegalArgumentException(
					"correspondenceSource, element and persistancePath must be specified");
			}

			val _resourceURI = PersistenceHelper.getURIFromSourceProjectFolder(alreadyPersistedObject, persistencePath);
			val modelURI = VURI.getInstance(_resourceURI)
			persistAsRoot(elementToPersist, modelURI)
		}

		protected def persistAsMetadataRoot(EObject rootObject, String... metadataKey) {
			if (rootObject === null) {
				throw new IllegalArgumentException("rootObject is null!");
			}
			val modelURI = resourceAccess.getMetadataModelURI(metadataKey)
			persistAsRoot(rootObject, modelURI)
		}

		private def persistAsRoot(EObject rootObject, VURI vuri) {
			logger.debug("Registered to persist root " + rootObject + " in: " + vuri)
			if (rootObject.eResource?.URI !== vuri.EMFUri) {
				// Update TUID after removal, as persistence will also change it and rely on an up-to-date value
				TuidManager.getInstance().registerObjectUnderModification(rootObject)
				EcoreUtil.remove(rootObject)
				TuidManager.getInstance().updateTuidsOfRegisteredObjects()
				resourceAccess.persistAsRoot(rootObject, vuri)
			}
		}
	}
}
