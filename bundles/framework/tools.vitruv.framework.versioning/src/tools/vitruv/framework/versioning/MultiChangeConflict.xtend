package tools.vitruv.framework.versioning

import java.util.List

interface MultiChangeConflict extends Conflict {
	def List<ChangeMatch> getSourceChanges()

	def List<ChangeMatch> getTargetChanges()
}
