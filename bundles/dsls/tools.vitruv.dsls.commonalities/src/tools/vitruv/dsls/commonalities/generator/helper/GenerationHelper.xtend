package tools.vitruv.dsls.commonalities.generator.helper

import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.generator.GenerationContext
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScoped

/**
 * Base for helper classes that need to be aware of the current generation
 * context.
 */
@GenerationScoped
abstract class GenerationHelper {

	@Inject protected extension GenerationContext generationContext
}
