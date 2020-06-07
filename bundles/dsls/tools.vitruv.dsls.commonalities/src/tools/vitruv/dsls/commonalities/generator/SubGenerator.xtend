package tools.vitruv.dsls.commonalities.generator;

import org.eclipse.xtext.generator.IGenerator2
import tools.vitruv.dsls.commonalities.generator.helper.GenerationHelper
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScoped

/**
 * Base class for generators invoked by the
 * {@link CommonalitiesLanguageGenerator}.
 * <p>
 * Implementations should be annotated with {@link GenerationScoped}.
 */
abstract class SubGenerator extends GenerationHelper {

	/**
	 * @see IGenerator2#beforeGenerate
	 */
	def void beforeGenerate() {}

	/**
	 * @see IGenerator2#doGenerate
	 */
	abstract def void generate()

	/**
	 * @see IGenerator2#afterGenerate
	 */
	def void afterGenerate() {}
}
