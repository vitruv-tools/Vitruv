package tools.vitruv.framework.change.uuid

import org.eclipse.emf.ecore.EObject

interface UuidGeneratorAndResolver extends UuidResolver {
	def String getOrRegisterUuid(EObject object);
	
	/**
	 * Registers an object and returns the generated UUID for it.
	 */
	def String registerEObject(EObject eObject);
	
	/**
	 * Registers an object that was not created before and thus has no UUID.
	 * This is only successful if the element is globally accessible (third party element) or if
	 * we are not in strict mode. Otherwise an exception is thrown, because a previous create is missing. 
	 */
	def String registerNotCreatedEObject(EObject eObject, boolean strictMode);
}
