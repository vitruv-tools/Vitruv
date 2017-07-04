package tools.vitruv.framework.versioning

import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.commit.ChangeMatch

interface SimpleChangeConflict extends Conflict {
	def ChangeMatch getSourceChange()

	def void setSourceChange(ChangeMatch value)

	def ChangeMatch getTargetChange()

	def void setTargetChange(ChangeMatch value)
}
