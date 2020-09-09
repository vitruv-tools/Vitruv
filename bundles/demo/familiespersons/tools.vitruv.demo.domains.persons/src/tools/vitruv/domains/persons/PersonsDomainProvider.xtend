package tools.vitruv.domains.persons

import tools.vitruv.framework.domains.VitruvDomainProvider

class PersonsDomainProvider implements VitruvDomainProvider<PersonsDomain>{
	static var PersonsDomain instance;
	
	override getDomain() {
		if (instance === null) {
			instance = new PersonsDomain();
		}
		return instance;
	}
	
}