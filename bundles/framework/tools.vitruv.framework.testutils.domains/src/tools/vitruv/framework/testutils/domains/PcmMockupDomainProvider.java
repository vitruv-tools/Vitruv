package tools.vitruv.framework.testutils.domains;

import tools.vitruv.framework.domains.VitruvDomainProvider;

public class PcmMockupDomainProvider implements VitruvDomainProvider<PcmMockupDomain> {
	public static final String EXTENSION_ID = "tools.vitruv.framework.testutils.domains.pcmMockupDomainProvider";

	private static PcmMockupDomain instance;

	@Override
	public synchronized PcmMockupDomain getDomain() {
		if (instance == null) {
			instance = new PcmMockupDomain();
		}
		return instance;
	}

}
