package tools.vitruv.framework.vsum

import java.util.List

import tools.vitruv.framework.change.description.PropagatedChange

interface VersioningVirtualModel extends InternalVirtualModel {
	/**
	 * Defines, which id of a {@link PropagatedChange} was last included in a commit.
	 * @param id the id of the last PropagatedChange included in a commit
	 */
	def void setAllLastPropagatedChangeId(String id)

	def List<PropagatedChange> getAllUnresolvedPropagatedChanges()

	def List<PropagatedChange> getAllUnresolvedPropagatedChangesSinceLastCommit()

	/**
	 * @see propagateChange(PropagatedChange propagatedChange, VURI vuri)
	 */
	def void propagateChange(PropagatedChange propagatedChange)
}
