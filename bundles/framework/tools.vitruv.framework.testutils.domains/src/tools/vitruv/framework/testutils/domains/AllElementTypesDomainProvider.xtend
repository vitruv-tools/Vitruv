package tools.vitruv.framework.testutils.domains

import tools.vitruv.framework.domains.VitruvDomainProvider

class AllElementTypesDomainProvider implements VitruvDomainProvider<AllElementTypesDomain> {
	public static final String EXTENSION_ID = "tools.vitruv.framework.testutils.domains.allElementTypesDomainProvider"
	static AllElementTypesDomain instance

	override synchronized AllElementTypesDomain getDomain() {
		if (instance === null) {
			instance = new AllElementTypesDomain
		}
		return instance
	}
}
