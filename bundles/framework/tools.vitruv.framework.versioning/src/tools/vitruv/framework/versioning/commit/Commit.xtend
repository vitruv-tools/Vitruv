package tools.vitruv.framework.versioning.commit

import tools.vitruv.framework.change.echange.EChange
import java.util.List

interface Commit {

	def List<EChange> getChanges()

	def CommitMessage getCommitmessage()

	def List<SimpleCommit> getCommitsBranchedFromThis()

	def List<MergeCommit> getCommitsMergedFromThis()

	def int getIdentifier()
}
