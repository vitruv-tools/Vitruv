package tools.vitruv.framework.vsum

import java.io.File
import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI

interface VirtualModel {
	def void setLastPropagatedChangeId(VURI vuri, String id)

	def File getFolder()

	def List<PropagatedChange> getUnresolvedPropagatedChangesSinceLastCommit(VURI vuri)

	def List<PropagatedChange> getResolvedPropagatedChanges(VURI vuri)

	def List<PropagatedChange> getUnresolvedPropagatedChanges(VURI vuri)

	def List<PropagatedChange> propagateChange(VitruviusChange change)

	def ModelInstance getModelInstance(VURI modelVuri)

	def void forwardChanges(List<PropagatedChange> changes)

	def void reverseChanges(List<PropagatedChange> changes)

}
