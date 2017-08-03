package tools.vitruv.framework.util.command

import java.util.concurrent.Callable
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.transaction.TransactionalEditingDomain

class EMFCommandBridge {
	private new() {
	}

	def static VitruviusTransformationRecordingCommand createVitruviusTransformationRecordingCommand(
		Callable<ChangePropagationResult> callable
	) {
		val VitruviusTransformationRecordingCommand recordingCommand = new VitruviusTransformationRecordingCommand {
			override protected void doExecute() {
				try {
					val transformationResult = callable.call
					if (null === transformationResult)
						warn(
							"Transformation change result is null. This indicates that the previous transformation had an error.")
					this.transformationResult = transformationResult
				} catch (Throwable e) {
					storeAndRethrowException(e)
				}
			}
		}
		return recordingCommand
	}

	def static VitruviusRecordingCommand createVitruviusRecordingCommand(Callable<Void> callable) {
		val VitruviusRecordingCommand recordingCommand = new VitruviusRecordingCommand {
			override protected void doExecute() {
				try {
					callable.call
				} catch (Throwable e) {
					storeAndRethrowException(e)
				}

			}
		}
		return recordingCommand
	}

	def static void executeVitruviusRecordingCommand(
		TransactionalEditingDomain domain,
		VitruviusRecordingCommand command
	) {
		command.transactionDomain = domain
		executeCommand(domain, command)
		command.rethrowRuntimeExceptionIfExisting
	}

	private def static void executeCommand(
		TransactionalEditingDomain domain,
		Command command
	) {
		domain.commandStack.execute(command)
	}

	def static void createAndExecuteVitruviusRecordingCommand(
		Callable<Void> callable,
		TransactionalEditingDomain domain
	) {
		val VitruviusRecordingCommand command = createVitruviusRecordingCommand(callable)
		executeVitruviusRecordingCommand(domain, command)
	}
}
