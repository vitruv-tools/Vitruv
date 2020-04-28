package tools.vitruv.dsls.commonalities.generator;

import com.google.inject.Inject

@GenerationScoped
package abstract class SubGenerator {

	@Inject protected extension GenerationContext generationContext

	def abstract void generate()

	def void beforeGenerate() {}

	def void afterGenerate() {}
}
