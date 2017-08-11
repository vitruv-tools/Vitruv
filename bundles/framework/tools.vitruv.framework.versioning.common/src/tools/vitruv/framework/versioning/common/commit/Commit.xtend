package tools.vitruv.framework.versioning.common.commit

import java.util.List

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.versioning.common.JSONSerializable

interface Commit extends JSONSerializable {

	def CommitMessage getCommitmessage()

	def List<MergeCommit> getCommitsMergedFromThis()

	def List<PropagatedChange> getChanges()

	def List<SimpleCommit> getCommitsBranchedFromThis()

	def String getIdentifier()

	def int getNumberOfChanges()

	def List<Integer> getUserInteractions()
}
