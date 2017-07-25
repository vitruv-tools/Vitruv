package tools.vitruv.framework.versioning.branch.impl

import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.UserBranch
import tools.vitruv.framework.versioning.commit.Commit
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.Set

class BranchImpl implements Branch {
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	Commit currentHeadCommit
	@Accessors(PUBLIC_GETTER)
	val Set<Author> contributors
	@Accessors(PUBLIC_GETTER)
	val Set<UserBranch> childBranches
	@Accessors(PUBLIC_GETTER)
	val String name

	new(String name) {
		this.name = name
		contributors = newHashSet
		childBranches = newHashSet
	}

	override isMasterBranch() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
