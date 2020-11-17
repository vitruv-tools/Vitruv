package tools.vitruv.dsls.commonalities.testutils

import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.runner.RunWith
import tools.vitruv.testutils.VitruviusApplicationTest

@RunWith(XtextRunner)
@InjectWith(CombinedUiInjectorProvider)
abstract class CommonalitiesExecutionTest extends VitruviusApplicationTest {
	protected val CommonalitiesCompiler compiler
	
	protected new(CommonalitiesCompiler compiler) {
		this.compiler = compiler
	}
	
	override protected createChangePropagationSpecifications() {
		compiler.changePropagationSpecifications
	}

	override protected getVitruvDomains() {
		createChangePropagationSpecifications.flatMap[#[sourceDomain, targetDomain]].toSet
	}
}
