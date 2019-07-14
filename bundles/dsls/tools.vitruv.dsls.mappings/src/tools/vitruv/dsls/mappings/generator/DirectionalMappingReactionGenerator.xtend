package tools.vitruv.dsls.mappings.generator

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.mappings.generator.action.ParameterCorrespondenceTagging
import tools.vitruv.dsls.mappings.generator.action.ReactionActionGenerator
import tools.vitruv.dsls.mappings.generator.action.ReactionMatchGenerator
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.conditions.MappingRoutineGenerator
import tools.vitruv.dsls.mappings.generator.conditions.ReactionTypeFactory
import tools.vitruv.dsls.mappings.generator.conditions.SingleSidedConditionGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AttributeReplacedReactionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.BidirectionalizableCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.ObserveAttributes
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference

import static tools.vitruv.dsls.mappings.generator.action.ParameterCorrespondenceTagging.*
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping

class DirectionalMappingReactionGenerator {

	private List<NamedMetaclassReference> fromParameters
	private List<NamedMetaclassReference> toParameters
	private Mapping mapping

	new(List<NamedMetaclassReference> fromParameters, List<NamedMetaclassReference> toParameters, Mapping mapping) {
		this.fromParameters = fromParameters
		this.toParameters = toParameters
		this.mapping = mapping
	}

	def generate(ReactionGeneratorContext context, List<SingleSidedCondition> fromConditions,
		List<BidirectionalizableCondition> mappingConditions, List<RoutineIntegration> mappingRoutines,
		ObserveAttributes mappingAttributes) {
		ParameterCorrespondenceTagging.context = context
		val reactionFactory = new ReactionTypeFactory(fromConditions)
		val bidirectionCondtionGenerators = generateBidirectionalMappingConditions(mappingConditions, mappingRoutines)
		val reactionGenerators = reactionFactory.constructGenerators(fromParameters, toParameters)
		reactionGenerators.appendBidirectionalMappingAttributeReactions(mappingAttributes)
		reactionGenerators.forEach [ reactionGenerator |
			reactionGenerator.mappingName = mapping.name
			println('''=> generate reaction: «reactionGenerator.toString»''')
			val singleSidedConditionGenerator = new SingleSidedConditionGenerator(reactionGenerator, fromConditions)
			val reactionTemplate = reactionGenerator.generateTrigger(context)
			val actionGenerator = new ReactionActionGenerator(reactionGenerator, bidirectionCondtionGenerators, context)
			val matchGenerator = new ReactionMatchGenerator(reactionGenerator, singleSidedConditionGenerator)
			context.getSegmentBuilder += reactionTemplate.call([
				alwaysRequireAffectedEObject
				match(matchGenerator, true)
				action(actionGenerator)
			])
		]
	}

	private def List<AbstractBidirectionalCondition> generateBidirectionalMappingConditions(
		List<BidirectionalizableCondition> mappingConditions, List<RoutineIntegration> mappingRoutines) {
		val conditions = new ArrayList<AbstractBidirectionalCondition>()
		/* 
		 * mappingConditions.forEach [ mappingCondition |
		 * 	val leftFeature = mappingCondition.featureToBeAssigned
		 * 	val expression = mappingCondition.bidirectionalizableExpression
		 ]*/
		conditions.addAll(mappingRoutines.map[new MappingRoutineGenerator(it)])
		conditions
	}

	private def appendBidirectionalMappingAttributeReactions(List<AbstractReactionTypeGenerator> generators,
		ObserveAttributes mappingAttributes) {
		if (mappingAttributes !== null) {
			mappingAttributes.attributes.forEach [
				// check if its a relevant metaclass for this direction
				if (metaclass.metaclassApplicable) {
					val generator = new AttributeReplacedReactionGenerator(it)
					if (!generators.contains(generator)) {
						generators.add(generator)
					}
				}
			]

		}
	}

	private def isMetaclassApplicable(EClass metaclass) {
		fromParameters.exists[it.metaclass == metaclass]
	}

}
