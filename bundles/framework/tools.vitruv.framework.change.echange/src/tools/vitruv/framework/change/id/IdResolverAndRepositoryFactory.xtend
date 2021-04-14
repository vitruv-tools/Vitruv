package tools.vitruv.framework.change.id

import org.eclipse.emf.ecore.resource.ResourceSet
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class IdResolverAndRepositoryFactory {
	/**
	 * Instantiates a {@link IdResolverAndRepository} with the given {@link ResourceSet}
	 * for resolving objects.
	 * 
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be {@code null}
	 * @throws IllegalArgumentException if given {@link ResourceSet} is {@code null}
	 */
	static def IdResolver createIdResolver(ResourceSet resourceSet) {
		return new IdResolverImpl(resourceSet)
	}
	
}