package tools.vitruv.framework.vsum

import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.util.datatypes.VURI

interface VersioningVirtualModel extends InternalVirtualModel {
	def void setLastPropagatedChangeId(VURI vuri, String id)

	def List<PropagatedChange> getUnresolvedPropagatedChangesSinceLastCommit(VURI vuri)

	def List<PropagatedChange> getResolvedPropagatedChanges(VURI vuri)

	def List<PropagatedChange> getUnresolvedPropagatedChanges(VURI vuri)

	def void forwardChanges(List<PropagatedChange> changes)
}
