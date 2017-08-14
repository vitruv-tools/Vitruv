package tools.vitruv.framework.vsum

import java.util.List

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.VURI

interface VersioningVirtualModel extends InternalVirtualModel {

	def List<Integer> getUserInteractionsSinceLastCommit()

	def List<Integer> getUserInteractionsSinceLastCommit(VURI vuri)

	def List<PropagatedChange> getAllResolvedPropagatedChanges()

	def List<PropagatedChange> getAllUnresolvedPropagatedChanges()

	def List<PropagatedChange> getAllUnresolvedPropagatedChangesSinceLastCommit()

	def List<PropagatedChange> getResolvedPropagatedChanges(VURI vuri)

	def List<PropagatedChange> getUnresolvedPropagatedChanges(VURI vuri)

	def List<PropagatedChange> getUnresolvedPropagatedChangesSinceLastCommit(VURI vuri)

	def List<PropagatedChange> propagateChange(VitruviusChange change, String changeId)

	def List<PropagatedChange> propagateChange(VURI vuri, VitruviusChange change, String changeId)

	def void forwardChanges(List<PropagatedChange> changes)

	def PropagatedChange getResolvedChange(String id)

	def void setAllLastPropagatedChangeId(String id)

	def void setLastPropagatedChangeId(VURI vuri, String id)

	/**
	 * Method to prevent the 'TUID management with several virtual models' error
	 * @see https://github.com/vitruv-tools/Vitruv/issues/114
	 * @author Patrick Stoeckle
	 */
	def void registerCorrespondenceModelToTUIDManager()

	/**
	 * Method to prevent the 'TUID management with several virtual models' error
	 * @see https://github.com/vitruv-tools/Vitruv/issues/114
	 * @author Patrick Stoeckle
	 */
	def void deregisterCorrespondenceModelFromTUIDManager()

}
