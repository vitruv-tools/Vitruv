package tools.vitruv.dsls.commonalities.generator

import org.eclipse.xtext.generator.IGenerator2
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.generator.intermediatemodel.CommonalityIntermediateModelGenerator

class CommonalitiesLanguageGenerator implements IGenerator2 {
	
	@Inject CommonalityIntermediateModelGenerator intermediateModelGenerator
	
	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		subGenerators.forEach [beforeGenerate(input, fsa, context)]
	}
	
	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		subGenerators.forEach [doGenerate(input, fsa, context)]
	}
	
	override afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		subGenerators.forEach [afterGenerate(input, fsa, context)]
	}
	
	def private subGenerators() {
		#[intermediateModelGenerator]
	}
	
}