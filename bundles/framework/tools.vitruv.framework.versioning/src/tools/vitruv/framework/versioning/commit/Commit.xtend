package tools.vitruv.framework.versioning.commit

import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange

interface Commit {

	def List<PropagatedChange> getChanges()

	def CommitMessage getCommitmessage()

	def List<SimpleCommit> getCommitsBranchedFromThis()

	def List<MergeCommit> getCommitsMergedFromThis()

	def int getIdentifier()
}
