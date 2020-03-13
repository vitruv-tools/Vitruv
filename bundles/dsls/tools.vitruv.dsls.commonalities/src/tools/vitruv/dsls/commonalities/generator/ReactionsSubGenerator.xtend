package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject

@GenerationScoped
package abstract class ReactionsSubGenerator {

	@Inject protected extension ReactionsGenerationContext reactionsGenerationContext
	@Inject protected extension GenerationContext generationContext
}
