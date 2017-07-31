package tools.vitruv.framework.versioning.commit

import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange

interface Commit {

	def CommitMessage getCommitmessage()

	def List<MergeCommit> getCommitsMergedFromThis()

	def List<PropagatedChange> getChanges()

	def int getNumberOfChanges()

	def List<SimpleCommit> getCommitsBranchedFromThis()

	def String getIdentifier()
}
