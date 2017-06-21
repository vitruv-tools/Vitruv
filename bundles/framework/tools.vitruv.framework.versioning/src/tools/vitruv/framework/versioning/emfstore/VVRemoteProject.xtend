package tools.vitruv.framework.versioning.emfstore

import tools.vitruv.framework.versioning.commit.Commit

interface VVRemoteProject extends VVProject {
	def VVLocalProject getLocalProject()

	def void setLocalProject(VVLocalProject localProject)

	def VVServer getServer()

	def void setServer(VVServer server)

	def VVLocalProject checkout(String name)

	def void delete()

	def void pushCommit(Commit lastCommit, Commit newCommit)

}
