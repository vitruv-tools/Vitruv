package tools.vitruv.framework.versioning.emfstore.impl

import java.util.Set
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.emfstore.RemoteRepository
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException
import tools.vitruv.framework.versioning.exceptions.RemoteBranchNotFoundException
import tools.vitruv.framework.versioning.exceptions.NoSuchCommitException

class RemoteRepositoryImpl extends AbstractRepositoryImpl implements RemoteRepository {
	@Accessors(PUBLIC_GETTER)
	val Set<Branch> branches

	new() {
		branches = newHashSet(masterBranch)
	}

	override pushCommit(Commit lastCommit, Commit newCommit) {
		val currentLastCommit = commits.last
		if (currentLastCommit != lastCommit)
			throw new CommitNotExceptedException
		commits += newCommit
	}

	override getIdentifiers(String branchName) {
		val branch = branchName.branch
		val ids = branch.commits.map[identifier]
		return ids
	}

	private def Branch getBranch(String branchName) {
		val branch = branches.findFirst[name == branchName]
		if (null === branch)
			throw new RemoteBranchNotFoundException
		return branch
	}

	override push(Commit commit, String branchName) throws CommitNotExceptedException {
		val branch = branchName.branch
		val currentLastCommit = commits.last
		if (commit instanceof SimpleCommit) {
			if (currentLastCommit.identifier != commit.parent)
				throw new CommitNotExceptedException
		}
		addCommit(commit, branch)
		head = commit
	}

	override pullCommit(String id, String branchName) {
		val branch = branchName.branch
		val commit = branch.commits.findFirst[identifier == id]
		if (null === commit)
			throw new NoSuchCommitException
		return commit
	}

}
