package tools.vitruv.framework.change.uuid

import org.eclipse.emf.ecore.EObject

interface UuidResolver {
	def boolean hasUuid(EObject object);
	def String getUuid(EObject object);
	def EObject getEObject(String uuid);
}