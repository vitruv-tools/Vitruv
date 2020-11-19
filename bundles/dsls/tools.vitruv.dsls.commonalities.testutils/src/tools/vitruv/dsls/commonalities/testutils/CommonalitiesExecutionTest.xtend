package tools.vitruv.dsls.commonalities.testutils

import javax.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.testutils.VitruvApplicationTest

@ExtendWith(InjectionExtension)
@InjectWith(CombinedUiInjectorProvider)
@TestInstance(PER_CLASS)
abstract class CommonalitiesExecutionTest extends VitruvApplicationTest {
	var CommonalitiesCompiler compiler

	protected abstract def CommonalitiesCompiler createCompiler(ExecutionTestCompiler.Factory factory)

	@Inject
	def setCompilerFactory(ExecutionTestCompiler.Factory factory) {
		factory.commonalitiesOwner = this
		if (compiler === null) {
			compiler = createCompiler(factory)
		}
	}

	override protected createChangePropagationSpecifications() {
		compiler.changePropagationSpecifications
	}

	override protected getVitruvDomains() {
		createChangePropagationSpecifications.flatMap[#[sourceDomain, targetDomain]].toSet
	}
}
