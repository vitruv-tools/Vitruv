package tools.vitruv.framework.versioning.commit.impl

import tools.vitruv.framework.versioning.commit.InitialCommit
import java.util.List
import tools.vitruv.framework.versioning.commit.CommitMessage
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.commit.MergeCommit
import tools.vitruv.framework.change.description.PropagatedChange

class InitialCommitImpl extends CommitImpl implements InitialCommit {
	new(List<PropagatedChange> changes, CommitMessage commitmessage, List<SimpleCommit> commitsBranchedFromThis,
		List<MergeCommit> commitsMergedFromThis, int identifier) {
		super(changes, commitmessage, commitsBranchedFromThis, commitsMergedFromThis, identifier)
	}
}
