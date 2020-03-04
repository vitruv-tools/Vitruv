package tools.vitruv.framework.util.command

import java.util.concurrent.Callable

interface CommandCreatorAndExecutor {
	def void executeAsCommand(Callable<Void> command);
	def VitruviusRecordingCommand createCommand(Callable<Void> command);
}