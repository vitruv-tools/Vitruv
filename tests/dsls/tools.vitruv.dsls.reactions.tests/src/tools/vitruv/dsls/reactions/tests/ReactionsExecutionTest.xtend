package tools.vitruv.dsls.reactions.tests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import tools.vitruv.testutils.VitruvApplicationTest
import org.junit.jupiter.api.BeforeAll
import java.nio.file.Path
import tools.vitruv.testutils.TestProject
import org.junit.jupiter.api.TestInstance

@ExtendWith(InjectionExtension)
@InjectWith(ReactionsLanguageInjectorProvider)
@TestInstance(PER_CLASS)
abstract class ReactionsExecutionTest extends VitruvApplicationTest {
	var TestReactionsCompiler compiler
	var Path compilationProjectDir

	protected abstract def TestReactionsCompiler createCompiler(TestReactionsCompiler.Factory factory)

	@BeforeAll
	def void acquireCompilationTargetDir(@TestProject(variant="reactions compilation") Path compilationDir) {
		compilationProjectDir = compilationDir
	}

	@Inject
	def setCompilerFactory(TestReactionsCompiler.Factory factory) {
		if (compiler === null) {
			factory.setParameters [
				reactionsOwner = this
				it.setCompilationProjectDir = this.compilationProjectDir
			]
			compiler = createCompiler(factory)
		}
	}

	override protected getChangePropagationSpecifications() {
		compiler.getChangePropagationSpecifications()
	}
}
