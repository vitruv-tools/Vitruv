package tools.vitruv.framework.domains.repository

import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.emf.ecore.resource.Resource
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.LinkedHashMap
import tools.vitruv.framework.domains.VitruvDomain
import java.io.InputStream
import java.io.OutputStream

@FinalFieldsConstructor
class DomainAwareResource implements Resource.Internal {
	@Delegate val Resource.Internal original
	val VitruvDomain domain
	
	override load(Map<?, ?> options) {
		original.load(options.withDefaults(domain.defaultLoadOptions))
	}
	
	override load(InputStream inputStream, Map<?, ?> options) {
		original.load(inputStream, options.withDefaults(domain.defaultLoadOptions))
	}
	
	override save(Map<?, ?> options) {
		original.save(options.withDefaults(domain.defaultSaveOptions))
	}
	
	override save(OutputStream outputStream, Map<?, ?> options) {
		original.save(outputStream, options.withDefaults(domain.defaultSaveOptions))
	}
	
	def static private Map<Object, Object> withDefaults(Map<?, ?> options, Map<?, ?> defaults) {
		if (defaults.isEmpty) {
			options as Map<Object, Object>
		} else if (options === null || options.isEmpty) {
			defaults as Map<Object, Object>
		} else {
			val result = new LinkedHashMap(defaults)
			result.putAll(options)
			result
		}
	}
	
	@FinalFieldsConstructor
	static class Factory implements Resource.Factory {
		val Resource.Factory original
		val VitruvDomainRepository domainRepository
		
		override createResource(URI uri) {
			val domain = domainRepository.getDomain(uri.fileExtension)
			val originalResource = original.createResource(uri)
			return if (domain !== null) {
				// EMF does the cast to Resource.Internal all the time (because object-oriented principles don’t apply
				// to them, I guess), so we’re doing it as well.
				new DomainAwareResource(originalResource as Resource.Internal, domain)
			} else {
				originalResource
			}
		}
	
		@FinalFieldsConstructor
		static class Registry implements Resource.Factory.Registry {
			@Delegate val Resource.Factory.Registry original
			val VitruvDomainRepository domainRepository
	
			override getFactory(URI uri) {
				new DomainAwareResource.Factory(original.getFactory(uri), domainRepository)
			}
	
			override getFactory(URI uri, String contentType) {
				new DomainAwareResource.Factory(original.getFactory(uri, contentType), domainRepository)
			}
	
			// we do *not* adapt the maps because:
			// * Our understanding of the contract of those maps is that they should not be used to 
			//   create new resources, but only to register new factories. Hence, adapting in #getFactory
			//   should be sufficient.
			// * We would not only need to transform the values, but also make sure that newly added values
			//   are wrapped. On the other hand, we must not write the mapped values back to the original,
			//   otherwise we might double-wrap them. All of this is complicated and can be avoided because
			//   of point 1.
			// * If we adapted the values, we stand the risk of leaking the adapted vales elsewhere
			//   where they don’t belong.
		}
	}
}