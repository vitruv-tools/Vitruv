package tools.vitruv.dsls.commonalities.language.elements

import com.google.inject.Singleton
import tools.vitruv.framework.domains.VitruvDomainProvider
import edu.kit.ipd.sdq.activextendannotations.Lazy
import java.util.HashMap
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI

@Singleton
class DomainProvider {

	/*
	 * To be referenced from an Xtext language, EObjects must be contained in
	 * a resource. So we create a fake resource to put the domains it. This 
	 * resource is never serialised and has no other purpose.
	 */	
	val static CONTAINER_RESOURCE_URI = URI.createURI('http://vitruv.tools/framework/domains/vitruvdomains')
	val container = containerResource
	
	// the members registered at the extension point will not change at runtime, so we can cache them.
	@Lazy(PRIVATE) HashMap<String, Domain> vitruvDomainsFromExtensionPoint = newHashMap(
		VitruvDomainProvider.allDomainProvidersFromExtensionPoint.map[domain].map [ domain |
		domain.name -> (LanguageElementsFactory.eINSTANCE.createVitruvDomain => [
			wrapVitruvDomain(domain)
			container.contents += it
		])
	])
	
	def private containerResource() {
		val resourceSet = new ResourceSetImpl 
		resourceSet.createResource(CONTAINER_RESOURCE_URI)
	}

	def getDomainByName(String name) {
		vitruvDomainsFromExtensionPoint.get(name)
	}
	
	def getAllDomains() {
		vitruvDomainsFromExtensionPoint.values
	}
}
