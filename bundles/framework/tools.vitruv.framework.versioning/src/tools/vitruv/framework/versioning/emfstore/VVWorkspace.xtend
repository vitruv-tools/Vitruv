package tools.vitruv.framework.versioning.emfstore

import java.util.List

interface VVWorkspace {

	def List<VVServer> getServers()

	def VVLocalProject createLocalProject(String name)

	def void addServer(VVServer server)

	def void removeServer(VVServer server)

	def List<VVLocalProject> getLocalProjects()
}
