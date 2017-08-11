package tools.vitruv.framework.versioning.branch.impl

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.branch.RemoteBranch
import tools.vitruv.framework.versioning.branch.impl.BranchImpl

class RemoteBranchImpl<T> extends BranchImpl implements RemoteBranch<T> {
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	T remoteRepository

	new(String name) {
		super(name)
	}

}
