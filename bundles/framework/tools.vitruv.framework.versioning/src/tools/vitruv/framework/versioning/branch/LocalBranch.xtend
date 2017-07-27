package tools.vitruv.framework.versioning.branch

import tools.vitruv.framework.versioning.branch.Branch

interface LocalBranch extends Branch {
	def RemoteBranch getRemoteBranch()

	def void setRemoteBranch(RemoteBranch branch)
}
