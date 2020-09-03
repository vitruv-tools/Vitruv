package tools.vitruv.domains.insurance

import tools.vitruv.framework.domains.VitruvDomainProvider

class InsuranceDomainProvider implements VitruvDomainProvider<InsuranceDomain>{
	static var InsuranceDomain instance;
	
	override getDomain() {
		if (instance === null) {
			instance = new InsuranceDomain();
		}
		return instance;
	}
	
}