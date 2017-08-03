package tools.vitruv.framework.vsum

import java.util.List

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.VURI

interface VersioningVirtualModel extends InternalVirtualModel {

	def List<PropagatedChange> getAllResolvedPropagatedChanges()

	def List<PropagatedChange> getAllUnresolvedPropagatedChanges()

	def List<PropagatedChange> getAllUnresolvedPropagatedChangesSinceLastCommit()

	def List<PropagatedChange> getResolvedPropagatedChanges(VURI vuri)

	def List<PropagatedChange> getUnresolvedPropagatedChanges(VURI vuri)

	def List<PropagatedChange> getUnresolvedPropagatedChangesSinceLastCommit(VURI vuri)

	def List<PropagatedChange> propagateChange(VitruviusChange change, String changeId)

	def void forwardChanges(List<PropagatedChange> changes)

	def void setAllLastPropagatedChangeId(String id)

	def void setLastPropagatedChangeId(VURI vuri, String id)
}
