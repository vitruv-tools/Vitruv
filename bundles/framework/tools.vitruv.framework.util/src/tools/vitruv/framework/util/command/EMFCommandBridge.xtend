package tools.vitruv.framework.util.command

import java.util.concurrent.Callable
import org.eclipse.emf.transaction.TransactionalEditingDomain
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.util.Capture

@Utility
class EMFCommandBridge {
	def private static createVitruviusRecordingCommand(TransactionalEditingDomain domain, Callable<Void> callable) {
		new VitruviusRecordingCommand(domain) {
			override protected void doExecute() {
				try {
					callable.call()
				} catch (Throwable e) {
					storeAndRethrowException(e)
				}
			}
		}
	}

	def static <T> T executeVitruviusRecordingCommand(TransactionalEditingDomain domain, Callable<T> callable) {
		val result = new Capture<T>
		domain.createVitruviusRecordingCommand [
			result += callable.call()
			return null
		].executeAndRethrowException()
		return -result
	}

	def static executeVitruviusRecordingCommand(TransactionalEditingDomain domain, Runnable runnable) {
		domain.createVitruviusRecordingCommand [
			runnable.run()
			return null
		] => [executeAndRethrowException()]
	}
}
