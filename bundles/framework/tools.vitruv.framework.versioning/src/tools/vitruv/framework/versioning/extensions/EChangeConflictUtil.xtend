package tools.vitruv.framework.versioning.extensions

import tools.vitruv.framework.versioning.extensions.impl.EChangeConflictUtilImpl
import tools.vitruv.framework.versioning.ConflictType
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.ConflictSolvability

interface EChangeConflictUtil {
	static EChangeConflictUtil instance = EChangeConflictUtilImpl::init

	def ConflictType getConflictType(EChange e1, EChange e2)

	def ConflictSolvability getConflictSolvability(EChange e1, EChange e2, ConflictType type)
}
