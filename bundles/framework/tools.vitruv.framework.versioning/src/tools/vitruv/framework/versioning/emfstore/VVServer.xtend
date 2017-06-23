package tools.vitruv.framework.versioning.emfstore

import java.util.List

interface VVServer {
	def VVUsersession login(String name)

	def List<VVRemoteProject> getRemoteProjects()
}
