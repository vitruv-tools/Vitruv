package tools.vitruv.extensions.dslsruntime.reactions

import org.eclipse.emf.ecore.EObject
import java.io.IOException
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.framework.userinteraction.UserInteracting
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.extensions.dslsruntime.reactions.helper.PersistenceHelper
import tools.vitruv.framework.util.datatypes.VURI
import java.util.function.Function
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.extensions.dslsruntime.reactions.structure.Loggable
import tools.vitruv.extensions.dslsruntime.reactions.effects.ReactionElementsHandlerImpl
import tools.vitruv.framework.change.processing.ChangePropagationObservable
import tools.vitruv.framework.util.command.ResourceAccess
import tools.vitruv.framework.tuid.TuidManager
import java.util.List

abstract class AbstractRepairRoutineRealization extends CallHierarchyHaving implements RepairRoutine, ReactionElementsHandler {
	private extension val ReactionExecutionState executionState;

	@Delegate
	private val ReactionElementsHandler _reactionElementsHandler;

	public new(ReactionExecutionState executionState, CallHierarchyHaving calledBy) {
		super(calledBy);
		this.executionState = executionState;
		this._reactionElementsHandler = new ReactionElementsHandlerImpl(correspondenceModel);
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
				elementClass, executionState.userInteracting);
		}
		if (asserted && retrievedElements.size == 0) {
			CorrespondenceFailHandlerFactory.createExceptionHandler().handle(retrievedElements, correspondenceSource,
				elementClass, executionState.userInteracting);
		}
		val retrievedElement = if (!retrievedElements.empty) retrievedElements.get(0) else null;
		return retrievedElement;
	}

	public override boolean applyRoutine() {
		// Exception handling could be added here when productively used
		executeRoutine();
	}

	protected abstract def boolean executeRoutine() throws IOException;

	public static class UserExecution extends Loggable {
		protected final UserInteracting userInteracting;
		protected final ResourceAccess resourceAccess;
		protected final CorrespondenceModel correspondenceModel;
		protected final ChangePropagationObservable changePropagationObservable;

		new(ReactionExecutionState executionState) {
			this.userInteracting = executionState.userInteracting;
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
			logger.debug("Registered to persist root " + elementToPersist + " in: " + VURI.getInstance(_resourceURI));
			
			// Update TUID after removal, as persistence will also change it and rely on an up-to-date value			
			TuidManager.getInstance().registerObjectUnderModification(elementToPersist);
			EcoreUtil.remove(elementToPersist);
			TuidManager.getInstance().updateTuidsOfRegisteredObjects();
			resourceAccess.persistAsRoot(elementToPersist, VURI.getInstance(_resourceURI));
		}

		/**
		 * Gets a resource suitable to store metadata for consistency 
		 * preservation.
		 * 
		 *  @param storageKey
		 * 		The key uniquely identifying the portion of metadata that is to be 
		 * 		stored in the returned resource. The different parts of the key
		 * 		can be used to convey some sort of hierarchy in the metadata.
		 * @return A resource suitable to store the metadata identified by the
		 * 		 given {@code key}. Subsequent calls to this method with the same
		 * 		 {@code key} will return resources for the same location.
		 */
		protected def getMetadataResource(String... storageKey) {
			if (storageKey === null) {
				throw new IllegalArgumentException("No storage key provided!")
			}
			resourceAccess.getResourceForMetadataStorage(storageKey)
		}
	}
}
