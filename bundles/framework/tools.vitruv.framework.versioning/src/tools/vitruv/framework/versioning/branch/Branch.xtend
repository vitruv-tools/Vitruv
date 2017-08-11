package tools.vitruv.framework.versioning.branch

import java.util.Set

import tools.vitruv.framework.versioning.Named
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.common.commit.Commit

interface Branch extends Named {
	def Commit getCurrentHeadCommit()

	def void setCurrentHeadCommit(Commit value)

	def Set<Author> getContributors()

	def Set<UserBranch> getChildBranches()

	def boolean isMasterBranch()
}
