package tools.vitruv.dsls.reactions.tests

import tools.vitruv.framework.domains.VitruvDomainProvider

class BowlingDomainProvider implements VitruvDomainProvider<BowlingDomain> {
	public static final String EXTENSION_ID = "tools.vitruv.framework.testutils.domains.bowlingDomainProvider"
	static BowlingDomain instance

	override synchronized BowlingDomain getDomain() {
		if (instance === null) {
			instance = new BowlingDomain
		}
		return instance
	}
}
