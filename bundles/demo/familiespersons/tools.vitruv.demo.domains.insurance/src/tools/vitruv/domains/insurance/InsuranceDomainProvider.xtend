package tools.vitruv.domains.insurance

import tools.vitruv.framework.domains.VitruvDomainProvider

class InsuranceDomainProvider implements VitruvDomainProvider<InsuranceDomain>{
	private static var InsuranceDomain instance;
	
	override getDomain() {
		if (instance === null) {
			instance = new InsuranceDomain();
		}
		return instance;
	}
	
}