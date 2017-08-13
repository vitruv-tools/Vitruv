package tools.vitruv.dsls.commonalities

import tools.vitruv.dsls.commonalities.names.CommonalitiesLanguageQualifiedNameConverter
import tools.vitruv.dsls.commonalities.names.CommonalitiesLanguageQualifiedNameProvider
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider
import tools.vitruv.dsls.commonalities.names.QualifiedNameProviderDescriptionProvider
import tools.vitruv.dsls.commonalities.scoping.CommonalitiesLanguageGlobalScopeProvider
import org.eclipse.xtext.generator.IGenerator2
import tools.vitruv.dsls.commonalities.generator.CommonalitiesLanguageGenerator

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
class CommonalitiesLanguageRuntimeModule extends AbstractCommonalitiesLanguageRuntimeModule {
	
	override bindIGlobalScopeProvider() {
		CommonalitiesLanguageGlobalScopeProvider
	}
	
	override bindIQualifiedNameConverter() {
		CommonalitiesLanguageQualifiedNameConverter
	}
	
	override bindIQualifiedNameProvider() {
		CommonalitiesLanguageQualifiedNameProvider
	}
	
	def Class<? extends IEObjectDescriptionProvider> bindIEObjectDescriptionProvider() {
		QualifiedNameProviderDescriptionProvider
	}
	
	def Class<? extends IGenerator2> bindIGenerator2() {
		CommonalitiesLanguageGenerator
	}
}	
	
	
