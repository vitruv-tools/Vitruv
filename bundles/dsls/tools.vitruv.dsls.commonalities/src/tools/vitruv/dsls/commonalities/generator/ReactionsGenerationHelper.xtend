package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject

/**
 * Base for helper classes that need to be aware of the current reactions
 * generation context.
 */
abstract class ReactionsGenerationHelper extends GenerationHelper {

	@Inject protected extension ReactionsGenerationContext reactionsGenerationContext
}