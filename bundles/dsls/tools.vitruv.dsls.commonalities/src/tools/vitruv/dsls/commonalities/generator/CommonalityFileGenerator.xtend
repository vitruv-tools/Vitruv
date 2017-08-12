package tools.vitruv.dsls.commonalities.generator;

import org.eclipse.xtext.generator.IGeneratorContext;

abstract package class CommonalityFileGenerator {

	protected extension IGeneratorContext context
	
	protected extension CommonalitiesLanguageGenerationContext generationContext

	def abstract void generate()
	
	def void beforeGenerate() {}
	def void afterGenerate() {}
}
