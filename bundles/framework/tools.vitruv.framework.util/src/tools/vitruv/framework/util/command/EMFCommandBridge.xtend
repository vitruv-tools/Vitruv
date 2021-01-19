package tools.vitruv.framework.util.command

import java.util.concurrent.Callable
import org.eclipse.emf.transaction.TransactionalEditingDomain
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.util.Capture

@Utility
class EMFCommandBridge {
	/**
	 * Executes the given {@link Callable} on the given {@link TransactionalEditingDomain},
	 * thus ensuring transactional behavior of changes to all resources handled by that domain.
	 * Returns the result of {@code callable}.
	 */
	def static <T> T executeVitruviusRecordingCommand(TransactionalEditingDomain domain, Callable<T> callable) {
		val result = new Capture<T>
		domain.createVitruviusRecordingCommand [
			result += callable.call()
			return null
		].executeAndRethrowException()
		return -result
	}
	
	/**
	 * Executes the given {@link Callable} on the given {@link TransactionalEditingDomain},
	 * thus ensuring transactional behavior of changes to all resources handled by that domain.
	 * Returns the result of {@code callable}.
	 * <p>
	 * The execution flushes the command history of the {@code domain} afterwards, such that
	 * undoing commands is not possible anymore.
	 */
	def static <T> T executeVitruviusRecordingCommandAndFlushHistory(TransactionalEditingDomain domain, Callable<T> callable) {
		val result = domain.executeVitruviusRecordingCommand(callable)
		domain.flushCommandHistory
		return result
	}

	/**
	 * Executes the given {@link Runnable} on the given {@link TransactionalEditingDomain},
	 * thus ensuring transactional behavior of changes to all resources handled by that domain.
	 */
	def static VitruviusRecordingCommand executeVitruviusRecordingCommand(TransactionalEditingDomain domain,
		Runnable runnable) {
		val command = domain.createVitruviusRecordingCommand [
			runnable.run()
			return null
		] => [executeAndRethrowException()]
		return command
	}
	
	/**
	 * Executes the given {@link Runnable} on the given {@link TransactionalEditingDomain},
	 * thus ensuring transactional behavior of changes to all resources handled by that domain.
	 * <p>
	 * The execution flushes the command history of the {@code domain} afterwards, such that
	 * undoing commands is not possible anymore.
	 */
	def static VitruviusRecordingCommand executeVitruviusRecordingCommandAndFlushHistory(TransactionalEditingDomain domain,
		Runnable runnable) {
		val result = domain.executeVitruviusRecordingCommand(runnable)
		domain.flushCommandHistory
		return result		
	}

	/**
	 * Flushes the {@code domain}'s command stack, thus undoing changes on the {@code domain 
	 * is not possible anymore.
	 */
	def static flushCommandHistory(TransactionalEditingDomain domain) {
		domain.commandStack.flush
	}

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
}
