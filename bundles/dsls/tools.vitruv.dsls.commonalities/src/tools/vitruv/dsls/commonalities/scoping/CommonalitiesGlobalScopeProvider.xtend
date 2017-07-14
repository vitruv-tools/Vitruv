package tools.vitruv.dsls.commonalities.scoping

import org.eclipse.xtext.scoping.IGlobalScopeProvider
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.EReference
import com.google.common.base.Predicate
import org.eclipse.xtext.resource.IEObjectDescription
import static tools.vitruv.dsls.commonalities.commonalitiesLanguage.CommonalitiesLanguagePackage.Literals.*;
import org.eclipse.xtext.scoping.IScope
import com.google.inject.Singleton
import com.google.inject.Inject

@Singleton
class CommonalitiesGlobalScopeProvider implements IGlobalScopeProvider {
	
	@Inject EPackageRegistryScope metamodelsScope;
	
	override getScope(Resource context, EReference reference, Predicate<IEObjectDescription> filter) {
		switch (reference) {
			case METAPACKAGE_IMPORT_REFERENCE__PACKAGE,
			case COMMONALITY_IMPORT_REFERENCE__COMMONALITY:
				metamodelsScope
			default:
				IScope.NULLSCOPE
		}
	}
	
}