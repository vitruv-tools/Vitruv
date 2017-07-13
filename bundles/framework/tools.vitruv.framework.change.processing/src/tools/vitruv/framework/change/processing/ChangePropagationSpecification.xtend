package tools.vitruv.framework.change.processing

import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.domains.VitruvDomain

interface ChangePropagationSpecification extends ChangePropagationObservable {
	def void setUserInteracting(UserInteracting userInteracting);
	def VitruvDomain getSourceDomain();
	def VitruvDomain getTargetDomain();
	def boolean doesHandleChange(TransactionalChange change, CorrespondenceModel correspondenceModel);
	def ChangePropagationResult propagateChange(TransactionalChange change, CorrespondenceModel correspondenceModel);
}