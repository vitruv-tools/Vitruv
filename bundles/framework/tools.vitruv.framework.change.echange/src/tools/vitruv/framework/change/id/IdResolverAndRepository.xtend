package tools.vitruv.framework.change.id

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet

/**
 * A {@link IdResolverAndRepository} assigns IDs to {@link EObject}s and is able to 
 * resolve the relation in both directions.
 * It is possible to define a hierarchy of them.
 * A {@link IdResolverAndRepository} is assigned to a {@link ResourceSet} it is responsible for. 
 * It registers a notifier on that {@link ResourceSet}, which informs about newly added/loaded {@link Resource}s
 * and automatically loads the assigned IDs for contained elements from the parent resolver.
 */
interface IdResolverAndRepository extends IdResolver, AutoCloseable {	
	/**
	 * Returns the {@link ResourceSet} used in this ID repository.
	 */
	def ResourceSet getResourceSet()

	/**
	 * Loads the mapping between IDs and {@link EObject}s from the persistence at the {@link URI}
	 * given in the constructor and resolves the referenced {@link EObject}s in the {@link ResourceSet}
	 * given in the constructor.
	 */
	def void loadIdsAndModelsFromSerializedIdRepository()

	/**
	 * Saves the mapping between IDs and {@link EObject}s to an underlying resource.
	 */
	def void save()

}
