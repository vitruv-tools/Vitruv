package tools.vitruv.framework.versioning

import tools.vitruv.framework.change.echange.EChange

interface ConflictDetectionStrategy {
	def boolean conflicts(EChange e1, EChange e2)

	def ConflictType getConflictType(EChange e1, EChange e2)

	def ConflictSeverity getConflictSolvability(EChange e1, EChange e2, ConflictType type)
}
