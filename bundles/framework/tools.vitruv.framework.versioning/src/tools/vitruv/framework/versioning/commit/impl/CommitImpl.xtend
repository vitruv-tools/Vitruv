package tools.vitruv.framework.versioning.commit.impl

import java.util.List
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.CommitMessage
import tools.vitruv.framework.versioning.commit.MergeCommit
import tools.vitruv.framework.versioning.commit.SimpleCommit

@Data
abstract class CommitImpl implements Commit {
	List<EChange> changes

	CommitMessage commitmessage

	List<SimpleCommit> commitsBranchedFromThis

	List<MergeCommit> commitsMergedFromThis

	int identifier
}
