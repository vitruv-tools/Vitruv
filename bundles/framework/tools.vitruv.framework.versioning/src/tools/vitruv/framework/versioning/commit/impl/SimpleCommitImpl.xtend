package tools.vitruv.framework.versioning.commit.impl

import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.commit.impl.CommitImpl
import tools.vitruv.framework.versioning.commit.Commit

@Data
class SimpleCommitImpl extends CommitImpl implements SimpleCommit {
	Commit parent

	override isInitialCommit() {
		null !== parent
	}
}
