package tools.vitruv.framework.versioning.commit

interface MergeCommit extends Commit {
	def String getSourceCommit()

	def String getTargetCommit()
}
