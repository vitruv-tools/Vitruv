package tools.vitruv.dsls.commonalities.generator

import org.eclipse.xtext.generator.IGenerator2
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.language.CommonalityFile
import com.google.inject.Provider

class CommonalitiesLanguageGenerator implements IGenerator2 {

	@Inject Provider<CommonalityIntermediateModelGenerator> intermediateModelGenerator
	@Inject Provider<CommonalityReactionsGenerator> reactionsGenerator
	@Inject Provider<CommonalitiesLanguageGenerationContext> generationContextProvider
	CommonalitiesLanguageGenerationContext currentGenerationContext

	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		// subGenerators.forEach[beforeGenerate(input, fsa, context)]
		currentGenerationContext = generationContextProvider.get()
	}

	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		val comFile = input.containedCommonalityFile
		for (generator : subGenerators) {
			generator.get()
				.withFileSystemAccess(fsa)
				.withContext(context)
				.withGenerationContext(currentGenerationContext)
				.forCommonalityFile(comFile)
				.generate()
		}
	}

	override afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		// subGenerators.forEach[afterGenerate(input, fsa, context)]
		currentGenerationContext = null
	}

	def private subGenerators() {
		#[intermediateModelGenerator, reactionsGenerator]
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
