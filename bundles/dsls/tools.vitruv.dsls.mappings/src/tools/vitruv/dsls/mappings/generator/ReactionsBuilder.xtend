package tools.vitruv.dsls.mappings.generator

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.trigger.ReactionTypeGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.BidirectionalizableCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.ReactionTrigger
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference
import tools.vitruv.dsls.mappings.generator.conditions.SingleSidedConditionGenerator
import tools.vitruv.dsls.mappings.generator.action.ReactionActionGenerator

class ReactionsBuilder {
	
	private EClass fromMetaClass
	private List<NamedMetaclassReference> fromAttributes
	private List<NamedMetaclassReference> toAttributes
	
	new(EClass fromMetaClass, List<NamedMetaclassReference> fromAttributes, List<NamedMetaclassReference> toAttributes) {
		this.fromAttributes = fromAttributes
		this.fromMetaClass = fromMetaClass
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
	
	private def List<ReactionTypeGenerator> constructReactionGenerators(List<ReactionTrigger> reactionTriggers){
		val generators = new ArrayList<ReactionTypeGenerator>()
		reactionTriggers.forEach[
			val metaclass = it.parameter.metaclass
			if(metaclass.equals(fromMetaClass)){		
				generators.add(new ReactionTypeGenerator(it))
			}
		]
		generators
	}
	
	def generate(ReactionGeneratorContext context, List<SingleSidedCondition> fromConditions, List<BidirectionalizableCondition> mappingConditions, List<ReactionTrigger> reactionTriggers) {
		val reactionGenerators = constructReactionGenerators(reactionTriggers)
		val singleSidedConditionGenerator = new SingleSidedConditionGenerator(fromConditions)
		val bidirectionCondtionGenerators = generateBidirectionalMappingConditions(mappingConditions)
		reactionGenerators.forEach[ typeGenerator |
			val reactionTemplate = typeGenerator.generate(context)
			val actionGenerator = new ReactionActionGenerator(typeGenerator, bidirectionCondtionGenerators)			
			context.getSegmentBuilder += reactionTemplate.call([
				//check single sided conditions
				match(singleSidedConditionGenerator)
				//do crud operations to corresponding element and enforce bidrectional conditions
				action(actionGenerator)
			])
		]
	}
	
}