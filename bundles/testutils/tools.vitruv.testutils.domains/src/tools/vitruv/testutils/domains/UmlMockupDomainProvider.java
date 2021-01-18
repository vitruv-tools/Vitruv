package tools.vitruv.testutils.domains;

import tools.vitruv.framework.domains.VitruvDomainProvider;

public class UmlMockupDomainProvider implements VitruvDomainProvider<UmlMockupDomain> {
	private static UmlMockupDomain instance;

	@Override
	public synchronized UmlMockupDomain getDomain() {
		if (instance == null) {
			instance = new UmlMockupDomain();
		}
		return instance;
	}

}
