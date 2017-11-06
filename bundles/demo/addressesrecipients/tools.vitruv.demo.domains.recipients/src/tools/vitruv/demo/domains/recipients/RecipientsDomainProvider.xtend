package tools.vitruv.demo.domains.recipients

import tools.vitruv.framework.domains.VitruvDomainProvider

class RecipientsDomainProvider implements VitruvDomainProvider<RecipientsDomain> {
	private static var RecipientsDomain instance;
	
	override public RecipientsDomain getDomain() {
		if (instance === null) {
			instance = new RecipientsDomain();
		}
		return instance;
	}
}