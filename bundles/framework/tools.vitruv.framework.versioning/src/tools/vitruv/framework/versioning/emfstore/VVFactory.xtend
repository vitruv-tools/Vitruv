package tools.vitruv.framework.versioning.emfstore

import tools.vitruv.framework.versioning.emfstore.impl.VVFactoryImpl

interface VVFactory {
	VVFactory instance = VVFactoryImpl::init

	def VVWorkspace createWorkspace()

	def VVServer createServer()

	def LocalRepository createLocalProject(String name)

	def RemoteRepository createRemoteProject()
}
