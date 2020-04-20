package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.scoping.IGlobalScopeProvider
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationClassOperand
import tools.vitruv.dsls.commonalities.language.TupleParticipation

import static tools.vitruv.dsls.commonalities.language.LanguagePackage.Literals.*

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.names.QualifiedNameHelper.*

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
	@Inject extension IQualifiedNameProvider qualifiedNameProvider

	// Context differs during content assist:
	// * If no input is provided yet, the container is the context as the element is not known yet
	// * If some input is already provided, the element is the context
	override getScope(EObject context, EReference reference) {
		switch reference {
			case PARTICIPATION_CLASS_OPERAND__PARTICIPATION_CLASS: {
				if (context instanceof ParticipationClassOperand) {
					val participation = context.participation
					return participation.unqualifiedParticipationClassScope
				}
			}
			case PARTICIPATION_ATTRIBUTE__PARTICIPATION_CLASS: {
				if (context instanceof ParticipationAttribute) {
					val participationCondition = context.optionalContainingParticipationCondition
					if (participationCondition !== null) {
						val participation = participationCondition.participation
						return participation.unqualifiedParticipationClassScope
					}
					val commonality = context.containingCommonality
					return commonality.participationClassScope
				}
			}
			case PARTICIPATION_ATTRIBUTE__ATTRIBUTE: {
				if (context instanceof ParticipationAttribute) {
					val participationClass = context.participationClass
					return participationClass.unqualifiedParticipationAttributeScope
				}
			}
			case PARTICIPATION_CLASS__SUPER_METACLASS: {
				val participation = switch context {
					ParticipationClass: context.participation
					Participation: context
					default: null
				}
				if (participation !== null) {
					val globalScope = globalScopeProvider.getScope(context.eResource, reference, null)
					switch participation {
						TupleParticipation: {
							return participation.getUnqualifiedMetaclassScope(globalScope)
						}
						default:
							return globalScope
					}
				}
			}
		}
		return globalScopeProvider.getScope(context.eResource, reference, null)
	}

	def private getParticipationClassScope(Commonality commonality) {
		return participationClassesScope.get.forCommonality(commonality)
	}

	def private getUnqualifiedParticipationClassScope(Participation participation) {
		val commonality = participation.containingCommonality
		val participationClassScope = commonality.participationClassScope
		val parentQualifiedName = participation.fullyQualifiedName
		return new PrefixedScope(participationClassScope, parentQualifiedName)
	}

	def private getUnqualifiedMetaclassScope(TupleParticipation participation, IScope metaclassScope) {
		val parentQualifiedName = participation.domainName.qualifiedName
		return new PrefixedScope(metaclassScope, parentQualifiedName)
	}

	def private getUnqualifiedParticipationAttributeScope(ParticipationClass participationClass) {
		if (participationClass.eIsProxy) {
			throw new IllegalStateException("ParticipationClass is a proxy. This might indicate an issue with the"
				+ " participation class scoping.")
			// Note: This can also be reached as result of an invalid/incomplete commonality file.
		}
		val participationAttributeScope = participationAttributesScope.get.forParticipationClass(participationClass)
		val parentQualifiedName = participationClass.fullyQualifiedName
		return new PrefixedScope(participationAttributeScope, parentQualifiedName)
	}
}
