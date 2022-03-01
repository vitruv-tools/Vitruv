package tools.vitruv.framework.propagation

import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.propagation.ResourceAccess
import tools.vitruv.framework.change.echange.EChange
import java.util.Set

interface ChangePropagationSpecification extends ChangePropagationObservable {
	def void setUserInteractor(UserInteractor userInteractor)

	def Set<String> getSourceMetamodelRootNsUris()

	def Set<String> getTargetMetamodelRootNsUris()

	def boolean doesHandleChange(EChange change, CorrespondenceModel correspondenceModel)

	def void propagateChange(EChange change, CorrespondenceModel correspondenceModel, ResourceAccess resourceAccess)
}
