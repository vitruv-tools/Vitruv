package tools.vitruv.framework.change.recording

import java.util.Collection
import java.util.List
import org.eclipse.emf.common.notify.Notifier
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.datatypes.VURI

interface AtomicEmfChangeRecorder {

	def void beginRecording(VURI modelVURI, Collection<? extends Notifier> elementsToObserve)

	/** 
	 * Stops recording without returning a result 
	 */
	def void stopRecording()

	def void endRecording()

	def List<TransactionalChange> getUnresolvedChanges()

	def List<TransactionalChange> getResolvedChanges()

	def boolean isRecording()

	def void dispose()
}
