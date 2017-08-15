package tools.vitruv.framework.change.uuid

import org.eclipse.emf.ecore.EObject

interface UuidProviderAndResolver {
	def String getOrRegisterUuid(EObject object);
	def boolean hasUuid(EObject object);
	def String getUuid(EObject object);
	def String registerEObject(EObject eObject);
	def void registerEObject(String uuid, EObject eObject);
	def EObject getEObject(String uuid);
}
