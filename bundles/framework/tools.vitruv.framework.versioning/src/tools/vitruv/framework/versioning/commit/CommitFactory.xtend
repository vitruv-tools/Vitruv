package tools.vitruv.framework.versioning.commit

import tools.vitruv.framework.versioning.commit.impl.CommitFactoryImpl
import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.versioning.author.Author

interface CommitFactory {
	CommitFactory instance = CommitFactoryImpl::init

	def MergeCommit createMergeCommit()

	def SimpleCommit createSimpleCommit(List<EChange> changes, String message, Author author, Commit parent)

	def CommitMessage createCommitMessage(String message, Author author)

	def InitialCommit createInitialCommit()

}
