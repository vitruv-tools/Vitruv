package tools.vitruv.framework.versioning.branch

import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.emfstore.RemoteRepository

interface RemoteBranch extends Branch {
	def void setRemoteRepository(RemoteRepository remoteRepository)

	def RemoteRepository getRemoteRepository()
}
