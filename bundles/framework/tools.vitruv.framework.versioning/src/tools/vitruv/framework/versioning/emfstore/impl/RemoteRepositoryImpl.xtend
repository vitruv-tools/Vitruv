package tools.vitruv.framework.versioning.emfstore.impl

import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.emfstore.RemoteRepository
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException

class RemoteRepositoryImpl extends AbstractRepositoryImpl implements RemoteRepository {

	override pushCommit(Commit lastCommit, Commit newCommit) {
		val currentLastCommit = commits.last
		if (currentLastCommit != lastCommit)
			throw new CommitNotExceptedException
		commits += newCommit
	}

	override push(Commit commit) throws CommitNotExceptedException {
		val currentLastCommit = commits.last
		if (commit instanceof SimpleCommit) {
			if (currentLastCommit != commit.parent)
				throw new CommitNotExceptedException
		}
		commits += commit
		head = commit
	}

}
