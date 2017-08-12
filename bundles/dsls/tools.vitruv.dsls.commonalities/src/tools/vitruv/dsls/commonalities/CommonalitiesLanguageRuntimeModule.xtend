package tools.vitruv.dsls.commonalities

import tools.vitruv.dsls.commonalities.scoping.CommonalitiesLanguageScopeProvider
import tools.vitruv.dsls.commonalities.generator.CommonalitiesLanguageGenerator
import com.google.inject.Binder
import org.eclipse.xtext.generator.IGenerator2
import tools.vitruv.dsls.commonalities.scoping.CommonalitiesLanguageGlobalScopeProvider
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider
import tools.vitruv.dsls.commonalities.names.QualifiedNameProviderDescriptionProvider
import tools.vitruv.dsls.commonalities.names.CommonalitiesLanguageQualifiedNameConverter
import tools.vitruv.dsls.commonalities.names.CommonalitiesLanguageQualifiedNameProvider

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
	
	override bindIQualifiedNameConverter() {
		CommonalitiesLanguageQualifiedNameConverter
	}
	
	override bindIQualifiedNameProvider() {
		CommonalitiesLanguageQualifiedNameProvider
	}
	
	def Class<? extends IGenerator2> bindIGenerator2() {
		CommonalitiesLanguageGenerator
	}
	
	def Class<? extends IEObjectDescriptionProvider> bindIEObjectDescriptionProvider() {
		QualifiedNameProviderDescriptionProvider
	}
	
	override configure(Binder binder) {
		binder.bind(IGenerator2).to(bindIGenerator2)
		binder.bind(IEObjectDescriptionProvider).to(bindIEObjectDescriptionProvider)
		super.configure(binder)
	}
	
}	
	
	
