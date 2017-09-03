package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.TupleParticipationDeclaration

import static tools.vitruv.dsls.commonalities.language.LanguagePackage.Literals.*

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class CommonalitiesLanguageScopeProvider extends AbstractCommonalitiesLanguageScopeProvider {

	@Inject Provider<DomainMetaclassesScope> metaclassesScope
	@Inject Provider<AllDomainsScope> allDomainsScope
	@Inject Provider<AllMetaclassesScope> allMetaclassesScope
	@Inject Provider<ParticipationRelationOperatorScope> participationRelationOperatorScope
	@Inject Provider<ParticipationClassesScope> participationClassesScope
	@Inject Provider<ParticipationAttributesScope> participationAttributesScope

	override getScope(EObject context, EReference reference) {
		switch reference {
			case PARTICIPATION_ATTRIBUTE__PARTICIPATION_CLASS:
				participationClassesScope.get.forCommonality(context.containingCommonalityFile.commonality)
				
			case PARTICIPATION_ATTRIBUTE__ATTRIBUTE:
				participationAttributesScope.get.forParticipationClass(
					(context as ParticipationAttribute).participationClass)
					
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
				participationRelationOperatorScope.get.forResourceSet(context.eResource.resourceSet)
				
			case TUPLE_PARTICIPATION_DECLARATION__DOMAIN:
				allDomainsScope.get()
				
			default:
				IScope.NULLSCOPE
		}
	}
}
