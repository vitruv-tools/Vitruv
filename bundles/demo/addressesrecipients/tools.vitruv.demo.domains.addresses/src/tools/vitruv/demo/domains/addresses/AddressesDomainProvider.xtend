package tools.vitruv.demo.domains.addresses

import tools.vitruv.framework.domains.VitruvDomainProvider

class AddressesDomainProvider implements VitruvDomainProvider<AddressesDomain> {
	private static var AddressesDomain instance;
	
	override public AddressesDomain getDomain() {
		if (instance === null) {
			instance = new AddressesDomain();
		}
		return instance;
	}
}