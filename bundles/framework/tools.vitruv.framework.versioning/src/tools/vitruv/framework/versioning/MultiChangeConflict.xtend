package tools.vitruv.framework.versioning

import java.util.List
import tools.vitruv.framework.versioning.commit.ChangeMatch

interface MultiChangeConflict extends Conflict {
	def List<ChangeMatch> getSourceChanges()

	def List<ChangeMatch> getTargetChanges()
}
