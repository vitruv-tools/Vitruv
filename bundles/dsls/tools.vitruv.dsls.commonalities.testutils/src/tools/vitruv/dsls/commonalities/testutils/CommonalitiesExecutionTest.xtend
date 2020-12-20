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
import edu.kit.ipd.sdq.activextendannotations.Lazy
import static com.google.common.base.Preconditions.checkNotNull

@ExtendWith(InjectionExtension)
@InjectWith(CombinedUiInjectorProvider)
@TestInstance(PER_CLASS)
abstract class CommonalitiesExecutionTest extends VitruvApplicationTest {
	var Path compilationDir
	ExecutionTestCompiler.Factory factory
	@Lazy
	val ExecutionTestCompiler compiler = createCompiler(
		checkNotNull(factory, "The compiler factory was not injected yet!").setParameters [
			commonalitiesOwner = this
			compilationProjectDir = checkNotNull(compilationDir, "The compilation directory was not acquired yet!")
		]
	)

	protected abstract def ExecutionTestCompiler createCompiler(ExecutionTestCompiler.Factory factory)

	@BeforeAll
	def void acquireCompilationTargetDir(@TestProject(variant="commonalities compilation") Path compilationDir) {
		this.compilationDir = compilationDir
	}

	@Inject
	def setCompilerFactory(ExecutionTestCompiler.Factory factory) {
		this.factory = factory
	}

	override protected getChangePropagationSpecifications() {
		compiler.changePropagationSpecifications
	}
}
