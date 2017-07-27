package tools.vitruv.framework.versioning.branch

import tools.vitruv.framework.versioning.Named
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.commit.Commit
import java.util.Set

interface Branch extends Named {
	def Commit getCurrentHeadCommit()

	def void setCurrentHeadCommit(Commit value)

	def Set<Author> getContributors()

	def Set<UserBranch> getChildBranches()

	def boolean isMasterBranch()
}
