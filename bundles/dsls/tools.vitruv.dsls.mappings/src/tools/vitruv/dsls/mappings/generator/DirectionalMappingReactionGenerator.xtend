package tools.vitruv.dsls.mappings.generator

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.conditions.ReactionTypeFactory
import tools.vitruv.dsls.mappings.generator.conditions.SingleSidedConditionFactory
import tools.vitruv.dsls.mappings.generator.conditions.impl.MappingRoutineGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AttributeReplacedReactionGenerator
import tools.vitruv.dsls.mappings.generator.routines.MappingRoutinesGenerator
import tools.vitruv.dsls.mappings.generator.utils.ParameterCorrespondenceTagging
import tools.vitruv.dsls.mappings.mappingsLanguage.BidirectionalizableCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.ObserveAttributes
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition

import static tools.vitruv.dsls.mappings.generator.utils.ParameterCorrespondenceTagging.*

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
		val mappingName = mapping.name
		val reactionFactory = new ReactionTypeFactory(mappingName, fromConditions)
		val bidirectionalCondtionGenerators = generateBidirectionalMappingConditions(context, mappingConditions,
			mappingRoutines, fromParameters)
		val singlesidedConditionGenerators = SingleSidedConditionFactory.construct(fromConditions)
		val reactionGenerators = reactionFactory.constructGenerators(fromParameters, toParameters)
		reactionGenerators.appendBidirectionalMappingAttributeReactions(mappingAttributes)
		val routinesGenerator = new MappingRoutinesGenerator(fromParameters, toParameters)
		routinesGenerator.generateRoutines(mappingName, context, singlesidedConditionGenerators,
			bidirectionalCondtionGenerators)
		reactionGenerators.forEach [ reactionGenerator |
			println('''=> generate reaction: «reactionGenerator.toString»''')
			val reactionTemplate = reactionGenerator.generateTrigger(context)
			context.getSegmentBuilder += reactionTemplate.call(
				routinesGenerator.generateRoutineCall(reactionGenerator)
			)
		]
	}

	private def List<AbstractBidirectionalCondition> generateBidirectionalMappingConditions(
		ReactionGeneratorContext context, List<BidirectionalizableCondition> mappingConditions,
		List<RoutineIntegration> mappingRoutines, List<MappingParameter> parameters) {
		val conditions = new ArrayList<AbstractBidirectionalCondition>()
		/*  in the future: construct from actual bidirectional conditions
		 * mappingConditions.forEach [ mappingCondition |
		 * 	val leftFeature = mappingCondition.featureToBeAssigned
		 * 	val expression = mappingCondition.bidirectionalizableExpression
		 ]*/
		// right now: using routines as the implementation
		val routines = new ArrayList<MappingRoutineGenerator>()
		mappingRoutines.forEach [
			val routine = new MappingRoutineGenerator(parameters, it)
			if (routine.validRoutine) {
				routine.integrateRoutine(context)
				routines += routine
			}
		]
		conditions.addAll(routines)
		conditions
	}

	private def appendBidirectionalMappingAttributeReactions(List<AbstractReactionTriggerGenerator> generators,
		ObserveAttributes mappingAttributes) {
		if (mappingAttributes !== null) {
			mappingAttributes.attributes.forEach [
				// check if its a relevant metaclass for this direction
				if (metaclass.metaclassApplicable) {
					val generator = new AttributeReplacedReactionGenerator(it)
					generator.derivedFromBidirectionalCondition = true
					generator.init(mapping.name, fromParameters, toParameters)
					generators.add(generator)
				}
			]

		}
	}

	private def isMetaclassApplicable(EClass metaclass) {
		fromParameters.exists[it.value.metaclass == metaclass]
	}

}
