package tools.vitruv.dsls.mappings.generator

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.mappings.generator.action.SingleSidedConditionGenerator
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.conditions.MappingRoutineGenerator
import tools.vitruv.dsls.mappings.generator.conditions.ReactionTypeFactory
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AttributeReplacedReactionGenerator
import tools.vitruv.dsls.mappings.generator.utils.ParameterCorrespondenceTagging
import tools.vitruv.dsls.mappings.mappingsLanguage.BidirectionalizableCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.ObserveAttributes
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition

import static tools.vitruv.dsls.mappings.generator.utils.ParameterCorrespondenceTagging.*
import tools.vitruv.dsls.mappings.generator.action.ReactionRoutineContentGenerator

class DirectionalMappingReactionGenerator {

	private List<MappingParameter> fromParameters
	private List<MappingParameter> toParameters
	private Mapping mapping

	new(List<MappingParameter> fromParameters, List<MappingParameter> toParameters, Mapping mapping) {
		this.fromParameters = fromParameters
		this.toParameters = toParameters
		this.mapping = mapping
	}

	def generate(ReactionGeneratorContext context, List<SingleSidedCondition> fromConditions,
		List<BidirectionalizableCondition> mappingConditions, List<RoutineIntegration> mappingRoutines,
		ObserveAttributes mappingAttributes) {
		ParameterCorrespondenceTagging.context = context
		val reactionFactory = new ReactionTypeFactory(fromConditions)
		val bidirectionCondtionGenerators = generateBidirectionalMappingConditions(mappingConditions, mappingRoutines,
			fromParameters)
		val reactionGenerators = reactionFactory.constructGenerators(fromParameters, toParameters)
		reactionGenerators.appendBidirectionalMappingAttributeReactions(mappingAttributes)
		reactionGenerators.forEach [ reactionGenerator |
			reactionGenerator.mappingName = mapping.name
			println('''=> generate reaction: «reactionGenerator.toString»''')
			val reactionTemplate = reactionGenerator.generateTrigger(context)
			val singleSidedConditionGenerator = new SingleSidedConditionGenerator(reactionGenerator, fromConditions)
			val featureConditionsGenerator = singleSidedConditionGenerator.constructFeatureConditions
			val contentGenerator = new ReactionRoutineContentGenerator(reactionGenerator, bidirectionCondtionGenerators,
				featureConditionsGenerator, context)
			singleSidedConditionGenerator.contentGenerator = contentGenerator
			contentGenerator.generateSubRoutine
			context.getSegmentBuilder += reactionTemplate.call([
				alwaysRequireAffectedEObject
				if (reactionGenerator.usesNewValue) {
					alwaysRequireNewValue
				}
				contentGenerator.currentRoutine = retrieveRoutineBuilder
				match(singleSidedConditionGenerator, true)
				action(contentGenerator)
			])
		]
	}

	private def List<AbstractBidirectionalCondition> generateBidirectionalMappingConditions(
		List<BidirectionalizableCondition> mappingConditions, List<RoutineIntegration> mappingRoutines,
		List<MappingParameter> parameters) {
		val conditions = new ArrayList<AbstractBidirectionalCondition>()
		/* 
		 * mappingConditions.forEach [ mappingCondition |
		 * 	val leftFeature = mappingCondition.featureToBeAssigned
		 * 	val expression = mappingCondition.bidirectionalizableExpression
		 ]*/
		conditions.addAll(mappingRoutines.map[new MappingRoutineGenerator(it)].filter[it.isValidRoutine(parameters)])
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
						generator.init(fromParameters, toParameters)
						generators.add(generator)
					}
				}
			]

		}
	}

	private def isMetaclassApplicable(EClass metaclass) {
		fromParameters.exists[it.value.metaclass == metaclass]
	}

}
