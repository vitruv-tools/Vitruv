package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.versioning.BranchDiffCreator
import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange

class BranchDiffCreatorImpl implements BranchDiffCreator {
	static final def BranchDiffCreator init() {
		new BranchDiffCreatorImpl
	}

	override createVersionDiff(List<PropagatedChange> sourceChanges, List<PropagatedChange> targetChanges) {
		new BranchDiffImpl(sourceChanges, targetChanges)
	}

}
