package tools.vitruv.framework.versioning.impl

import org.eclipse.emf.common.util.EList
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.ChangeMatch
import tools.vitruv.framework.versioning.SimpleChangeConflict

@Data
class SimpleChangeConflictImpl extends ConflictImpl implements SimpleChangeConflict {
	EChange sourceChange
	EChange targetChange

	override resolveConflict(EList<ChangeMatch> acceptedLocalChangeMatches,
		EList<ChangeMatch> rejectedRemoteOperations) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override getDefaultSolution() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
