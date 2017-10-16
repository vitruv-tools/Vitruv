package tools.vitruv.framework.versioning.mococo.impl

import java.util.Set
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.mococo.RemoteRepository
import tools.vitruv.framework.versioning.exceptions.RemoteBranchNotFoundException
import tools.vitruv.framework.versioning.exceptions.NoSuchCommitException
import tools.vitruv.framework.versioning.mococo.PushState
import tools.vitruv.framework.versioning.common.commit.Commit
import tools.vitruv.framework.versioning.common.commit.SimpleCommit
import tools.vitruv.framework.versioning.common.commit.MergeCommit

class RemoteRepositoryImpl extends AbstractRepositoryImpl implements RemoteRepository {
	@Accessors(PUBLIC_GETTER)
	val Set<Branch> branches

	new() {
		super()
		branches = newHashSet(masterBranch)
	}

	override pushCommit(Commit newCommit) {
		val currentLastCommit = commits.last
		if (newCommit instanceof SimpleCommit) {
			if (currentLastCommit.identifier != newCommit.parent)
				return PushState::COMMIT_NOT_ACCEPTED
		}
		if (newCommit instanceof MergeCommit) {
			if (newCommit.sourceCommit != currentLastCommit.identifier &&
				newCommit.targetCommit != currentLastCommit.identifier)
				return PushState::COMMIT_NOT_ACCEPTED
		}

		commits += newCommit
		return PushState::SUCCESS
	}

	override getIdentifiers(String branchName) {
		val branch = branchName.branch
		val commits = branch.commits
		val ids = commits.map[identifier]
		return ids
	}

	override push(Commit commit, String branchName) {
		val branch = branchName.branch
		val currentLastCommit = commits.last
		if (commit instanceof SimpleCommit) {
			if (currentLastCommit.identifier != commit.parent)
				return PushState::COMMIT_NOT_ACCEPTED
		}
		addCommit(commit, branch)
		head = commit
		return PushState::SUCCESS
	}

	override pullCommit(String id, String branchName) {
		val branch = branchName.branch
		val commit = branch.commits.findFirst[identifier == id]
		if (null === commit)
			throw new NoSuchCommitException
		return commit
	}

	private def Branch getBranch(String branchName) {
		val branch = branches.findFirst[name == branchName]
		if (null === branch)
			throw new RemoteBranchNotFoundException
		return branch
	}
}
