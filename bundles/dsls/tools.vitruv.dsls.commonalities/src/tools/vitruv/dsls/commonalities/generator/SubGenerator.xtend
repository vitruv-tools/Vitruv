package tools.vitruv.dsls.commonalities.generator;

import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScoped

@GenerationScoped
abstract class SubGenerator {

	@Inject protected extension GenerationContext generationContext

	abstract def void generate()

	def void beforeGenerate() {}

	def void afterGenerate() {}
}
