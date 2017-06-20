package tools.vitruv.framework.versioning.emfstore.impl

import tools.vitruv.framework.versioning.emfstore.VVWorkspaceProvider
import tools.vitruv.framework.versioning.emfstore.VVFactory

class VVWorkspaceProviderImpl implements VVWorkspaceProvider {
	static def VVWorkspaceProvider init() {
		new VVWorkspaceProviderImpl
	}

	override getWorkspace() {
		VVFactory::instance.createWorkspace
	}

}
