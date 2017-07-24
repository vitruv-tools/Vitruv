package tools.vitruv.framework.versioning.emfstore.impl

import java.util.Set
import java.util.UUID
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.CommitFactory
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.emfstore.AbstractRepository
import tools.vitruv.framework.versioning.branch.impl.BranchImpl
import com.google.common.collect.ListMultimap
import com.google.common.collect.ArrayListMultimap

abstract class AbstractRepositoryImpl implements AbstractRepository {
	static protected extension CommitFactory = CommitFactory::instance

	val ListMultimap<Branch, Commit> branchToCommit
	@Accessors(PUBLIC_GETTER)
	val String id
	@Accessors(PUBLIC_GETTER)
	val SimpleCommit initialCommit
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	String name
	@Accessors(PUBLIC_GETTER)
	val Set<Branch> branches

	@Accessors(PUBLIC_GETTER)
	protected Commit head

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	Branch currentBranch

	new() {
		branches = newHashSet
		val Branch masterBranch = new BranchImpl("master")
		id = UUID.randomUUID.toString
		initialCommit = createInitialCommit
		branchToCommit = ArrayListMultimap::create
		branchToCommit.put(masterBranch, initialCommit)
		head = initialCommit
	}

	override getCommitsFrom(String from) {
		commits.dropWhile[id !== from].toList
	}

	override createBranch(String currentName) {
		val newBranch = new BranchImpl(currentName)
		branches += newBranch
		currentBranch = newBranch
	}

	override getCommits() {
		branchToCommit.get(currentBranch)
	}

}
