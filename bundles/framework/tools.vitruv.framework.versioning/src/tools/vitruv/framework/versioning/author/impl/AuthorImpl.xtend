package tools.vitruv.framework.versioning.author.impl

import java.util.List
import java.util.Set

import org.eclipse.xtend.lib.annotations.Accessors

import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.UserBranch
import tools.vitruv.framework.versioning.common.commit.Commit

class AuthorImpl implements Author {
	@Accessors(PUBLIC_GETTER)
	val Set<UserBranch> ownedBranches
	@Accessors(PUBLIC_GETTER)
	val List<Commit> commits
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	String name
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	Set<Branch> contributedBranches
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	String email

	new() {
		this("")
	}

	new(String name) {
		this(name, "")
	}

	new(String name, String email) {
		this.name = name
		this.email = email
		commits = newArrayList
		ownedBranches = newHashSet
	}

}
