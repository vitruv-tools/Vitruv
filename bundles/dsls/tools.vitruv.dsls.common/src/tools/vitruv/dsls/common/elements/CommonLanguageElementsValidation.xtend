package tools.vitruv.dsls.common.elements

import tools.vitruv.framework.domains.VitruvDomainProviderRegistry

class CommonLanguageElementsValidation {

	def static boolean isValid(DomainReference domainReference) {
		val domainNames = VitruvDomainProviderRegistry.allDomainProviders.map[domain.name].toList
		return domainNames.contains(domainReference.domain)
	}

}
