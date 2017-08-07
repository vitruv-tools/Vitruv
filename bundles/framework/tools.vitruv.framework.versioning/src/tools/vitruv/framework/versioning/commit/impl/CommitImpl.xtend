package tools.vitruv.framework.versioning.commit.impl

import java.util.List

import org.eclipse.xtend.lib.annotations.Data

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.CommitMessage
import tools.vitruv.framework.versioning.commit.MergeCommit
import tools.vitruv.framework.versioning.commit.SimpleCommit

@Data
abstract class CommitImpl implements Commit {
	public static val CHANGES_KEY = "changes"
	public static val COMMITMESSAGE_KEY = "commitmessage"
	public static val IDENTIFIER_KEY = "identifier"
	public static val NUMBEROFCHANGES_KEY = "numberOfChanges"
	List<PropagatedChange> changes

	int numberOfChanges

	CommitMessage commitmessage

	List<SimpleCommit> commitsBranchedFromThis

	List<MergeCommit> commitsMergedFromThis

	String identifier

}
