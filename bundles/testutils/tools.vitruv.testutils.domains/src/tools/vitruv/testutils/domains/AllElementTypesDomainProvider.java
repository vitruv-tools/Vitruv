package tools.vitruv.testutils.domains;

import tools.vitruv.framework.domains.VitruvDomainProvider;

public class AllElementTypesDomainProvider implements VitruvDomainProvider<AllElementTypesDomain> {
	private static AllElementTypesDomain instance;
	
	@Override
	public synchronized AllElementTypesDomain getDomain() {
		if (instance == null) {
			instance = new AllElementTypesDomain();
		}
		return instance;
	}

}
