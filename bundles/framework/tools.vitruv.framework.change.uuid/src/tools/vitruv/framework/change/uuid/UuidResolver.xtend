package tools.vitruv.framework.change.uuid

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet

interface UuidResolver {
	def boolean hasUuid(EObject object);
	def String getUuid(EObject object);
	def EObject getEObject(String uuid);
	def void registerEObject(String uuid, EObject eObject);
	def ResourceSet getResourceSet();
}