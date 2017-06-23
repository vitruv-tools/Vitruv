package tools.vitruv.framework.versioning.emfstore.impl

import tools.vitruv.framework.versioning.emfstore.VVFactory

class VVFactoryImpl implements VVFactory {
	static def VVFactory init() {
		new VVFactoryImpl
	}

	override createWorkspace() {
		new VVWorkspaceImpl
	}

	override createServer() {
		new VVServerImpl
	}

	override createLocalProject() {
		new VVLocalProjectImpl
	}

	override createRemoteProject() {
		new VVRemoteProjectImpl
	}

}
