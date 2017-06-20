package tools.vitruv.framework.versioning.emfstore

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.EList

interface VVLocalProject extends VVProject {

	def EList<EObject> getModelElements()

	def VVRemoteProject getRemoteProject()

	def void setRemoteProject(VVRemoteProject remote)

	def VVCommit commit(String s)

	def VVRemoteProject shareProject(VVServer server)

	def void update()

	def void delete()
}
