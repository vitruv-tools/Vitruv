package tools.vitruv.testutils.domains;

import tools.vitruv.framework.domains.VitruvDomainProvider;

public class PcmMockupDomainProvider implements VitruvDomainProvider<PcmMockupDomain> {
	private static PcmMockupDomain instance;

	@Override
	public synchronized PcmMockupDomain getDomain() {
		if (instance == null) {
			instance = new PcmMockupDomain();
		}
		return instance;
	}

}
