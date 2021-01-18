package tools.vitruv.testutils.domains;

import tools.vitruv.framework.domains.VitruvDomainProvider;

public class AllElementTypes2DomainProvider implements VitruvDomainProvider<AllElementTypes2Domain> {
	private static AllElementTypes2Domain instance;
	
	@Override
	public synchronized AllElementTypes2Domain getDomain() {
		if (instance == null) {
			instance = new AllElementTypes2Domain();
		}
		return instance;
	}

}
