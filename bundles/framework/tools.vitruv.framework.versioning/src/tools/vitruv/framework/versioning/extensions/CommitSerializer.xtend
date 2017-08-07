package tools.vitruv.framework.versioning.extensions

import tools.vitruv.framework.versioning.extensions.impl.CommitSerializerImpl
import tools.vitruv.framework.versioning.commit.Commit
import java.util.List

interface CommitSerializer {
	CommitSerializer instance = CommitSerializerImpl::init

	def List<Commit> deserializeAll(String allCommitsString)

	def String serializeAll(List<Commit> commits)
}
