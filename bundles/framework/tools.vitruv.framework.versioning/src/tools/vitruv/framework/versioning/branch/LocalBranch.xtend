package tools.vitruv.framework.versioning.branch

import tools.vitruv.framework.versioning.branch.Branch

interface LocalBranch<T> extends Branch {
	def RemoteBranch<T> getRemoteBranch()

	def void setRemoteBranch(RemoteBranch<T> branch)
}
