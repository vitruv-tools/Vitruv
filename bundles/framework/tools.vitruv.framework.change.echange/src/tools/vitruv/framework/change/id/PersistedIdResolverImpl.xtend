package tools.vitruv.framework.change.id

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import static com.google.common.base.Preconditions.checkState
import static com.google.common.base.Preconditions.checkArgument
import tools.vitruv.framework.change.echange.id.IdEObjectRepository
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.resource.ResourceSet

class PersistedIdResolverImpl extends IdResolverImpl implements PersistedIdResolver {
	val Resource idResource
	
	/**
	 * Instantiates a persisted ID resolver with the given {@link ResourceSet} for resolving objects
	 * and a resource at the given {@link URI} for storing the mapping in.
	 * 
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be {@code null}
	 * @param resourceUri -
	 * 		the {@link URI} to place a resource for storing the mapping in, may not be {@code null}
	 * @throws IllegalArgumentExceptionif given {@link ResourceSet} or {@link URI} is {@code null}
	 */
	new(ResourceSet resourceSet, URI resourceUri) {
		super(resourceSet)
		checkArgument(resourceUri !== null, "resource URI must not be null")
		this.idResource = if (resourceUri !== null)
			new ResourceSetImpl().withGlobalFactories.createResource(resourceUri) => [
				contents += repository
			]	
	}
	
	override loadIdsAndModelsFromSerializedIdRepository() {
		checkState(idResource !== null, "ID resource must be specified to load existing IDs")
		val loadedResource = new ResourceSetImpl().withGlobalFactories.loadOrCreateResource(idResource.URI)
		if (!loadedResource.contents.empty) {
			val loadedRepository = loadedResource.contents.get(0) as IdEObjectRepository
			for (proxyEntry : loadedRepository.EObjectToId.entrySet) {
				val resolvedObject = EcoreUtil.resolve(proxyEntry.key, resourceSet)
				if (resolvedObject.eIsProxy) {
					throw new IllegalStateException("Object " + proxyEntry.key +
						" has an ID but could not be resolved")
				}
				getAndUpdateId(resolvedObject)
			}
		}
	}

	override save() {
		endTransaction()
		idResource?.save(null)
	}
	
	override close() {
		this.idResource?.unload()
	}
}