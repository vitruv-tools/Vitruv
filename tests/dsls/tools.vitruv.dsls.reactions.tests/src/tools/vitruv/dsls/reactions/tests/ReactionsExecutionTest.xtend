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
import edu.kit.ipd.sdq.activextendannotations.Lazy
import static com.google.common.base.Preconditions.checkNotNull
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider

@ExtendWith(InjectionExtension)
@InjectWith(ReactionsLanguageInjectorProvider)
@TestInstance(PER_CLASS)
abstract class ReactionsExecutionTest extends VitruvApplicationTest {
	Path compilationDir
	TestReactionsCompiler.Factory factory
	@Lazy
	val TestReactionsCompiler compiler = createCompiler(
		checkNotNull(factory, "The compiler factory was not injected yet!").setParameters [
			reactionsOwner = this
			compilationProjectDir = checkNotNull(compilationDir, "The compilation directory was not acquired yet!")
		]
	)

	@BeforeEach
	def void patchDomain() {
		new AllElementTypesDomainProvider().domain.enableTransitiveChangePropagation()
	}
	
	protected abstract def TestReactionsCompiler createCompiler(TestReactionsCompiler.Factory factory)

	@BeforeAll
	def void acquireCompilationTargetDir(@TestProject(variant="reactions compilation") Path compilationDir) {
		this.compilationDir = compilationDir
	}

	@Inject
	def setCompilerFactory(TestReactionsCompiler.Factory factory) {
		this.factory = factory
	}

	override protected getChangePropagationSpecifications() {
		compiler.getChangePropagationSpecifications()
	}
}
