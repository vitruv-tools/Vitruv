package tools.vitruv.dsls.mappings.generator

import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.conditions.SingleSidedConditionFactory
import tools.vitruv.dsls.mappings.generator.conditions.impl.BidirectionalMappingRoutineGenerator
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.routines.MappingRoutinesGenerator
import tools.vitruv.dsls.mappings.generator.utils.ParameterCorrespondenceTagging
import tools.vitruv.dsls.mappings.mappingsLanguage.BidirectionalizableCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mappings.mappingsLanguage.ObserveChanges
import tools.vitruv.dsls.mappings.mappingsLanguage.RoutineIntegration
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition

import static tools.vitruv.dsls.mappings.generator.utils.ParameterCorrespondenceTagging.*
import tools.vitruv.dsls.mappings.generator.reactions.AttributeReplacedReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.reactions.ElementReplacedReactionTriggerGenerator
import tools.vitruv.dsls.mappings.generator.conditions.ReactionTriggerGeneratorFactory
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mappings.mappingsLanguage.ObserveChange

class DirectionalMappingReactionGenerator {

	private static val Logger logger = Logger.getLogger(DirectionalMappingReactionGenerator)

	private List<MappingParameter> fromParameters
	private List<MappingParameter> toParameters
	private Mapping mapping

	new(List<MappingParameter> fromParameters, List<MappingParameter> toParameters, Mapping mapping) {
		this.fromParameters = fromParameters
		this.toParameters = toParameters
		this.mapping = mapping
	}

	/**
	 * Generates reaction files from a single mapping for one direction
	 */
	def generate(MappingGeneratorContext context, List<SingleSidedCondition> fromConditions,
		List<SingleSidedCondition> toConditions, List<BidirectionalizableCondition> mappingConditions,
		List<RoutineIntegration> mappingRoutines, ObserveChanges observeChanges) {
		ParameterCorrespondenceTagging.context = context
		val mappingName = mapping.name
		// init reaction trigger generator factory
		val reactionTriggerGeneratorsFactory = new ReactionTriggerGeneratorFactory(mappingName, fromConditions)
		// init bidirectional condition generators
		val bidirectionalCondtionGenerators = generateBidirectionalMappingConditions(context, mappingConditions,
			mappingRoutines, fromParameters)
		// init single-sided condition generators (for both sides)
		val singlesidedConditionGenerators = SingleSidedConditionFactory.construct(fromConditions)
		val correspondingSinglesidedConditionGenerators = SingleSidedConditionFactory.construct(toConditions)
		// init reaction routines generator
		val routinesGenerator = new MappingRoutinesGenerator(fromParameters, toParameters)
		// create the reaction routines
		routinesGenerator.generateRoutines(mappingName, context, singlesidedConditionGenerators,
			correspondingSinglesidedConditionGenerators, bidirectionalCondtionGenerators)
		// create the reaction trigger generators
		val reactionTriggerGenerators = reactionTriggerGeneratorsFactory.constructGenerators(fromParameters,
			toParameters)
		reactionTriggerGenerators.appendBidirectionalMappingAttributeReactions(observeChanges)
		// create reaction triggers from the generators and connect them with the created routines
		reactionTriggerGenerators.forEach [ reactionTriggerGenerator |
			logger.info('''=> generate reaction trigger: «reactionTriggerGenerator.toString»''')
			val reactionTemplate = reactionTriggerGenerator.generateTrigger(context)
			context.getSegmentBuilder +=
				routinesGenerator.generateRoutineCall(reactionTemplate, reactionTriggerGenerator)
		]
	}

	private def List<AbstractBidirectionalCondition> generateBidirectionalMappingConditions(
		MappingGeneratorContext context, List<BidirectionalizableCondition> mappingConditions,
		List<RoutineIntegration> mappingRoutines, List<MappingParameter> parameters) {
		val conditions = new ArrayList<AbstractBidirectionalCondition>()
		/*  in the future: construct from actual bidirectional conditions
		 * mappingConditions.forEach [ mappingCondition |
		 * 	val leftFeature = mappingCondition.featureToBeAssigned
		 * 	val expression = mappingCondition.bidirectionalizableExpression
		 ]*/
		// right now: using routines as the implementation
		val routines = new ArrayList<BidirectionalMappingRoutineGenerator>()
		mappingRoutines.forEach [
			val routine = new BidirectionalMappingRoutineGenerator(parameters, it)
			if (routine.validRoutine) {
				routine.integrateRoutine(context)
				routines += routine
			}
		]
		conditions.addAll(routines)
		conditions
	}

	private def appendBidirectionalMappingAttributeReactions(List<AbstractReactionTriggerGenerator> generators,
		ObserveChanges observeChanges) {
		if (observeChanges !== null) {
			observeChanges.changes.forEach [
				// check if its a relevant metaclass for this direction
				if (feature.metaclass.metaclassApplicable) {
					val generator = createObserveChangeTrigger
					generator.init(mapping.name, fromParameters, toParameters)
					generators.add(generator)
				}
			]
		}
	}

	private def createObserveChangeTrigger(ObserveChange observeChange) {
		val featureReference = observeChange.feature
		val feature = featureReference.feature
		var AbstractReactionTriggerGenerator generator
		// attribute replaced trigger for attributes or element replaced for references
		if (feature instanceof EAttribute) {
			generator = new AttributeReplacedReactionTriggerGenerator(featureReference)
		} else if (feature instanceof EReference) {
			generator = new ElementReplacedReactionTriggerGenerator(featureReference)
		}
		// overwrite the default mapping scenario type of the created triggers
		generator.overwriteScenarioType(MappingScenarioType.UPDATE)
		generator.sourceObserveChange = observeChange
		generator
	}

	private def isMetaclassApplicable(EClass metaclass) {
		fromParameters.exists[it.value.metaclass == metaclass]
	}

}
