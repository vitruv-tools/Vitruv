package tools.vitruv.dsls.reactions.scoping

import tools.vitruv.dsls.common.elements.CommonLanguageElementsGlobalScopeProvider
import org.eclipse.xtext.common.types.xtext.TypesAwareDefaultGlobalScopeProvider
import com.google.inject.Inject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.EReference
import com.google.common.base.Predicate
import org.eclipse.xtext.resource.IEObjectDescription

class ReactionsLanguageGlobalScopeProvider extends TypesAwareDefaultGlobalScopeProvider {
	@Inject CommonLanguageElementsGlobalScopeProvider commonGlobalScopeProvider

	override getScope(Resource resource, EReference reference, Predicate<IEObjectDescription> filter) {
		commonGlobalScopeProvider.getScope(resource, reference, filter) 
			?: super.getScope(resource, reference, filter)
	}

}
