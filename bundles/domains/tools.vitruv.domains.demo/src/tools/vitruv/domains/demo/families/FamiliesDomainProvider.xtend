package tools.vitruv.domains.demo.families

import tools.vitruv.framework.domains.VitruvDomainProvider

class FamiliesDomainProvider implements VitruvDomainProvider<FamiliesDomain> {
	static var FamiliesDomain instance

	override getDomain() {
		if (instance === null) {
			instance = new FamiliesDomain()
		}
		return instance
	}

}
