package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.HashMap
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGenerator2
import org.eclipse.xtext.generator.IGeneratorContext
import tools.vitruv.dsls.commonalities.language.CommonalityFile

class CommonalitiesLanguageGenerator implements IGenerator2 {

	@Inject GenerationContext.Factory generationContextFactory

	@Inject Provider<IntermediateModelGenerator> intermediateModelGenerator
	@Inject Provider<IntermediateModelCodeGenerator> intermediateModelCodeGenerator
	@Inject Provider<ReactionsGenerator> reactionsGenerator
	@Inject Provider<DomainGenerator> domainGenerator

	val generationScopes = new HashMap<Resource, GenerationScope>()

	def private getSubGenerators() {
		#[
			intermediateModelGenerator.get,
			intermediateModelCodeGenerator.get,
			domainGenerator.get,
			reactionsGenerator.get
		]
	}

	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		val generationScope = new GenerationScope()
		val commonalityFile = input.containedCommonalityFile
		generationScope.seed(GenerationContext, generationContextFactory.create(fsa, commonalityFile))
		generationScopes.put(input, generationScope)

		input.runInGenerationScope [
			subGenerators.forEach[beforeGenerate]
		]
	}

	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		input.runInGenerationScope [
			subGenerators.forEach[generate]
		]
	}

	override afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		input.runInGenerationScope [
			subGenerators.forEach[afterGenerate]
		]
		generationScopes.remove(input)
	}

	def private runInGenerationScope(Resource input, Runnable runnable) {
		val generationScope = generationScopes.get(input)
		try {
			generationScope.enter()
			runnable.run()
		} finally {
			generationScope.leave()
		}
	}

	def static private containedCommonalityFile(Resource input) {
		if (input.contents.length == 0) {
			throw new GeneratorException('Input resource is empty.')
		}
		if (input.contents.length > 1) {
			throw new GeneratorException('''The input resource may only contain one element (found «
				input.contents.length»)''')
		}
		val inputObject = input.contents.get(0)
		if (!(inputObject instanceof CommonalityFile)) {
			throw new GeneratorException('''The input resource does not contain a Commonality file (but a «
				inputObject.class.simpleName»''')
		}
		inputObject as CommonalityFile
	}
}
