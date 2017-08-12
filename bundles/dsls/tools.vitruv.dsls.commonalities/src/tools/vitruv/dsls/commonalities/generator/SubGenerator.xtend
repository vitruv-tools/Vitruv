package tools.vitruv.dsls.commonalities.generator;

import org.eclipse.xtext.generator.IGeneratorContext

abstract package class SubGenerator {

	protected extension IGeneratorContext context
	
	protected extension GenerationContext generationContext

	def abstract void generate()
	
	def void beforeGenerate() {}
	def void afterGenerate() {}
}
