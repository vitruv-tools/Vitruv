package tools.vitruv.framework.versioning

import java.util.List
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.impl.MultiChangeConflictImpl
import tools.vitruv.framework.util.datatypes.VURI

interface MultiChangeConflict extends Conflict {
	def EChange getSourceRepresentative()

	def EChange getTargetRepresentative()

	def List<EChange> getSourceChanges()

	def List<EChange> getTriggeredSourceChanges()

	def List<EChange> getTriggeredTargetChanges()

	def List<EChange> getTargetChanges()

	static def MultiChangeConflict createMultiChangeConflict(
		ConflictType type,
		ConflictSeverity solvability,
		EChange sourceRepresentative,
		EChange targetRepresentative,
		List<EChange> sourceChanges,
		List<EChange> targetChanges,
		VURI myVURI,
		VURI theirVURI,
		List<EChange> triggeredSourceChanges,
		List<EChange> triggeredTargetChanges
	) {
		new MultiChangeConflictImpl(type, solvability, myVURI, theirVURI, sourceRepresentative, targetRepresentative,
			sourceChanges, targetChanges, triggeredTargetChanges, triggeredTargetChanges)
	}
}
