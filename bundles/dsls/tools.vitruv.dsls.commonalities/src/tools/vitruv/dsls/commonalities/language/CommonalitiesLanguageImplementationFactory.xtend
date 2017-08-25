package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.LanguageFactoryImpl

class CommonalitiesLanguageImplementationFactory extends LanguageFactoryImpl {
	
	override createSimpleParticipationDeclaration() {
		new SimpleParticipationDeclarationI
	}
	
	override createTupleParticipationDeclaration() {
		new TupleParticipationDeclarationI
	}
}