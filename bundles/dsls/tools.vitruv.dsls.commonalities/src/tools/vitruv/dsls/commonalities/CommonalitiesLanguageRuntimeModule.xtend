package tools.vitruv.dsls.commonalities

import com.google.inject.Binder
import tools.vitruv.dsls.commonalities.names.CommonalitiesLanguageQualifiedNameConverter
import tools.vitruv.dsls.commonalities.names.CommonalitiesLanguageQualifiedNameProvider
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider
import tools.vitruv.dsls.commonalities.names.QualifiedNameProviderDescriptionProvider
import tools.vitruv.dsls.commonalities.scoping.CommonalitiesLanguageGlobalScopeProvider
import tools.vitruv.dsls.commonalities.scoping.CommonalitiesLanguageScopeProvider
import org.eclipse.xtext.naming.IQualifiedNameConverter

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
class CommonalitiesLanguageRuntimeModule extends AbstractCommonalitiesLanguageRuntimeModule {
	
	override bindIScopeProvider() {
		CommonalitiesLanguageScopeProvider
	}
	
	override bindIGlobalScopeProvider() {
		CommonalitiesLanguageGlobalScopeProvider
	}
	
	def Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
		CommonalitiesLanguageQualifiedNameConverter
	}
	
	override bindIQualifiedNameProvider() {
		CommonalitiesLanguageQualifiedNameProvider
	}
	
	def Class<? extends IEObjectDescriptionProvider> bindIEObjectDescriptionProvider() {
		QualifiedNameProviderDescriptionProvider
	}
	
	override configure(Binder binder) {
		binder.bind(IEObjectDescriptionProvider).to(bindIEObjectDescriptionProvider)
		binder.bind(IQualifiedNameConverter).to(bindIQualifiedNameConverter())
		super.configure(binder)
	}
	
}	
	
	
