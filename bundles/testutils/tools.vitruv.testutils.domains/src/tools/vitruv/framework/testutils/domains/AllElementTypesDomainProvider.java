package tools.vitruv.framework.testutils.domains;

import tools.vitruv.framework.domains.VitruvDomainProvider;

public class AllElementTypesDomainProvider implements VitruvDomainProvider<AllElementTypesDomain> {
	public static final String EXTENSION_ID = "tools.vitruv.framework.testutils.domains.allElementTypesDomainProvider";
	
	private static AllElementTypesDomain instance;
	
	@Override
	public synchronized AllElementTypesDomain getDomain() {
		if (instance == null) {
			instance = new AllElementTypesDomain();
		}
		return instance;
	}

}
