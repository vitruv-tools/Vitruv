package tools.vitruv.framework.versioning.commit

import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import java.util.Date

interface Commit {

	def CommitMessage getCommitmessage()

	def Date getDate()

	def List<MergeCommit> getCommitsMergedFromThis()

	def List<PropagatedChange> getChanges()

	def List<SimpleCommit> getCommitsBranchedFromThis()

	def String getIdentifier()
}
