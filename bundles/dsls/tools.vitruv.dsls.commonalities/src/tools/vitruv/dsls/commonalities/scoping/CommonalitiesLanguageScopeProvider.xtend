package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.Collections
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.scoping.IGlobalScopeProvider
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.SimpleScope
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeReference
import tools.vitruv.dsls.commonalities.language.OperatorReferenceMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationClassOperand
import tools.vitruv.dsls.commonalities.language.TupleParticipation
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

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
	@Inject Provider<CommonalityAttributesScope> commonalityAttributesScope
	@Inject IGlobalScopeProvider globalScopeProvider
	@Inject extension IQualifiedNameProvider qualifiedNameProvider
	@Inject extension IEObjectDescriptionProvider descriptionProvider

	// Context differs during content assist:
	// * If no input is provided yet, the container is the context as the element is not known yet
	// * If some input is already provided, the element is the context
	override getScope(EObject context, EReference reference) {
		switch reference {
			case PARTICIPATION_CLASS_OPERAND__PARTICIPATION_CLASS: {
				if (context instanceof ParticipationClassOperand) {
					if (context.isInParticipationConditionContext) {
						val participation = context.participation
						return participation.unqualifiedParticipationClassScope
					} else if (context.isInAttributeMappingContext) {
						val commonality = context.containingCommonality
						return commonality.participationClassScope
					} else {
						throw new IllegalStateException("Unexpected ParticipationClassOperand context")
					}
				}
			}
			case OPERATOR_REFERENCE_MAPPING__PARTICIPATION_CLASS: {
				if (context instanceof OperatorReferenceMapping) {
					val commonality = context.containingCommonality
					return commonality.participationClassScope
				}
			}
			case PARTICIPATION_ATTRIBUTE__PARTICIPATION_CLASS: {
				if (context instanceof ParticipationAttribute) {
					val participationCondition = context.optionalContainingParticipationCondition
					if (participationCondition !== null) {
						val participation = participationCondition.participation
						return participation.unqualifiedParticipationClassScope
					}

					val referenceMapping = context.optionalContainingOperatorReferenceMapping
					if (referenceMapping !== null) {
						val referencedAttributeOperand = context.optionalContainingReferencedParticipationAttributeOperand
						if (referencedAttributeOperand !== null) {
							val participation = referenceMapping.referencedParticipation
							return participation.unqualifiedParticipationClassScope
						}

						val attributeOperand = context.optionalContainingParticipationAttributeOperand
						if (attributeOperand !== null) {
							val participation = referenceMapping.participation
							return participation.unqualifiedParticipationClassScope
						}
						// Else: Qualified participation class.
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
			case COMMONALITY_ATTRIBUTE_REFERENCE__COMMONALITY: {
				if (context instanceof CommonalityAttributeReference) {
					// We currently only support unqualified commonality attribute references (which omit the concept
					// name) to the local commonality:
					val commonality = context.containingCommonality
					val conceptName = commonality.concept.name
					val commonalityScope = commonality.singleCommonalityScope
					return conceptName.getUnqualifiedCommonalityScope(commonalityScope)
				}
			}
			case COMMONALITY_ATTRIBUTE_REFERENCE__ATTRIBUTE: {
				if (context instanceof CommonalityAttributeReference) {
					val commonality = context.commonality
					return commonality.unqualifiedCommonalityAttributeScope
				}
			}
		}
		return globalScopeProvider.getScope(context.eResource, reference, null)
	}

	private def getParticipationClassScope(Commonality commonality) {
		return participationClassesScope.get.forCommonality(commonality)
	}

	private def getUnqualifiedParticipationClassScope(Participation participation) {
		val commonality = participation.containingCommonality
		val participationClassScope = commonality.participationClassScope
		val parentQualifiedName = participation.fullyQualifiedName
		return new PrefixedScope(participationClassScope, parentQualifiedName)
	}

	private def getUnqualifiedMetaclassScope(TupleParticipation participation, IScope metaclassScope) {
		val parentQualifiedName = participation.domainName.qualifiedDomainName
		return new PrefixedScope(metaclassScope, parentQualifiedName)
	}

	private def getUnqualifiedParticipationAttributeScope(ParticipationClass participationClass) {
		if (participationClass.eIsProxy) {
			// This may indicate an issue with the participation class scoping, but may also be the result of an
			// invalid/incomplete commonality file.
			return IScope.NULLSCOPE
		}
		val participationAttributeScope = participationAttributesScope.get.forParticipationClass(participationClass)
		val parentQualifiedName = participationClass.fullyQualifiedName
		return new PrefixedScope(participationAttributeScope, parentQualifiedName)
	}

	private def getSingleCommonalityScope(Commonality commonality) {
		return new SimpleScope(IScope.NULLSCOPE, Collections.singleton(
			commonality.describe()
		))
	}

	private def getUnqualifiedCommonalityScope(String conceptName, IScope qualifiedCommonalityScope) {
		val parentQualifiedName = conceptName.qualifiedDomainName
		return new PrefixedScope(qualifiedCommonalityScope, parentQualifiedName)
	}

	private def getUnqualifiedCommonalityAttributeScope(Commonality commonality) {
		if (commonality.eIsProxy) {
			// The commonality could not be resolved:
			return IScope.NULLSCOPE
		}
		val commonalityAttributeScope = commonalityAttributesScope.get.forCommonality(commonality)
		val parentQualifiedName = commonality.fullyQualifiedName
		return new PrefixedScope(commonalityAttributeScope, parentQualifiedName)
	}
}
