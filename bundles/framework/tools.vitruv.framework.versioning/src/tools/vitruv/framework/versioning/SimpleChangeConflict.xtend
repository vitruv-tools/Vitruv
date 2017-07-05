package tools.vitruv.framework.versioning

import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.impl.SimpleChangeConflictImpl

interface SimpleChangeConflict extends Conflict {
	static def SimpleChangeConflict createSimpleChangeConflict(ConflictType type, ConflictSolvability solvability,
		EChange sourceChange, EChange targetChange) {
		new SimpleChangeConflictImpl(type, solvability, sourceChange, targetChange)
	}

	def EChange getSourceChange()

	def EChange getTargetChange()

}
