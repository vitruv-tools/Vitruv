package tools.vitruv.framework.versioning.emfstore.impl

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.ListMultimap
import java.util.UUID
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.impl.LocalBranchImpl
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.CommitFactory
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.emfstore.AbstractRepository

class AbstractRepositoryImpl implements AbstractRepository {
	static protected extension CommitFactory = CommitFactory::instance
	@Accessors(PUBLIC_GETTER)
	val Branch masterBranch
	val ListMultimap<Branch, Commit> branchToCommit
	@Accessors(PUBLIC_GETTER)
	val String id
	@Accessors(PUBLIC_GETTER)
	val SimpleCommit initialCommit
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

	override getCommitsFrom(String from) {
		commits.dropWhile[id !== from].toList
	}

	override getCommits() {
		masterBranch.commits
	}

	override getCommits(Branch branch) {
		branchToCommit.get(branch)
	}


	protected def void addCommit(Commit c, Branch branch) {
		val lastCommit = branchToCommit.get(branch).last
		c.changes.forEach [ change |
			if (lastCommit.changes.exists[it === change])
				throw new IllegalStateException
		]
		branchToCommit.put(branch, c)
	}

	protected def void removeCommit(Commit c, Branch branch) {
		val lastCommit = branchToCommit.get(branch).last
		if (lastCommit.identifier == c.identifier)
			branchToCommit.remove(branch, c)
		else
			throw new IllegalStateException
	}

	protected def void addCommit(Commit c) {
		addCommit(c, masterBranch)
	}

}
