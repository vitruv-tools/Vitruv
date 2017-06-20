package tools.vitruv.framework.versioning.emfstore

import tools.vitruv.framework.versioning.emfstore.impl.VVWorkspaceProviderImpl

interface VVWorkspaceProvider {
	VVWorkspaceProvider instance = VVWorkspaceProviderImpl::init
	
	def VVWorkspace getWorkspace()
}
