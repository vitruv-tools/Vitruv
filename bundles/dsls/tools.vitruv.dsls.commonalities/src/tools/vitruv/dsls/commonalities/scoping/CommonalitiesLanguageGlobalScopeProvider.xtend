package tools.vitruv.dsls.commonalities.scoping

import com.google.common.base.Predicate
import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.common.types.xtext.TypesAwareDefaultGlobalScopeProvider
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope

import static tools.vitruv.dsls.commonalities.language.LanguagePackage.Literals.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.scoping.impl.SimpleScope
import java.util.Collections

class CommonalitiesLanguageGlobalScopeProvider extends TypesAwareDefaultGlobalScopeProvider {

	@Inject Provider<VitruvDomainMetaclassesScope> allMetaclassesScope
	@Inject Provider<ParticipationRelationOperatorScope> participationRelationOperatorScope
	@Inject extension IEObjectDescriptionProvider descriptionProvider

	override getScope(Resource resource, EReference reference, Predicate<IEObjectDescription> filter) {
		new ComposedScope(
			super.getScope(resource, reference, filter),
			getVitruvDomainsScope(resource, reference),
			getSelfScope(resource, reference)
		)
	}

	def private getVitruvDomainsScope(Resource resource, EReference reference) {
		switch (reference) {
			case PARTICIPATION_CLASS__SUPER_METACLASS:
				allMetaclassesScope.get()
				
			case PARTICIPATION_RELATION__OPERATOR:
				participationRelationOperatorScope.get.forResourceSet(resource.resourceSet)
				
			default:
				IScope.NULLSCOPE
		}
	}
	
	def private getSelfScope(Resource resource, EReference reference) {
		switch (reference) {
			case COMMONALITY_REFERENCE__REFERENCE_TYPE:
				new SimpleScope(IScope.NULLSCOPE, Collections.singleton(
					resource.containedCommonalityFile.commonality.describe()
				))
			default:
				IScope.NULLSCOPE
		}
	}
}
