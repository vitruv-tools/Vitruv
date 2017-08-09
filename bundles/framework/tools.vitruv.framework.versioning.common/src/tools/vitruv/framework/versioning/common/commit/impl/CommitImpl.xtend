package tools.vitruv.framework.versioning.common.commit.impl

import java.util.List

import org.eclipse.xtend.lib.annotations.Data

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.versioning.common.commit.Commit
import tools.vitruv.framework.versioning.common.commit.CommitMessage
import tools.vitruv.framework.versioning.common.commit.SimpleCommit
import tools.vitruv.framework.versioning.common.commit.MergeCommit

@Data
abstract class CommitImpl implements Commit {
	public static val CHANGES_KEY = "changes"
	public static val COMMITMESSAGE_KEY = "commitmessage"
	public static val IDENTIFIER_KEY = "identifier"
	public static val NUMBEROFCHANGES_KEY = "numberOfChanges"
	public static val USERINTERACTIONS_KEY = "userInteractions"

	List<PropagatedChange> changes

	int numberOfChanges

	CommitMessage commitmessage

	List<SimpleCommit> commitsBranchedFromThis

	List<MergeCommit> commitsMergedFromThis

	String identifier

	List<Integer> userInteractions
}
