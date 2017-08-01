package tools.vitruv.framework.util.command

import org.eclipse.xtend.lib.annotations.Accessors

abstract class VitruviusTransformationRecordingCommand extends VitruviusRecordingCommand {
	@Accessors(PUBLIC_GETTER)
	protected ChangePropagationResult transformationResult
}
