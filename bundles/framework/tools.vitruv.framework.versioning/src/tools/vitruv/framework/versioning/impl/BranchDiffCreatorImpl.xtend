package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.versioning.BranchDiffCreator

class BranchDiffCreatorImpl implements BranchDiffCreator {
	static final def BranchDiffCreator init() {
		new BranchDiffCreatorImpl
	}

	override createVersionDiff() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
