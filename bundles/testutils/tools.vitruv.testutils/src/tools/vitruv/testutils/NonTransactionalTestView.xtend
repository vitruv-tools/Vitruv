package tools.vitruv.testutils

import org.eclipse.emf.common.notify.Notifier
import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange

/**
 * An extension of the {@link TestView}, which provides non-transactional options for change recording, i.e.,
 * for manually starting and stopping recording changes of elements and for propagating all changes recorded
 * since the last propagation.   
 */
interface NonTransactionalTestView extends TestView {
	/**
	 * Starts recording changes for the given {@code notifier} and all its contained elements. Has to be stopped by calling 
	 * {@link #stopRecordingChanges stopRecordingChanges} with the same argument.
	 * Use {@link #record record} to run recording in a transaction that automatically stops recording on return. 
	 * <p>
	 * Whether changes will effectively be recorded depends on this view. It is permissible for a view not to record
	 * any changes if it deems them irrelevant.
	 */
	def void startRecordingChanges(Notifier notifier)

	/**
	 * Stops recording changes for the given {@code notifier} and all its contained elements. Has to be started by calling 
	 * {@link #startRecordingChanges startRecordingChanges} with the same argument before.
	 * Has no effect if recording has not been started before.
	 * Use {@link #record record} to run recording in a transaction that automatically stops recording on return. 
	 */
	def void stopRecordingChanges(Notifier notifier)

	/**
	 * Propagates all changes recorded since the last call of this method at all objects for which recording has been started
	 * before the changes were performed using {@link #startRecordingChanges startRecordingChanges}. 
	 * Calling this method does not stop any recording, so recording for all elements for which it has been started before
	 * continues.
	 */
	def List<PropagatedChange> propagate()

	/**
	 * Clears all loaded resources, such that yet loaded resources become invalid and have to be reloaded using 
	 * {@link #resourceAt resourceAt}.
	 */
	def void renewResourceCache()
}
