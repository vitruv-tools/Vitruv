package tools.vitruv.dsls.commonalities.language.elements

import tools.vitruv.dsls.commonalities.language.elements.impl.LanguageElementsFactoryImpl

class LanguageElementsAdapterFactory extends LanguageElementsFactoryImpl {
	
	override createVitruvDomain() {
		new VitruvDomainAdapter
	}
	
	override createEClassMetaclass() {
		new EClassAdapter
	}
	
	override createParticipationClass() {
		new ParticipationClassI
	}
	
	override createParticipationAttribute() {
		new ParticipationAttributeI
	}
	
	override createParticipationReference() {
		new ParticipationReferenceI
	}
	
	override createResourceMetaclass() {
		new ResourceMetaclassI
	}
}