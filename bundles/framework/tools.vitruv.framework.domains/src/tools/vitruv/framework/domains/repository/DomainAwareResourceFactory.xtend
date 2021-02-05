package tools.vitruv.framework.domains.repository

import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.emf.ecore.resource.Resource
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.emf.ecore.resource.impl.ResourceImpl
import static extension tools.vitruv.framework.domains.repository.ResourceImplOpener.*
import java.util.LinkedHashMap

@FinalFieldsConstructor
class DomainAwareResourceFactory implements Resource.Factory {
	val Resource.Factory original
	val VitruvDomainRepository domainRepository
	
	// We are in an awkward situation here. EMF and related projects check for the concrete implementation
	// of a Resource all the time (because why should object oriented design principles apply to them, I guess?).
	// There are also many different implementations. Hence, we cannot simply wrap a Resource (because the instanceof
	// checks will fail), and we can also not extend the resource implementation (because there are so many).
	// Any resource in EMF has to be a Resource.Internal (otherwise it cannot be put into a ResourceSetImpl), and
	// all resources effectively extend ResourceImpl. Hence, we exploit the protected fields of ResourceImpl and
	// hope that all implementations will use them appropriately. 
	override createResource(URI uri) {
		val domain = domainRepository.getDomain(uri.fileExtension)
		val originalResource = original.createResource(uri)
		if (domain !== null) {
			if (originalResource instanceof ResourceImpl) {
				val domainLoadOptions = domain.defaultLoadOptions
				if (!domainLoadOptions.isEmpty) {
					originalResource.defaultLoadOptions = 
						originalResource.defaultLoadOptions.withDefaults(domainLoadOptions)
				}
				val domainSaveOptions = domain.defaultSaveOptions
				if (!domainSaveOptions.isEmpty) {
					originalResource.defaultSaveOptions = 
						originalResource.defaultSaveOptions.withDefaults(domain.defaultSaveOptions)
				}
			}
		}
		return originalResource
	}

	def static private <K, V> withDefaults(Map<K, V> options, Map<K, V> defaults) {
		if (defaults.isEmpty) {
			options
		} else if (options === null || options.isEmpty) {
			defaults
		} else {
			val result = new LinkedHashMap(defaults)
			result.putAll(options)
			result
		}
	}

	@FinalFieldsConstructor
	static class Registry implements Resource.Factory.Registry {
		@Delegate val Resource.Factory.Registry original
		val VitruvDomainRepository domainRepository

		override getFactory(URI uri) {
			new DomainAwareResourceFactory(original.getFactory(uri), domainRepository)
		}

		override getFactory(URI uri, String contentType) {
			new DomainAwareResourceFactory(original.getFactory(uri, contentType), domainRepository)
		}

		// we do *not* adapt the maps because:
		// * Our understanding of the contract of those maps is that they should not be used to 
		// create new resources, but only to register new factories. Hence, adapting in #getFactory
		// should be sufficient.
		// * We would not only need to transform the values, but also make sure that newly added values
		// are wrapped. On the other hand, we must not write the mapped values back to the original,
		// otherwise we might double-wrap them. All of this is complicated and can be avoided because
		// of point 1.
		// * If we adapted the values, we stand the risk of leaking the adapted vales elsewhere
		// where they don’t belong.
	}
}
