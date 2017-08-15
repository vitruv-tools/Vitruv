package tools.vitruv.framework.vsum

import java.util.List

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.util.datatypes.VURI

interface VersioningVirtualModel extends InternalVirtualModel {

	def List<Integer> getUserInteractionsSinceLastCommit()

	def List<PropagatedChange> getAllResolvedPropagatedChanges()

	def List<PropagatedChange> getAllUnresolvedPropagatedChanges()

	def List<PropagatedChange> getAllUnresolvedPropagatedChangesSinceLastCommit()

	def List<PropagatedChange> getResolvedPropagatedChanges(VURI vuri)

	def List<PropagatedChange> getUnresolvedPropagatedChanges(VURI vuri)

	def List<PropagatedChange> getUnresolvedPropagatedChangesSinceLastCommit(VURI vuri)

	def void forwardChanges(List<PropagatedChange> changes)

	def PropagatedChange getResolvedChange(String id)

	def void setAllLastPropagatedChangeId(String id)

	def void setLastPropagatedChangeId(VURI vuri, String id)

	/**
	 * Propagates an already recorded {@link PropagatedChange} into the {@link VirtualModel}.
	 * @param propagatedChange the change to propagate.
	 */
	def void propagateChange(PropagatedChange propagatedChange)

}
