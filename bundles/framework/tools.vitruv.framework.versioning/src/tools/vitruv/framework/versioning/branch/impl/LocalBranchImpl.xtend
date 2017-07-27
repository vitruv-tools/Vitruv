package tools.vitruv.framework.versioning.branch.impl

import tools.vitruv.framework.versioning.branch.impl.BranchImpl
import tools.vitruv.framework.versioning.branch.LocalBranch
import tools.vitruv.framework.versioning.branch.RemoteBranch
import org.eclipse.xtend.lib.annotations.Accessors

class LocalBranchImpl extends BranchImpl implements LocalBranch {
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	RemoteBranch remoteBranch

	new(String name) {
		super(name)
	}

}
