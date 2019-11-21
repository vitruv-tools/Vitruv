package tools.vitruv.dsls.mappings.generator.conditions

import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import tools.vitruv.dsls.mappings.generator.conditions.impl.EqualsValueConditionGenerator
import tools.vitruv.dsls.mappings.generator.conditions.impl.InValueConditionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.CheckAndEnforceCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.ElementCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.EnforceableCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.FeatureCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.IndexCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MultiValueConditionOperator
import tools.vitruv.dsls.mappings.mappingsLanguage.NotEmptyCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.NumCompareCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.ResourceCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition

/**
 *  TODO: implement missing single-sided conditions and call fitting constructors in this factory
 */
class SingleSidedConditionFactory {

	static val Logger logger = Logger.getLogger(SingleSidedConditionFactory)

	static def List<AbstractSingleSidedCondition<?>> construct(List<SingleSidedCondition> conditions) {
		val generators = new ArrayList<AbstractSingleSidedCondition<?>>
		conditions.forEach [ condition |
			val generator = SingleSidedConditionFactory.construct(condition)
			if (generator === null) {
				logger.error('''No single sided condition generator found for condition «condition»''')
			} else {
				generators += generator
			}
		]
		generators
	}


	static def AbstractSingleSidedCondition<?> construct(SingleSidedCondition singleSidedCondition) {
		val condition = singleSidedCondition.condition
		switch (condition) {
			EnforceableCondition: return construct(singleSidedCondition, condition)
			CheckAndEnforceCondition: return null //TODO
		}
	}

	private static def construct(SingleSidedCondition singleSidedCondition, EnforceableCondition enforceableCondition) {
		val condition = enforceableCondition.condition
		switch (condition) {
			FeatureCondition: return construct(singleSidedCondition, condition)
			ResourceCondition: return null //TODO
		}
	}

	private static def construct(SingleSidedCondition singleSidedCondition, FeatureCondition featureCondition) {
		val condition = featureCondition.condition
		switch (condition) {
			IndexCondition:
				return null //TODO
			NumCompareCondition:
				return null //TODO
			MultiValueCondition:
				return singleSidedCondition.construct(condition)
			ElementCondition:
				return null //TODO
			NotEmptyCondition:
				return null //TODO
		}
	}

	private static def construct(SingleSidedCondition singleSidedCondition, MultiValueCondition multiValueCondition) {
		if (multiValueCondition.operator == MultiValueConditionOperator.EQUALS) {
			new EqualsValueConditionGenerator(multiValueCondition)
		} else {
			new InValueConditionGenerator(multiValueCondition)
		}
	}
}
