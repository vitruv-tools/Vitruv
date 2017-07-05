package tools.vitruv.framework.versioning

import java.util.List
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.impl.MultiChangeConflictImpl

interface MultiChangeConflict extends Conflict {
	def EChange getSourceRepresentative()

	def EChange getTargetRepresentative()

	def List<EChange> getSourceChanges()

	def List<EChange> getTargetChanges()

	static def MultiChangeConflict createMultiChangeConflict(ConflictType type, ConflictSolvability solvability,
		EChange sourceRepresentative, EChange targetRepresentative, List<EChange> sourceChanges,
		List<EChange> targetChanges) {
			new MultiChangeConflictImpl(type, solvability, sourceRepresentative, targetRepresentative, sourceChanges,
				targetChanges)
		}
	}
	