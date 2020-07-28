package tools.vitruv.dsls.commonalities.generator;

import org.eclipse.xtext.generator.IGenerator2
import tools.vitruv.dsls.commonalities.generator.helper.GenerationHelper

/**
 * Base class for generators invoked by the
 * {@link CommonalitiesLanguageGenerator}.
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
