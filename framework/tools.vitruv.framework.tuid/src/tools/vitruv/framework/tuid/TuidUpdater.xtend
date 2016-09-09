package tools.vitruv.framework.tuid

import org.eclipse.emf.ecore.EObject

interface TuidUpdater {
	def boolean canUpdate(EObject objectToUpdate);
	def void registerObjectForUpdate(EObject objectToUpdate);
	def void updateObjectTuidForRegisteredObject(EObject objectToUpdate)
	def void updateObjectTuid(EObject oldObject, EObject newObject)
	def void updateObjectTuid(TUID oldObjectTuid, EObject newObject)
}