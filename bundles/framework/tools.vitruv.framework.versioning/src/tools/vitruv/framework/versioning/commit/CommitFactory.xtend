package tools.vitruv.framework.versioning.commit

import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.versioning.commit.impl.CommitFactoryImpl

interface CommitFactory {
	CommitFactory instance = CommitFactoryImpl::init

	def MergeCommit createMergeCommit(
		List<PropagatedChange> changes,
		String message,
		String authorName,
		String authorEMail,
		List<String> source,
		List<String> target
	)

	def SimpleCommit createSimpleCommit(
		List<PropagatedChange> changes,
		String message,
		String authorName,
		String authorEMail,
		String parent
	)

	def CommitMessage createCommitMessage(
		String message,
		String authorName,
		String authorEMail
	)

	def SimpleCommit createInitialCommit()

}
