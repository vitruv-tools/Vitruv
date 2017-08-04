package tools.vitruv.dsls.reactions.generator

import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import com.google.inject.Inject
import com.google.inject.Provider

class ExternalReactionsGenerator implements IReactionsGenerator {
	
	// is injected during runtime module setup, see ReactionsLanguageRuntimeModule
	@Inject
	static Provider<InternalReactionsGenerator> internalGeneratorProvider
	
	@Delegate val InternalReactionsGenerator internalGenerator
	
	public new() {
		internalGenerator = internalGeneratorProvider.get()
	}
}