package tools.vitruv.extensions.dslsruntime.reactions

import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.change.processing.ChangePropagationObservable
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.command.ChangePropagationResult

@Data
class ReactionExecutionState {
	UserInteracting userInteracting
	CorrespondenceModel correspondenceModel
	ChangePropagationResult transformationResult
	ChangePropagationObservable changePropagationObservable
}