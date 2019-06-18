package tools.vitruv.dsls.mappings.generator

import java.util.ArrayList
import java.util.List
import tools.vitruv.dsls.mappings.generator.action.ReactionActionGenerator
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.conditions.SingleSidedConditionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.BidirectionalizableCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference
import tools.vitruv.dsls.mappings.generator.conditions.ReactionTypeFactory

class ReactionsBuilder {
	
	private List<NamedMetaclassReference> fromAttributes
	private List<NamedMetaclassReference> toAttributes
	
	new(List<NamedMetaclassReference> fromAttributes, List<NamedMetaclassReference> toAttributes) {
		this.fromAttributes = fromAttributes
		this.toAttributes = toAttributes
	}
	
	private def List<AbstractBidirectionalCondition> generateBidirectionalMappingConditions(List<BidirectionalizableCondition> mappingConditions){
		val conditions = new ArrayList<AbstractBidirectionalCondition>()
		mappingConditions.forEach[mappingCondition | 
			val leftFeature = mappingCondition.featureToBeAssigned
			val expression = mappingCondition.bidirectionalizableExpression
		]
		conditions
	}

	
	def generate(ReactionGeneratorContext context, List<SingleSidedCondition> fromConditions, List<BidirectionalizableCondition> mappingConditions) {
		val reactionFactory = new ReactionTypeFactory(fromConditions)
		val bidirectionCondtionGenerators = generateBidirectionalMappingConditions(mappingConditions)
		reactionFactory.constructGenerators(fromAttributes).forEach[reactionGenerator | 
			val singleSidedConditionGenerator = new SingleSidedConditionGenerator(reactionGenerator, fromConditions)
			val fromList = fromAttributes.filter[metaclass == reactionGenerator.metaclass].toList
			val reactionTemplate = reactionGenerator.generateTrigger(context)
			val actionGenerator = new ReactionActionGenerator(reactionGenerator, bidirectionCondtionGenerators)			
			context.getSegmentBuilder += reactionTemplate.call([
				//check single sided conditions
				match(singleSidedConditionGenerator)
				//do crud operations to corresponding element and enforce bidrectional conditions
				action(actionGenerator)
			])
		]
	}
	
}