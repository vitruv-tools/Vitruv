package tools.vitruv.framework.versioning.mococo

import tools.vitruv.framework.versioning.branch.Branch
import java.util.List
import tools.vitruv.framework.versioning.common.commit.Commit
import tools.vitruv.framework.versioning.common.commit.SimpleCommit

interface AbstractRepository {
	def Branch getMasterBranch()

	def Commit getHead()

	def List<Commit> getCommits()

	def List<Commit> getCommits(Branch branch)

	def List<Commit> getCommitsFrom(String from)

	def SimpleCommit getInitialCommit()

	def String getId()

	def String getName()

	def void setName(String name)

}
