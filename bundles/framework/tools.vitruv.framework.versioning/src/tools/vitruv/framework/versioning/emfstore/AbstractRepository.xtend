package tools.vitruv.framework.versioning.emfstore

import java.util.Set
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.InitialCommit
import java.util.List

interface AbstractRepository {
	def Commit getHead()

	def List<Commit> getCommits()

	def InitialCommit getInitialCommit()

	def Set<Branch> getBranches()

	def String getId()

	def String getName()

	def void setName(String name)
}
