package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.LanguageFactoryImpl

class CommonalitiesLanguageImplementationFactory extends LanguageFactoryImpl {
	
	override createSimpleParticipationDeclaration() {
		new SimpleParticipationDeclarationI
	}
	
	override createTupleParticipationDeclaration() {
		new TupleParticipationDeclarationI
	}
	
	override createParticipationClass() {
		new ParticipationClassI
	}
	
	override createCommonalityDeclaration() {
		new CommonalityDeclarationI
	}
	
	override createParticipationAttribute() {
		new ParticipationAttributeI
	}
	
	override createAttributeDeclaration() {
		new AttributeDeclarationI
	}
	
	override createConceptDeclaration() {
		new ConceptDeclarationI
	}
	
}