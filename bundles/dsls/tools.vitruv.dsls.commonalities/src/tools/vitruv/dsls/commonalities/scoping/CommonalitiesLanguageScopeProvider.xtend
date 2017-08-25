package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.TupleParticipationDeclaration
import tools.vitruv.dsls.commonalities.language.elements.Participation
import tools.vitruv.dsls.commonalities.language.elements.ParticipationClass

import static tools.vitruv.dsls.commonalities.language.LanguagePackage.Literals.*
import static tools.vitruv.dsls.commonalities.language.elements.LanguageElementsPackage.Literals.*

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class CommonalitiesLanguageScopeProvider extends AbstractCommonalitiesLanguageScopeProvider {

	@Inject Provider<ParticipationAttributesScope> attributesScope
	@Inject Provider<DomainMetaclassesScope> metaclassesScope
	@Inject Provider<AllDomainsScope> allDomainsScope
	@Inject Provider<AllMetaclassesScope> allMetaclassesScope
	@Inject Provider<ParticipationRelationScope> participationRelationScope

	override getScope(EObject context, EReference reference) {
		switch reference {
			case ATTRIBUTE_MAPPING_SPECIFIATION__ATTRIBUTE:
				attributesScope.get.forCommonality(context.containingCommonalityFile.commonality)
				
			case PARTICIPATION_CLASS__SUPER_METACLASS: {
				var participation = switch context {
					ParticipationClass: context.participation
					Participation: context
				}
				switch participation {
					TupleParticipationDeclaration:
						metaclassesScope.get.forDomain(participation.domain)
					default:
						allMetaclassesScope.get()
				}
			}
			
			case PARTICIPATION_RELATION_DECLARATION__OPERATOR:
				participationRelationScope.get.forResourceSet(context.eResource.resourceSet)
			
			case PARTICIPATION__DOMAIN:
				allDomainsScope.get()
				
			default:
				IScope.NULLSCOPE
		}
	}
}
