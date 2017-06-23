package tools.vitruv.framework.versioning.emfstore

import java.util.List
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.conflict.Conflict
import tools.vitruv.framework.versioning.emfstore.VVObjectContainer
import tools.vitruv.framework.versioning.exceptions.CommitNotExceptedException

interface VVLocalProject extends VVProject, VVObjectContainer<VVModelElementId> {

	def Commit commit(String s) throws CommitNotExceptedException

	def VVRemoteProject getRemoteProject()

	def VVRemoteProject shareProject(VVServer server)

	def void delete()

	def void setRemoteProject(VVRemoteProject remote)

	def void update()

	def void update(Function1<List<Conflict>, Boolean> conflictCallback)
}
