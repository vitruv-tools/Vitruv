package tools.vitruv.framework.change.processing

import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.datatypes.MetamodelPair

interface ChangePropagationSpecification {
	def void setUserInteracting(UserInteracting userInteracting);
	def MetamodelPair getMetamodelPair();
	def boolean doesHandleChange(TransactionalChange change, CorrespondenceModel correspondenceModel);
	def ChangePropagationResult propagateChange(TransactionalChange change, CorrespondenceModel correspondenceModel);
}