package tools.vitruv.dsls.reactions.generator

import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import com.google.inject.Inject
import com.google.inject.Provider
import tools.vitruv.dsls.reactions.ReactionsLanguageRuntimeModule
import com.google.inject.Guice

class ExternalReactionsGenerator implements IReactionsGenerator {
	
	// is injected during runtime module setup, see ReactionsLanguageRuntimeModule
	@Inject
	static Provider<InternalReactionsGenerator> internalGeneratorProvider
	
	@Delegate val InternalReactionsGenerator internalGenerator
	
	public new() {
		// TODO This does not work inside ui (eclipse) context, because the runtime module is missing the ui (eclipse) specific overrides.
		// Since we don't know here whether we are in ui context or not, move the responsibility for setting up the correct injector into clients?
		if (internalGeneratorProvider === null) {
			// the reactions runtime module was not initialised yet
			Guice.createInjector(new ReactionsLanguageRuntimeModule())
		}
		internalGenerator = internalGeneratorProvider.get()
	}
}