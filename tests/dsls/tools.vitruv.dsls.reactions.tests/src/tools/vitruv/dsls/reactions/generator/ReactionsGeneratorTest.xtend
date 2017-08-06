package tools.vitruv.dsls.reactions.generator

import org.junit.Test
import com.google.inject.Provider
import com.google.inject.Inject
import tools.vitruv.dsls.reactions.api.generator.ReactionBuilderFactory
import tools.vitruv.framework.testutils.domains.AllElementTypesDomainProvider
import org.junit.runner.RunWith
import tools.vitruv.dsls.reactions.tests.ReactionsLanguageInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.generator.InMemoryFileSystemAccess
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import java.util.function.Predicate
import java.io.InputStreamReader
import com.google.common.io.CharStreams
import org.eclipse.xtext.resource.XtextResourceSet

@RunWith(XtextRunner)
@InjectWith(ReactionsLanguageInjectorProvider)
class ReactionsGeneratorTest {

	@Inject Provider<InMemoryFileSystemAccess> fsaProvider
	@Inject Provider<IReactionsGenerator> generatorProvider
	@Inject Provider<XtextResourceSet> resourceSetProvider
	static val allElementTypesDomain = new AllElementTypesDomainProvider().domain
	static val EXPECTED_CHANGE_PROPAGATION_SPEC_NAME = 'AllElementTypesToAllElementTypesChangePropagationSpecification.java'

	def private createReaction(String name) {
		new ReactionBuilderFactory().createReactionBuilder().setName("Test").setTrigger(allElementTypesDomain).
			setTargetChange(allElementTypesDomain).setExecutionBlock(''' {
				println("That's it");
			}
			''').generateReaction()
	}

	@Test
	def testGenerateReactionsEnvironment() {
		val reaction = createReaction('TestReaction')
		var generator = generatorProvider.get()
		generator.useResourceSet(resourceSetProvider.get())
		
		val fsa = fsaProvider.get() => [
			currentSource = "src"
			outputPath = "src-gen"
		]
		generator.addReaction("FirstTestReaction", reaction)
		generator.addReaction("SecondTestReaction", reaction)
		generator.addReaction("ThirdTestReaction", reaction)
		generator.generate(fsa)

		var generatedFilePaths = fsa.allFiles.keySet

		assertThat(generatedFilePaths,
			hasItem(endsWith('firstTestReaction/ExecutorAllElementTypesToAllElementTypes.java')))
		assertThat(generatedFilePaths,
			hasItem(endsWith('secondTestReaction/ExecutorAllElementTypesToAllElementTypes.java')))
		assertThat(generatedFilePaths,
			hasItem(endsWith('thirdTestReaction/ExecutorAllElementTypesToAllElementTypes.java')))
		assertThat(generatedFilePaths, hasItem(endsWith(EXPECTED_CHANGE_PROPAGATION_SPEC_NAME)))

		var spec = fsa.readMatching [endsWith(EXPECTED_CHANGE_PROPAGATION_SPEC_NAME)]

		assertThat(spec, containsString('firstTestReaction.ExecutorAllElementTypesToAllElementTypes::new'))
		assertThat(spec, containsString('secondTestReaction.ExecutorAllElementTypesToAllElementTypes::new'))
		assertThat(spec, containsString('thirdTestReaction.ExecutorAllElementTypesToAllElementTypes::new'))

		val secondExecutorFileName = fsa.allFiles.entrySet.findFirst [
			key.endsWith('secondTestReaction/ExecutorAllElementTypesToAllElementTypes.java')
		].key
		fsa.deleteFile(secondExecutorFileName, '')

		generator = generatorProvider.get()
		generator.useResourceSet(resourceSetProvider.get())
		generator.addReaction("FourthTestReaction", reaction)
		generator.generate(fsa)
		
		generatedFilePaths = fsa.allFiles.keySet

		assertThat(generatedFilePaths,
			hasItem(endsWith('firstTestReaction/ExecutorAllElementTypesToAllElementTypes.java')))
		assertThat(generatedFilePaths,
			not(hasItem(endsWith('secondTestReaction/ExecutorAllElementTypesToAllElementTypes.java'))))
		assertThat(generatedFilePaths,
			hasItem(endsWith('thirdTestReaction/ExecutorAllElementTypesToAllElementTypes.java')))
		assertThat(generatedFilePaths,
			hasItem(endsWith('fourthTestReaction/ExecutorAllElementTypesToAllElementTypes.java')))
		assertThat(generatedFilePaths, hasItem(endsWith(EXPECTED_CHANGE_PROPAGATION_SPEC_NAME)))
		
		spec = fsa.readMatching [endsWith(EXPECTED_CHANGE_PROPAGATION_SPEC_NAME)]

		assertThat(spec, containsString('firstTestReaction.ExecutorAllElementTypesToAllElementTypes::new'))
		assertThat(spec, not(containsString('secondTestReaction.ExecutorAllElementTypesToAllElementTypes::new')))
		assertThat(spec, containsString('thirdTestReaction.ExecutorAllElementTypesToAllElementTypes::new'))
		assertThat(spec, containsString('fourthTestReaction.ExecutorAllElementTypesToAllElementTypes::new'))
	}
	
	def private readMatching(InMemoryFileSystemAccess fsa, Predicate<String> predicate) {
		CharStreams.toString(new InputStreamReader(fsa.readBinaryFile(fsa.allFiles.keySet.findFirst(predicate), '')))
	}
}
