package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.LanguageFactoryImpl

class CommonalitiesLanguageImplementationFactory extends LanguageFactoryImpl {
	
	override createSimpleParticipation() {
		new SimpleParticipationI
	}
	
	override createTupleParticipation() {
		new TupleParticipationI
	}
	
	override createParticipationClass() {
		new ParticipationClassI
	}
	
	override createCommonality() {
		new CommonalityI
	}
	
	override createParticipationAttribute() {
		new ParticipationAttributeI
	}
	
	override createCommonalityAttribute() {
		new CommonalityAttributeI
	}
	
	override createConcept() {
		new ConceptI
	}
	
}