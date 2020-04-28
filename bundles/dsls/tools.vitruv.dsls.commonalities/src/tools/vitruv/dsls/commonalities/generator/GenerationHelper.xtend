package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject

/**
 * Base for helper classes that need to be aware of the current generation
 * context.
 */
@GenerationScoped
package abstract class GenerationHelper {

	@Inject protected extension GenerationContext generationContext
}
