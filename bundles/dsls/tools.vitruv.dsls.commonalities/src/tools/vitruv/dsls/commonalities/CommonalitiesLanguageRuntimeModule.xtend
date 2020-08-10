package tools.vitruv.dsls.commonalities

import com.google.inject.Binder
import org.eclipse.xtext.generator.IGenerator2
import tools.vitruv.dsls.commonalities.conversion.CommonalitiesLanguageValueConverterService
import tools.vitruv.dsls.commonalities.export.CommonalityFileResourceDescriptionStrategy
import tools.vitruv.dsls.commonalities.generator.CommonalitiesLanguageGenerator
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScope
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScoped
import tools.vitruv.dsls.commonalities.names.CommonalitiesLanguageQualifiedNameConverter
import tools.vitruv.dsls.commonalities.names.CommonalitiesLanguageQualifiedNameProvider
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider
import tools.vitruv.dsls.commonalities.names.QualifiedNameProviderDescriptionProvider
import tools.vitruv.dsls.commonalities.scoping.CommonalitiesLanguageGlobalScopeProvider

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
class CommonalitiesLanguageRuntimeModule extends AbstractCommonalitiesLanguageRuntimeModule {

	override bindIValueConverterService() {
		return CommonalitiesLanguageValueConverterService
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

	override bindIDefaultResourceDescriptionStrategy() {
		CommonalityFileResourceDescriptionStrategy
	}

	override bindIGlobalScopeProvider() {
		CommonalitiesLanguageGlobalScopeProvider
	}

	override configure(Binder binder) {
		super.configure(binder)
		binder.bindScope(GenerationScoped, GenerationScope.GuiceScope.INSTANCE)
	}
}
