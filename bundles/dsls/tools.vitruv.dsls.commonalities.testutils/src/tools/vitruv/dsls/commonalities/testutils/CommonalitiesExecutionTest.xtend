package tools.vitruv.dsls.commonalities.testutils

import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.runner.RunWith
import tools.vitruv.testutils.VitruviusApplicationTest

@RunWith(XtextRunner)
@InjectWith(CombinedUiInjectorProvider)
abstract class CommonalitiesExecutionTest extends VitruviusApplicationTest {

	override protected getVitruvDomains() {
		createChangePropagationSpecifications.flatMap[#[sourceDomain, targetDomain]].toSet
	}
}
