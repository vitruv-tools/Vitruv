package tools.vitruv.framework.versioning.commit.impl

import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.commit.impl.CommitImpl

@Data
class SimpleCommitImpl extends CommitImpl implements SimpleCommit {
	String parent

	override isInitialCommit() {
		null !== parent
	}
}
