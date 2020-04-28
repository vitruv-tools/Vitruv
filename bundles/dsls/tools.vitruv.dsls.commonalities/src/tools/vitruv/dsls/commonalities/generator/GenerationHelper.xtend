package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject

/**
 * Base for helper classes that need to be aware of the current generation
 * context.
 * <p>
 * If the implementation is meant to be injected, consider annotating it with
 * {@link GenerationScoped}.
 */
package abstract class GenerationHelper {

	@Inject protected extension GenerationContext generationContext
}
