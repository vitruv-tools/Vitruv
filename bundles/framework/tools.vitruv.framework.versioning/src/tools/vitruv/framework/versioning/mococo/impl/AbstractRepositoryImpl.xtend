package tools.vitruv.framework.versioning.mococo.impl

import java.util.UUID

import org.eclipse.xtend.lib.annotations.Accessors

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.ListMultimap

import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.impl.LocalBranchImpl
import tools.vitruv.framework.versioning.common.commit.Commit
import tools.vitruv.framework.versioning.common.commit.CommitFactory
import tools.vitruv.framework.versioning.common.commit.SimpleCommit
import tools.vitruv.framework.versioning.mococo.AbstractRepository

class AbstractRepositoryImpl implements AbstractRepository {
	// Extensions.
	static protected extension CommitFactory = CommitFactory::instance

	// Values.
	@Accessors(PUBLIC_GETTER)
	val Branch masterBranch

	@Accessors(PUBLIC_GETTER)
	val String id

	@Accessors(PUBLIC_GETTER)
	val SimpleCommit initialCommit

	val ListMultimap<Branch, Commit> branchToCommit

	// Variables.
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	String name

	@Accessors(PUBLIC_GETTER)
	protected Commit head

	new() {
		masterBranch = new LocalBranchImpl("master")
		id = UUID.randomUUID.toString
		initialCommit = createInitialCommit
		branchToCommit = ArrayListMultimap::create
		branchToCommit.put(masterBranch, initialCommit)
		head = initialCommit
	}

	// Overridden methods.
	override getCommitsFrom(String from) {
		commits.dropWhile[id !== from].toList
	}

	override getCommits() {
		masterBranch.commits
	}

	override getCommits(Branch branch) {
		branchToCommit.get(branch)
	}

	// Protected methods.
	protected def void addCommit(Commit c, Branch branch) {
		val lastCommit = branchToCommit.get(branch).last
		if(null !== lastCommit && lastCommit.numberOfChanges !== lastCommit.changes.length)
			throw new IllegalStateException
		c.changes.forEach [ change |
			if(null !== lastCommit && lastCommit.changes.exists[it === change])
				throw new IllegalStateException
		]
		val otherCommit = branchToCommit.get(branch).findFirst[identifier == c.identifier]
		if(otherCommit === null)
			branchToCommit.put(branch, c)
	}

	protected def void removeCommit(Commit c, Branch branch) {
		val lastCommit = branchToCommit.get(branch).last
		if(lastCommit.identifier == c.identifier)
			branchToCommit.remove(branch, c)
		else
			throw new IllegalStateException
	}

	protected def void addCommit(Commit c) {
		addCommit(c, masterBranch)
	}

}
