package tools.vitruv.framework.versioning.emfstore

import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException

interface VVRemoteProject extends AbstractRepository {
	def LocalRepository getLocalProject()

	def void setLocalProject(LocalRepository localProject)

	def VVServer getServer()

	def void setServer(VVServer server)

	def LocalRepository checkout(String name)

	def void delete()

	def void pushCommit(Commit lastCommit, Commit newCommit)throws CommitNotExceptedException

}
