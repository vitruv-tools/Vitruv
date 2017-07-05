package tools.vitruv.framework.versioning.author

import java.util.List
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.Named
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.UserBranch
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.author.impl.AuthorImpl

interface Author extends Named {
	static def Author createAuthor(String name) {
		new AuthorImpl(name)
	}

	def String getEmail()

	def void setEmail(String value)

	def List<UserBranch> getOwnedBranches()

	def List<Branch> getContributedBranches()

	def List<Commit> getCommits()

//	def Repository getCurrentRepository()
//	def void setCurrentRepository(Repository value)
//
//	def Branch getCurrentBranch()
//	def void setCurrentBranch(Branch value)
	def SimpleCommit createSimpleCommit(String message, Commit parent, List<EChange> changes)

//	def UserBranch createBranch(String branchName)
//
//	def void switchToBranch(Branch targetBranch)
//
//	def void switchToRepository(Repository repository)
}
