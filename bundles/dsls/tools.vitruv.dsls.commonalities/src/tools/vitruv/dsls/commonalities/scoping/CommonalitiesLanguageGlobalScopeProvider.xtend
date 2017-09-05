package tools.vitruv.dsls.commonalities.scoping

import com.google.common.base.Predicate
import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.common.types.xtext.TypesAwareDefaultGlobalScopeProvider
import org.eclipse.xtext.resource.IEObjectDescription

import static tools.vitruv.dsls.commonalities.language.LanguagePackage.Literals.*

class CommonalitiesLanguageGlobalScopeProvider extends TypesAwareDefaultGlobalScopeProvider {

	@Inject Provider<VitruvDomainMetaclassesScope> allMetaclassesScope
	@Inject Provider<ParticipationRelationOperatorScope> participationRelationOperatorScope
	

	override getScope(Resource resource, EReference reference, Predicate<IEObjectDescription> filter) {
		new ComposedScope(super.getScope(resource, reference, filter), getVitruvDomainsScope(resource, reference))
	}

	def private getVitruvDomainsScope(Resource resource, EReference reference) {
		switch (reference) {
			case PARTICIPATION_CLASS__SUPER_METACLASS:
				allMetaclassesScope.get()
				
			case PARTICIPATION_RELATION__OPERATOR:
				participationRelationOperatorScope.get.forResourceSet(resource.resourceSet)
		}
	}
}
