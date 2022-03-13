package tools.vitruv.testutils.domains;

import tools.vitruv.framework.domains.VitruvDomainProvider;

public class RegistryOfficeDomainProvider implements VitruvDomainProvider<RegistryofficeDomain> {
	private static RegistryofficeDomain instance;

	@Override
	public synchronized RegistryofficeDomain getDomain() {
		if (instance == null) {
			instance = new RegistryofficeDomain();
		}
		return instance;
	}

}
