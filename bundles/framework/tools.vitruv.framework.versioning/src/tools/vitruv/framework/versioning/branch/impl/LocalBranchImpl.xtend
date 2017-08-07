package tools.vitruv.framework.versioning.branch.impl

import tools.vitruv.framework.versioning.branch.impl.BranchImpl
import tools.vitruv.framework.versioning.branch.LocalBranch
import tools.vitruv.framework.versioning.branch.RemoteBranch
import org.eclipse.xtend.lib.annotations.Accessors

class LocalBranchImpl<T> extends BranchImpl implements LocalBranch<T> {
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	RemoteBranch<T> remoteBranch

	new(String name) {
		super(name)
	}

}
