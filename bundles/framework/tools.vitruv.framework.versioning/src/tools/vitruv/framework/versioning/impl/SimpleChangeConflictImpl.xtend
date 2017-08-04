package tools.vitruv.framework.versioning.impl

import org.eclipse.xtend.lib.annotations.Data

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.SimpleChangeConflict

@Data
class SimpleChangeConflictImpl extends ConflictImpl implements SimpleChangeConflict {
	EChange sourceChange
	EChange targetChange

	override getSourceDefaultSolution() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override getTriggeredDefaultSolution() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
