package tools.vitruv.dsls.commonalities.generator.reactions

import javax.inject.Inject
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScoped
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder

@GenerationScoped
class ReactionsGenerationContext {
	@Accessors(PUBLIC_GETTER)
	@Inject FluentReactionsLanguageBuilder create
}
