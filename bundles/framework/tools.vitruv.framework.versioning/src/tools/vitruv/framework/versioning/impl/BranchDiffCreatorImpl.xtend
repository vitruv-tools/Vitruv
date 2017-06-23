package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.versioning.BranchDiffCreator
import java.util.List
import tools.vitruv.framework.versioning.commit.ChangeMatch

class BranchDiffCreatorImpl implements BranchDiffCreator {
	static final def BranchDiffCreator init() {
		new BranchDiffCreatorImpl
	}

	override createVersionDiff(List<ChangeMatch> sourceChanges, List<ChangeMatch> targetChanges) {
		new BranchDiffImpl(sourceChanges, targetChanges)
	}

}
