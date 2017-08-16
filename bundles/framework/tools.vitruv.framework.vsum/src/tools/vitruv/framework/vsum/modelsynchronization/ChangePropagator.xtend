package tools.vitruv.framework.vsum.modelsynchronization

import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.VURI

interface ChangePropagator {
	def void removePropagatedChange(VURI vuri, String id)

	def void addPropagatedChanges(VURI vuri, String id)

	def List<PropagatedChange> getAllResolvedPropagatedChanges()

	def List<PropagatedChange> getAllUnresolvedPropagatedChanges()

	def List<PropagatedChange> getResolvedPropagatedChanges(VURI vuri)

	def List<PropagatedChange> getUnresolvedPropagatedChanges(VURI vuri)

	def PropagatedChange getResolvedChange(String id)

	/**
	 * Propagates an already recorded {@link PropagatedChange} .
	 * @param propagatedChange the  
	 */
	def void propagateChange(PropagatedChange propagatedChange, VURI vuri)

	/**
	 * Resort changes and igores undos/redos.
	 * @param changelist of changes
	 * @return TODO
	 */
	def List<PropagatedChange> propagateChange(VitruviusChange change)

	def void addChangePropagationListener(ChangePropagationListener propagationListener)

	def void removeChangePropagationListener(ChangePropagationListener propagationListener)

	def List<PropagatedChange> propagateChange(VURI vuri, VitruviusChange change, String changeId)
}
