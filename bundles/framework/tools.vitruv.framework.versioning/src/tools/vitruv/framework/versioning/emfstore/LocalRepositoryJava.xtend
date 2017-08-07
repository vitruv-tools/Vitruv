package tools.vitruv.framework.versioning.emfstore

import java.util.List
import tools.vitruv.framework.versioning.branch.LocalBranch
import tools.vitruv.framework.versioning.branch.RemoteBranch

interface LocalRepositoryJava extends LocalRepository {
	def RemoteRepository getRemoteProject()

	def void setRemoteProject(RemoteRepository remote)

	def void addOrigin(LocalBranch branch, RemoteRepository remoteRepository)

	def void addRemoteRepository(RemoteRepository remoteRepository)

	def List<RemoteBranch> getRemoteBranches()

	def RemoteBranch createRemoteBranch(String name, RemoteRepository remoteRepository)
}
