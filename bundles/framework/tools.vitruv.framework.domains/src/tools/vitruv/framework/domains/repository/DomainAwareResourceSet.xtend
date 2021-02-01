package tools.vitruv.framework.domains.repository

import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import java.util.HashMap
import java.util.Map
import static extension tools.vitruv.framework.util.ResourceSetUtil.*
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.framework.util.SavingResourceSet
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.util.DecoratedResourceSet

/**
 * A resource set allowing loading and saving EMF resources while respecting domain settings. Domain settings will 
 * only be respected when using methods from this class. Calling methods like {@link Resource#load} or 
 * {@link Resource#save} directly on the returned resources will <em>not</em> respect domain settings!
 */
class DomainAwareResourceSet implements SavingResourceSet, DecoratedResourceSet {
	@Delegate @Accessors val ResourceSet original
	val VitruvDomainRepository domains
	
	/**
	 * Provides access to resources through the provided {@code resourceSet} while respecting the settings of the
	 * provided {@code domains}.
	 */
	new(ResourceSet resourceSet, VitruvDomainRepository domains) {
		this.original = resourceSet
		this.domains = domains
	}
	
	/**
	 * Provides access to resources while respecting the settings of the provided {@code domains}.
	 */
	new(VitruvDomainRepository domains) {
		this(new ResourceSetImpl().withGlobalFactories, domains)
	}

	override getResource(URI uri, boolean loadOnDemand) {
		var Map<?, ?> loadOptions
		if (!loadOnDemand || (loadOptions = getLoadOptionsFor(uri)).isEmpty) {
			original.getResource(uri, loadOnDemand)
		} else {
			original.withLoadOptions(loadOptions) [getResource(uri, loadOnDemand)]
		}
	}
	
	override getEObject(URI uri, boolean loadOnDemand) {
		var Map<?, ?> loadOptions
		if (!loadOnDemand || (loadOptions = getLoadOptionsFor(uri)).isEmpty) {
			original.getEObject(uri, loadOnDemand)
		} else {
			original.withLoadOptions(loadOptions) [getEObject(uri, loadOnDemand)]
		}
	}
	
	override save(Resource resource, Map<?, ?> saveOptions) {
		val domainSaveOptions = domains.getDomain(resource.URI.fileExtension)?.defaultSaveOptions ?: emptyMap
		if (domainSaveOptions.isEmpty) {
			resource.save(saveOptions)
		} else {
			if (saveOptions.isEmpty) {
				resource.save(domainSaveOptions)
			} else {
				resource.save(domainSaveOptions + saveOptions)
			}
		}
	}
	
	def <RS extends ResourceSet, R> R withLoadOptions(RS resourceSet, Map<?, ?> options, (RS)=>R block) {
		synchronized (resourceSet) {
			val resourceSetLoadOptions = resourceSet.loadOptions
			val oldOptions = if (!resourceSetLoadOptions.isEmpty) new HashMap(resourceSetLoadOptions) else emptyMap
			resourceSetLoadOptions.putAll(options)
			try {
				block.apply(resourceSet)
			} finally {
				resourceSetLoadOptions.clear()
				resourceSetLoadOptions.putAll(oldOptions)
			}
		}
	}
	
	def private getLoadOptionsFor(URI uri) {
		domains.getDomain(uri.fileExtension)?.defaultLoadOptions ?: emptyMap
	}
}
