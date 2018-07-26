package tools.vitruv.framework.change.processing

import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.util.command.ResourceAccess

interface ChangePropagationSpecification extends ChangePropagationObservable {
	def void setUserInteractor(UserInteractor userInteractor);
	def VitruvDomain getSourceDomain();
	def VitruvDomain getTargetDomain();
	def boolean doesHandleChange(TransactionalChange change, CorrespondenceModel correspondenceModel);
	def void propagateChange(TransactionalChange change, CorrespondenceModel correspondenceModel, ResourceAccess resourceAccess);
}