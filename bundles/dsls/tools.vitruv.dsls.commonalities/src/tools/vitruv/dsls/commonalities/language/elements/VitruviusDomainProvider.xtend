package tools.vitruv.dsls.commonalities.language.elements

import com.google.inject.Singleton
import edu.kit.ipd.sdq.activextendannotations.Lazy
import java.util.HashMap
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry

@Singleton
class VitruviusDomainProvider {

	/*
	 * To be referenced from an Xtext language, EObjects must be contained in
	 * a resource. So we create a fake resource to put the domains in. This 
	 * resource is never serialised and has no other purpose.
	 */
	val static CONTAINER_RESOURCE_URI = URI.createURI('http://vitruv.tools/framework/domains/vitruvdomains')
	val container = containerResource

	// there is currently no way to change the domains while developing, so
	// it’s okay to cache them.
	@Lazy(PRIVATE) HashMap<String, VitruviusDomain> allVitruviusDomainsByName = loadDomains()

	def private loadDomains() {
		val classifierProvider = new ClassifierProvider
		return newHashMap(
		VitruvDomainProviderRegistry.allDomainProviders.map[domain].map [ domain |
			val vitruvDomain = LanguageElementsFactory.eINSTANCE.createVitruviusDomain.
				withClassifierProvider(classifierProvider).forVitruvDomain(domain)
			container.contents += vitruvDomain
			return domain.name -> vitruvDomain
		])
	}

	def private containerResource() {
		val resourceSet = new ResourceSetImpl
		resourceSet.createResource(CONTAINER_RESOURCE_URI)
	}

	def getDomainByName(String name) {
		allVitruviusDomainsByName.get(name)
	}

	def getAllDomains() {
		allVitruviusDomainsByName.values
	}
}
