package tools.vitruv.framework.versioning.extensions.impl

import tools.vitruv.framework.versioning.extensions.EChangeConflictUtil
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.versioning.ConflictType
import tools.vitruv.framework.versioning.ConflictSolvability

class EChangeConflictUtilImpl implements EChangeConflictUtil {
	static def EChangeConflictUtil init() {
		new EChangeConflictUtilImpl
	}

	private new() {
	}

	override getConflictType(EChange e1, EChange e2) {
		determineConflictType(e1, e2)
	}

	override getConflictSolvability(EChange e1, EChange e2, ConflictType type) {
		determineConflictSolvability(e1, e2, type)
	}

	private static dispatch def determineConflictSolvability(EChange e1, EChange e2, ConflictType type) {
		ConflictSolvability::MANUAL
	}

	private static dispatch def determineConflictSolvability(ReplaceSingleValuedEAttribute<?, ?> e1,
		ReplaceSingleValuedEAttribute<?, ?> e2, ConflictType type) {
		ConflictSolvability::AUTOMATIC
	}

	private static dispatch def determineConflictSolvability(CreateAndInsertNonRoot<?, ?> e1,
		CreateAndInsertNonRoot<?, ?> e2, ConflictType type) {
		return if (type === ConflictType::INSERTING_IN_SAME_CONTANER)
			ConflictSolvability::AUTOMATIC
		else
			ConflictSolvability::MANUAL
	}

	private static dispatch def determineConflictType(EChange e1, EChange e2) {
		ConflictType::UNKNOWN
	}

	private static dispatch def determineConflictType(ReplaceSingleValuedEAttribute<?, ?> e1,
		ReplaceSingleValuedEAttribute<?, ?> e2) {
		ConflictType::UNKNOWN
	}

	private static dispatch def determineConflictType(CreateAndReplaceNonRoot<?, ?> e1,
		CreateAndReplaceNonRoot<?, ?> e2) {
		ConflictType::UNKNOWN
	}

	private static dispatch def determineConflictType(CreateAndInsertNonRoot<?, ?> e1,
		CreateAndInsertNonRoot<?, ?> e2) {
		ConflictType::INSERTING_IN_SAME_CONTANER
	}

}
