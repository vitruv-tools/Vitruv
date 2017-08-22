package tools.vitruv.framework.vsum

import java.util.List

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.VURI

interface InternalTestVersioningVirtualModel extends VersioningVirtualModel {
	/**
	 * Adds a {@link VURI} pair. This indicates that changes recorded under one of them 
	 * should be returned also when asking for changes in the other.
	 * Example: my_repository.uml <-> their_repository.uml
	 * @param vuri1 the first VURI
	 * @param vuri2 the second VURI
	 */
	def void addMappedVURIs(VURI vuri1, VURI vuri2)

	/**
	 * Adds a {@link VURI} pair. This indicates that changes recorded under one of them 
	 * should be returned also when asking for changes in the other.
	 * Example: my_repository.uml <-> my_repository.repository
	 * @param vuri1 the first VURI
	 * @param vuri2 the second VURI
	 */
	def void addTriggeredRelation(VURI source, VURI target)

	def VURI getMappedVURI(VURI vuri)

	def List<Integer> getUserInteractionsSinceLastCommit()

	def List<PropagatedChange> getAllResolvedPropagatedChanges()

	def List<PropagatedChange> getResolvedPropagatedChanges(VURI vuri)

	def List<PropagatedChange> getUnresolvedPropagatedChanges(VURI vuri)

	def List<PropagatedChange> getUnresolvedPropagatedChangesSinceLastCommit(VURI vuri)

	def void forwardChanges(List<PropagatedChange> changes)

	def PropagatedChange getResolvedChange(String id)

	def void setLastPropagatedChangeId(VURI vuri, String id)

	def List<PropagatedChange> propagateChange(VURI vuri, VitruviusChange change, String changeId)

	/**
	 * Propagates an already recorded {@link PropagatedChange} into the {@link VirtualModel}.
	 * @param propagatedChange the change to propagate.
	 * @param vuri the vuri, where the change should be saved
	 */
	def void propagateChange(PropagatedChange propagatedChange, VURI vuri)
}
