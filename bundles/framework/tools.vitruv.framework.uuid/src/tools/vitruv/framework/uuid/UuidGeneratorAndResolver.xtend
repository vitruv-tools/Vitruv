package tools.vitruv.framework.uuid

import org.eclipse.emf.ecore.EObject

/**
 * A {@link UuidGeneratorAndResolver} assigns {@link Uuid}s to {@link EObject}s and is able to 
 * resolve the relation in both directions.
 * It is possible to define a hierarchy of them.
 * A {@link UuidGeneratorAndResolver} is assigned to a {@link ResourceSet} it is responsible for. 
 * It registers a notifier on that {@link ResourceSet}, which informs about newly added/loaded {@link Resource}s
 * and automatically loads the assigned {@link Uuid}s for contained elements from the parent resolver.
 */
interface UuidGeneratorAndResolver extends UuidResolver, AutoCloseable {
	/**
	 * Registers an object and returns the generated UUID for it.
	 */
	def String generateUuid(EObject eObject);

	/**
	 * Registers an object that was not created before and thus has no UUID.
	 * This is only successful if the element is globally accessible (third party element) or if
	 * we are not in strict mode. Otherwise an exception is thrown, because a previous create is missing. 
	 */
	def String generateUuidWithoutCreate(EObject eObject);

	/**
	 * Saves the mapping between {@link Uuids}s and {@link EObject}s to an underlying resource.
	 */
	def void save()
}
