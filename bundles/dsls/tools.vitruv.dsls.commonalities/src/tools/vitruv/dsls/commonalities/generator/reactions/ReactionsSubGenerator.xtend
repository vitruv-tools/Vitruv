package tools.vitruv.dsls.commonalities.generator.reactions

import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper

/**
 * Base class for sub-generators which get directly invoked by the
 * {@link ReactionsGenerator}.
 * <p>
 * Currently this class has no functionality other than distinguishing its
 * subclasses from regular {@link ReactionsGenerationHelper}s.
 */
abstract class ReactionsSubGenerator extends ReactionsGenerationHelper {
}
