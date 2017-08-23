package tools.vitruv.framework.change.uuid

import org.eclipse.emf.ecore.EObject

interface UuidGeneratorAndResolver extends UuidResolver {
	def String getOrRegisterUuid(EObject object);
	def String registerEObject(EObject eObject);
}
