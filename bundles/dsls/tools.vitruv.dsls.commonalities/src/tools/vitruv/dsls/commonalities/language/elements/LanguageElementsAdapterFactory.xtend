package tools.vitruv.dsls.commonalities.language.elements

import tools.vitruv.dsls.commonalities.language.elements.impl.LanguageElementsFactoryImpl

class LanguageElementsAdapterFactory extends LanguageElementsFactoryImpl {
	
	override createVitruviusDomain() {
		new VitruvDomainAdapter
	}
	
	override createEFeatureAttribute() {
		new EFeatureAdapter
	}
	
	override createEClassMetaclass() {
		new EClassAdapter
	}
	
	override createResourceMetaclass() {
		new ResourceMetaclassI
	}
	
	override createEDataTypeClassifier() {
		new EDataTypeAdapter
	}
	
}