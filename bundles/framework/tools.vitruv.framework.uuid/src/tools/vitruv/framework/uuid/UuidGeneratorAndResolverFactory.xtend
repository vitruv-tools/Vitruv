package tools.vitruv.framework.uuid

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.common.util.URI

class UuidGeneratorAndResolverFactory {
	/**
	 * Instantiates a {@link UuidGeneratorAndResolver} with no parent resolver, the given {@link ResourceSet} 
	 * for resolving objects and a resource at the given {@link URI} for storing the mapping in.
	 * It loads an existing repository from the given {@link URI} and resolves the referenced objects
	 * in the given {@link ResourceSet}.
	 * 
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be {@code null}
	 * @param resourceUri -
	 * 		the {@link URI} to place a resource for storing the mapping in, may be {@code null}
	 * @throws IllegalArgumentException if given {@link ResourceSet} is {@code null}
	 */
	static def UuidGeneratorAndResolver createAndLoadUuidGeneratorAndResolver(ResourceSet resourceSet, URI resourceUri) {
		val generatorAndResolver = new UuidGeneratorAndResolverImpl(null, resourceSet, resourceUri)
		generatorAndResolver.loadUuidsAndModelsFromSerializedUuidRepository()
		return generatorAndResolver
	}

	/**
	 * Instantiates a {@link UuidGeneratorAndResolver} with no parent resolver, the given {@link ResourceSet} 
	 * for resolving objects and a resource at the given {@link URI} for storing the mapping in.
	 * 
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be {@code null}
	 * @param resourceUri -
	 * 		the {@link URI} to place a resource for storing the mapping in, may be {@code null}
	 * @throws IllegalArgumentException if given {@link ResourceSet} is {@code null}
	 */
	static def UuidGeneratorAndResolver createUuidGeneratorAndResolver(ResourceSet resourceSet, URI resourceUri) {
		return new UuidGeneratorAndResolverImpl(null, resourceSet, resourceUri)
	}
		
	/**
	 * Instantiates a {@link UuidGeneratorAndResolver} with the given parent resolver, used when this resolver 
	 * cannot resolve a UUID, the given {@link ResourceSet} for resolving objects and no {@link Resource} in 
	 * which the mapping is stored.
	 * 
	 * @param parentUuidResolver -
	 * 		the parent {@link UuidResolver} used to resolve UUID if this contains no appropriate mapping, may 
	 * 		be {@link null}
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be {@code null}
	 * @throws IllegalArgumentException if given {@link ResourceSet} is {@code null}
	 */
	static def UuidGeneratorAndResolver createUuidGeneratorAndResolver(UuidResolver parentUuidResolver, ResourceSet resourceSet) {
		return new UuidGeneratorAndResolverImpl(parentUuidResolver, resourceSet, null)
	}
	
	/**
	 * Instantiates a {@link UuidGeneratorAndResolver} with no parent resolver, the given {@link ResourceSet} 
	 * for resolving objects and no {@link Resource} in which the mapping is stored.
	 * 
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be {@code null}
	 * @throws IllegalArgumentException if given {@link ResourceSet} is {@code null}
	 */
	static def UuidGeneratorAndResolver createUuidGeneratorAndResolver(ResourceSet resourceSet) {
		return new UuidGeneratorAndResolverImpl(null, resourceSet, null)
	}
	
}