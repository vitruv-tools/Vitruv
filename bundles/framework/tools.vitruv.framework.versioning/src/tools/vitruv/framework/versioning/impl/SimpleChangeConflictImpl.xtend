package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.versioning.impl.ConflictImpl
import tools.vitruv.framework.versioning.SimpleChangeConflict
import tools.vitruv.framework.versioning.ConflictType
import tools.vitruv.framework.versioning.ConflictSolvability
import org.eclipse.emf.common.util.EList
import tools.vitruv.framework.versioning.ChangeMatch
import tools.vitruv.framework.change.echange.EChange
import org.eclipse.xtend.lib.annotations.Accessors

class SimpleChangeConflictImpl extends ConflictImpl implements SimpleChangeConflict {
	@Accessors(PUBLIC_GETTER)
	EChange sourceChange
	@Accessors(PUBLIC_GETTER)
	EChange targetChange

	new(ConflictType type, ConflictSolvability solvability, EChange sourceChange, EChange targetChange) {
		super(type, solvability)
		this.sourceChange = sourceChange
		this.targetChange = targetChange
	}

	override resolveConflict(EList<ChangeMatch> acceptedLocalChangeMatches,
		EList<ChangeMatch> rejectedRemoteOperations) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
