package tools.vitruv.framework.change.id

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class IdResolverAndRepositoryFactory {
	/**
	 * Instantiates a {@link IdResolverAndRepository}, the given {@link ResourceSet}
	 * for resolving objects and a resource at the given {@link URI} for storing the mapping in.
	 * 
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be {@code null}
	 * @param resourceUri -
	 * 		the {@link URI} to place a resource for storing the mapping in, may be {@code null}
	 * @throws IllegalArgumentException if given {@link ResourceSet} is {@code null}
	 */
	static def IdResolverAndRepository createIdResolverAndRepository(ResourceSet resourceSet, URI resourceUri) {
		return new IdResolverAndRepositoryImpl(resourceSet, resourceUri)
	}
		
	/**
	 * Instantiates a {@link IdResolverAndRepository}, the given {@link ResourceSet}
	 * for resolving objects and no {@link Resource} in which the mapping is stored.
	 * 
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be {@code null}
	 * @throws IllegalArgumentException if given {@link ResourceSet} is {@code null}
	 */
	static def IdResolverAndRepository createIdResolverAndRepository(ResourceSet resourceSet) {
		return new IdResolverAndRepositoryImpl(resourceSet, null)
	}
	
}