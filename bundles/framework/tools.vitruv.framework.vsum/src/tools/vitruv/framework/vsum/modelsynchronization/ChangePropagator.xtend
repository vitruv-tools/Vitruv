package tools.vitruv.framework.vsum.modelsynchronization

import java.util.List
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationListener

interface ChangePropagator {
	/** 
	 * Resort changes and igores undos/redos.
	 * @param changelist of changes
	 * @return TODO
	 */
	def List<List<VitruviusChange>> propagateChange(VitruviusChange change)

	def void addChangePropagationListener(ChangePropagationListener propagationListener)

	def void removeChangePropagationListener(ChangePropagationListener propagationListener)

}
