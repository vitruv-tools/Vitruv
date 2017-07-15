package tools.vitruv.framework.versioning

import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.impl.SimpleChangeConflictImpl
import tools.vitruv.framework.util.datatypes.VURI

interface SimpleChangeConflict extends Conflict {
	static def SimpleChangeConflict createSimpleChangeConflict(ConflictType type, ConflictSeverity solvability,
		EChange sourceChange, EChange targetChange, VURI myVURI, VURI theirVURI) {
		new SimpleChangeConflictImpl(type, solvability, myVURI, theirVURI, sourceChange, targetChange)
	}

	def EChange getSourceChange()

	def EChange getTargetChange()

}
