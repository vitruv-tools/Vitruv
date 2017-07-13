package tools.vitruv.framework.vsum.modelsynchronization

import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange

interface ChangePropagator {
	/** 
	 * Resort changes and igores undos/redos.
	 * @param changelist of changes
	 * @return TODO
	 */
	def List<PropagatedChange> propagateChange(VitruviusChange change)

	def void addChangePropagationListener(ChangePropagationListener propagationListener)

	def void removeChangePropagationListener(ChangePropagationListener propagationListener)

}
