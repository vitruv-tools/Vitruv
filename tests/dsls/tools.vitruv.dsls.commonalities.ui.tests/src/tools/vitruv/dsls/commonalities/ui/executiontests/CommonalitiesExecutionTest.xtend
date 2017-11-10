package tools.vitruv.dsls.commonalities.ui.executiontests

import tools.vitruv.framework.tests.VitruviusApplicationTest

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

abstract class CommonalitiesExecutionTest extends VitruviusApplicationTest {
	
	override protected getVitruvDomains() {
		createChangePropagationSpecifications.flatMap [#[sourceDomain, targetDomain]].toSet
	}
	
}