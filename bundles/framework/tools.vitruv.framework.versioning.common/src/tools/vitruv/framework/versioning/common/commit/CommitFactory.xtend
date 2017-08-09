package tools.vitruv.framework.versioning.common.commit

import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.versioning.common.commit.impl.CommitFactoryImpl

interface CommitFactory {
	static val initialCommitHash = "2aae6c35c94fcfb415dbe95f408b9ce91ee846ed"
	CommitFactory instance = CommitFactoryImpl::init

	def MergeCommit createMergeCommit(
		List<PropagatedChange> changes,
		String message,
		String authorName,
		String authorEMail,
		String source,
		String target,
		List<Integer> userInteractions
	)

	def SimpleCommit createSimpleCommit(
		List<PropagatedChange> changes,
		String message,
		String authorName,
		String authorEMail,
		String parent,
		List<Integer> userInteractions
	)

	def CommitMessage createCommitMessage(
		String message,
		String authorName,
		String authorEMail
	)

	def SimpleCommit createInitialCommit()

}
