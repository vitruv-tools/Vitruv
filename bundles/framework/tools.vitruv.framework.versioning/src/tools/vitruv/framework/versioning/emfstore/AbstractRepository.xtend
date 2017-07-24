package tools.vitruv.framework.versioning.emfstore

import java.util.Set
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.commit.Commit
import java.util.List
import tools.vitruv.framework.versioning.commit.SimpleCommit

interface AbstractRepository {

	def Branch getCurrentBranch()

	def Commit getHead()

	def List<Commit> getCommits()

	def List<Commit> getCommitsFrom(String from)

	def Set<Branch> getBranches()

	def SimpleCommit getInitialCommit()

	def String getId()

	def String getName()

	def void createBranch(String name)

	def void setCurrentBranch(Branch branch)

	def void setName(String name)

}
