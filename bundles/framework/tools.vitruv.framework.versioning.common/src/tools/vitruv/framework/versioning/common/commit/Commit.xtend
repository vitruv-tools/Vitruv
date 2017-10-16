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

	// TODO PS this can only handle user interactions as selections. 
	// Other user selection cannot be handled yet.
	def List<Integer> getUserInteractions()
}
 