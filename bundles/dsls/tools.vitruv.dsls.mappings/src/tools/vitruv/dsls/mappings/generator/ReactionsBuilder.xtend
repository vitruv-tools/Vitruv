package tools.vitruv.dsls.mappings.generator

import java.util.ArrayList
import java.util.List
import tools.vitruv.dsls.mappings.generator.action.ParameterCorrespondenceTagging
import tools.vitruv.dsls.mappings.generator.action.ReactionActionGenerator
import tools.vitruv.dsls.mappings.generator.action.ReactionMatchGenerator
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.conditions.ReactionTypeFactory
import tools.vitruv.dsls.mappings.generator.conditions.SingleSidedConditionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.BidirectionalizableCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference

import static tools.vitruv.dsls.mappings.generator.action.ParameterCorrespondenceTagging.*
import tools.vitruv.dsls.mappings.generator.conditions.MappingRoutineGenerator

class ReactionsBuilder {

	private List<NamedMetaclassReference> fromParameters
	private List<NamedMetaclassReference> toParameters

	new(List<NamedMetaclassReference> fromParameters, List<NamedMetaclassReference> toParameters) {
		this.fromParameters = fromParameters
		this.toParameters = toParameters
	}

	private def List<AbstractBidirectionalCondition> generateBidirectionalMappingConditions(
		List<BidirectionalizableCondition> mappingConditions, List<RoutineIntegration> mappingRoutines) {
		val conditions = new ArrayList<AbstractBidirectionalCondition>()
		/* 
		mappingConditions.forEach [ mappingCondition |
			val leftFeature = mappingCondition.featureToBeAssigned
			val expression = mappingCondition.bidirectionalizableExpression
		]*/
		conditions.addAll(mappingRoutines.map[new MappingRoutineGenerator(it)])
		conditions
	}

	def generate(ReactionGeneratorContext context, List<SingleSidedCondition> fromConditions,
		List<BidirectionalizableCondition> mappingConditions, List<RoutineIntegration> mappingRoutines) {
		ParameterCorrespondenceTagging.context = context
		val reactionFactory = new ReactionTypeFactory(fromConditions)
		val bidirectionCondtionGenerators = generateBidirectionalMappingConditions(mappingConditions, mappingRoutines)
		reactionFactory.constructGenerators(fromParameters, toParameters).forEach [ reactionGenerator |
			val singleSidedConditionGenerator = new SingleSidedConditionGenerator(reactionGenerator, fromConditions)
			val reactionTemplate = reactionGenerator.generateTrigger(context)
			val actionGenerator = new ReactionActionGenerator(reactionGenerator, bidirectionCondtionGenerators, context)
			val matchGenerator = new ReactionMatchGenerator(reactionGenerator, singleSidedConditionGenerator)
			context.getSegmentBuilder += reactionTemplate.call([
				match(matchGenerator)
				action(actionGenerator)
			])
		]
	}

}
