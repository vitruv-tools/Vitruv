package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import com.google.inject.Provider
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
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

import static tools.vitruv.dsls.commonalities.language.LanguagePackage.Literals.*

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.names.QualifiedNameHelper.*
import java.util.List
import tools.vitruv.dsls.commonalities.language.ParticipationCondition
import tools.vitruv.dsls.commonalities.language.ReferencedParticipationAttributeOperand
import tools.vitruv.dsls.commonalities.language.ParticipationAttributeOperand

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class CommonalitiesLanguageScopeProvider extends AbstractCommonalitiesLanguageScopeProvider {

	@Inject ParticipationClassesScope.Factory createParticipationClassesScope
	@Inject Provider<ParticipationAttributesScope> participationAttributesScope
	@Inject CommonalityAttributesScope.Factory createCommonalityAttributesScope
	@Inject ParticipationRelationOperatorScopeProvider relationOperatorScopeProvider
	@Inject ParticipationConditionOperatorScopeProvider conditionOperatorScopeProvider
	@Inject AttributeMappingOperatorScopeProvider attributeMappingOperatorScopeProvider
	@Inject ReferenceMappingOperatorScopeProvider referenceMappingOperatorScopeProvider
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
						return createUnqualifiedParticipationClassScope(context.participation)
					} else if (context.isInAttributeMappingContext) {
						return createParticipationClassesScope.forCommonality(context.getEContainer(Commonality))
					} else {
						throw new IllegalStateException("Unexpected ParticipationClassOperand context")
					}
				}
			}
			case OPERATOR_REFERENCE_MAPPING__PARTICIPATION_CLASS: {
				if (context instanceof OperatorReferenceMapping) {
					return createParticipationClassesScope.forCommonality(context.getEContainer(Commonality))
				}
			}
			case PARTICIPATION_ATTRIBUTE__PARTICIPATION_CLASS: {
				if (context instanceof ParticipationAttribute) {
					val participationCondition = context.getOptionalEContainer(ParticipationCondition)
					if (participationCondition !== null) {
						return createUnqualifiedParticipationClassScope(participationCondition.participation)
					}

					val referenceMapping = context.getOptionalEContainer(OperatorReferenceMapping)
					if (referenceMapping !== null) {
						if (context.hasEContainer(ReferencedParticipationAttributeOperand)) {
							return createUnqualifiedParticipationClassScope(referenceMapping.referencedParticipation)
						} else if (context.hasEContainer(ParticipationAttributeOperand)) {
							return createUnqualifiedParticipationClassScope(referenceMapping.participation)
						}
					// Else: Qualified participation class.
					}

					return createParticipationClassesScope.forCommonality(context.getEContainer(Commonality))
				}
			}
			case PARTICIPATION_ATTRIBUTE__ATTRIBUTE: {
				if (context instanceof ParticipationAttribute) {
					return createUnqualifiedParticipationAttributeScope(context.participationClass)
				}
			}
			case PARTICIPATION_CLASS__SUPER_METACLASS: {
				if (context instanceof ParticipationClass) {
					val participation = context.participation
					val domainName = participation.domainName.qualifiedDomainName
					val globalScope = globalScopeProvider.getScope(context.eResource, reference, null)
					return new PrefixedScope(globalScope, domainName)
				}
			}
			case COMMONALITY_ATTRIBUTE_REFERENCE__COMMONALITY: {
				if (context instanceof CommonalityAttributeReference) {
					// We currently only support unqualified commonality attribute references (which omit the concept
					// name) to the local commonality:
					val commonality = context.getEContainer(Commonality )
					val conceptName = commonality.concept.name
					val commonalityScope = createSingleCommonalityScope(commonality)
					return createUnqualifiedCommonalityScope(conceptName, commonalityScope)
				}
			}
			case COMMONALITY_ATTRIBUTE_REFERENCE__ATTRIBUTE: {
				if (context instanceof CommonalityAttributeReference) {
					return createUnqualifiedCommonalityAttributeScope(context.commonality)
				}
			}
			
			case OPERATOR_ATTRIBUTE_MAPPING__OPERATOR:
				return attributeMappingOperatorScopeProvider.getScope(context, reference)
				
			case OPERATOR_REFERENCE_MAPPING__OPERATOR:
				return referenceMappingOperatorScopeProvider.getScope(context, reference)
				
			case PARTICIPATION_RELATION__OPERATOR:
				return relationOperatorScopeProvider.getScope(context, reference)
				
			case PARTICIPATION_CONDITION__OPERATOR:
				return conditionOperatorScopeProvider.getScope(context, reference)
		}
		
		return globalScopeProvider.getScope(context.eResource, reference, null)
	}

	private def createUnqualifiedParticipationClassScope(Participation participation) {
		if (participation === null) {
			// sometimes we don’t know the target Participation yet
			return IScope.NULLSCOPE
		}
		val participationClassScope = createParticipationClassesScope.forCommonality(participation.declaringCommonality)
		val parentQualifiedName = participation.fullyQualifiedName
		
		return if (parentQualifiedName !== null) {
			new PrefixedScope(participationClassScope, parentQualifiedName)
		} else {
			participationClassScope
		}
	}

	private def createUnqualifiedParticipationAttributeScope(ParticipationClass participationClass) {
		if (participationClass.eIsProxy) {
			// This may indicate an issue with the participation class scoping, but may also be the result of an
			// invalid/incomplete commonality file.
			return IScope.NULLSCOPE
		}
		val participationAttributeScope = participationAttributesScope.get.forParticipationClass(participationClass)
		val parentQualifiedName = participationClass.fullyQualifiedName
		return new PrefixedScope(participationAttributeScope, parentQualifiedName)
	}

	private def createSingleCommonalityScope(Commonality commonality) {
		new SimpleScope(IScope.NULLSCOPE, List.of(commonality.describe()))
	}

	private def createUnqualifiedCommonalityScope(String conceptName, IScope qualifiedCommonalityScope) {
		new PrefixedScope(qualifiedCommonalityScope, conceptName.qualifiedDomainName)
	}

	private def createUnqualifiedCommonalityAttributeScope(Commonality commonality) {
		if (commonality.eIsProxy) {
			// The commonality could not be resolved:
			return IScope.NULLSCOPE
		}
		val commonalityAttributeScope = createCommonalityAttributesScope.forCommonality(commonality)
		val parentQualifiedName = commonality.fullyQualifiedName
		return new PrefixedScope(commonalityAttributeScope, parentQualifiedName)
	}
}
