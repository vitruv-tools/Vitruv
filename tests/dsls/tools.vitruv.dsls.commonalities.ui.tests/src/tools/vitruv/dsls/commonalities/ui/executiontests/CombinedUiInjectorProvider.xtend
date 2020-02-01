package tools.vitruv.dsls.commonalities.ui.executiontests

import com.google.inject.Injector
import tools.vitruv.dsls.commonalities.ui.tests.CommonalitiesLanguageUiInjectorProvider
import tools.vitruv.dsls.reactions.ui.internal.ReactionsActivator

/**
 * Sets up both the guice injector for the Reactions language and the Commonalities language.
 * <p>
 * See https://zarnekow.blogspot.com/2014/10/testing-multiple-xtext-dsls.html and https://github.com/szarnekow/testing-multiple-dsls
 */
class CombinedUiInjectorProvider extends CommonalitiesLanguageUiInjectorProvider {

	override Injector getInjector() {
		// trigger injector creation of ReactionsLanguage:
		ReactionsActivator.getInstance().getInjector(ReactionsActivator.TOOLS_VITRUV_DSLS_REACTIONS_REACTIONSLANGUAGE);
		return super.injector;
	}
}
