package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.framework.change.processing.ChangePropagationObservable
import org.eclipse.xtend.lib.annotations.Data

@Data
class ReactionExecutionState {
	private val UserInteracting userInteracting;
	private val CorrespondenceModel correspondenceModel;
	private val ChangePropagationResult transformationResult;
	private val ChangePropagationObservable changePropagationObservable;
}