package tools.vitruv.framework.versioning

import java.util.List
import tools.vitruv.framework.change.echange.EChange

interface MultiChangeConflict extends Conflict {
	def EChange getSourceRepresentative()

	def EChange getTargetRepresentative()

	def List<EChange> getSourceChanges()

	def List<EChange> getTriggeredSourceChanges()

	def List<EChange> getTriggeredTargetChanges()

	def List<EChange> getTargetChanges()

}
