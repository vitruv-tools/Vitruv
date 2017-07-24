package tools.vitruv.framework.versioning.emfstore

import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException

interface RemoteRepository extends AbstractRepository {

	def void pushCommit(Commit lastCommit, Commit newCommit)

	def void push(Commit commit) throws CommitNotExceptedException
}
