package tools.vitruv.dsls.commonalities.generator

import org.eclipse.xtext.generator.IGenerator2
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.language.CommonalityFile
import com.google.inject.Provider
import java.util.HashMap

class CommonalitiesLanguageGenerator implements IGenerator2 {

	@Inject Provider<CommonalityIntermediateModelGenerator> intermediateModelGenerator
	@Inject Provider<CommonalityIntermediateModelCodeGenerator> intermediateModelCodeGenerator
	@Inject Provider<CommonalityReactionsGenerator> reactionsGenerator
	
	@Inject Provider<CommonalitiesLanguageGenerationContext> generationContextProvider
	val resourcesSubGenerators = new HashMap<Resource, CommonalityFileGenerator[]>

	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		val generationContext = generationContextProvider.get()
		val commonalityFile = input.containedCommonalityFile
		val resourceSubGenerators = newSubGenerators()
		resourceSubGenerators.forEach [
			it.fsa = fsa
			it.context = context
			it.generationContext = generationContext
			it.commonalityFile = commonalityFile
		]
		resourcesSubGenerators.put(input, resourceSubGenerators)
		resourceSubGenerators.forEach [beforeGenerate]
	}

	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		val resourceSubGenerators = resourcesSubGenerators.get(input)
		resourceSubGenerators.forEach [generate]
	}

	override afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		val resourceSubGenerators = resourcesSubGenerators.get(input)
		resourceSubGenerators.forEach [afterGenerate]
		resourcesSubGenerators.remove(input)
	}

	def private newSubGenerators() {
		#[intermediateModelGenerator.get, intermediateModelCodeGenerator.get, reactionsGenerator.get]
	}

	def static private containedCommonalityFile(Resource input) {
		if (input.contents.length == 0) {
			throw new GeneratorException('Input resource is empty.')
		}
		if (input.contents.length > 1) {
			throw new GeneratorException('''The input resource may only contain one element (found «input.contents.length»)''')
		}
		val inputObject = input.contents.get(0)
		if (!(inputObject instanceof CommonalityFile)) {
			throw new GeneratorException('''The input resource does not contain a Commonality file (but a «inputObject.class.simpleName»''')
		}
		inputObject as CommonalityFile
	}

}
