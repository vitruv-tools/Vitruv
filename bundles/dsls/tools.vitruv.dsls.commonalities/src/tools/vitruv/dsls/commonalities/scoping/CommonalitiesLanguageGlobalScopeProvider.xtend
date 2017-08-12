package tools.vitruv.dsls.commonalities.scoping

import org.eclipse.xtext.scoping.IGlobalScopeProvider
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.EReference
import com.google.common.base.Predicate
import org.eclipse.xtext.resource.IEObjectDescription
import com.google.inject.Inject
import org.eclipse.xtext.scoping.IScope
import static tools.vitruv.dsls.commonalities.language.elements.LanguageElementsPackage.Literals.*

class CommonalitiesLanguageGlobalScopeProvider implements IGlobalScopeProvider {
	
	@Inject MetaclassScope metaclassScope
	
	override getScope(Resource context, EReference reference, Predicate<IEObjectDescription> filter) {
		switch (reference) {
			case PARTICIPATION_CLASS__SUPER_METACLASS:
				metaclassScope
			
		default:
			IScope.NULLSCOPE			
		}
	}
	
}