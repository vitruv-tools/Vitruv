package tools.vitruv.framework.versioning.emfstore.impl

import java.util.List
import java.util.Set
import java.util.UUID
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.CommitFactory
import tools.vitruv.framework.versioning.commit.InitialCommit
import tools.vitruv.framework.versioning.emfstore.AbstractRepository

abstract class AbstractRepositoryImpl implements AbstractRepository {
	static protected extension CommitFactory = CommitFactory::instance
	@Accessors(PUBLIC_GETTER)
	protected val List<Commit> commits

	@Accessors(PUBLIC_GETTER)
	val String id
	@Accessors(PUBLIC_GETTER)
	val InitialCommit initialCommit
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	String name
	@Accessors(PUBLIC_GETTER)
	val Set<Branch> branches

	@Accessors(PUBLIC_GETTER)
	protected Commit head

	new() {
		branches = newHashSet
		id = UUID.randomUUID.toString
		initialCommit = createInitialCommit
		commits = newArrayList(initialCommit)
		head = initialCommit
	}
}
