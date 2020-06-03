package tools.vitruv.dsls.commonalities.generator.reactions.helper

import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.generator.helper.GenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGenerationContext
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScoped

/**
 * Base for helper classes that need to be aware of the current reactions
 * generation context.
 * <p>
 * If the implementation is meant to be injected, consider annotating it with
 * {@link GenerationScoped}.
 */
abstract class ReactionsGenerationHelper extends GenerationHelper {

	@Inject protected extension ReactionsGenerationContext reactionsGenerationContext
}
