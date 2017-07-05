package tools.vitruv.framework.versioning.commit.impl

import tools.vitruv.framework.versioning.commit.InitialCommit
import java.util.List
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.commit.CommitMessage
import tools.vitruv.framework.versioning.commit.SimpleCommit
import tools.vitruv.framework.versioning.commit.MergeCommit

class InitialCommitImpl extends CommitImpl implements InitialCommit {
	new(List<EChange> changes, CommitMessage commitmessage, List<SimpleCommit> commitsBranchedFromThis,
		List<MergeCommit> commitsMergedFromThis, int identifier) {
		super(changes, commitmessage, commitsBranchedFromThis, commitsMergedFromThis, identifier)
	}
}
