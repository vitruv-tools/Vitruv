package tools.vitruv.dsls.commonalities.generator

import javax.inject.Inject
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder

@GenerationScoped
package class ReactionsGenerationContext {

	@Accessors(PACKAGE_GETTER)
	@Inject FluentReactionsLanguageBuilder create
}
