package tools.vitruv.framework.versioning.branch

interface RemoteBranch<S> extends Branch {
	def void setRemoteRepository(S remoteRepository)

	def S getRemoteRepository()
}
