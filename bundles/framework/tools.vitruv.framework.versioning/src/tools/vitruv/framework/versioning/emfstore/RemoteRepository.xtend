package tools.vitruv.framework.versioning.emfstore

import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException
import tools.vitruv.framework.versioning.branch.Branch
import java.util.Set
import java.util.List

interface RemoteRepository extends AbstractRepository {
	def Set<Branch> getBranches()

	def List<String> getIdentifiers(String branchName)

	def void pushCommit(Commit lastCommit, Commit newCommit)

	def void push(Commit commit, String branchName) throws CommitNotExceptedException

	def Commit pullCommit(String identifier, String branchName)
}
