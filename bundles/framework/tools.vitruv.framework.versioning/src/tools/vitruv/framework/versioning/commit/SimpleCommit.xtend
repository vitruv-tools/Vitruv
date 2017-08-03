package tools.vitruv.framework.versioning.commit

interface SimpleCommit extends Commit {
	def String getParent()

	def boolean isInitialCommit()

}
