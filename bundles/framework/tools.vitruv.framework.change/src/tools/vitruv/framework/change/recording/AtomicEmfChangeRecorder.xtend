package tools.vitruv.framework.change.recording

import java.util.List
import org.eclipse.emf.common.notify.Notifier
import tools.vitruv.framework.change.description.TransactionalChange

interface AtomicEmfChangeRecorder {
	def void beginRecording()

	def void clearNotifiers()

	def void addToRecording(Notifier elementToObserve)

	def void removeFromRecording(Notifier elementToObserve)

	def void stopRecording()

	def void endRecording()

	def List<TransactionalChange> getUnresolvedChanges()

	def List<TransactionalChange> getResolvedChanges()

	def boolean isRecording()

	def void dispose()
}
