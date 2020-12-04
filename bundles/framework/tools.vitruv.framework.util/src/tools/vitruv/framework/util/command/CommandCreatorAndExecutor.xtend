package tools.vitruv.framework.util.command

import java.util.concurrent.Callable

interface CommandCreatorAndExecutor {
	def <T> T executeAsCommand(Callable<T> command)
	def VitruviusRecordingCommand executeAsCommand(Runnable command)
}