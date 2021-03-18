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
	def String generateUuid(EObject eObject)

	/**
	 * Loads the mapping between UUIDs and {@link EObject}s from the persistence at the {@link URI}
	 * given in the constructor and resolves the referenced {@link EObject}s in the {@link ResourceSet}
	 * given in the constructor.
	 */
	def void loadUuidsAndModelsFromSerializedUuidRepository()
	
	/**
	 * Saves the mapping between {@link Uuids}s and {@link EObject}s to an underlying resource.
	 */
	def void save()
}
