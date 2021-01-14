package tools.vitruv.dsls.commonalities.testutils

import tools.vitruv.testutils.activeannotations.ModelCreators
import tools.vitruv.dsls.commonalities.language.LanguageFactory
import tools.vitruv.dsls.commonalities.language.elements.LanguageElementsFactory

@ModelCreators(factory=LanguageFactory)
class CommonalitiesLanguageCreators {
	public static val commonalities = new CommonalitiesLanguageCreators
	public val languageElements = new LanguageElementsCreators

	@ModelCreators(factory=LanguageElementsFactory)
	static class LanguageElementsCreators {
	}
}
