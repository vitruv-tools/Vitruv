package tools.vitruv.framework.versioning.emfstore.impl

import tools.vitruv.framework.versioning.emfstore.VVServer
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.ArrayList
import tools.vitruv.framework.versioning.emfstore.RemoteRepository

class VVServerImpl implements VVServer {
	@Accessors(PUBLIC_GETTER)
	List<RemoteRepository> remoteProjects

	override login(String name) {
		new VVUsersessionImpl(name, this)
	}

	new() {
		remoteProjects = new ArrayList
	}
}
