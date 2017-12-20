package tools.vitruv.framework.testutils.domains;

import tools.vitruv.framework.domains.VitruvDomainProvider;

public class UmlMockupDomainProvider implements VitruvDomainProvider<UmlMockupDomain> {
	public static final String EXTENSION_ID = "tools.vitruv.framework.testutils.domains.umlMockupDomainProvider";

	private static UmlMockupDomain instance;

	@Override
	public synchronized UmlMockupDomain getDomain() {
		if (instance == null) {
			instance = new UmlMockupDomain();
		}
		return instance;
	}

}
