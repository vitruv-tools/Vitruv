package tools.vitruv.dsls.reactions.tests

import tools.vitruv.framework.tests.VitruviusApplicationTest
import tools.vitruv.framework.testutils.domains.AllElementTypesDomainProvider

abstract class AbstractAllElementTypesReactionsTests extends VitruviusApplicationTest {
	protected static val MODEL_FILE_EXTENSION = new AllElementTypesDomainProvider().domain.fileExtensions.get(0);
	
	protected override getVitruvDomains() {
		return #[new AllElementTypesDomainProvider().domain];
	}
}