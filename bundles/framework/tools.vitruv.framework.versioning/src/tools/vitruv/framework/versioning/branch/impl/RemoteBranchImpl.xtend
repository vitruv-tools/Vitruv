package tools.vitruv.framework.versioning.branch.impl

import tools.vitruv.framework.versioning.branch.impl.BranchImpl
import tools.vitruv.framework.versioning.branch.RemoteBranch
import tools.vitruv.framework.versioning.emfstore.RemoteRepository
import org.eclipse.xtend.lib.annotations.Accessors

class RemoteBranchImpl extends BranchImpl implements RemoteBranch {
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	RemoteRepository remoteRepository

	new(String name) {
		super(name)
	}

}
