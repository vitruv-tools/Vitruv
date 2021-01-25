package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.LanguageFactoryImpl

class CommonalitiesLanguageImplementationFactory extends LanguageFactoryImpl {
	override createParticipation() {
		new ParticipationI
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

	override createSimpleAttributeMapping() {
		new SimpleAttributeMappingI
	}

	override createOperatorAttributeMapping() {
		new OperatorAttributeMappingI
	}

	override createConcept() {
		new ConceptI
	}

	override createCommonalityReference() {
		new CommonalityReferenceI
	}

	override createSimpleReferenceMapping() {
		new SimpleReferenceMappingI
	}

	override createOperatorReferenceMapping() {
		new OperatorReferenceMappingI
	}
}
