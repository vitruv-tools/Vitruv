package tools.vitruv.dsls.reactions.generator

import com.google.inject.Inject
import com.google.inject.Provider
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtext.resource.IGlobalServiceProvider
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator

class ExternalReactionsGenerator implements IReactionsGenerator {

	static Logger logger = Logger.getLogger(ExternalReactionsGenerator)

	// Injected during the setup of the runtime module (see ReactionsLanguageRuntimeModule).
	@Inject
	static Provider<InternalReactionsGenerator> internalGeneratorProvider

	@Delegate val InternalReactionsGenerator internalGenerator

	@Inject
	package new(IGlobalServiceProvider globalServiceProvider) {
		if (internalGeneratorProvider === null) {
			// The Reactions language (and its runtime module) has not been setup yet.
			logger.warn("The Reactions language has not been setup yet. Performing setup now.")

			// Note: Depending on whether the client has already started using parts of the Reactions language, the
			// following setup may be too late. For instance, if the client has already started using the
			// FluentReactionsLanguageBuilder, its FluentBuilderContext will already use objects which would have been
			// bound differently if the correct runtime module had been setup earlier. Clients should therefore ideally
			// setup the Reactions language themselves earlier already.

			// This call has the side-effect of initializing the Reactions language's injector with the correct runtime
			// module (i.e. ui or standalone, depending on whether we are currently in Eclipse context or not):
			globalServiceProvider.findService(URI.createFileURI('dummy.reactions'), IReactionsGenerator)
			if (internalGeneratorProvider === null) {
				throw new IllegalStateException("Setup of the Reactions language has failed!")
			}
		}
		internalGenerator = internalGeneratorProvider.get()
	}
}
