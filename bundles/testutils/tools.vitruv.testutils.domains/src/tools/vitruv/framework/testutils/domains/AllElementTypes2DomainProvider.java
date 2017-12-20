package tools.vitruv.framework.testutils.domains;

import tools.vitruv.framework.domains.VitruvDomainProvider;

public class AllElementTypes2DomainProvider implements VitruvDomainProvider<AllElementTypes2Domain> {
	public static final String EXTENSION_ID = "tools.vitruv.framework.testutils.domains.allElementTypes2DomainProvider";
	
	private static AllElementTypes2Domain instance;
	
	@Override
	public synchronized AllElementTypes2Domain getDomain() {
		if (instance == null) {
			instance = new AllElementTypes2Domain();
		}
		return instance;
	}

}
