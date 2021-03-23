package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.propagation.ChangePropagationObservable
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.propagation.ResourceAccess

@Data
class ReactionExecutionState {
	val UserInteractor userInteractor
	val CorrespondenceModel correspondenceModel
	val ResourceAccess resourceAccess 
	val ChangePropagationObservable changePropagationObservable
}