package tools.vitruv.framework.versioning.common.commit

interface MergeCommit extends Commit {
	def String getSourceCommit()

	def String getTargetCommit()
}
