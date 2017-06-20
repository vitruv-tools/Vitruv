package tools.vitruv.framework.versioning.emfstore

interface VVRemoteProject extends VVProject {
	def VVLocalProject getLocalProject()

	def void setLocalProject(VVLocalProject localProject)

	def VVServer getServer()

	def void setServer(VVServer server)

	def VVLocalProject checkout(String name)

	def void delete()

}
