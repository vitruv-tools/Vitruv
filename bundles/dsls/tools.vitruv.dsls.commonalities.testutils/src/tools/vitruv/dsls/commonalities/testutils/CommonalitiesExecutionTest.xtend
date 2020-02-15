package tools.vitruv.dsls.commonalities.testutils

import tools.vitruv.testutils.VitruviusApplicationTest

abstract class CommonalitiesExecutionTest extends VitruviusApplicationTest {

	override protected getVitruvDomains() {
		createChangePropagationSpecifications.flatMap[#[sourceDomain, targetDomain]].toSet
	}
}
