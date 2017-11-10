package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IGlobalScopeProvider
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.TupleParticipation

import static tools.vitruv.dsls.commonalities.language.LanguagePackage.Literals.*

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class CommonalitiesLanguageScopeProvider extends AbstractCommonalitiesLanguageScopeProvider {

	@Inject Provider<ParticipationClassesScope> participationClassesScope
	@Inject Provider<ParticipationAttributesScope> participationAttributesScope
	@Inject IGlobalScopeProvider globalScopeProvider

	override getScope(EObject context, EReference reference) {
		switch reference {
			case PARTICIPATION_ATTRIBUTE__PARTICIPATION_CLASS:
				participationClassesScope.get.forCommonality(context.containingCommonalityFile.commonality)
				
			case PARTICIPATION_ATTRIBUTE__ATTRIBUTE:
				participationAttributesScope.get.forParticipationClass((context as ParticipationAttribute).participationClass)
				
			case PARTICIPATION_CLASS__SUPER_METACLASS: {
				val participation = switch context {
					ParticipationClass: context.participation
					Participation: context
				}
				val globalScope = globalScopeProvider.getScope(context.eResource, reference, null)
				switch participation {
					TupleParticipation:
						new QualifiedNameTransformingScope(globalScope, [
							QualifiedName.create(#[participation.domainName] + segments)
						])
					default:
						globalScope
				}
			}
				
			default:
				globalScopeProvider.getScope(context.eResource, reference, null)
		}
	}
}
