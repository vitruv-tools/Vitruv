package tools.vitruv.dsls.commonalities.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import com.google.inject.Inject
import org.eclipse.xtext.scoping.IGlobalScopeProvider
import tools.vitruv.dsls.commonalities.commonalitiesLanguage.ClassLikeReference
import static tools.vitruv.dsls.commonalities.commonalitiesLanguage.CommonalitiesLanguagePackage.Literals.*
import tools.vitruv.dsls.commonalities.commonalitiesLanguage.PackageLikeImportReference
import tools.vitruv.dsls.commonalities.commonalitiesLanguage.MetaclassReference
import static extension tools.vitruv.dsls.commonalities.modelextension.CommonalitiesLanguageModelExtensions.*

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class CommonalitiesLanguageScopeProvider extends AbstractCommonalitiesLanguageScopeProvider {

	@Inject IGlobalScopeProvider globalScopeProvider;

	override getScope(EObject context, EReference reference) {
		switch context {
			/* In case of an import, go directly to the global scope, bypassing
			 * the Xbase import handling scopes.
			 */
			PackageLikeImportReference:
				globalScopeProvider.getScope(context.eResource, reference, null)
				
			MetaclassReference case reference == METACLASS_REFERENCE__METACLASS:
					new MetapackageScope(context.metamodel)
			
			ClassLikeReference:
					new ImportScope(context.containingCommonalityFile)
				
			default:
				super.getScope(context, reference)
		}
	}
}
