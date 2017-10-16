package tools.vitruv.framework.versioning.mococo

import java.util.List
import java.util.Set
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.common.commit.Commit

interface RemoteRepository extends AbstractRepository {
	def Set<Branch> getBranches()

	def List<String> getIdentifiers(String branchName)

	def PushState pushCommit(Commit newCommit)

	def PushState push(Commit commit, String branchName)

	def Commit pullCommit(String identifier, String branchName)
}
