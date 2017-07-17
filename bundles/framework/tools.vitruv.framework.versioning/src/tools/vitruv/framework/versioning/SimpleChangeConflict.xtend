package tools.vitruv.framework.versioning

import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.change.echange.EChange

interface SimpleChangeConflict extends Conflict {

	def EChange getSourceChange()

	def EChange getTargetChange()

}
