package tools.vitruv.dsls.commonalities.generator;

abstract package class SubGenerator {
	protected extension GenerationContext generationContext

	def abstract void generate()
	
	def void beforeGenerate() {}
	def void afterGenerate() {}
}
