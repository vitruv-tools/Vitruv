package tools.vitruv.dsls.reactions.builder

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.jvmmodel.JvmModelAssociator

/**
 * Provides access to “the outside” to 
 * {@link FluentReactionElementBuilder fluent builders}.
 */
@Singleton
package class FluentBuilderContext {
	// the associator is a singleton, which is why we can have it injected
	// once and for all
	@Accessors(PACKAGE_GETTER)
	@Inject JvmModelAssociator jvmModelAssociator
}