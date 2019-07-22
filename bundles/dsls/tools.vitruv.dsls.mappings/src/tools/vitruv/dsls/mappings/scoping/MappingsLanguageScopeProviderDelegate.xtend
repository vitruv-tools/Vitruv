package tools.vitruv.dsls.mappings.scoping

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import tools.vitruv.dsls.mappings.mappingsLanguage.BidirectionalizableCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.BidirectionalizableExpression
import tools.vitruv.dsls.mappings.mappingsLanguage.EnforceableCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.FeatureCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsLanguagePackage.Literals
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.mappings.mappingsLanguage.NumCompareCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.ObserveAttributes
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference
import tools.vitruv.dsls.mirbase.scoping.MirBaseScopeProviderDelegate

import static tools.vitruv.dsls.mirbase.mirBase.MirBasePackage.Literals.*
import tools.vitruv.dsls.mappings.mappingsLanguage.FeatureConditionParameter
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference

class MappingsLanguageScopeProviderDelegate extends MirBaseScopeProviderDelegate {

	override getScope(EObject context, EReference reference) {

		// context differs during content assist: 
		// * if no input is provided yet, the container is the context as the element is not known yet
		// * if some input is already provided, the element is the context
		val contextContainer = context.eContainer();
		if (reference.equals(METACLASS_FEATURE_REFERENCE__FEATURE))
			return createEStructuralFeatureScope(context as MetaclassFeatureReference)
		else if (reference.equals(Literals.FEATURE_CONDITION_PARAMETER__PARAMETER)
			 ||			reference.equals(Literals.MAPPING_PARAMETER_REFERENCE__PARAMETER)
			) {
			val enforcableCondition = contextContainer.eContainer as EnforceableCondition;
			val singleSidedCondition = enforcableCondition.eContainer as SingleSidedCondition;
			val mapping = singleSidedCondition.eContainer as Mapping;
			if (mapping.leftConditions.contains(singleSidedCondition)) {
				// left side
				return createMappingParameterMetaclassesScope(mapping, true, false);
			} else {
				// right side			
				return createMappingParameterMetaclassesScope(mapping, false, true);
			}
		} else if (reference.equals(Literals.FEATURE_CONDITION_PARAMETER__FEATURE)) {
			val featureConditionParameter = context as FeatureConditionParameter
			return createEStructuralFeatureScope(featureConditionParameter.parameter.value)
		} else if (reference.equals(METACLASS_REFERENCE__METACLASS)) {
			if (contextContainer instanceof FeatureCondition) {
				val enforcableCondition = contextContainer.eContainer as EnforceableCondition;
				val singleSidedCondition = enforcableCondition.eContainer as SingleSidedCondition;
				val mapping = singleSidedCondition.eContainer as Mapping;
				if (mapping.leftConditions.contains(singleSidedCondition)) {
					// left side
					return createMappingNamedMetaclassesScope(mapping, true, false);
				} else {
					// right side			
					return createMappingNamedMetaclassesScope(mapping, false, true);
				}
			} else if (contextContainer instanceof BidirectionalizableCondition) {
				val mapping = contextContainer.eContainer as Mapping;
				// both sides
				return createMappingNamedMetaclassesScope(mapping, true, true);
			} else if (contextContainer instanceof BidirectionalizableExpression) {
				val bidirectionalizableCondition = contextContainer.eContainer;
				val mapping = bidirectionalizableCondition.eContainer as Mapping;
				// both sides
				return createMappingNamedMetaclassesScope(mapping, true, true);
			} else if (contextContainer instanceof ObserveAttributes) { // for observe attributes
			// both sides 
				val mapping = contextContainer.eContainer as Mapping;
				return createMappingMetaclassesScope(mapping, true, true);
			}
		}
		super.getScope(context, reference)
	}

	def createMappingParameterMetaclassesScope(Mapping mapping, boolean leftSide, boolean rightSide) {
		val featureList = new ArrayList();
		if (leftSide) {
			featureList.addAll(mapping.leftParameters);
		}
		if (rightSide) {
			featureList.addAll(mapping.rightParameters);
		}
		return createScope(IScope.NULLSCOPE, featureList.iterator, [
			EObjectDescription.create(it.value.name, it)
		]);
	}

	def createMappingNamedMetaclassesScope(Mapping mapping, boolean leftSide, boolean rightSide) {
		val featureList = new ArrayList();
		if (leftSide) {
			featureList.addAll(mapping.leftParameters.map[it.value]);
		}
		if (rightSide) {
			featureList.addAll(mapping.rightParameters.map[it.value]);
		}
		return createScope(IScope.NULLSCOPE, featureList.iterator, [
			EObjectDescription.create(it.name, it.metaclass)
		]);
	}

	def createMappingMetaclassesScope(Mapping mapping, boolean leftSide, boolean rightSide) {
		val featureList = new ArrayList();
		if (leftSide) {
			featureList.addAll(mapping.leftParameters.map[value].uniqueMetaclasses);
		}
		if (rightSide) {
			featureList.addAll(mapping.rightParameters.map[value].uniqueMetaclasses);
		}
		return createScope(IScope.NULLSCOPE, featureList.iterator, [
			EObjectDescription.create(it.metaclass.name, it.metaclass)
		]);
	}

	private def uniqueMetaclasses(List<NamedMetaclassReference> references) {
		val unique = new ArrayList<NamedMetaclassReference>
		references.forEach [ ref |
			if (!unique.exists[it.metaclass == ref.metaclass]) {
				unique.add(ref)
			}
		]
		unique
	}

	private enum ScopeManyFeature {
		MANY,
		SINGLE,
		BOTH
		;
	}

	def ScopeManyFeature getManyScopeType(FeatureCondition featureCondition) {
		val condition = featureCondition.condition;
		if (condition instanceof NumCompareCondition) {
			return ScopeManyFeature.SINGLE;
		} else if (condition instanceof MultiValueCondition) {
			if (condition.operator == MultiValueConditionOperator.EQUALS) {
				return ScopeManyFeature.BOTH;
			}
		}
		return ScopeManyFeature.MANY;
	}

	def createEStructuralFeatureScope(MetaclassReference featureReference) {
		if (featureReference?.metaclass !== null) {
			val container = featureReference.eContainer;
			var multiplicityFilterFunction = [EStructuralFeature feat|true];
			/*	if (container instanceof FeatureCondition) {
			 * 		val scopeManyType = getManyScopeType(container);
			 * 		if (scopeManyType != ScopeManyFeature.BOTH) {
			 * 			multiplicityFilterFunction = [ EStructuralFeature feat |
			 * 				if (scopeManyType == ScopeManyFeature.MANY) {
			 * 					return feat.many
			 * 				} else {
			 * 					return !feat.many
			 * 				}
			 * 			];
			 * 		}
			 } */
			createScope(IScope.NULLSCOPE,
				featureReference.metaclass.EAllStructuralFeatures.filter(multiplicityFilterFunction).iterator, [
					EObjectDescription.create(it.name, it)
				])
		} else {
			return IScope.NULLSCOPE
		}
	}
}
