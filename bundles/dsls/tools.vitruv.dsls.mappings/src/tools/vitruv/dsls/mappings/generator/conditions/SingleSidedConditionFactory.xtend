package tools.vitruv.dsls.mappings.generator.conditions

import tools.vitruv.dsls.mappings.mappingsLanguage.CheckAndEnforceCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.EnforceableCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.FeatureCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.ResourceCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleValueCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.ElementCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.NotEmptyCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.IndexCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.NumCompareCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.mappings.generator.conditions.impl.EqualsValueConditionGenerator
import tools.vitruv.dsls.mappings.generator.conditions.impl.InValueConditionGenerator

class SingleSidedConditionFactory {

	static def AbstractSingleSidedCondition construct(SingleSidedCondition singleSidedCondition) {
		val condition = singleSidedCondition.condition
		if (condition instanceof EnforceableCondition) {
			return construct(singleSidedCondition, condition)
		} else if (condition instanceof CheckAndEnforceCondition) {
		}
	}

	private static def  construct(SingleSidedCondition singleSidedCondition,
		EnforceableCondition enforceableCondition) {
		val condition = enforceableCondition.condition
		if (condition instanceof FeatureCondition) {
			return construct(singleSidedCondition, condition)
		} else if (condition instanceof ResourceCondition) {
		}
	}

	private static def construct(SingleSidedCondition singleSidedCondition,
		FeatureCondition featureCondition) {
		val condition = featureCondition.condition
		switch (condition) {
			IndexCondition:
				return null
			NumCompareCondition:
				return null
			MultiValueCondition:
				return singleSidedCondition.construct(condition)
			ElementCondition:
				return null
			NotEmptyCondition:
				return null
		}
	}

	private static def construct(SingleSidedCondition singleSidedCondition,
		MultiValueCondition multiValueCondition) {
		if (multiValueCondition.operator == MultiValueConditionOperator.EQUALS) {
			new EqualsValueConditionGenerator(multiValueCondition)
		} else {
			new InValueConditionGenerator(multiValueCondition)
		}
	}
}
