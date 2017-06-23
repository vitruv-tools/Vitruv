package tools.vitruv.framework.versioning.emfstore.impl

import tools.vitruv.framework.versioning.emfstore.VVWorkspaceProvider
import tools.vitruv.framework.versioning.emfstore.VVFactory
import tools.vitruv.framework.versioning.emfstore.VVWorkspace

class VVWorkspaceProviderImpl implements VVWorkspaceProvider {
	VVWorkspace currentWorkspace

	static def VVWorkspaceProvider init() {
		new VVWorkspaceProviderImpl
	}

	private new() {
	}

	override getWorkspace() {
		if (null === currentWorkspace)
			currentWorkspace = VVFactory::instance.createWorkspace
		return currentWorkspace
	}

}
