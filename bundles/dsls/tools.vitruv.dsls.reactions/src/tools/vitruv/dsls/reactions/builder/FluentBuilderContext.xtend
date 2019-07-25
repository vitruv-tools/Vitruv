package tools.vitruv.dsls.reactions.builder

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.access.IJvmTypeProvider
import org.eclipse.xtext.common.types.util.TypeReferences
import org.eclipse.xtext.xbase.jvmmodel.JvmModelAssociator
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder

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
	
	// is also a singleton
	@Accessors(PACKAGE_GETTER)
	@Inject TypeReferences typeReferences
	
	@Accessors(PACKAGE_GETTER)
	@Inject IJvmTypeProvider.Factory typeProviderFactory
	
	@Accessors(PACKAGE_GETTER)
	@Inject JvmTypeReferenceBuilder.Factory referenceBuilderFactory
}