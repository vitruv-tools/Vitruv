package tools.vitruv.framework.versioning.commit

interface SimpleCommit extends Commit {
	def Commit getParent()
}
