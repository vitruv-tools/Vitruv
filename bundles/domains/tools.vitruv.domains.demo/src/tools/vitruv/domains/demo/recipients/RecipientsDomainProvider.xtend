package tools.vitruv.domains.demo.recipients

import tools.vitruv.framework.domains.VitruvDomainProvider

class RecipientsDomainProvider implements VitruvDomainProvider<RecipientsDomain> {
	static var RecipientsDomain instance

	override RecipientsDomain getDomain() {
		if (instance === null) {
			instance = new RecipientsDomain()
		}
		return instance
	}
}
