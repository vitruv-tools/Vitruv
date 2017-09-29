package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.processing.ChangePropagationObservable
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.util.command.ResourceAccess

@Data
class ReactionExecutionState {
	val UserInteracting userInteracting
	val CorrespondenceModel correspondenceModel
	val ResourceAccess resourceAccess 
	val ChangePropagationObservable changePropagationObservable
}