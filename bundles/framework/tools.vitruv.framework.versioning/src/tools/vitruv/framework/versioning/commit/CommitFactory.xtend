package tools.vitruv.framework.versioning.commit

import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.commit.impl.CommitFactoryImpl

interface CommitFactory {
	CommitFactory instance = CommitFactoryImpl::init

	def MergeCommit createMergeCommit()

	def SimpleCommit createSimpleCommit(List<PropagatedChange> changes, String message, Author author, String parent)

	def CommitMessage createCommitMessage(String message, Author author)

	def SimpleCommit createInitialCommit()

}
