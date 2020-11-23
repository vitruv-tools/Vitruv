package tools.vitruv.dsls.commonalities.testutils

import java.nio.file.Path
import javax.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.testutils.TestProject
import tools.vitruv.testutils.VitruvApplicationTest

@ExtendWith(InjectionExtension)
@InjectWith(CombinedUiInjectorProvider)
@TestInstance(PER_CLASS)
abstract class CommonalitiesExecutionTest extends VitruvApplicationTest {
	var ExecutionTestCompiler compiler
	var Path compilationProjectDir

	protected abstract def ExecutionTestCompiler createCompiler(ExecutionTestCompiler.Factory factory)

	@BeforeAll
	def void acquireCompilationTargetDir(@TestProject(variant="commonalities compilation") Path compilationDir) {
		compilationProjectDir = compilationDir
	}

	@Inject
	def setCompilerFactory(ExecutionTestCompiler.Factory factory) {
		if (compiler === null) {
			factory.setParameters [
				commonalitiesOwner = this
				it.compilationProjectDir = this.compilationProjectDir
			]
			compiler = createCompiler(factory)
		}
	}

	override protected getChangePropagationSpecifications() {
		compiler.changePropagationSpecifications
	}
}
