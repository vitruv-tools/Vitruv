package tools.vitruv.framework.versioning.emfstore

import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.emfstore.VVObjectContainer

interface VVLocalProject extends VVProject, VVObjectContainer<VVModelElementId> {

	def VVRemoteProject getRemoteProject()

	def void setRemoteProject(VVRemoteProject remote)

	def Commit commit(String s)

	def VVRemoteProject shareProject(VVServer server)

	def void update()

	def void delete()
}
