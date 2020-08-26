package tools.vitruv.dsls.commonalities.generator.reactions.helper

import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.generator.helper.GenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGenerationContext

/**
 * Base for helper classes that need to be aware of the current reactions
 * generation context.
 */
abstract class ReactionsGenerationHelper extends GenerationHelper {

	@Inject protected extension ReactionsGenerationContext reactionsGenerationContext
}
