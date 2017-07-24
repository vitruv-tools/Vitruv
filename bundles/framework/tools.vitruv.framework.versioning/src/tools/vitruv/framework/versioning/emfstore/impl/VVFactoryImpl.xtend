package tools.vitruv.framework.versioning.emfstore.impl

import tools.vitruv.framework.versioning.emfstore.VVFactory

class VVFactoryImpl implements VVFactory {
	static def VVFactory init() {
		new VVFactoryImpl
	}

	private new() {
	}

	override createWorkspace() {
		new VVWorkspaceImpl
	}

	override createServer() {
		new VVServerImpl
	}

	override createRemoteProject() {
		new RemoteRepositoryImpl
	}

	override createLocalProject(String name) {
		new LocalRepositoryImpl(name)
	}

}
