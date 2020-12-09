package tools.vitruv.dsls.reactions.generator

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.generator.InMemoryFileSystemAccess
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.dsls.reactions.tests.ReactionsLanguageInjectorProvider
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.junit.jupiter.api.Test

@ExtendWith(InjectionExtension)
@InjectWith(ReactionsLanguageInjectorProvider)
class ReactionsGeneratorTest {

	@Inject Provider<InMemoryFileSystemAccess> fsaProvider
	@Inject Provider<IReactionsGenerator> generatorProvider
	@Inject Provider<XtextResourceSet> resourceSetProvider
	static val allElementTypesDomain = new AllElementTypesDomainProvider().domain
	static val CHANGE_PROPAGATION_SPEC_NAME_SUFFIX = 'ChangePropagationSpecification'
	static val EXECUTOR_CLASS_NAME = 'ReactionsExecutor'
	static val REACTION_NAME = 'TestReaction'
	static val FIRST_SEGMENT = 'firstTestReaction'
	static val SECOND_SEGMENT = 'secondTestReaction'
	static val THIRD_SEGMENT = 'thirdTestReaction'
	static val FOURTH_SEGMENT = 'fourthTestReaction'

	def private createReaction(String reactionName, String reactionsFileName) {
		val create = new FluentReactionsLanguageBuilder()
		val fileBuilder = create.reactionsFile(reactionsFileName);
		fileBuilder +=
			create.reactionsSegment(reactionsFileName).inReactionToChangesIn(allElementTypesDomain).executeActionsIn(
				allElementTypesDomain) += create.reaction(reactionName).afterAnyChange.
				call [
					action[
						execute[createPrintlnStatement]
					]
				]
		return fileBuilder;
	}

	private def createPrintlnStatement(TypeProvider typeProvider) {
		XbaseFactory.eINSTANCE.createXFeatureCall => [
			val type = typeProvider.findTypeByName(InputOutput.name) as JvmGenericType
			feature = type.members.filter(JvmOperation).filter[it.simpleName == 'println'].head
			explicitOperationCall = true
			it.featureCallArguments += XbaseFactory.eINSTANCE.createXStringLiteral() => [
				it.value = 'That\'s it';
			]
		]
	}

	private static def assertFilesForReaction(InMemoryFileSystemAccess fsa, String segmentName, String reactionName) {
		assertFilesForReactionWithoutExecutor(fsa, segmentName, reactionName)
		assertThat(fsa.allFiles.keySet,
			hasItem(endsWith(segmentName + '/' + EXECUTOR_CLASS_NAME + '.java')))
		assertThat(fsa.allFiles.keySet,
			hasItem(endsWith(segmentName + '/' + segmentName.toFirstUpper + CHANGE_PROPAGATION_SPEC_NAME_SUFFIX + '.java')))
	}

	private static def assertFilesForReactionWithoutExecutor(InMemoryFileSystemAccess fsa, String segmentName, String reactionName) {
		assertThat(fsa.allFiles.keySet,
			hasItem(endsWith(segmentName + '/' + reactionName + 'Reaction.java')))
		assertThat(fsa.allFiles.keySet,
			hasItem(endsWith(segmentName + '/' + reactionName + 'RepairRoutine.java')))
	}

	@Test
	def testGenerateReactionsEnvironment() {
		var generator = generatorProvider.get()
		generator.useResourceSet(resourceSetProvider.get())

		val fsa = fsaProvider.get() => [
			currentSource = "src"
			outputPath = "src-gen"
		]
		generator.addReactionsFile(createReaction(REACTION_NAME, FIRST_SEGMENT))
		generator.addReactionsFile(createReaction(REACTION_NAME, SECOND_SEGMENT))
		generator.addReactionsFile(createReaction(REACTION_NAME, THIRD_SEGMENT))
		generator.generate(fsa)

		fsa.assertFilesForReaction(FIRST_SEGMENT, REACTION_NAME);
		fsa.assertFilesForReaction(SECOND_SEGMENT, REACTION_NAME);
		fsa.assertFilesForReaction(THIRD_SEGMENT, REACTION_NAME);

		val secondExecutorFileName = fsa.allFiles.entrySet.findFirst [
			key.endsWith(SECOND_SEGMENT + '/' + EXECUTOR_CLASS_NAME + '.java')
		].key
		fsa.deleteFile(secondExecutorFileName, '')

		generator = generatorProvider.get()
		generator.useResourceSet(resourceSetProvider.get())
		generator.addReactionsFile(createReaction(REACTION_NAME, FOURTH_SEGMENT))
		generator.generate(fsa)

		fsa.assertFilesForReaction(FIRST_SEGMENT, REACTION_NAME);
		fsa.assertFilesForReactionWithoutExecutor(SECOND_SEGMENT, REACTION_NAME);
		fsa.assertFilesForReaction(THIRD_SEGMENT, REACTION_NAME);
		fsa.assertFilesForReaction(FOURTH_SEGMENT, REACTION_NAME);
	}
}
