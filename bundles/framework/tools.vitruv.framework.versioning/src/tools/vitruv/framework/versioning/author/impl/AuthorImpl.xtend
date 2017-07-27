package tools.vitruv.framework.versioning.author.impl

import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.UserBranch
import tools.vitruv.framework.versioning.commit.Commit
import java.util.Set

class AuthorImpl implements Author {
	@Accessors(PUBLIC_GETTER)
	val Set<UserBranch> ownedBranches
	@Accessors(PUBLIC_GETTER)
	val List<Commit> commits
	@Accessors(PUBLIC_GETTER)
	val String name
	@Accessors(PUBLIC_GETTER)
	Set<Branch> contributedBranches
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	String email

	new(String name) {
		this.name = name
		commits = newArrayList
		ownedBranches = newHashSet

	}
}
