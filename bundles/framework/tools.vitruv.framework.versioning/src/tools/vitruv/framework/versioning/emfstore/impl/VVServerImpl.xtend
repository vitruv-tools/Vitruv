package tools.vitruv.framework.versioning.emfstore.impl

import tools.vitruv.framework.versioning.emfstore.VVServer
import java.util.List
import tools.vitruv.framework.versioning.emfstore.VVRemoteProject
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.ArrayList

class VVServerImpl implements VVServer {
	@Accessors(PUBLIC_GETTER)
	List<VVRemoteProject> remoteProjects

	override login(String name) {
		new VVUsersessionImpl(name, this)
	}

	new() {
		remoteProjects = new ArrayList
	}
}
