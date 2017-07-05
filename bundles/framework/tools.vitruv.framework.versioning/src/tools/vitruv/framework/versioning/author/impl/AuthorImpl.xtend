package tools.vitruv.framework.versioning.author.impl

import java.util.ArrayList
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.UserBranch
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.CommitFactory

class AuthorImpl implements Author {
	static extension CommitFactory = CommitFactory::instance
	@Accessors(PUBLIC_GETTER)
	val List<UserBranch> ownedBranches
	@Accessors(PUBLIC_GETTER)
	val List<Commit> commits
	@Accessors(PUBLIC_GETTER)
	val String name
	@Accessors(PUBLIC_GETTER)
	List<Branch> contributedBranches
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	String email

	override createSimpleCommit(String message, Commit parent, List<EChange> changes) {
		createSimpleCommit(changes, message, this, parent)
	}

	new(String name) {
		this.name = name
		commits = new ArrayList
		ownedBranches = new ArrayList

	}
}
