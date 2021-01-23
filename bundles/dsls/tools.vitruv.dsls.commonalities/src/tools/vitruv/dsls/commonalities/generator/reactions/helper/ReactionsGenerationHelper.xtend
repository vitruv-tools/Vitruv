package tools.vitruv.dsls.commonalities.generator.reactions.helper

import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGenerationContext
import tools.vitruv.dsls.commonalities.generator.GenerationContext
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScoped

/**
 * Base for helper classes that need to be aware of the current reactions
 * generation context.
 */
@GenerationScoped
abstract class ReactionsGenerationHelper {
	@Inject protected extension GenerationContext
	@Inject protected extension ReactionsGenerationContext
}
