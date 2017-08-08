package tools.vitruv.framework.versioning.common.commit

interface SimpleCommit extends Commit {
	def String getParent()

	def boolean isInitialCommit()

}
