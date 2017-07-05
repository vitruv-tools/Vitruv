package tools.vitruv.framework.versioning.emfstore.impl

import java.util.ArrayList
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.emfstore.VVFactory
import tools.vitruv.framework.versioning.emfstore.VVLocalProject
import tools.vitruv.framework.versioning.emfstore.VVServer
import tools.vitruv.framework.versioning.emfstore.VVWorkspace

class VVWorkspaceImpl implements VVWorkspace {
	static extension VVFactory v = VVFactory::instance
	@Accessors(PUBLIC_GETTER)
	List<VVServer> servers
	@Accessors(PUBLIC_GETTER)
	List<VVLocalProject> localProjects

	new() {
		servers = new ArrayList
		localProjects = new ArrayList
	}

	override createLocalProject(String name) {
		val localProject = v.createLocalProject(name)
		localProjects += localProject
		return localProject
	}

	override addServer(VVServer server) {
		servers += server
	}

	override removeServer(VVServer server) {
		servers -= server
	}

}
