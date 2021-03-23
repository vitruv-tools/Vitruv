package tools.vitruv.dsls.common.elements

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry

@Utility
class CommonLanguageElementsInitialization {
	def static initializeVitruvDomainsRepository() {
		VitruvDomainProviderRegistry.allDomainProviders
	}
}